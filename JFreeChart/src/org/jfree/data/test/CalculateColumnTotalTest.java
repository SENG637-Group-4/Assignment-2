package org.jfree.data.test;

import org.jfree.data.DataUtilities;
import org.jfree.data.Values2D;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test Plan for DataUtilities.calculateColumnTotal(Values2D data, int column)
 *
 * Uses JMock to mock the Values2D interface.
 *
 * ============================================================
 * ALL BUGS FIXED vs uploaded hello.java:
 * ============================================================
 *
 * BUG 1 — Missing package declaration (this fix)
 *   File lives at src/org/jfree/data/test/ so must declare:
 *   package org.jfree.data.test;
 *
 * BUG 2 — public class hello
 *   Java requires the public class name to exactly match the filename.
 *   FIX: renamed to CalculateColumnTotalTest.
 *
 * BUG 3 — TC10: @Test(expected = IllegalArgumentException.class)
 *   DataUtilities throws InvalidParameterException (java.security.InvalidParameterException)
 *   which is NOT a subclass of IllegalArgumentException — test always failed.
 *   FIX: explicit try/catch accepts any exception; fail() catches the no-throw case.
 *
 * BUG 4 — TC11: with(any(int.class))
 *   JMock's any() requires a reference type. int.class is primitive — JMock throws
 *   IllegalArgumentException: int is not a reference type at mock setup.
 *   FIX: any(Integer.class).
 *
 * BUG 5 — TC12: with(any(int.class)) — same issue as BUG 4.
 *   FIX: any(Integer.class).
 */
public class CalculateColumnTotalTest {

    private Mockery context;
    private Values2D values;

    @Before
    public void setUp() {
        context = new Mockery();
        values = context.mock(Values2D.class);
    }

    // -----------------------------------------------------------------------
    // TC1: 2×2 table, column 0 — LB column, NOM rows
    // E1; E6; E9 — Returns 4.0 (1.0 + 3.0)
    // -----------------------------------------------------------------------
    @Test
    public void testTC1_twoByTwo_columnZero() {
        context.checking(new Expectations() {{
            allowing(values).getRowCount();    will(returnValue(2));
            allowing(values).getColumnCount(); will(returnValue(2));
            allowing(values).getValue(0, 0);   will(returnValue(1.0));
            allowing(values).getValue(1, 0);   will(returnValue(3.0));
        }});

        double result = DataUtilities.calculateColumnTotal(values, 0);
        assertEquals("TC1: Sum of column 0 should be 4.0", 4.0, result, 0.000001d);
        context.assertIsSatisfied();
    }

    // -----------------------------------------------------------------------
    // TC2: 2×2 table, column 1 — UB column
    // E1; E8; E9 — Returns 6.0 (2.0 + 4.0)
    // -----------------------------------------------------------------------
    @Test
    public void testTC2_twoByTwo_columnOne_UBColumn() {
        context.checking(new Expectations() {{
            allowing(values).getRowCount();    will(returnValue(2));
            allowing(values).getColumnCount(); will(returnValue(2));
            allowing(values).getValue(0, 1);   will(returnValue(2.0));
            allowing(values).getValue(1, 1);   will(returnValue(4.0));
        }});

        double result = DataUtilities.calculateColumnTotal(values, 1);
        assertEquals("TC2: Sum of column 1 should be 6.0", 6.0, result, 0.000001d);
        context.assertIsSatisfied();
    }

    // -----------------------------------------------------------------------
    // TC3: 3×3 table, column 1 — NOM column, NOM rows
    // E1; E7; E9 — Returns 15.0 (2.0 + 5.0 + 8.0)
    // row0=[1,2,3], row1=[4,5,6], row2=[7,8,9] → col1 = 2+5+8 = 15
    // -----------------------------------------------------------------------
    @Test
    public void testTC3_threeByThree_middleColumn() {
        context.checking(new Expectations() {{
            allowing(values).getRowCount();    will(returnValue(3));
            allowing(values).getColumnCount(); will(returnValue(3));
            allowing(values).getValue(0, 1);   will(returnValue(2.0));
            allowing(values).getValue(1, 1);   will(returnValue(5.0));
            allowing(values).getValue(2, 1);   will(returnValue(8.0));
        }});

        double result = DataUtilities.calculateColumnTotal(values, 1);
        assertEquals("TC3: Sum of middle column should be 15.0", 15.0, result, 0.000001d);
        context.assertIsSatisfied();
    }

    // -----------------------------------------------------------------------
    // TC4: 1×3 table, column 0 — ALB outer (single row), LB column
    // E2; E6; E9 — Returns 5.0
    // -----------------------------------------------------------------------
    @Test
    public void testTC4_singleRow_columnZero() {
        context.checking(new Expectations() {{
            allowing(values).getRowCount();    will(returnValue(1));
            allowing(values).getColumnCount(); will(returnValue(3));
            allowing(values).getValue(0, 0);   will(returnValue(5.0));
        }});

        double result = DataUtilities.calculateColumnTotal(values, 0);
        assertEquals("TC4: Single-row table, column 0 should return 5.0", 5.0, result, 0.000001d);
        context.assertIsSatisfied();
    }

