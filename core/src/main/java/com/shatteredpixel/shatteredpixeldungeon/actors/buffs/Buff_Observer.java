package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import com.watabou.utils.Bundlable;

public interface Buff_Observer extends Bundlable {
    public abstract Buff get();
    public abstract void detach();
}
