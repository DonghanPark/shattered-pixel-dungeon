package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import com.watabou.utils.Bundle;

import java.util.LinkedHashSet;
import java.util.stream.Collectors;

public class Buff_collection implements Buff_Observer{
    private LinkedHashSet<Buff> buffs = new LinkedHashSet<>();


    @Override
    public void put(String key, Bundle bundle){
        bundle.put(key,buffs);
    }

    @Override
    public LinkedHashSet<Buff> get(){
        return new LinkedHashSet<>(buffs);
    }

    @Override
    public void add(Buff buff){
        buffs.add(buff);
    }
    @Override
    public void remove(Buff buff){
        buffs.remove(buff);
    }
    @Override
    public void detach(){
        buffs.stream().collect(Collectors.toList()).forEach(buff -> {buff.detach();});
    }

}
