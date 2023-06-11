package com.shatteredpixel.shatteredpixeldungeon.test;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.*;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.duelist.Challenge;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.exotic.PotionOfCleansing;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.LinkedHashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CharTest {
    static Char CharTest;
    static Fury buff = new Fury();
    @BeforeAll
    static void beforeAll(){
        CharTest = Mockito.mock(Hero.class);
    }

    @DisplayName("객체 초기 상태 확인")
    @Test
    void Char(){
        assertNotNull(CharTest);
        assertTrue(CharTest instanceof Char);
        assertTrue(CharTest instanceof Hero);// Mocking + Stubbing
    }

    /**
     * Purpose: run detach() depend on HP,HT state
     * Input: act target.HP > (target.HT * 0.5f) ? detach() : void
     * Expected:
     * return true
     * (0, 0) = true
     * (20, 20) = true
     */
    @DisplayName("Fury act() 테스트")
    @Test
    void testFury() {
        /** Stubbing **/
        when(CharTest.add((Buff) any())).thenReturn(true);

        Object value = new LinkedHashSet<Buff_Observer>();  //buff_collection
        Object value2 = new Object();                       //HP
        Object value3 = new Object();                       //HT
        try{
            Field priv_field_test = CharTest.getClass().getSuperclass().getDeclaredField("buff_collection");
            priv_field_test.setAccessible(true);
//            Arrays.stream(CharTest.getClass().getDeclaredFields()).map(h -> h.getName()).forEach(System.out::println);
            value = priv_field_test.get(CharTest);
            /** checking initial state **/
            assertNull(value);
            /** checking adding Fury buff **/

            /** attachTo() **/
            assertFalse(CharTest.isImmune(buff.getClass()));
            assertTrue(CharTest.add(buff));
            assertTrue(buff.attachTo(CharTest));

            /** act() **/
            assertTrue(buff.act());
            assertNull(CharTest.buff(PotionOfCleansing.Cleanse.class));
            assertNull(CharTest.buff(Challenge.SpectatorFreeze.class));

//            Healing buff_h = new Healing();
//            buff_h.setHeal(100,20,10);
//            assertTrue(buff_h.attachTo(CharTest));
//            assertTrue(buff_h.act());
//            assertTrue(buff.act());

            /* setting HP, HT with Reflection */
            Field priv_field_test_HP = CharTest.getClass().getSuperclass().getDeclaredField("HP");
            priv_field_test_HP.setAccessible(true);
            value2 = priv_field_test_HP.get(CharTest);

            Field priv_field_test_HT = CharTest.getClass().getSuperclass().getDeclaredField("HT");
            priv_field_test_HT.setAccessible(true);
            value3 = priv_field_test_HT.get(CharTest);

            assertEquals(0,value2);
            assertEquals(0,value3);

            /* construct state, detach() state */
            priv_field_test_HT.setInt(CharTest,20);
            priv_field_test_HP.setInt(CharTest,20);
            value2 = priv_field_test_HP.get(CharTest);
            value3 = priv_field_test_HT.get(CharTest);
            assertEquals(20,value2);
            assertEquals(20,value3);
            assertTrue(buff.act());

            Buff_Observer observer = Build_buff_collection.to_colleciton(buff);
            assertEquals(observer.get().type, Buff.buffType.POSITIVE);
            assertNull(CharTest.sprite);



        }catch(NoSuchFieldException e){
            e.printStackTrace();
        }catch(IllegalAccessException e){
            e.printStackTrace();
        }
        assertNull(value);
    }
    /**
     * Purpose: get Fury icon ID
     * Input: icon act as get()
     * Expected:
     * return 18
     */
    @DisplayName("Fury icon 테스트")
    @Test
    void testFury2() {
        /** icon() **/
        assertEquals(18,buff.icon());
    }
}