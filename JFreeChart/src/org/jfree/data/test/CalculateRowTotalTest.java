package org.jfree.data.test;

import static org.junit.Assert.*;
import java.security.InvalidParameterException;

import org.jfree.data.Values2D;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;
import org.jfree.data.DataUtilities;

public class CalculateRowTotalTest {

    private Mockery context;
    private Values2D values;

    @Before
    public void setUp() {
        context = new Mockery();
        values = context.mock(Values2D.class);
    }

    @Test
    public void testTC1_firstRow() {
        context.checking(new Expectations() {{
            allowing(values).getRowCount(); will(returnValue(2));
            allowing(values).getColumnCount(); will(returnValue(2));
            allowing(values).getValue(0, 0); will(returnValue(1.0));
            allowing(values).getValue(0, 1); will(returnValue(2.0));
        }});

        double result = DataUtilities.calculateRowTotal(values, 0);
        assertEquals(3.0, result, 1e-9);
    }

    @Test
    public void testTC2_secondRow_ALB() {
        context.checking(new Expectations() {{
            allowing(values).getRowCount(); will(returnValue(4));
            allowing(values).getColumnCount(); will(returnValue(2));
            allowing(values).getValue(1, 0); will(returnValue(3.0));
            allowing(values).getValue(1, 1); will(returnValue(4.0));
        }});

        double result = DataUtilities.calculateRowTotal(values, 1);
        assertEquals(7.0, result, 1e-9);
    }

    @Test
    public void testTC3_BUB_row() {
        context.checking(new Expectations() {{
            allowing(values).getRowCount(); will(returnValue(4));
            allowing(values).getColumnCount(); will(returnValue(2));
            allowing(values).getValue(2, 0); will(returnValue(5.0));
            allowing(values).getValue(2, 1); will(returnValue(6.0));
        }});

        double result = DataUtilities.calculateRowTotal(values, 2);
        assertEquals(11.0, result, 1e-9);
    }

    @Test
    public void testTC4_lastRow_UB() {
        context.checking(new Expectations() {{
            allowing(values).getRowCount(); will(returnValue(4));
            allowing(values).getColumnCount(); will(returnValue(2));
            allowing(values).getValue(3, 0); will(returnValue(7.0));
            allowing(values).getValue(3, 1); will(returnValue(8.0));
        }});

        double result = DataUtilities.calculateRowTotal(values, 3);
        assertEquals(15.0, result, 1e-9);
    }

    @Test
    public void testTC5_singleElement() {
        context.checking(new Expectations() {{
            allowing(values).getRowCount(); will(returnValue(1));
            allowing(values).getColumnCount(); will(returnValue(1));
            allowing(values).getValue(0, 0); will(returnValue(5.0));
        }});

        double result = DataUtilities.calculateRowTotal(values, 0);
        assertEquals(5.0, result, 1e-9);
    }

    @Test
    public void testTC6_allZeros() {
        context.checking(new Expectations() {{
            allowing(values).getRowCount(); will(returnValue(2));
            allowing(values).getColumnCount(); will(returnValue(2));
            allowing(values).getValue(0, 0); will(returnValue(0.0));
            allowing(values).getValue(0, 1); will(returnValue(0.0));
        }});

        double result = DataUtilities.calculateRowTotal(values, 0);
        assertEquals(0.0, result, 1e-9);
    }

    @Test
    public void testTC7_negativeValues() {
        context.checking(new Expectations() {{
            allowing(values).getRowCount(); will(returnValue(2));
            allowing(values).getColumnCount(); will(returnValue(2));
            allowing(values).getValue(0, 0); will(returnValue(-1.0));
            allowing(values).getValue(0, 1); will(returnValue(-2.0));
        }});

        double result = DataUtilities.calculateRowTotal(values, 0);
        assertEquals(-3.0, result, 1e-9);
    }

    @Test
    public void testTC8_mixedPositiveNegative() {
        context.checking(new Expectations() {{
            allowing(values).getRowCount(); will(returnValue(2));
            allowing(values).getColumnCount(); will(returnValue(2));
            allowing(values).getValue(1, 0); will(returnValue(3.0));
            allowing(values).getValue(1, 1); will(returnValue(-3.0));
        }});

        double result = DataUtilities.calculateRowTotal(values, 1);
        assertEquals(0.0, result, 1e-9);
    }

    // ------------------------------------------------------------
    // Invalid Data Object
    // ------------------------------------------------------------
    @Test(expected = InvalidParameterException.class)
    public void testTC9_nullData() {
        DataUtilities.calculateRowTotal(null, 0);
    }

    // ------------------------------------------------------------
    // Invalid Row Index (behavior depends on implementation)
    // ------------------------------------------------------------
    @Test(expected = IndexOutOfBoundsException.class)
    public void testTC10_rowBelowLowerBound() {

        context.checking(new Expectations() {{
            allowing(values).getRowCount(); will(returnValue(2));
            allowing(values).getColumnCount(); will(returnValue(2));
            allowing(values).getValue(-1, 0); will(throwException(new IndexOutOfBoundsException()));
        }});

        DataUtilities.calculateRowTotal(values, -1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testTC11_rowAboveUpperBound() {

        context.checking(new Expectations() {{
            allowing(values).getRowCount(); will(returnValue(2));
            allowing(values).getColumnCount(); will(returnValue(2));
            allowing(values).getValue(2, 0); will(throwException(new IndexOutOfBoundsException()));
        }});

        DataUtilities.calculateRowTotal(values, 2);
    }

    // ------------------------------------------------------------
    // Special Floating-Point Values
    // ------------------------------------------------------------

    @Test
    public void testTC12_NaNValue() {
        context.checking(new Expectations() {{
            allowing(values).getRowCount(); will(returnValue(1));
            allowing(values).getColumnCount(); will(returnValue(2));
            allowing(values).getValue(0, 0); will(returnValue(Double.NaN));
            allowing(values).getValue(0, 1); will(returnValue(1.0));
        }});

        double result = DataUtilities.calculateRowTotal(values, 0);
        assertTrue(Double.isNaN(result));
    }

    @Test
    public void testTC13_infinityValue() {
        context.checking(new Expectations() {{
            allowing(values).getRowCount(); will(returnValue(1));
            allowing(values).getColumnCount(); will(returnValue(2));
            allowing(values).getValue(0, 0); will(returnValue(Double.POSITIVE_INFINITY));
            allowing(values).getValue(0, 1); will(returnValue(1.0));
        }});

        double result = DataUtilities.calculateRowTotal(values, 0);
        assertEquals(Double.POSITIVE_INFINITY, result, 0.0);
    }

    @Test
    public void testTC14_overflow_MAX_VALUE() {
        context.checking(new Expectations() {{
            allowing(values).getRowCount(); will(returnValue(1));
            allowing(values).getColumnCount(); will(returnValue(2));
            allowing(values).getValue(0, 0); will(returnValue(Double.MAX_VALUE));
            allowing(values).getValue(0, 1); will(returnValue(Double.MAX_VALUE));
        }});

        double result = DataUtilities.calculateRowTotal(values, 0);
        assertEquals(Double.POSITIVE_INFINITY, result, 0.0);
    }
}