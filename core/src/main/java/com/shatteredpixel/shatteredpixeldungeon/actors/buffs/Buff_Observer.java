package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import com.watabou.utils.Bundle;

import java.util.LinkedHashSet;

public interface Buff_Observer {
    public abstract void put(String key, Bundle bundle);
    public abstract LinkedHashSet<Buff> get();
    public abstract void add(Buff buff);
    public abstract void remove(Buff buff);
    public abstract void detach();


}
