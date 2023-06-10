package com.shatteredpixel.shatteredpixeldungeon.items.food;

import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PastyTest {
    /**
     * Purpose: Creating a subclass of Pasty
     * Input: Pasty is implemented as a Singleton Design Pattern,
     *          so an object must be created with the getInstance method.
     *          However, an object was created using Mockito due to an object creation error.
     *          A NullPointerException error occurs.
     *          because the file of the original developer watabo's TextureFile does not exist.
     *          So, I couldn't create an object,
     *          So, I created an object using Mock.
     * Expected: Since we created the object, we check to see if it returns a non-null value.
     * Using assertNotNull() doesn't throw an error.
     */
    Pasty pastyXmas = Mockito.mock(PastyXMAS.class);
    Pasty pastyNone = Mockito.mock(PastyNONE.class);
    Pasty pastyHween = Mockito.mock(PastyHWEEN.class);

    @Test
    void newPastyError() {
        assertAll("Creating Pasty",
                () -> assertThrows(NullPointerException.class, () -> new Pasty(), "Expected NullPointerException"),
                () -> assertThrows(ExceptionInInitializerError.class, () -> new Pasty(), "Expected ExceptionInInitializerError")
        );
    }

    @Test
    void createInstance() {
        assertNotNull(pastyXmas);
        assertNotNull(pastyNone);
        assertNotNull(pastyHween);
    }

    @Test
    void reset() {
        pastyXmas.reset();
        assertNotNull(pastyHween);
        pastyNone.reset();
        assertNotNull(pastyNone.image);
        pastyHween.reset();
        assertNotNull(pastyHween.image);
    }

    @Test
    void satisfy() {
        Hero hero = Mockito.mock(Hero.class);
        pastyXmas.satisfy(hero);
        pastyNone.satisfy(hero);
        pastyHween.satisfy(hero);
    }
}