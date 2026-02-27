
package org.jfree.data.test;

import org.jfree.data.DataUtilities;
import org.jfree.data.KeyedValues;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test Plan for DataUtilities.getCumulativePercentages(KeyedValues data)
 *
 * Uses JMock to mock the KeyedValues interface.
 *
 * TC1  - {0→5, 1→9, 2→2}                          → {0→0.3125, 1→0.875, 2→1.0}
 * TC2  - {0→10}                                    → {0→1.0}
 * TC3  - {} empty                                  → empty result
 * TC4  - null                                      → exception
 * TC5  - {0→5, 1→-2, 2→3}                         → mixed positive/negative
 * TC6  - {0→4, 1→4, 2→4}                          → uniform distribution
 * TC7  - {0→5, 1→0, 2→3}                          → zero entry does not advance
 * TC8  - {0→0, 1→0, 2→0}                          → all-zero (division by zero)
 * TC9  - {"a"→6, "b"→4}                           → string keys preserved
 * TC10 - {0→null, 1→4}                             → null value entry
 * TC11 - {0→NaN, 1→5.0}                           → NaN propagation
 * TC12 - {0→POSITIVE_INFINITY, 1→5.0}             → Infinity propagation
 * TC13 - {0→MAX_VALUE, 1→1.0}                     → extreme boundary value
 * TC14 - {0→5, 1→9, 2→2} verify keys preserved    → keys {0,1,2} in order
 * TC15 - {0→5, 1→9, 2→2} verify element types     → each value instanceof Double
 */
public class GetCumulativePercentagesTest {

    private Mockery context;
    private KeyedValues data;

    @Before
    public void setUp() {
        context = new Mockery();
        data = context.mock(KeyedValues.class);
    }

    // -----------------------------------------------------------------------
    // Helper: set up a mock KeyedValues with the given parallel key/value arrays.
    // Keys are passed as Comparable (supports Integer and String).
    // -----------------------------------------------------------------------
    private void setupMock(final Comparable[] keys, final Number[] values) {
        context.checking(new Expectations() {{
            allowing(data).getItemCount(); will(returnValue(keys.length));
            for (int i = 0; i < keys.length; i++) {
                final int idx = i;
                allowing(data).getKey(idx);   will(returnValue(keys[idx]));
                allowing(data).getIndex(keys[idx]); will(returnValue(idx));
                allowing(data).getValue(idx); will(returnValue(values[idx]));
            }
        }});
    }

    // -----------------------------------------------------------------------
    // TC1: {0→5, 1→9, 2→2} — NOM; 3 entries, all positive, ordered integer keys
    // E1; E4; E9
    // Expected: {0→0.3125, 1→0.875, 2→1.0}
    // -----------------------------------------------------------------------
    @Test
    public void testTC1_threeEntries_allPositive_orderedKeys() {
        setupMock(
            new Comparable[]{0, 1, 2},
            new Number[]   {5, 9, 2}
        );

        KeyedValues result = DataUtilities.getCumulativePercentages(data);

        assertEquals("TC1: key 0 cumulative % should be 0.3125",
                0.3125, result.getValue(0).doubleValue(), 0.000001d);
        assertEquals("TC1: key 1 cumulative % should be 0.875",
                0.875,  result.getValue(1).doubleValue(), 0.000001d);
        assertEquals("TC1: last key cumulative % should be exactly 1.0",
                1.0,    result.getValue(2).doubleValue(), 0.000001d);
        context.assertIsSatisfied();
    }

    // -----------------------------------------------------------------------
    // TC2: {0→10} — ALB; single entry
    // E2; E4
    // Expected: {0→1.0} — single entry always yields 1.0
    // -----------------------------------------------------------------------
    @Test
    public void testTC2_singleEntry_returnsOne() {
        setupMock(
            new Comparable[]{0},
            new Number[]   {10}
        );

        KeyedValues result = DataUtilities.getCumulativePercentages(data);

        assertEquals("TC2: single entry should yield 1.0",
                1.0, result.getValue(0).doubleValue(), 0.000001d);
        context.assertIsSatisfied();
    }

