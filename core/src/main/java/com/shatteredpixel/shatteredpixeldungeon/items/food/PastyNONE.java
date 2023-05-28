package com.shatteredpixel.shatteredpixeldungeon.items.food;

import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class PastyNONE extends Pasty {
    @Override
    public void reset() {
        super.reset();
        image = ItemSpriteSheet.PASTY;
    }
    @Override
    protected void satisfy(Hero hero) {
        super.satisfy(hero);
    }

    public String name() {
        return Messages.get(this, "pasty");
    }

    public String info() {
        return Messages.get(this, "pasty_desc");
    }

    public int value() {
        return 20 * quantity;
    }
}