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

package com.shatteredpixel.shatteredpixeldungeon.items.food;

import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Hunger;

public class Pasty extends Food {
    // TODO: implement fun stuff for other holidays
    // TODO: probably should externalize this if I want to add any more festive stuff.

    private static final Pasty pasty;
    private static final HolidayEnum holiday;

    static {
        holiday = Holiday.getHoliday();
    }

    static {
        pasty = createInstanceInternal();
    }

    {
        reset();
        energy = Hunger. STARVING;
        bones = true;
    }

    protected Pasty() {}

    private static Pasty createInstanceInternal() {
        switch (holiday) {
            case NONE:
                return new PastyNONE();
            case HWEEN:
                return new PastyHWEEN();
            case XMAS:
                return new PastyXMAS();
            default:
                throw new IllegalArgumentException("Invalid holiday value: " + holiday);
        }
    }

    public static Pasty createInstance() {
        return pasty;
    }
}