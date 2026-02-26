package org.jfree.data.test;

import static org.junit.Assert.*;
import org.jfree.data.Range;
import org.junit.*;

/**
 * Unit tests for the getLength() method of org.jfree.data.Range.
 *
 * Method under test:
 *   public double getLength()
 *   Returns the length of the range (upper - lower).
 *
 * Test strategy: Equivalence class partitioning and boundary value analysis.
 * See test_case_design/range_class_getLength_method_test_case_design.md
 * for the full test case design.
 *
 * Equivalence classes:
 *   EC1: lower < 0, upper > 0  — cross-zero range
 *   EC2: lower >= 0, upper > 0 — positive/zero-lower range
 *   EC3: lower < 0, upper <= 0 — negative/zero-upper range
 *   EC4: lower = upper          — zero-length range
 *   EC5: extreme double values  — overflow boundary
 *
 * BVA boundary of interest: zero for each bound independently.
 *
 * Author: Shuvam
 */
public class RangeGetLengthTest {

    private Range exampleRange;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        exampleRange = new Range(-1, 1);
    }

    // -------------------------------------------------------------------------
    // Test Case 1
    // Range(2.0, 6.0): both bounds positive (EC2), NOM positive range
    // Expected: 6.0 - 2.0 = 4.0
    // -------------------------------------------------------------------------
    @Test
    public void getLengthOfPositiveRange() {
        // This test covers: EC2 (both positive); NOM positive range
        // Input: Range(2.0, 6.0)  Expected: 4.0
        Range range = new Range(2.0, 6.0);
        assertEquals("Length of Range(2.0, 6.0) should be 4.0",
                4.0, range.getLength(), .000000001d);
    }

    // -------------------------------------------------------------------------
    // Test Case 2
    // Range(-10.0, 10.0): cross-zero range (EC1), NOM
    // Expected: 10.0 - (-10.0) = 20.0
    // -------------------------------------------------------------------------
    @Test
    public void getLengthOfCrossZeroRange() {
        // This test covers: EC1 (lower negative, upper positive); NOM cross-zero range
        // Input: Range(-10.0, 10.0)  Expected: 20.0
        Range range = new Range(-10.0, 10.0);
        assertEquals("Length of Range(-10.0, 10.0) should be 20.0",
                20.0, range.getLength(), .000000001d);
    }

    // -------------------------------------------------------------------------
    // Test Case 3
    // Range(-10.0, -2.0): both bounds negative (EC3)
    // Expected: -2.0 - (-10.0) = 8.0
    // -------------------------------------------------------------------------
    @Test
    public void getLengthOfNegativeOnlyRange() {
        // This test covers: EC3 (both negative); negative-only range
        // Input: Range(-10.0, -2.0)  Expected: 8.0
        Range range = new Range(-10.0, -2.0);
        assertEquals("Length of Range(-10.0, -2.0) should be 8.0",
                8.0, range.getLength(), .000000001d);
    }

    // -------------------------------------------------------------------------
    // Test Case 4
    // Range(5.0, 5.0): lower = upper (EC4), zero-length range, LB = UB
    // Expected: 5.0 - 5.0 = 0.0
    // -------------------------------------------------------------------------
    @Test
    public void getLengthOfZeroLengthRange() {
        // This test covers: EC4 (lower = upper); zero-length range; LB = UB boundary
        // Input: Range(5.0, 5.0)  Expected: 0.0
        Range range = new Range(5.0, 5.0);
        assertEquals("Length of Range(5.0, 5.0) should be 0.0",
                0.0, range.getLength(), .000000001d);
    }

    // -------------------------------------------------------------------------
    // Test Case 5
    // Range(0.0, 5.0): lower exactly at zero (LB boundary for lower)
    // Expected: 5.0 - 0.0 = 5.0
    // -------------------------------------------------------------------------
    @Test
    public void getLengthWhenLowerBoundIsZero() {
        // This test covers: EC2; lower = 0.0 (LB boundary for lower variable)
        // Input: Range(0.0, 5.0)  Expected: 5.0
        Range range = new Range(0.0, 5.0);
        assertEquals("Length of Range(0.0, 5.0) should be 5.0",
                5.0, range.getLength(), .000000001d);
    }

    // -------------------------------------------------------------------------
    // Test Case 6
    // Range(-5.0, 0.0): upper exactly at zero (UB boundary for upper)
    // Expected: 0.0 - (-5.0) = 5.0
    // -------------------------------------------------------------------------
    @Test
    public void getLengthWhenUpperBoundIsZero() {
        // This test covers: EC3; upper = 0.0 (UB boundary for upper variable)
        // Input: Range(-5.0, 0.0)  Expected: 5.0
        Range range = new Range(-5.0, 0.0);
        assertEquals("Length of Range(-5.0, 0.0) should be 5.0",
                5.0, range.getLength(), .000000001d);
    }

    // -------------------------------------------------------------------------
    // Test Case 7
    // Range(0.0, 0.0): both bounds at zero (EC4 + E2 + E5)
    // Expected: 0.0 - 0.0 = 0.0
    // -------------------------------------------------------------------------
    @Test
    public void getLengthWhenBothBoundsAreZero() {
        // This test covers: EC4 (lower = upper = 0); both bounds at zero boundary
        // Input: Range(0.0, 0.0)  Expected: 0.0
        Range range = new Range(0.0, 0.0);
        assertEquals("Length of Range(0.0, 0.0) should be 0.0",
                0.0, range.getLength(), .000000001d);
    }

    // -------------------------------------------------------------------------
    // Test Case 8
    // Range(-0.0001, 0.0001): tiny range straddling zero (EC1), BLB/AUB
    // Expected: 0.0001 - (-0.0001) = 0.0002
    // -------------------------------------------------------------------------
    @Test
    public void getLengthOfTinyRangeStraddlingZero() {
        // This test covers: EC1; BLB for lower (just below zero), AUB for upper
        // Input: Range(-0.0001, 0.0001)  Expected: 0.0002
        Range range = new Range(-0.0001, 0.0001);
        assertEquals("Length of Range(-0.0001, 0.0001) should be 0.0002",
                0.0002, range.getLength(), .000000001d);
    }

    // -------------------------------------------------------------------------
    // Test Case 9
    // Range(-1.0, -0.0001): upper just below zero (EC3), BUB boundary
    // Expected: -0.0001 - (-1.0) = 0.9999
    // -------------------------------------------------------------------------
    @Test
    public void getLengthWhenUpperBoundIsJustBelowZero() {
        // This test covers: EC3; BUB for upper (just below zero boundary)
        // Input: Range(-1.0, -0.0001)  Expected: 0.9999
        Range range = new Range(-1.0, -0.0001);
        assertEquals("Length of Range(-1.0, -0.0001) should be 0.9999",
                0.9999, range.getLength(), .000000001d);
    }

    // -------------------------------------------------------------------------
    // Test Case 10
    // Range(0.0001, 1.0): lower just above zero (EC2), ALB boundary
    // Expected: 1.0 - 0.0001 = 0.9999
    // -------------------------------------------------------------------------
    @Test
    public void getLengthWhenLowerBoundIsJustAboveZero() {
        // This test covers: EC2; ALB for lower (just above zero boundary)
        // Input: Range(0.0001, 1.0)  Expected: 0.9999
        Range range = new Range(0.0001, 1.0);
        assertEquals("Length of Range(0.0001, 1.0) should be 0.9999",
                0.9999, range.getLength(), .000000001d);
    }

    @After
    public void tearDown() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }
}
