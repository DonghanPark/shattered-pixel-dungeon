package com.shatteredpixel.shatteredpixeldungeon.actors.blobs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlobTest {

    static Blob blob = new Blob();
    int width = 2;
    int height = 5;

    private void setDungeonSize(){

        Dungeon.level = new Level() {
            @Override
            protected boolean build() {
                return false;
            }

            @Override
            protected void createMobs() {

            }

            @Override
            protected void createItems() {

            }
        };
        Dungeon.level.setSize(width, height); // set length. length = 2*5
    }
    private void setSeed(int cell, int amount){
        blob.fullyClear();
        blob.seed(Dungeon.level, cell, amount);
    }

    /**
     * Purpose: test dungeon Size
     * Input: setSize() width = 2, height = 5
     * Expected: valid Size
     */
    @Test
    void testSetDungeonSize() {
        setDungeonSize();
        assertEquals(Dungeon.level.length(), width*height);
    }

    /**
     * Purpose: test blob attribute
     * Input: seed() cell = 9, amount = 10
     * Expected: valid blob attribute
     */
    @Test
    void testSetSeed() {
        int cell = 9;
        int amount = 10;
        setSeed(cell, amount);

        assertEquals(blob.volume, amount);
        assertNotNull(blob.cur);
        assertNotNull(blob.off);
        assertEquals(blob.cur[cell], amount);
    }
}