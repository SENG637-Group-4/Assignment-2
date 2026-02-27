package org.jfree.data.test;

import org.jfree.data.DataUtilities;
import org.jfree.data.Values2D;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test Plan for DataUtilities.calculateRowTotal(Values2D data, int row)
 *
 * Uses JMock to mock the Values2D interface.
 *
 * TC1  - {{1.0,2.0},{3.0,4.0}} row 0       → 3.0  (LB row)
 * TC2  - {{1.0,2.0},{3.0,4.0}} row 1       → 7.0  (ALB row)
 * TC3  - {{1.0,2.0},{3.0,4.0}} row 1       → 7.0  (UB row, n-1=1)
 * TC4  - {{5.0}}               row 0       → 5.0  (single-row single-column)
 * TC5  - {{0.0,0.0},{0.0,0.0}} row 0       → 0.0  (all zeros)
 * TC6  - {{-1.0,-2.0},{3.0,4.0}} row 0     → -3.0 (negative values)
 * TC7  - {{-1.5,2.5},{3.0,-3.0}} row 1     → 0.0  (mixed positive/negative)
 * TC8  - null                  row 0       → exception
 * TC9  - {{1.0,2.0},{3.0,4.0}} row -1      → 0.0  (BLB invalid index)
 * TC10 - {{1.0,2.0},{3.0,4.0}} row 2       → 0.0  (AUB invalid index)
 * TC11 - {{NaN, 1.0}}          row 0       → NaN  (NaN propagation)
 * TC12 - {{POSITIVE_INFINITY, 1.0}} row 0  → POSITIVE_INFINITY
 * TC13 - {{MAX_VALUE, MAX_VALUE}} row 0    → POSITIVE_INFINITY (overflow)
 * TC14 - {{MIN_VALUE, 1.0}}    row 0       → sum including MIN_VALUE
 * TC15 - {{},{1.0,2.0}}        row 0       → 0.0  (empty row, LB inner)
 * TC16 - {{1.0,2.0},{3.0,4.0}} row 0       → 3.0  (only target row summed)
 */
public class CalculateRowTotalTest {

    private Mockery context;
    private Values2D data;

    @Before
    public void setUp() {
        context = new Mockery();
        data = context.mock(Values2D.class);
    }

    // -----------------------------------------------------------------------
    // TC1: {{1.0, 2.0}, {3.0, 4.0}}, row 0 — LB row
    // E1, E6, E9 — Returns 3.0 (1.0 + 2.0)
    // -----------------------------------------------------------------------
    @Test
    public void testTC1_twoByTwo_rowZero_LBRow() {
        context.checking(new Expectations() {{
            allowing(data).getRowCount();       will(returnValue(2));
            allowing(data).getColumnCount();    will(returnValue(2));
            allowing(data).getValue(0, 0);      will(returnValue(1.0));
            allowing(data).getValue(0, 1);      will(returnValue(2.0));
        }});

        double result = DataUtilities.calculateRowTotal(data, 0);
        assertEquals("TC1: Sum of row 0 should be 3.0", 3.0, result, 0.000001d);
        context.assertIsSatisfied();
    }

    // -----------------------------------------------------------------------
    // TC2: {{1.0, 2.0}, {3.0, 4.0}}, row 1 — ALB row
    // E1, E7, E9 — Returns 7.0 (3.0 + 4.0)
    // -----------------------------------------------------------------------
    @Test
    public void testTC2_twoByTwo_rowOne_ALBRow() {
        context.checking(new Expectations() {{
            allowing(data).getRowCount();       will(returnValue(2));
            allowing(data).getColumnCount();    will(returnValue(2));
            allowing(data).getValue(1, 0);      will(returnValue(3.0));
            allowing(data).getValue(1, 1);      will(returnValue(4.0));
        }});

        double result = DataUtilities.calculateRowTotal(data, 1);
        assertEquals("TC2: Sum of row 1 should be 7.0", 7.0, result, 0.000001d);
        context.assertIsSatisfied();
    }

