package com.shatteredpixel.shatteredpixeldungeon.levels.traps;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public interface TrapActivate {
    static void startAlarmDevice(int pos, Trap trap) {
        for (Mob mob : Dungeon.level.mobs) {
            mob.beckon(pos);
        }

        if (Dungeon.level.heroFOV[pos]) {
            GLog.w( Messages.get(trap, "alarm") );
            CellEmitter.center(pos).start( Speck.factory( Speck.SCREAM ), 0.3f, 3 );
        }

        Sample.INSTANCE.play( Assets.Sounds.ALERT );
    }

    static ArrayList<Integer> getMobsRespawnPoints(int nMobs, int pos) {
        if (Random.Int( 2 ) == 0) {
            nMobs++;
            if (Random.Int( 2 ) == 0) {
                nMobs++;
            }
        }

        ArrayList<Integer> candidates = new ArrayList<>();

        for (int i = 0; i < PathFinder.NEIGHBOURS8.length; i++) {
            int p = pos + PathFinder.NEIGHBOURS8[i];
            if (Actor.findChar( p ) == null && (Dungeon.level.passable[p] || Dungeon.level.avoid[p])) {
                candidates.add( p );
            }
        }

        ArrayList<Integer> respawnPoints = new ArrayList<>();

        while (nMobs > 0 && candidates.size() > 0) {
            int index = Random.index( candidates );

            respawnPoints.add( candidates.remove( index ) );
            nMobs--;
        }
        return respawnPoints;
    }

    static Char findTheClosestChar(int pos) {
        Char target = Actor.findChar(pos);

        //find the closest char that can be aimed at
        if (target == null){
            float closestDist = Float.MAX_VALUE;
            for (Char ch : Actor.chars()){
                float curDist = Dungeon.level.trueDistance(pos, ch.pos);
                if (ch.invisible > 0) curDist += 1000;
                Ballistica bolt = new Ballistica(pos, ch.pos, Ballistica.PROJECTILE);
                if (bolt.collisionPos == ch.pos && curDist < closestDist){
                    target = ch;
                    closestDist = curDist;
                }
            }
        }
        return target;
    }

    static void processSpawnedMobsAndTraps(ArrayList<Mob> mobs) {
        //important to process the visuals and pressing of cells last, so spawned mobs have a chance to occupy cells first
        Trap t;
        for (Mob mob : mobs){
            //manually trigger traps first to avoid sfx spam
            if ((t = Dungeon.level.traps.get(mob.pos)) != null && t.active){
                if (t.disarmedByActivation) t.disarm();
                t.reveal();
                t.activate();
            }
            ScrollOfTeleportation.appear(mob, mob.pos);
            Dungeon.level.occupyCell(mob);
        }
    }
}
