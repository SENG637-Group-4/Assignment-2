package org.jfree.data.test;

import static org.junit.Assert.*;
import org.jfree.data.DataUtilities;
import org.junit.*;

/**
 * Unit tests for the createNumberArray(double[]) method of
 * org.jfree.data.DataUtilities.
 *
 * Method under test:
 *   public static Number[] createNumberArray(double[] data)
 *   Constructs an array of Number (Double) objects from an array of
 *   double primitives. Throws IllegalArgumentException if data is null.
 *
 * Test strategy: Equivalence class partitioning and boundary value analysis.
 * See test_case_design/data_utilities_createNumberArray_method_test_case_design.md
 * for the full test case design.
 *
 * Note: createNumberArray() accepts a plain double[] — no interface parameter
 * is involved, so mocking is not required for this method. Tests are driven
 * directly with concrete double[] inputs.
 *
 * Equivalence classes (array length):
 *   E1: length > 1   — standard non-empty array (NOM)
 *   E2: length = 1   — single-element array (ALB)
 *   E3: length = 0   — empty array (LB)
 *   U1: null         — throws IllegalArgumentException
 *
 * Equivalence classes (element values):
 *   E4: all-positive doubles
 *   E5: all-negative doubles
 *   E6: mixed values (positive, negative, zero)
 *   U2: Double.NaN
 *   U3: Double.POSITIVE_INFINITY / NEGATIVE_INFINITY
 *   U4: Double.MAX_VALUE / Double.MIN_VALUE
 *
 * Author: Shuvam
 */
public class DataUtilitiesCreateNumberArrayTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    // -------------------------------------------------------------------------
    // Test Case 1
    // Input: {1.0, 2.5, 3.0} — NOM length (E1), all-positive (E4)
    // Expected: Number[]{1.0, 2.5, 3.0}
    // -------------------------------------------------------------------------
    @Test
    public void createNumberArrayWithPositiveValues() {
        // This test covers: E1 (length > 1); E4 (all-positive doubles); NOM
        // Input: {1.0, 2.5, 3.0}  Expected: Number[]{1.0, 2.5, 3.0}
        double[] input = {1.0, 2.5, 3.0};
        Number[] result = DataUtilities.createNumberArray(input);
        assertNotNull("Result should not be null for valid positive input", result);
        assertEquals("Result[0] should equal 1.0", 1.0, result[0].doubleValue(), .000000001d);
        assertEquals("Result[1] should equal 2.5", 2.5, result[1].doubleValue(), .000000001d);
        assertEquals("Result[2] should equal 3.0", 3.0, result[2].doubleValue(), .000000001d);
    }

    // -------------------------------------------------------------------------
    // Test Case 2
    // Input: {-1.0, -2.5, -3.0} — NOM length (E1), all-negative (E5)
    // Expected: Number[]{-1.0, -2.5, -3.0}
    // -------------------------------------------------------------------------
    @Test
    public void createNumberArrayWithNegativeValues() {
        // This test covers: E1 (length > 1); E5 (all-negative doubles); NOM
        // Input: {-1.0, -2.5, -3.0}  Expected: Number[]{-1.0, -2.5, -3.0}
        double[] input = {-1.0, -2.5, -3.0};
        Number[] result = DataUtilities.createNumberArray(input);
        assertNotNull("Result should not be null for valid negative input", result);
        assertEquals("Result[0] should equal -1.0", -1.0, result[0].doubleValue(), .000000001d);
        assertEquals("Result[1] should equal -2.5", -2.5, result[1].doubleValue(), .000000001d);
        assertEquals("Result[2] should equal -3.0", -3.0, result[2].doubleValue(), .000000001d);
    }

    // -------------------------------------------------------------------------
    // Test Case 3
    // Input: {-1.0, 0.0, 3.0} — NOM length (E1), mixed values with zero (E6)
    // Expected: Number[]{-1.0, 0.0, 3.0}
    // -------------------------------------------------------------------------
    @Test
    public void createNumberArrayWithMixedValuesIncludingZero() {
        // This test covers: E1 (length > 1); E6 (mixed: negative, zero, positive)
        // Input: {-1.0, 0.0, 3.0}  Expected: Number[]{-1.0, 0.0, 3.0}
        double[] input = {-1.0, 0.0, 3.0};
        Number[] result = DataUtilities.createNumberArray(input);
        assertNotNull("Result should not be null for mixed input", result);
        assertEquals("Result[0] should equal -1.0", -1.0, result[0].doubleValue(), .000000001d);
        assertEquals("Result[1] should equal 0.0",   0.0, result[1].doubleValue(), .000000001d);
        assertEquals("Result[2] should equal 3.0",   3.0, result[2].doubleValue(), .000000001d);
    }

    // -------------------------------------------------------------------------
    // Test Case 4
    // Input: {} — empty array (E3), LB for array length = 0
    // Expected: empty Number[] with length 0
    // -------------------------------------------------------------------------
    @Test
    public void createNumberArrayWithEmptyArray() {
        // This test covers: E3 (length = 0); LB boundary for array size
        // Input: {}  Expected: Number[] of length 0
        double[] input = {};
        Number[] result = DataUtilities.createNumberArray(input);
        assertNotNull("Result should not be null for empty array input", result);
        assertEquals("Result length should be 0 for empty input", 0, result.length);
    }

    // -------------------------------------------------------------------------
    // Test Case 5
    // Input: {5.0} — single-element array (E2), ALB for array length = 1
    // Expected: Number[]{5.0}
    // -------------------------------------------------------------------------
    @Test
    public void createNumberArrayWithSingleElement() {
        // This test covers: E2 (length = 1); E4 (positive); ALB boundary for array size
        // Input: {5.0}  Expected: Number[]{5.0}
        double[] input = {5.0};
        Number[] result = DataUtilities.createNumberArray(input);
        assertNotNull("Result should not be null for single-element input", result);
        assertEquals("Result length should be 1 for single-element input", 1, result.length);
        assertEquals("Result[0] should equal 5.0", 5.0, result[0].doubleValue(), .000000001d);
    }

    // -------------------------------------------------------------------------
    // Test Case 6
    // Input: null — (U1), null not permitted per spec
    // Expected: throws IllegalArgumentException
    // -------------------------------------------------------------------------
    @Test(expected = IllegalArgumentException.class)
    public void createNumberArrayWithNullShouldThrowException() {
        // This test covers: U1 (null input); must throw IllegalArgumentException
        // Input: null  Expected: throws IllegalArgumentException
        DataUtilities.createNumberArray(null);
    }

    // -------------------------------------------------------------------------
    // Test Case 7
    // Input: {Double.NaN} — single NaN element (U2)
    // Expected: Number[] containing NaN
    // -------------------------------------------------------------------------
    @Test
    public void createNumberArrayWithNaNValue() {
        // This test covers: E2 (length = 1); U2 (NaN special value)
        // Input: {Double.NaN}  Expected: result[0] is NaN
        double[] input = {Double.NaN};
        Number[] result = DataUtilities.createNumberArray(input);
        assertNotNull("Result should not be null for NaN input", result);
        assertEquals("Result[0] should equal NaN",
                Double.NaN, result[0].doubleValue(), .000000001d);
    }

    // -------------------------------------------------------------------------
    // Test Case 8
    // Input: {Double.POSITIVE_INFINITY} — positive infinity element (U3)
    // Expected: Number[] containing POSITIVE_INFINITY
    // -------------------------------------------------------------------------
    @Test
    public void createNumberArrayWithPositiveInfinity() {
        // This test covers: E2 (length = 1); U3 (positive infinity)
        // Input: {Double.POSITIVE_INFINITY}  Expected: result[0] = POSITIVE_INFINITY
        double[] input = {Double.POSITIVE_INFINITY};
        Number[] result = DataUtilities.createNumberArray(input);
        assertNotNull("Result should not be null for infinity input", result);
        assertEquals("Result[0] should equal POSITIVE_INFINITY",
                Double.POSITIVE_INFINITY, result[0].doubleValue(), .000000001d);
    }

    // -------------------------------------------------------------------------
    // Test Case 9
    // Input: {Double.MAX_VALUE, Double.MIN_VALUE} — UB and LB element values (U4)
    // Expected: Number[] with both extreme values preserved
    // -------------------------------------------------------------------------
    @Test
    public void createNumberArrayWithMaxAndMinDoubleValues() {
        // This test covers: E1 (length > 1); U4 (UB = MAX_VALUE, LB = MIN_VALUE)
        // Input: {Double.MAX_VALUE, Double.MIN_VALUE}
        // Expected: Number[]{MAX_VALUE, MIN_VALUE}
        double[] input = {Double.MAX_VALUE, Double.MIN_VALUE};
        Number[] result = DataUtilities.createNumberArray(input);
        assertNotNull("Result should not be null for MAX/MIN input", result);
        assertEquals("Result[0] should equal Double.MAX_VALUE",
                Double.MAX_VALUE, result[0].doubleValue(), .000000001d);
        assertEquals("Result[1] should equal Double.MIN_VALUE",
                Double.MIN_VALUE, result[1].doubleValue(), .000000001d);
    }

    // -------------------------------------------------------------------------
    // Test Case 10
    // Input: {1.0, 2.5, 3.0} — verify output array length matches input length
    // Expected: result.length == input.length == 3
    // -------------------------------------------------------------------------
    @Test
    public void createNumberArrayOutputLengthMatchesInputLength() {
        // This test covers: E1 (length > 1); E4 (positive); verify length contract
        // Input: {1.0, 2.5, 3.0}  Expected: result.length == 3
        double[] input = {1.0, 2.5, 3.0};
        Number[] result = DataUtilities.createNumberArray(input);
        assertEquals("Output array length should match input array length",
                input.length, result.length);
    }

    // -------------------------------------------------------------------------
    // Test Case 11
    // Input: {1.0, 2.5, 3.0} — verify each returned element is a Double instance
    // Expected: all elements are instanceof Double
    // -------------------------------------------------------------------------
    @Test
    public void createNumberArrayElementsAreInstancesOfDouble() {
        // This test covers: E1 (length > 1); E4 (positive);
        //   verify element type contract — spec says returns array of Double
        // Input: {1.0, 2.5, 3.0}  Expected: each element instanceof Double
        double[] input = {1.0, 2.5, 3.0};
        Number[] result = DataUtilities.createNumberArray(input);
        for (int i = 0; i < result.length; i++) {
            assertTrue("Element at index " + i + " should be an instance of Double",
                    result[i] instanceof Double);
        }
    }

    @After
    public void tearDown() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }
}