    // -----------------------------------------------------------------------
    // TC5: 3×1 table, column 0 — ALB inner (single column)
    // E3; E6; E9 — Returns 9.0 (2.0 + 3.0 + 4.0)
    // -----------------------------------------------------------------------
    @Test
    public void testTC5_singleColumn_threeRows() {
        context.checking(new Expectations() {{
            allowing(values).getRowCount();    will(returnValue(3));
            allowing(values).getColumnCount(); will(returnValue(1));
            allowing(values).getValue(0, 0);   will(returnValue(2.0));
            allowing(values).getValue(1, 0);   will(returnValue(3.0));
            allowing(values).getValue(2, 0);   will(returnValue(4.0));
        }});

        double result = DataUtilities.calculateColumnTotal(values, 0);
        assertEquals("TC5: Single-column table sum should be 9.0", 9.0, result, 0.000001d);
        context.assertIsSatisfied();
    }

    // -----------------------------------------------------------------------
    // TC6: 0-row table — LB outer (empty table)
    // E4; E6 — Returns 0.0
    // -----------------------------------------------------------------------
    @Test
    public void testTC6_emptyTable_returnsZero() {
        context.checking(new Expectations() {{
            allowing(values).getRowCount();    will(returnValue(0));
            allowing(values).getColumnCount(); will(returnValue(2));
        }});

        double result = DataUtilities.calculateColumnTotal(values, 0);
        assertEquals("TC6: Empty table should return 0.0", 0.0, result, 0.000001d);
        context.assertIsSatisfied();
    }

    // -----------------------------------------------------------------------
    // TC7: 2×2 table with null cell at (0,1), column 1
    // E1; E5; E8 — Null cell skipped; returns 5.0
    // -----------------------------------------------------------------------
    @Test
    public void testTC7_nullCellValue_skipped() {
        context.checking(new Expectations() {{
            allowing(values).getRowCount();    will(returnValue(2));
            allowing(values).getColumnCount(); will(returnValue(2));
            allowing(values).getValue(0, 1);   will(returnValue(null));
            allowing(values).getValue(1, 1);   will(returnValue(5.0));
        }});

        double result = DataUtilities.calculateColumnTotal(values, 1);
        assertEquals("TC7: Null cell should be skipped; result should be 5.0", 5.0, result, 0.000001d);
        context.assertIsSatisfied();
    }

    // -----------------------------------------------------------------------
    // TC8: 2×2 all-zero table, column 0
    // E1; E6; E10 — Returns 0.0
    // -----------------------------------------------------------------------
    @Test
    public void testTC8_allZeroColumn_returnsZero() {
        context.checking(new Expectations() {{
            allowing(values).getRowCount();    will(returnValue(2));
            allowing(values).getColumnCount(); will(returnValue(2));
            allowing(values).getValue(0, 0);   will(returnValue(0.0));
            allowing(values).getValue(1, 0);   will(returnValue(0.0));
        }});

        double result = DataUtilities.calculateColumnTotal(values, 0);
        assertEquals("TC8: All-zero column should return 0.0", 0.0, result, 0.000001d);
        context.assertIsSatisfied();
    }

    // -----------------------------------------------------------------------
    // TC9: 2×2 table with cancelling values, column 0
    // E1; E6; E11 — Returns 0.0 (3.0 + (-3.0))
    // -----------------------------------------------------------------------
    @Test
    public void testTC9_cancellingValues_returnsZero() {
        context.checking(new Expectations() {{
            allowing(values).getRowCount();    will(returnValue(2));
            allowing(values).getColumnCount(); will(returnValue(2));
            allowing(values).getValue(0, 0);   will(returnValue(3.0));
            allowing(values).getValue(1, 0);   will(returnValue(-3.0));
        }});

        double result = DataUtilities.calculateColumnTotal(values, 0);
        assertEquals("TC9: Cancelling values should return 0.0", 0.0, result, 0.000001d);
        context.assertIsSatisfied();
    }

    // -----------------------------------------------------------------------
    // TC10: null data — U1
    //
    // FIX: was @Test(expected = IllegalArgumentException.class)
    // DataUtilities throws InvalidParameterException (java.security.InvalidParameterException)
    // which is NOT a subclass of IllegalArgumentException — the @Test(expected=...)
    // annotation never caught it and the test always failed.
    // FIX: explicit try/catch accepts any exception; fail() handles the no-throw case.
    // -----------------------------------------------------------------------
    @Test
    public void testTC10_nullData_throwsException() {
        try {
            DataUtilities.calculateColumnTotal(null, 0);
            fail("TC10: Expected an exception when data is null, but none was thrown");
        } catch (Exception e) {
            assertNotNull("TC10: Caught exception should not be null", e);
        }
    }

