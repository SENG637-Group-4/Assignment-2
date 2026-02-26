package org.jfree.data.test;

import static org.junit.Assert.*;
import org.jfree.data.Range;
import org.junit.*;

/**
 * Unit tests for the contains() method of org.jfree.data.Range.
 *
 * Method under test:
 *   public boolean contains(double value)
 *   Returns true if the specified value is within the range and false otherwise.
 *
 * Test strategy: Equivalence class partitioning and boundary value analysis.
 * See test_case_design/range_class_contains_method_test_case_design.md
 * for the full test case design.
 *
 */
public class RangeContainsTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    // Test Case 1
    // Range(5, 95) with input 54: NOM (nominal value within range)
    // Expected: true
    @Test
    public void containsNominalValueWithinRange() {
        // This test covers: E1; NOM (nominal value within range)
        // Input: Range(5, 95), value = 54  Expected: true
        Range range = new Range(5, 95);
        assertTrue("Range(5, 95) should contain nominal value 54",
                range.contains(54));
    }

    // Test Case 2
    // Range(5, 95) with input 5: LB (value on the lower boundary)
    // Expected: true
    @Test
    public void containsValueAtLowerBoundary() {
        // This test covers: E1; LB (value on the lower boundary)
        // Input: Range(5, 95), value = 5  Expected: true
        Range range = new Range(5, 95);
        assertTrue("Range(5, 95) should contain lower boundary value 5",
                range.contains(5));
    }

    // Test Case 3
    // Range(5, 95) with input 95: UB (value on the upper boundary)
    // Expected: true
    @Test
    public void containsValueAtUpperBoundary() {
        // This test covers: E1; UB (value on the upper boundary)
        // Input: Range(5, 95), value = 95  Expected: true
        Range range = new Range(5, 95);
        assertTrue("Range(5, 95) should contain upper boundary value 95",
                range.contains(95));
    }

    // Test Case 4
    // Range(5, 95) with input 94: BUB (value just below the upper bound)
    // Expected: true
    @Test
    public void containsValueJustBelowUpperBound() {
        // This test covers: E1; BUB (value just below the upper bound)
        // Input: Range(5, 95), value = 94  Expected: true
        Range range = new Range(5, 95);
        assertTrue("Range(5, 95) should contain value 94 just below upper bound",
                range.contains(94));
    }

    // Test Case 5
    // Range(5, 95) with input 4: BLB (value just below the lower bound)
    // Expected: false
    @Test
    public void doesNotContainValueJustBelowLowerBound() {
        // This test covers: U1; BLB (value just below the lower bound)
        // Input: Range(5, 95), value = 4  Expected: false
        Range range = new Range(5, 95);
        assertFalse("Range(5, 95) should not contain value 4 just below lower bound",
                range.contains(4));
    }

    // Test Case 6
    // Range(5, 95) with input 96: AUB (value just above the upper bound)
    // Expected: false
    @Test
    public void doesNotContainValueJustAboveUpperBound() {
        // This test covers: U2; AUB (value just above the upper bound)
        // Input: Range(5, 95), value = 96  Expected: false
        Range range = new Range(5, 95);
        assertFalse("Range(5, 95) should not contain value 96 just above upper bound",
                range.contains(96));
    }

    // Test Case 7
    // Range(5, 95) with input 6: ALB (value just above the lower boundary)
    // Expected: true
    @Test
    public void containsValueJustAboveLowerBoundary() {
        // This test covers: E1; ALB (value just above the lower boundary)
        // Input: Range(5, 95), value = 6  Expected: true
        Range range = new Range(5, 95);
        assertTrue("Range(5, 95) should contain value 6 just above lower boundary",
                range.contains(6));
    }

    // Test Case 8
    // Range(5, 95) with input Double.MAX_VALUE: extreme upper value
    // Expected: false
    @Test
    public void doesNotContainDoubleMaxValue() {
        // This test covers: U2; extreme double maximum value
        // Input: Range(5, 95), value = Double.MAX_VALUE  Expected: false
        Range range = new Range(5, 95);
        assertFalse("Range(5, 95) should not contain Double.MAX_VALUE",
                range.contains(Double.MAX_VALUE));
    }

    // Test Case 9
    // Range(5, 95) with input -Double.MAX_VALUE: extreme lower value
    // Expected: false
    @Test
    public void doesNotContainDoubleMinValue() {
        // This test covers: U1; extreme double minimum value
        // Input: Range(5, 95), value = -Double.MAX_VALUE  Expected: false
        Range range = new Range(5, 95);
        assertFalse("Range(5, 95) should not contain -Double.MAX_VALUE",
                range.contains(-Double.MAX_VALUE));
    }

    @After
    public void tearDown() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }
}