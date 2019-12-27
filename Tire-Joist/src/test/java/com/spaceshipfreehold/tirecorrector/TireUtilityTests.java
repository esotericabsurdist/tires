package com.spaceshipfreehold.tirecorrector;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tire Joist Unit Tests
 *
 * TODO: Write tests here before addition of new utility's logic.
 *
 */
public class TireUtilityTests {

    @Test
    public void idealRatioReturnsZero(){
        assertTrue(Utils.getIdealRatio(0, 33, 4.1) == 0);
        assertTrue(Utils.getIdealRatio(30, 0, 4.1) == 0);
        assertTrue(Utils.getIdealRatio(29, 33, 0) == 0);
        assertTrue(Utils.getIdealRatio(-1, -1, 0) == 0);
    }

    @Test
    public void idealRatioReturnsNonZero(){
        assertTrue(Utils.getIdealRatio(28, 33, 3.73) > 0);
        assertTrue(Utils.getIdealRatio(2, 1, 3.73) > 0);
        assertTrue(Utils.getIdealRatio(1, 1, 1) > 0);
    }
}