    // -----------------------------------------------------------------------
    // TC3: {{1.0, 2.0}, {3.0, 4.0}}, row 1 — UB row (n-1 = 1)
    // E1, E8, E9 — Returns 7.0 (3.0 + 4.0)
    // -----------------------------------------------------------------------
    @Test
    public void testTC3_twoByTwo_rowOne_UBRow() {
        context.checking(new Expectations() {{
            allowing(data).getRowCount();       will(returnValue(2));
            allowing(data).getColumnCount();    will(returnValue(2));
            allowing(data).getValue(1, 0);      will(returnValue(3.0));
            allowing(data).getValue(1, 1);      will(returnValue(4.0));
        }});

        double result = DataUtilities.calculateRowTotal(data, 1);
        assertEquals("TC3: Sum of UB row should be 7.0", 7.0, result, 0.000001d);
        context.assertIsSatisfied();
    }

    // -----------------------------------------------------------------------
    // TC4: {{5.0}}, row 0 — single-row single-column
    // E2, E3, E6 — Returns 5.0
    // -----------------------------------------------------------------------
    @Test
    public void testTC4_singleRowSingleColumn() {
        context.checking(new Expectations() {{
            allowing(data).getRowCount();       will(returnValue(1));
            allowing(data).getColumnCount();    will(returnValue(1));
            allowing(data).getValue(0, 0);      will(returnValue(5.0));
        }});

        double result = DataUtilities.calculateRowTotal(data, 0);
        assertEquals("TC4: Single-cell table should return 5.0", 5.0, result, 0.000001d);
        context.assertIsSatisfied();
    }

    // -----------------------------------------------------------------------
    // TC5: {{0.0, 0.0}, {0.0, 0.0}}, row 0 — all zeros
    // E1, E4, E6 — Returns 0.0
    // -----------------------------------------------------------------------
    @Test
    public void testTC5_allZeroValues_returnsZero() {
        context.checking(new Expectations() {{
            allowing(data).getRowCount();       will(returnValue(2));
            allowing(data).getColumnCount();    will(returnValue(2));
            allowing(data).getValue(0, 0);      will(returnValue(0.0));
            allowing(data).getValue(0, 1);      will(returnValue(0.0));
        }});

        double result = DataUtilities.calculateRowTotal(data, 0);
        assertEquals("TC5: All-zero row should return 0.0", 0.0, result, 0.000001d);
        context.assertIsSatisfied();
    }

    // -----------------------------------------------------------------------
    // TC6: {{-1.0, -2.0}, {3.0, 4.0}}, row 0 — negative values
    // E1, E5, E6, E10 — Returns -3.0 (-1.0 + -2.0)
    // -----------------------------------------------------------------------
    @Test
    public void testTC6_negativeValues_returnsNegativeSum() {
        context.checking(new Expectations() {{
            allowing(data).getRowCount();       will(returnValue(2));
            allowing(data).getColumnCount();    will(returnValue(2));
            allowing(data).getValue(0, 0);      will(returnValue(-1.0));
            allowing(data).getValue(0, 1);      will(returnValue(-2.0));
        }});

        double result = DataUtilities.calculateRowTotal(data, 0);
        assertEquals("TC6: Row of negative values should return -3.0", -3.0, result, 0.000001d);
        context.assertIsSatisfied();
    }

    // -----------------------------------------------------------------------
    // TC7: {{-1.5, 2.5}, {3.0, -3.0}}, row 1 — mixed positive and negative
    // E1, E5, E10 — Returns 0.0 (3.0 + -3.0)
    // -----------------------------------------------------------------------
    @Test
    public void testTC7_mixedPositiveNegative_cancellingValues() {
        context.checking(new Expectations() {{
            allowing(data).getRowCount();       will(returnValue(2));
            allowing(data).getColumnCount();    will(returnValue(2));
            allowing(data).getValue(1, 0);      will(returnValue(3.0));
            allowing(data).getValue(1, 1);      will(returnValue(-3.0));
        }});

        double result = DataUtilities.calculateRowTotal(data, 1);
        assertEquals("TC7: Cancelling row values should return 0.0", 0.0, result, 0.000001d);
        context.assertIsSatisfied();
    }

