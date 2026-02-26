package org.jfree.data.test;

import static org.junit.Assert.*;
import org.jfree.data.DataUtilities;
import org.junit.*;

/**
 * Unit tests for the createNumberArray2D(double[][]) method of
 * org.jfree.data.DataUtilities.
 *
 * Method under test:
 *   public static Number[][] createNumberArray2D(double[][] data)
 *   Constructs a 2D array of Number (Double) objects from a corresponding
 *   2D array of double primitives. Throws IllegalArgumentException if data
 *   is null.
 *
 * Test strategy: Equivalence class partitioning and boundary value analysis.
 * See test_case_design/data_utilities_createNumberArray2D_method_test_case_design.md
 * for the full test case design.
 *
 * Note: createNumberArray2D() accepts a plain double[][] — no interface
 * parameter is involved, so mocking is not required for this method. Tests
 * are driven directly with concrete double[][] inputs.
 *
 * Equivalence classes (outer array length — number of rows):
 *   E1: outer length > 1 — multiple rows (NOM)
 *   E2: outer length = 1 — single-row array (ALB)
 *   E3: outer length = 0 — empty outer array (LB)
 *   U1: null             — throws IllegalArgumentException
 *
 * Equivalence classes (inner array length — number of columns):
 *   E4: all rows same length — rectangular (NOM)
 *   E5: rows of different lengths — jagged
 *   E6: inner length = 1 — single-column (ALB for inner)
 *   E7: inner length = 0 — empty row (LB for inner)
 *
 * Equivalence classes (element values):
 *   E8: normal positive/negative doubles
 *   U2: Double.NaN / POSITIVE_INFINITY / NEGATIVE_INFINITY
 *   U3: Double.MAX_VALUE / Double.MIN_VALUE
 *
 * Author: Shuvam
 */
