package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import com.watabou.utils.Bundlable;
import com.watabou.utils.Bundle;


public class Buff_collection implements Buff_Observer, Bundlable {
    private final Buff buff;
    public Buff_collection(Buff buff) {
        this.buff = buff;
    }

    @Override
    public Buff get(){
        return buff;
    }


    @SuppressWarnings("NewApi")
    @Override
    public void detach(){
        buff.detach();
    }


    @Override
    public void restoreFromBundle(Bundle bundle) {
        buff.restoreFromBundle(bundle);
    }

    @Override
    public void storeInBundle(Bundle bundle) {
        buff.storeInBundle(bundle);
    }
}
