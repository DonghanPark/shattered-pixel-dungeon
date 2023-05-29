/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2023 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.shatteredpixel.shatteredpixeldungeon.actors.blobs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.effects.BlobEmitter;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.watabou.utils.Bundle;
import com.watabou.utils.Rect;
import com.watabou.utils.Reflection;

public class Blob extends Actor {

	{
		actPriority = BLOB_PRIO;
	}
	
	public int volume = 0;
	
	public int[] cur;
	protected int[] off;
	
	public BlobEmitter emitter;

	public Rect area = new Rect();
	
	public boolean alwaysVisible = false;

	private static final String CUR		= "cur";
	private static final String START	= "start";
	private static final String LENGTH	= "length";

	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle( bundle );

		if (volume > 0) {
			int start = findStartIndex();
			int end = findEndIndex(start);

			bundle.put( START, start );
			bundle.put( LENGTH, cur.length );
			bundle.put( CUR, trim( start, end + 1 ) );

		}
	}

	private int findStartIndex() {
		for (int start=0; start < Dungeon.level.length(); start++) {
			if (cur[start] > 0) {
				return start;
			}
		}
		return 0;
	}

	private int findEndIndex(int start) {
		for (int end=Dungeon.level.length()-1; end > start; end--) {
			if (cur[end] > 0) {
				return end;
			}
		}
		return 0;
	}

	private int[] trim( int start, int end ) {
		int len = end - start;
		int[] copy = new int[len];
		System.arraycopy( cur, start, copy, 0, len );
		return copy;
	}

	@Override
	public void restoreFromBundle( Bundle bundle ) {

		super.restoreFromBundle( bundle );

		if (bundle.contains( CUR )) {
			int length = bundle.getInt(LENGTH);
			cur = new int[length];
			off = new int[cur.length];

			int[] data = bundle.getIntArray(CUR);
			int start = bundle.getInt(START);
			for (int i = 0; i < data.length; i++) {
				int index = i + start;
				cur[index] = data[i];
				volume += data[i];
			}

		}
	}

	@Override
	public boolean act() {
		spend(TICK);

		if (volume > 0) {
			if (area.isEmpty())
				setupArea();

			volume = 0;

			evolve();
			swapArrays();
		} else {
			clearArea();
		}

		return true;
	}

	private void swapArrays() {
		int[] tmp = off;
		off = cur;
		cur = tmp;
	}

	private void clearArea() {
		if (!area.isEmpty()) {
			area.setEmpty();
			System.arraycopy(cur, 0, off, 0, cur.length);
		}
	}

	public void setupArea() {
		for (int cell = 0; cell < cur.length; cell++) {
			if (cur[cell] != 0) {
				int x = cell % Dungeon.level.width();
				int y = cell / Dungeon.level.width();
				area.union(x, y);
			}
		}
	}

	public void use(BlobEmitter emitter) {
		this.emitter = emitter;
	}

	
	protected void evolve() {
		
		boolean[] blocking = Dungeon.level.solid;
		int cell;
		int levelWidth = Dungeon.level.width();

		for (int i=area.top-1; i <= area.bottom; i++) {
			for (int j = area.left-1; j <= area.right; j++) {
				cell = j + i*levelWidth;
				if (Dungeon.level.insideMap(cell)) {
					if (!blocking[cell]) {

						int count = 1;
						int sum = cur[cell];

						boolean isLeftNeighborUnblocked = j > area.left && !blocking[cell - 1];
						boolean isRightNeighborUnblocked = j < area.right && !blocking[cell + 1];
						boolean isTopNeighborUnblocked = i > area.top && !blocking[cell - levelWidth];
						boolean isBottomNeighborUnblocked = i < area.bottom && !blocking[cell + levelWidth];

						if (isLeftNeighborUnblocked) {
							sum += cur[cell-1];
							count++;
						}
						if (isRightNeighborUnblocked) {
							sum += cur[cell+1];
							count++;
						}
						if (isTopNeighborUnblocked) {
							sum += cur[cell-levelWidth];
							count++;
						}
						if (isBottomNeighborUnblocked) {
							sum += cur[cell+levelWidth];
							count++;
						}

						int value = sum >= count ? (sum / count) - 1 : 0;
						off[cell] = value;

						if (value > 0){
							if (i < area.top)
								area.top = i;
							else if (i >= area.bottom)
								area.bottom = i+1;
							if (j < area.left)
								area.left = j;
							else if (j >= area.right)
								area.right = j+1;
						}

						volume += value;
					} else {
						off[cell] = 0;
					}
				}
			}
		}
	}

	public void seed( Level level, int cell, int amount ) {
		if (cur == null) cur = new int[level.length()];
		if (off == null) off = new int[cur.length];

		cur[cell] += amount;
		volume += amount;

		area.union(cell%level.width(), cell/level.width());
	}
	
	public void clear( int cell ) {
		if (volume == 0) return;
		volume -= cur[cell];
		cur[cell] = 0;
	}

	public void fullyClear(){
		volume = 0;
		area.setEmpty();
		cur = new int[Dungeon.level.length()];
		off = new int[Dungeon.level.length()];
	}

	public void onBuildFlagMaps( Level l ){
		//do nothing by default, only some blobs affect flags
	}
	
	public String tileDesc() {
		return null;
	}
	
	public static<T extends Blob> T seed( int cell, int amount, Class<T> type ) {
		return seed(cell, amount, type, Dungeon.level);
	}
	
	@SuppressWarnings("unchecked")
	public static<T extends Blob> T seed( int cell, int amount, Class<T> type, Level level ) {
		
		T gas = (T)level.blobs.get( type );
		
		if (gas == null) {
			gas = Reflection.newInstance(type);
			//this ensures that gasses do not get an 'extra turn' if they are added by a mob or buff
			if (Actor.curActorPriority() < gas.actPriority) {
				gas.spend(1f);
			}
		}
		
		if (gas != null){
			level.blobs.put( type, gas );
			gas.seed( level, cell, amount );
		}
		
		return gas;
	}

	public static int volumeAt( int cell, Class<? extends Blob> type){
		Blob gas = Dungeon.level.blobs.get( type );
		boolean GasEmpty = gas == null || gas.volume == 0;

		if (GasEmpty) {
			return 0;
		} else {
			return gas.cur[cell];
		}
	}
}