public class DataUtilitiesCreateNumberArray2DTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    // -------------------------------------------------------------------------
    // Test Case 1
    // Input: {{1.0, 2.0}, {3.0, 4.0}} — NOM rectangular 2×2 (E1, E4, E8)
    // Expected: Number[][] matching all values exactly
    // -------------------------------------------------------------------------
    @Test
    public void createNumberArray2DWithRectangularArray() {
        // This test covers: E1 (multiple rows); E4 (rectangular, equal-length rows);
        //   E8 (normal values); NOM for both dimensions
        // Input: {{1.0, 2.0}, {3.0, 4.0}}  Expected: matching Number[][]
        double[][] input = {{1.0, 2.0}, {3.0, 4.0}};
        Number[][] result = DataUtilities.createNumberArray2D(input);
        assertNotNull("Result should not be null for rectangular input", result);
        assertEquals("result[0][0] should equal 1.0", 1.0, result[0][0].doubleValue(), .000000001d);
        assertEquals("result[0][1] should equal 2.0", 2.0, result[0][1].doubleValue(), .000000001d);
        assertEquals("result[1][0] should equal 3.0", 3.0, result[1][0].doubleValue(), .000000001d);
        assertEquals("result[1][1] should equal 4.0", 4.0, result[1][1].doubleValue(), .000000001d);
    }

    // -------------------------------------------------------------------------
    // Test Case 2
    // Input: {{1.0}, {2.0, 3.0}} — jagged array (E1, E5)
    // Expected: result[0].length = 1, result[1].length = 2
    // -------------------------------------------------------------------------
    @Test
    public void createNumberArray2DWithJaggedArray() {
        // This test covers: E1 (multiple rows); E5 (jagged — unequal row lengths)
        // Input: {{1.0}, {2.0, 3.0}}
        // Expected: result has 2 rows; first row length 1, second row length 2
        double[][] input = {{1.0}, {2.0, 3.0}};
        Number[][] result = DataUtilities.createNumberArray2D(input);
        assertNotNull("Result should not be null for jagged input", result);
        assertEquals("result[0] should have length 1", 1, result[0].length);
        assertEquals("result[1] should have length 2", 2, result[1].length);
        assertEquals("result[0][0] should equal 1.0", 1.0, result[0][0].doubleValue(), .000000001d);
        assertEquals("result[1][0] should equal 2.0", 2.0, result[1][0].doubleValue(), .000000001d);
        assertEquals("result[1][1] should equal 3.0", 3.0, result[1][1].doubleValue(), .000000001d);
    }

    // -------------------------------------------------------------------------
    // Test Case 3
    // Input: {{1.0, 2.0, 3.0}} — single-row array (E2, E4, ALB outer)
    // Expected: 1 row of 3 elements
    // -------------------------------------------------------------------------
    @Test
    public void createNumberArray2DWithSingleRow() {
        // This test covers: E2 (outer length = 1); E4 (uniform row length);
        //   ALB boundary for outer array size = 1
        // Input: {{1.0, 2.0, 3.0}}  Expected: Number[][] with 1 row, 3 elements
        double[][] input = {{1.0, 2.0, 3.0}};
        Number[][] result = DataUtilities.createNumberArray2D(input);
        assertNotNull("Result should not be null for single-row input", result);
        assertEquals("Result should have 1 row", 1, result.length);
        assertEquals("Row 0 should have 3 elements", 3, result[0].length);
        assertEquals("result[0][0] should equal 1.0", 1.0, result[0][0].doubleValue(), .000000001d);
        assertEquals("result[0][2] should equal 3.0", 3.0, result[0][2].doubleValue(), .000000001d);
    }

    // -------------------------------------------------------------------------
    // Test Case 4
    // Input: {{1.0}, {2.0}, {3.0}} — single column (E1, E6, ALB inner)
    // Expected: 3 rows each of length 1
    // -------------------------------------------------------------------------
    @Test
    public void createNumberArray2DWithSingleColumn() {
        // This test covers: E1 (multiple rows); E6 (inner length = 1);
        //   ALB boundary for inner array size = 1
        // Input: {{1.0}, {2.0}, {3.0}}  Expected: Number[][] with 3 rows, 1 element each
        double[][] input = {{1.0}, {2.0}, {3.0}};
        Number[][] result = DataUtilities.createNumberArray2D(input);
        assertNotNull("Result should not be null for single-column input", result);
        assertEquals("Result should have 3 rows", 3, result.length);
        assertEquals("Each row should have 1 element", 1, result[0].length);
        assertEquals("result[0][0] should equal 1.0", 1.0, result[0][0].doubleValue(), .000000001d);
        assertEquals("result[2][0] should equal 3.0", 3.0, result[2][0].doubleValue(), .000000001d);
    }

    // -------------------------------------------------------------------------
    // Test Case 5
    // Input: {} — empty outer array (E3, LB outer)
    // Expected: result.length == 0
    // -------------------------------------------------------------------------
    @Test
    public void createNumberArray2DWithEmptyOuterArray() {
        // This test covers: E3 (outer length = 0); LB boundary for outer array size
        // Input: {}  Expected: Number[][] with length 0
        double[][] input = {};
        Number[][] result = DataUtilities.createNumberArray2D(input);
        assertNotNull("Result should not be null for empty outer array", result);
        assertEquals("Result outer length should be 0", 0, result.length);
    }

    // -------------------------------------------------------------------------
    // Test Case 6
    // Input: {{}} — single row that is itself empty (E2, E7, LB inner)
    // Expected: result.length == 1, result[0].length == 0
    // -------------------------------------------------------------------------
    @Test
    public void createNumberArray2DWithEmptyInnerRow() {
        // This test covers: E2 (outer length = 1); E7 (inner length = 0);
        //   LB boundary for inner array size = 0
        // Input: {{}}  Expected: 1 row, that row has length 0
        double[][] input = {{}};
        Number[][] result = DataUtilities.createNumberArray2D(input);
        assertNotNull("Result should not be null for input with empty row", result);
        assertEquals("Result should have 1 row", 1, result.length);
        assertEquals("result[0] should have length 0", 0, result[0].length);
    }

    // -------------------------------------------------------------------------
    // Test Case 7
    // Input: null — (U1), null not permitted per spec
    // Expected: throws IllegalArgumentException
    // -------------------------------------------------------------------------
    @Test(expected = IllegalArgumentException.class)
    public void createNumberArray2DWithNullShouldThrowException() {
        // This test covers: U1 (null input); must throw IllegalArgumentException
        // Input: null  Expected: throws IllegalArgumentException
        DataUtilities.createNumberArray2D(null);
    }

    // -------------------------------------------------------------------------
    // Test Case 8
    // Input: {{Double.NaN, 1.0}, {2.0, Double.POSITIVE_INFINITY}} — (E1, E4, U2)
    // Expected: NaN and POSITIVE_INFINITY preserved in result
    // -------------------------------------------------------------------------
    @Test
    public void createNumberArray2DWithNaNAndInfinityValues() {
        // This test covers: E1 (multiple rows); E4 (rectangular); U2 (NaN/Infinity)
        // Input: {{Double.NaN, 1.0}, {2.0, Double.POSITIVE_INFINITY}}
        // Expected: result preserves NaN and POSITIVE_INFINITY
        double[][] input = {{Double.NaN, 1.0}, {2.0, Double.POSITIVE_INFINITY}};
        Number[][] result = DataUtilities.createNumberArray2D(input);
        assertNotNull("Result should not be null for NaN/Infinity input", result);
        assertEquals("result[0][0] should equal NaN",
                Double.NaN, result[0][0].doubleValue(), .000000001d);
        assertEquals("result[1][1] should equal POSITIVE_INFINITY",
                Double.POSITIVE_INFINITY, result[1][1].doubleValue(), .000000001d);
    }

    // -------------------------------------------------------------------------
    // Test Case 9
    // Input: {{Double.MAX_VALUE}, {Double.MIN_VALUE}} — UB and LB element values (U3)
    // Expected: MAX_VALUE and MIN_VALUE preserved in result
    // -------------------------------------------------------------------------
    @Test
    public void createNumberArray2DWithMaxAndMinDoubleValues() {
        // This test covers: E1 (multiple rows); E6 (single column); U3 (UB/LB elements)
        // Input: {{Double.MAX_VALUE}, {Double.MIN_VALUE}}
        // Expected: both extreme values preserved
        double[][] input = {{Double.MAX_VALUE}, {Double.MIN_VALUE}};
        Number[][] result = DataUtilities.createNumberArray2D(input);
        assertNotNull("Result should not be null for MAX/MIN input", result);
        assertEquals("result[0][0] should equal Double.MAX_VALUE",
                Double.MAX_VALUE, result[0][0].doubleValue(), .000000001d);
        assertEquals("result[1][0] should equal Double.MIN_VALUE",
                Double.MIN_VALUE, result[1][0].doubleValue(), .000000001d);
    }

    // -------------------------------------------------------------------------
    // Test Case 10
    // Input: {{1.0, 2.0}, {3.0, 4.0}} — verify outer array length (E1, E4)
    // Expected: result.length == input.length == 2
    // -------------------------------------------------------------------------
    @Test
    public void createNumberArray2DOuterLengthMatchesInput() {
        // This test covers: E1 (multiple rows); E4 (rectangular);
        //   verify outer array length contract
        // Input: {{1.0, 2.0}, {3.0, 4.0}}  Expected: result.length == 2
        double[][] input = {{1.0, 2.0}, {3.0, 4.0}};
        Number[][] result = DataUtilities.createNumberArray2D(input);
        assertEquals("Outer array length should match input outer length",
                input.length, result.length);
    }

    // -------------------------------------------------------------------------
    // Test Case 11
    // Input: {{1.0, 2.0}, {3.0, 4.0}} — verify inner array lengths (E1, E4)
    // Expected: result[i].length == input[i].length for each row i
    // -------------------------------------------------------------------------
    @Test
    public void createNumberArray2DInnerLengthMatchesInput() {
        // This test covers: E1 (multiple rows); E4 (rectangular);
        //   verify inner row length contract for each row
        // Input: {{1.0, 2.0}, {3.0, 4.0}}
        // Expected: result[0].length == 2 and result[1].length == 2
        double[][] input = {{1.0, 2.0}, {3.0, 4.0}};
        Number[][] result = DataUtilities.createNumberArray2D(input);
        assertEquals("Inner row 0 length should match input[0] length",
                input[0].length, result[0].length);
        assertEquals("Inner row 1 length should match input[1] length",
                input[1].length, result[1].length);
    }

    // -------------------------------------------------------------------------
    // Test Case 12
    // Input: {{1.0, 2.0}, {3.0, 4.0}} — verify all elements are Double (E1, E4, E8)
    // Expected: every element is instanceof Double
    // -------------------------------------------------------------------------
    @Test
    public void createNumberArray2DElementsAreInstancesOfDouble() {
        // This test covers: E1 (multiple rows); E4 (rectangular); E8 (normal values);
        //   verify element type contract — spec says returns arrays of Double
        // Input: {{1.0, 2.0}, {3.0, 4.0}}  Expected: all elements instanceof Double
        double[][] input = {{1.0, 2.0}, {3.0, 4.0}};
        Number[][] result = DataUtilities.createNumberArray2D(input);
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                assertTrue("Element at [" + i + "][" + j + "] should be instanceof Double",
                        result[i][j] instanceof Double);
            }
        }
    }

    @After
    public void tearDown() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }
}