    // -----------------------------------------------------------------------
    // TC8: null data, row 0 — U1; null not permitted
    // Expected: throws exception (InvalidParameterException or similar)
    // -----------------------------------------------------------------------
    @Test
    public void testTC8_nullData_throwsException() {
        try {
            DataUtilities.calculateRowTotal(null, 0);
            fail("TC8: Expected an exception when data is null, but none was thrown");
        } catch (Exception e) {
            assertNotNull("TC8: Caught exception should not be null", e);
        }
    }

    // -----------------------------------------------------------------------
    // TC9: {{1.0, 2.0}, {3.0, 4.0}}, row -1 — BLB; invalid index
    // E1, U2 — Spec: "with invalid input, a total of zero will be returned"
    // Wildcard getValue stub added defensively in case implementation iterates.
    // -----------------------------------------------------------------------
    @Test
    public void testTC9_negativeRowIndex_returnsZero() {
        context.checking(new Expectations() {{
            allowing(data).getRowCount();       will(returnValue(2));
            allowing(data).getColumnCount();    will(returnValue(2));
            allowing(data).getValue(with(any(Integer.class)), with(any(Integer.class)));
                                                will(returnValue(1.0));
        }});

        double result = DataUtilities.calculateRowTotal(data, -1);
        assertEquals("TC9: Invalid row -1 should return 0.0 per spec", 0.0, result, 0.000001d);
        context.assertIsSatisfied();
    }

    // -----------------------------------------------------------------------
    // TC10: {{1.0, 2.0}, {3.0, 4.0}}, row 2 — AUB; one past last valid row
    // E1, U3 — Spec: "with invalid input, a total of zero will be returned"
    // Wildcard getValue stub added defensively (same reason as TC9).
    // -----------------------------------------------------------------------
    @Test
    public void testTC10_rowBeyondUpperBound_returnsZero() {
        context.checking(new Expectations() {{
            allowing(data).getRowCount();       will(returnValue(2));
            allowing(data).getColumnCount();    will(returnValue(2));
            allowing(data).getValue(with(any(Integer.class)), with(any(Integer.class)));
                                                will(returnValue(1.0));
        }});

        double result = DataUtilities.calculateRowTotal(data, 2); // AUB: valid range is 0–1
        assertEquals("TC10: Out-of-bounds row 2 should return 0.0 per spec", 0.0, result, 0.000001d);
        context.assertIsSatisfied();
    }

    // -----------------------------------------------------------------------
    // TC11: {{Double.NaN, 1.0}}, row 0 — NaN element
    // E2, U4 — NaN + anything = NaN in IEEE 754
    // -----------------------------------------------------------------------
    @Test
    public void testTC11_nanElement_propagates() {
        context.checking(new Expectations() {{
            allowing(data).getRowCount();       will(returnValue(1));
            allowing(data).getColumnCount();    will(returnValue(2));
            allowing(data).getValue(0, 0);      will(returnValue((Number) Double.NaN));
            allowing(data).getValue(0, 1);      will(returnValue(1.0));
        }});

        double result = DataUtilities.calculateRowTotal(data, 0);
        assertTrue("TC11: NaN element should produce NaN result", Double.isNaN(result));
        context.assertIsSatisfied();
    }

    // -----------------------------------------------------------------------
    // TC12: {{Double.POSITIVE_INFINITY, 1.0}}, row 0 — Infinity element
    // E2, U5 — POSITIVE_INFINITY + finite = POSITIVE_INFINITY
    // -----------------------------------------------------------------------
    @Test
    public void testTC12_positiveInfinityElement() {
        context.checking(new Expectations() {{
            allowing(data).getRowCount();       will(returnValue(1));
            allowing(data).getColumnCount();    will(returnValue(2));
            allowing(data).getValue(0, 0);      will(returnValue((Number) Double.POSITIVE_INFINITY));
            allowing(data).getValue(0, 1);      will(returnValue(1.0));
        }});

        double result = DataUtilities.calculateRowTotal(data, 0);
        assertTrue("TC12: POSITIVE_INFINITY + 1.0 should return POSITIVE_INFINITY",
                Double.isInfinite(result) && result > 0);
        context.assertIsSatisfied();
    }

