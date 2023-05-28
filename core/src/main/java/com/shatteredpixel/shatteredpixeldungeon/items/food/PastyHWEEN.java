package com.shatteredpixel.shatteredpixeldungeon.items.food;

import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class PastyHWEEN extends Pasty {
    @Override
    public void reset() {
        super.reset();
        image = ItemSpriteSheet.PUMPKIN_PIE;
    }
    @Override
    protected void satisfy(Hero hero) {
        super.satisfy(hero);
        hero.HP = Math.min(hero.HP + hero.HT/10, hero.HT);
        hero.sprite.emitter().burst( Speck.factory( Speck.HEALING ), 1 );
    }

    public String name() {
        return Messages.get(this, "pie");
    }

    public String info() {
        return Messages.get(this, "pie_desc");
    }

    public int value() {
        return 20 * quantity;
    }
}