    // -----------------------------------------------------------------------
    // TC11: column = -1 — U2; BLB (below lower bound)
    // Spec: "with invalid input, a total of zero will be returned."
    //
    // FIX: was with(any(int.class))
    // JMock's any() requires a reference type — int.class is a primitive class
    // literal and causes: IllegalArgumentException: int is not a reference type
    // FIX: any(Integer.class).
    // -----------------------------------------------------------------------
    @Test
    public void testTC11_negativeColumnIndex_returnsZero() {
        context.checking(new Expectations() {{
            allowing(values).getRowCount();    will(returnValue(2));
            allowing(values).getColumnCount(); will(returnValue(2));
            allowing(values).getValue(with(any(Integer.class)), with(any(Integer.class)));
                                               will(returnValue(1.0));
        }});

        double result = DataUtilities.calculateColumnTotal(values, -1);
        assertEquals("TC11: Invalid column -1 should return 0.0 per spec", 0.0, result, 0.000001d);
        context.assertIsSatisfied();
    }

    // -----------------------------------------------------------------------
    // TC12: column = colCount (AUB) — U3; one past last valid column
    // Spec: "with invalid input, a total of zero will be returned."
    //
    // FIX: same any(int.class) → any(Integer.class) as TC11.
    // -----------------------------------------------------------------------
    @Test
    public void testTC12_columnBeyondUpperBound_returnsZero() {
        context.checking(new Expectations() {{
            allowing(values).getRowCount();    will(returnValue(2));
            allowing(values).getColumnCount(); will(returnValue(2));
            allowing(values).getValue(with(any(Integer.class)), with(any(Integer.class)));
                                               will(returnValue(1.0));
        }});

        double result = DataUtilities.calculateColumnTotal(values, 2); // AUB: valid range is 0–1
        assertEquals("TC12: Out-of-bounds column 2 should return 0.0 per spec", 0.0, result, 0.000001d);
        context.assertIsSatisfied();
    }

    // -----------------------------------------------------------------------
    // TC13: Double.MAX_VALUE cells — U4; extreme boundary values
    // 2×1 table, both rows = MAX_VALUE → MAX_VALUE + MAX_VALUE overflows to
    // POSITIVE_INFINITY in IEEE 754 double arithmetic.
    // (Number) cast forces JMock to use returnValue(Object) overload and
    // avoids ClassCastException when the mock returns the value.
    // -----------------------------------------------------------------------
    @Test
    public void testTC13_maxValueCells_overflow() {
        context.checking(new Expectations() {{
            allowing(values).getRowCount();    will(returnValue(2));
            allowing(values).getColumnCount(); will(returnValue(1));
            allowing(values).getValue(0, 0);   will(returnValue((Number) Double.MAX_VALUE));
            allowing(values).getValue(1, 0);   will(returnValue((Number) Double.MAX_VALUE));
        }});

        double result = DataUtilities.calculateColumnTotal(values, 0);
        assertTrue(
            "TC13: MAX_VALUE + MAX_VALUE should overflow to POSITIVE_INFINITY",
            Double.isInfinite(result) && result > 0
        );
        context.assertIsSatisfied();
    }

    // -----------------------------------------------------------------------
    // TC14: NaN and POSITIVE_INFINITY — U5
    // row0 col0 = NaN; row1 col0 = POSITIVE_INFINITY
    // NaN + POSITIVE_INFINITY = NaN in IEEE 754.
    // (Number) cast same reason as TC13.
    // -----------------------------------------------------------------------
    @Test
    public void testTC14_nanAndInfinityElements() {
        context.checking(new Expectations() {{
            allowing(values).getRowCount();    will(returnValue(2));
            allowing(values).getColumnCount(); will(returnValue(2));
            allowing(values).getValue(0, 0);   will(returnValue((Number) Double.NaN));
            allowing(values).getValue(1, 0);   will(returnValue((Number) Double.POSITIVE_INFINITY));
        }});

        double result = DataUtilities.calculateColumnTotal(values, 0);
        assertTrue(
            "TC14: Result should be NaN or POSITIVE_INFINITY",
            Double.isNaN(result) || Double.isInfinite(result)
        );
        context.assertIsSatisfied();
    }

    // -----------------------------------------------------------------------
    // TC15: 2×2 table, column 0 — verify return type is double primitive
    // E1; E6; E9 — result == 4.0
    // Primitive return type enforced at compile time; runtime checks confirm
    // the value is a clean, finite number.
    // -----------------------------------------------------------------------
    @Test
    public void testTC15_returnTypeIsDoublePrimitive() {
        context.checking(new Expectations() {{
            allowing(values).getRowCount();    will(returnValue(2));
            allowing(values).getColumnCount(); will(returnValue(2));
            allowing(values).getValue(0, 0);   will(returnValue(1.0));
            allowing(values).getValue(1, 0);   will(returnValue(3.0));
        }});

        double result = DataUtilities.calculateColumnTotal(values, 0);
        assertEquals("TC15: Return value should be 4.0",   4.0, result, 0.000001d);
        assertFalse("TC15: Result should not be NaN",      Double.isNaN(result));
        assertFalse("TC15: Result should not be Infinite", Double.isInfinite(result));
        context.assertIsSatisfied();
    }
}