    // -----------------------------------------------------------------------
    // TC3: {} empty KeyedValues — LB; itemCount = 0
    // E3
    // Expected: returns empty KeyedValues; getItemCount() == 0
    // -----------------------------------------------------------------------
    @Test
    public void testTC3_emptyInput_returnsEmptyResult() {
        context.checking(new Expectations() {{
            allowing(data).getItemCount(); will(returnValue(0));
        }});

        KeyedValues result = DataUtilities.getCumulativePercentages(data);

        assertEquals("TC3: empty input should produce empty result", 0, result.getItemCount());
        context.assertIsSatisfied();
    }

    // -----------------------------------------------------------------------
    // TC4: null — U1; null not permitted
    // Expected: throws exception (InvalidParameterException or similar)
    // -----------------------------------------------------------------------
    @Test
    public void testTC4_nullData_throwsException() {
        try {
            DataUtilities.getCumulativePercentages(null);
            fail("TC4: Expected an exception when data is null, but none was thrown");
        } catch (Exception e) {
            assertNotNull("TC4: Caught exception should not be null", e);
        }
    }

    // -----------------------------------------------------------------------
    // TC5: {0→5, 1→-2, 2→3} — mixed positive and negative values
    // E1; E5
    // Sum = 6; Expected: {0→5/6≈0.8333, 1→3/6=0.5, 2→6/6=1.0}
    // -----------------------------------------------------------------------
    @Test
    public void testTC5_mixedPositiveNegative() {
        setupMock(
            new Comparable[]{0,  1,  2},
            new Number[]   {5, -2,  3}
        );

        KeyedValues result = DataUtilities.getCumulativePercentages(data);

        assertEquals("TC5: key 0 should be 5/6 ≈ 0.8333",
                5.0 / 6.0, result.getValue(0).doubleValue(), 0.000001d);
        assertEquals("TC5: key 1 should be 3/6 = 0.5",
                3.0 / 6.0, result.getValue(1).doubleValue(), 0.000001d);
        assertEquals("TC5: last key should be 1.0",
                1.0,       result.getValue(2).doubleValue(), 0.000001d);
        context.assertIsSatisfied();
    }

    // -----------------------------------------------------------------------
    // TC6: {0→4, 1→4, 2→4} — all values equal (uniform distribution)
    // E1; E6
    // Sum = 12; Expected: {0→1/3, 1→2/3, 2→1.0}
    // -----------------------------------------------------------------------
    @Test
    public void testTC6_allValuesEqual_uniformDistribution() {
        setupMock(
            new Comparable[]{0, 1, 2},
            new Number[]   {4, 4, 4}
        );

        KeyedValues result = DataUtilities.getCumulativePercentages(data);

        assertEquals("TC6: key 0 should be 1/3",
                1.0 / 3.0, result.getValue(0).doubleValue(), 0.000001d);
        assertEquals("TC6: key 1 should be 2/3",
                2.0 / 3.0, result.getValue(1).doubleValue(), 0.000001d);
        assertEquals("TC6: key 2 should be 1.0",
                1.0,       result.getValue(2).doubleValue(), 0.000001d);
        context.assertIsSatisfied();
    }

    // -----------------------------------------------------------------------
    // TC7: {0→5, 1→0, 2→3} — one zero value
    // E1; E7
    // Sum = 8; Expected: {0→0.625, 1→0.625, 2→1.0}
    // The zero entry does not advance the cumulative sum.
    // -----------------------------------------------------------------------
    @Test
    public void testTC7_oneZeroValue_doesNotAdvanceCumulative() {
        setupMock(
            new Comparable[]{0, 1, 2},
            new Number[]   {5, 0, 3}
        );

        KeyedValues result = DataUtilities.getCumulativePercentages(data);

        assertEquals("TC7: key 0 should be 0.625",
                0.625, result.getValue(0).doubleValue(), 0.000001d);
        assertEquals("TC7: key 1 should be 0.625 (zero entry adds nothing)",
                0.625, result.getValue(1).doubleValue(), 0.000001d);
        assertEquals("TC7: key 2 should be 1.0",
                1.0,   result.getValue(2).doubleValue(), 0.000001d);
        context.assertIsSatisfied();
    }