    // -----------------------------------------------------------------------
    // TC13: {{Double.MAX_VALUE, Double.MAX_VALUE}}, row 0 — UB element values
    // E2, U6 — MAX_VALUE + MAX_VALUE overflows to POSITIVE_INFINITY in IEEE 754
    // -----------------------------------------------------------------------
    @Test
    public void testTC13_maxValueElements_overflow() {
        context.checking(new Expectations() {{
            allowing(data).getRowCount();       will(returnValue(1));
            allowing(data).getColumnCount();    will(returnValue(2));
            allowing(data).getValue(0, 0);      will(returnValue((Number) Double.MAX_VALUE));
            allowing(data).getValue(0, 1);      will(returnValue((Number) Double.MAX_VALUE));
        }});

        double result = DataUtilities.calculateRowTotal(data, 0);
        assertTrue("TC13: MAX_VALUE + MAX_VALUE should overflow to POSITIVE_INFINITY",
                Double.isInfinite(result) && result > 0);
        context.assertIsSatisfied();
    }

    // -----------------------------------------------------------------------
    // TC14: {{Double.MIN_VALUE, 1.0}}, row 0 — LB element value
    // E2, U6 — MIN_VALUE is the smallest positive double (~4.9e-324)
    // Expected: sum = MIN_VALUE + 1.0 ≈ 1.0 (MIN_VALUE is negligible)
    // -----------------------------------------------------------------------
    @Test
    public void testTC14_minValueElement() {
        context.checking(new Expectations() {{
            allowing(data).getRowCount();       will(returnValue(1));
            allowing(data).getColumnCount();    will(returnValue(2));
            allowing(data).getValue(0, 0);      will(returnValue((Number) Double.MIN_VALUE));
            allowing(data).getValue(0, 1);      will(returnValue(1.0));
        }});

        double result = DataUtilities.calculateRowTotal(data, 0);
        // Double.MIN_VALUE (~4.9e-324) + 1.0 rounds to 1.0 in IEEE 754
        assertEquals("TC14: MIN_VALUE + 1.0 should be approximately 1.0",
                1.0 + Double.MIN_VALUE, result, 0.000001d);
        context.assertIsSatisfied();
    }

    // -----------------------------------------------------------------------
    // TC15: {{}, {1.0, 2.0}}, row 0 — empty row (LB inner; 0 columns)
    // E1, LB inner — empty row has no values to sum; returns 0.0
    // -----------------------------------------------------------------------
    @Test
    public void testTC15_emptyRow_returnsZero() {
        context.checking(new Expectations() {{
            allowing(data).getRowCount();       will(returnValue(2));
            allowing(data).getColumnCount();    will(returnValue(0));
        }});

        double result = DataUtilities.calculateRowTotal(data, 0);
        assertEquals("TC15: Empty row (0 columns) should return 0.0", 0.0, result, 0.000001d);
        context.assertIsSatisfied();
    }

    // -----------------------------------------------------------------------
    // TC16: {{1.0, 2.0}, {3.0, 4.0}}, row 0 — verify only target row is summed
    // E1, E6 — Returns 3.0, not 10.0 (other rows must not be included)
    // -----------------------------------------------------------------------
    @Test
    public void testTC16_onlyTargetRowSummed_notEntireTable() {
        context.checking(new Expectations() {{
            allowing(data).getRowCount();       will(returnValue(2));
            allowing(data).getColumnCount();    will(returnValue(2));
            allowing(data).getValue(0, 0);      will(returnValue(1.0));
            allowing(data).getValue(0, 1);      will(returnValue(2.0));
            // Row 1 values are intentionally different to catch cross-row summing bugs
            allowing(data).getValue(1, 0);      will(returnValue(3.0));
            allowing(data).getValue(1, 1);      will(returnValue(4.0));
        }});

        double result = DataUtilities.calculateRowTotal(data, 0);
        assertEquals("TC16: Only row 0 should be summed; result should be 3.0, not 10.0",
                3.0, result, 0.000001d);
        assertFalse("TC16: Result must not be the total of all rows (10.0)",
                Math.abs(result - 10.0) < 0.000001d);
        context.assertIsSatisfied();
    }
}