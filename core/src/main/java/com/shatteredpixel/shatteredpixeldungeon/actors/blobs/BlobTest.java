package com.shatteredpixel.shatteredpixeldungeon.actors.blobs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlobTest {
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

    /**
     * Purpose: test dungeon size
     * Input: setSize() width = 2, height = 5
     * Expected: valid Size
     */
    @Test
    void testSetDungeonSize() {
        setDungeonSize();
        assertEquals(Dungeon.level.length(), width*height);
    }


}