    // -----------------------------------------------------------------------
    // TC8: {0→0, 1→0, 2→0} — all values zero; sum = 0 (division-by-zero risk)
    // E1; E8
    // Expected: throws exception OR returns NaN values — both acceptable
    // -----------------------------------------------------------------------
    @Test
    public void testTC8_allZeroValues_divisionByZero() {
        setupMock(
            new Comparable[]{0, 1, 2},
            new Number[]   {0, 0, 0}
        );

        try {
            KeyedValues result = DataUtilities.getCumulativePercentages(data);
            // If no exception: each value should be NaN (0/0 in IEEE 754)
            for (int i = 0; i < result.getItemCount(); i++) {
                assertTrue("TC8: result value should be NaN when sum is zero",
                        Double.isNaN(result.getValue(i).doubleValue()));
            }
        } catch (Exception e) {
            // Also acceptable: implementation may throw on a zero-sum input
            assertNotNull("TC8: exception should not be null", e);
        }
        context.assertIsSatisfied();
    }

    // -----------------------------------------------------------------------
    // TC9: {"a"→6, "b"→4} — non-integer String keys
    // E1; E10
    // Sum = 10; Expected: {"a"→0.6, "b"→1.0}; keys preserved in result
    // -----------------------------------------------------------------------
    @Test
    public void testTC9_stringKeys_preservedInResult() {
        setupMock(
            new Comparable[]{"a", "b"},
            new Number[]   {  6,   4}
        );

        KeyedValues result = DataUtilities.getCumulativePercentages(data);

        assertEquals("TC9: key 'a' should be 0.6",
                0.6, result.getValue("a").doubleValue(), 0.000001d);
        assertEquals("TC9: key 'b' should be 1.0",
                1.0, result.getValue("b").doubleValue(), 0.000001d);
        context.assertIsSatisfied();
    }

    // -----------------------------------------------------------------------
    // TC10: {0→null, 1→4} — null value within valid KeyedValues
    // U2
    // Expected: throws exception or handles gracefully per spec
    // -----------------------------------------------------------------------
    @Test
    public void testTC10_nullValueEntry_throwsOrHandles() {
        setupMock(
            new Comparable[]{0,    1},
            new Number[]   {null,  4}
        );

        try {
            KeyedValues result = DataUtilities.getCumulativePercentages(data);
            // If no exception: null entry should be skipped; key 1 = 1.0
            assertNotNull("TC10: result should not be null", result);
        } catch (Exception e) {
            assertNotNull("TC10: exception should not be null", e);
        }
        context.assertIsSatisfied();
    }

    // -----------------------------------------------------------------------
    // TC11: {0→Double.NaN, 1→5.0} — NaN element
    // U3
    // Expected: NaN propagates through cumulative sum;
    //           result for key 0 is NaN, result for key 1 is NaN
    // -----------------------------------------------------------------------
    @Test
    public void testTC11_nanValue_propagates() {
        setupMock(
            new Comparable[]{0,          1  },
            new Number[]   {Double.NaN, 5.0}
        );

        KeyedValues result = DataUtilities.getCumulativePercentages(data);

        assertTrue("TC11: key 0 result should be NaN",
                Double.isNaN(result.getValue(0).doubleValue()));
        assertTrue("TC11: key 1 result should be NaN (NaN propagates)",
                Double.isNaN(result.getValue(1).doubleValue()));
        context.assertIsSatisfied();
    }

