package com.shatteredpixel.shatteredpixeldungeon.items.food;

import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Recharging;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfRecharging;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class PastyXMAS extends Pasty {
    @Override
    public void reset() {
        super.reset();
        image = ItemSpriteSheet.CANDY_CANE;
    }
    @Override
    protected void satisfy(Hero hero) {
        super.satisfy(hero);
        Buff.affect( hero, Recharging.class, 2f ); //half of a charge
        ScrollOfRecharging.charge( hero );
    }

    public String name() {
        return Messages.get(this, "cane");
    }

    public String info() {
        return Messages.get(this, "cane_desc");
    }

    public int value() {
        return 20 * quantity;
    }
}
