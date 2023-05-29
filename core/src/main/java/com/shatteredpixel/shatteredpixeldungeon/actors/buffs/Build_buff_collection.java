package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

public interface Build_buff_collection {
    static Buff_Observer to_colleciton(Buff buff){ //
        return new Buff_collection(buff);
    }
}