    // -----------------------------------------------------------------------
    // TC12: {0→Double.POSITIVE_INFINITY, 1→5.0} — Infinity element
    // U3
    // Expected: result for key 0 is NaN or Infinity (Infinity/Infinity = NaN);
    //           result for key 1 is also NaN or Infinity
    // -----------------------------------------------------------------------
    @Test
    public void testTC12_positiveInfinityValue_propagates() {
        setupMock(
            new Comparable[]{0,                        1  },
            new Number[]   {Double.POSITIVE_INFINITY, 5.0}
        );

        KeyedValues result = DataUtilities.getCumulativePercentages(data);

        double v0 = result.getValue(0).doubleValue();
        double v1 = result.getValue(1).doubleValue();

        assertTrue("TC12: key 0 result should be NaN or Infinite",
                Double.isNaN(v0) || Double.isInfinite(v0));
        assertTrue("TC12: key 1 result should be NaN or Infinite",
                Double.isNaN(v1) || Double.isInfinite(v1));
        context.assertIsSatisfied();
    }

    // -----------------------------------------------------------------------
    // TC13: {0→Double.MAX_VALUE, 1→1.0} — extreme UB element value
    // U4
    // Sum ≈ MAX_VALUE (1.0 is negligible); Expected: key 0 ≈ 1.0, key 1 = 1.0
    // -----------------------------------------------------------------------
    @Test
    public void testTC13_maxValueElement() {
        setupMock(
            new Comparable[]{0,               1  },
            new Number[]   {Double.MAX_VALUE, 1.0}
        );

        KeyedValues result = DataUtilities.getCumulativePercentages(data);

        double v0 = result.getValue(0).doubleValue();
        double v1 = result.getValue(1).doubleValue();

        // MAX_VALUE / (MAX_VALUE + 1) ≈ 1.0 due to floating-point precision
        assertTrue("TC13: key 0 result should be between 0.0 and 1.0 (inclusive), or NaN/Infinite",
                (v0 >= 0.0 && v0 <= 1.0) || Double.isNaN(v0) || Double.isInfinite(v0));
        assertEquals("TC13: last key result should be 1.0",
                1.0, v1, 0.000001d);
        context.assertIsSatisfied();
    }

    // -----------------------------------------------------------------------
    // TC14: {0→5, 1→9, 2→2} — verify keys preserved in result
    // E1; E4; E9
    // Expected: result has keys {0, 1, 2} in the same order
    // -----------------------------------------------------------------------
    @Test
    public void testTC14_keysPreservedInResult() {
        setupMock(
            new Comparable[]{0, 1, 2},
            new Number[]   {5, 9, 2}
        );

        KeyedValues result = DataUtilities.getCumulativePercentages(data);

        assertEquals("TC14: result should have 3 entries",    3, result.getItemCount());
        assertEquals("TC14: first key should be 0",  0, result.getKey(0));
        assertEquals("TC14: second key should be 1", 1, result.getKey(1));
        assertEquals("TC14: third key should be 2",  2, result.getKey(2));
        context.assertIsSatisfied();
    }

    // -----------------------------------------------------------------------
    // TC15: {0→5, 1→9, 2→2} — verify each result value is instanceof Double
    // E1; E4; E9
    // Expected: every value in the result is a Number (specifically Double)
    // -----------------------------------------------------------------------
    @Test
    public void testTC15_resultValuesAreDoubleInstances() {
        setupMock(
            new Comparable[]{0, 1, 2},
            new Number[]   {5, 9, 2}
        );

        KeyedValues result = DataUtilities.getCumulativePercentages(data);

        for (int i = 0; i < result.getItemCount(); i++) {
            assertNotNull("TC15: value at index " + i + " should not be null",
                    result.getValue(i));
            assertTrue("TC15: value at index " + i + " should be instanceof Double",
                    result.getValue(i) instanceof Double);
        }
        context.assertIsSatisfied();
    }
}