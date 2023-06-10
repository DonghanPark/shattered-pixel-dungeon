package com.shatteredpixel.shatteredpixeldungeon.test;

import com.shatteredpixel.shatteredpixeldungeon.QuickSlot;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.watabou.utils.Bundle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuickSlotTest {

    QuickSlot quickSlot = new QuickSlot();
    Item item = new Item();
    Item item2 = new Item();
    Item item3 = new Item();
    Bundle bundle = new Bundle();

    /**
     * Purpose: Set Unit(item) to Array[slot]
     * Input: setSlot slots[0] -> item / slots[1] -> item / slots[0] -> item
     * Expected:
     *     return Success
     *     slot[0] = item
     */

    @Test
    void setSlotTest() {
        quickSlot.setSlot(0, item);
        quickSlot.setSlot(1, item);
        quickSlot.setSlot(0, item);
        assertEquals(quickSlot.getItem(0), item);
    }

    /**
     * Purpose: delete Unit(item) of Array(slot)
     * Input: setSlot slots[0] -> item / clearSlot slots[0]
     * Expected:
     *     return Success
     *     slot[0] = null
     */

    @Test
    void clearSlotTest() {
        quickSlot.setSlot(0, item);
        quickSlot.clearSlot(0);
        assertEquals(quickSlot.getItem(0), null);
    }

    /**
     * Purpose: reset Array(slot)
     * Input: setSlot slots[0] -> item / setSlot slots[1] -> item2 / reset
     * Expected:
     *     return Success
     *     slot[0] = null
     *     slot[1] = null
     */

    @Test
    void resetTest() {
        quickSlot.setSlot(0, item);
        quickSlot.setSlot(1, item2);
        quickSlot.reset();
        assertEquals(quickSlot.getItem(0), null);
        assertEquals(quickSlot.getItem(1), null);
    }

    /**
     * Purpose: return Unit(item)
     * Input: setSlot slots[0] -> item / getItem(0)
     * Expected:
     *     return Success
     *     getItem(0) = item
     */

    @Test
    void getItemTest() {
        quickSlot.setSlot(0, item);
        assertEquals(quickSlot.getItem(0), item);
    }

    /**
     * Purpose: return index of Unit(item) in Array(slot)
     * Input: setSlot slots[1] -> item2 / getSlot(item2)
     * Expected:
     *     return Success
     *     getSlot(item2) = 1
     */

    @Test
    void getSlotTest() {
        quickSlot.setSlot(0, item);
        quickSlot.setSlot(1, item2);
        assertEquals(quickSlot.getSlot(item2), 1);
    }

    /**
     * Purpose: check Unit(item)'s quantity equal to zero
     * Input: item.quantity -> 0 / setSlot slots[0] -> item / isPlaceholder(0)
     * Expected:
     *     return Success
     *     isPlaceholder(0) = True
     */

    @Test
    void isPlaceholderTest() {
        item.quantity(0);
        quickSlot.setSlot(0, item);
        assertTrue(quickSlot.isPlaceholder(0));
    }

    /**
     * Purpose: check Unit(item)'s quantity over one
     * Input: item.quantity -> 0 / setSlot slots[0] -> item / isNonePlaceholder(0)
     * Expected:
     *     return Success
     *     isNonePlaceholder(0) = False
     */

    @Test
    void isNonePlaceholderTest() {
        item.quantity(0);
        quickSlot.setSlot(0, item);
        assertFalse(quickSlot.isNonePlaceholder(0));
    }

    /**
     * Purpose: check Unit(item) of Array(slot) and delete it
     * Input: setSlot slots[0] -> item / clearItem(item) / getSlot(item)
     * Expected:
     *     return Success
     *     getSlot(item) = -1
     */

    @Test
    void clearItemTest() {
        quickSlot.setSlot(0, item);
        quickSlot.clearItem(item);
        assertEquals(quickSlot.getSlot(item), -1);
    }

    /**
     * Purpose: check quantity of Unit(item) and replace similar Unit(item)
     * Input: item.quantity -> 0 / setSlot slots[0] -> item / replacePlaceholder(item2) / contain(item2)
     * Expected:
     *     return Success
     *     contains(item2) = True
     *     getSlot(item2) = 0
     */

    @Test
    void replacePlaceholderTest() {
        item.quantity(0);
        quickSlot.setSlot(0, item);
        quickSlot.replacePlaceholder(item2);
        assertTrue(quickSlot.contains(item2));
        assertEquals(quickSlot.getSlot(item2), 0);
    }

    /**
     * Purpose: replace Unit(item) to virtual Unit(item) of Array(slot)
     * Input: setSlot slots[0] -> item / convertToPlaceholder(item) / getItem(item)
     * Expected:
     *     return Success
     *     getItem(0) = placeholder(new Unit)
     */

    @Test
    void convertToPlaceholderTest() {
        quickSlot.setSlot(0, item);
        quickSlot.convertToPlaceholder(item);
        assertNotEquals(quickSlot.getItem(0), item);
    }

    /**
     * Purpose: return random Unit(item) of Array(slot) that Unit(item)'s quantity is over one
     * Input: item.quantity -> 0 / setSlot slots[0] -> item / setSlot slots[2] -> item2
     *        setSlot slots[3] -> item3 / randomNonePlaceholder()
     * Expected:
     *     return Success
     *     quickSlot.randomNonPlaceholder() = item2 or item3
     */

    @Test
    void randomNonePlaceholderTest() {
        item.quantity(0);
        quickSlot.setSlot(0, item);
        quickSlot.setSlot(2, item2);
        quickSlot.setSlot(3, item3);
        Item result = quickSlot.randomNonePlaceholder();
        assertNotEquals(result, item);
    }

    /**
     * Purpose: store Unit(item) of Array(slot) that Unit(item)'s quantity is zero to bundle
     *          and replace to new Unit(item)
     * Input: item3.quantity -> 0 / setSlot slots[0] -> item / setSlot slots[1] -> item2
     *        setSlot slots[2] -> item3 / storePlaceholders() / restorePlaceholders()
     * Expected:
     *     return Success
     *     getItem(2) = placeholder(new item)
     */

    @Test
    void restorePlaceholdersTest() {
        item3.quantity(0);
        quickSlot.setSlot(0, item);
        quickSlot.setSlot(1, item2);
        quickSlot.setSlot(2, item3);
        quickSlot.storePlaceholders(bundle);
        quickSlot.restorePlaceholders(bundle);
        assertNotEquals(quickSlot.getItem(2), item3);
    }
}