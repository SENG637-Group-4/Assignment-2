package org.jfree.data.test;

import static org.junit.Assert.*;
import org.jfree.data.Range;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;

public class RangeIntersectsTest {
    private Range baseRange;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @Before
    public void setUp() {
        baseRange = new Range(-10, 10);
    }

    @Test
    public void testLowerJustBelowLowerBoundTouchesLower() {
        assertTrue(baseRange.intersects(-10.00001, -10));
    }

    @Test
    public void testLowerJustBelowLowerBoundOverlapsSlightly() {
        assertTrue(baseRange.intersects(-10.00001, -9.99999));
    }

    @Test
    public void testLowerJustBelowLowerBoundSpansAll() {
        assertTrue(baseRange.intersects(-10.00001, 10.00001));
    }

    // Tests for lower == range lower bound 
    @Test
    public void testLowerAtLowerBoundOverlapsSlightly() {
        assertTrue(baseRange.intersects(-10, -9.99999));
    }

    @Test
    public void testLowerAtLowerBoundMatchesRange() {
        assertTrue(baseRange.intersects(-10, 10));
    }

    // Tests for lower inside range
    @Test
    public void testLowerInsideRangeNormalValues() {
        assertTrue(baseRange.intersects(-1, 1));
    }

    @Test
    public void testLowerInsideRangeEndsAtUpper() {
        assertTrue(baseRange.intersects(9.99999, 10));
    }

    // Tests for lower == upper bound 
    @Test
    public void testLowerAtUpperBoundOverlapsSlightly() {
        assertTrue(baseRange.intersects(10, 10.00001));
    }

    // Tests for extreme/min/max values 
    @Test
    public void testLowerMinValueUpperBeyondRange() {
        assertTrue(baseRange.intersects(Double.MIN_VALUE, 10.00001));
    }

    @Test
    public void testLowerJustBelowLowerUpperMaxValue() {
        assertTrue(baseRange.intersects(-10.00001, Double.MAX_VALUE));
    }

    // Tests for single points
    @Test
    public void testSinglePointInsideRange() {
        assertTrue(baseRange.intersects(0, 0));
    }

    @Test
    public void testCase12_NaNLower() {
        assertFalse(baseRange.intersects(Double.NaN, 1));
    }

    @Test
    public void testLowerInsideUpperNaN() {
        assertFalse(baseRange.intersects(1, Double.NaN));
    }

    @Test
    public void testSinglePointAtLowerBound() {
        assertTrue(baseRange.intersects(-10, -10));
    }

    @Test
    public void testSinglePointAtUpperBound() {
        assertTrue(baseRange.intersects(10, 10));
    }
    
    @After
    public void tearDown() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        System.out.println("RangeIntersectsTest suite completed.");
    }
}