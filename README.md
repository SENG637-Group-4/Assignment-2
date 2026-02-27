# SENG 637 - Software Testing, Reliability, and Quality

## Lab. Report \#2 – Requirements-Based Test Generation**

| Group 4      |
|-----------------|
| Zohara Kamal            |   
| Thanoshan Vijayanandan          |   
| Minh Le                |   
| Shuvam Agarwala              | 

# Table of Contents

1. [Introduction](#1-introduction)
2. [Detailed description of unit test strategy](#2-detailed-description-of-unit-test-strategy)
3. [Test cases developed](#3-test-cases-developed)
4. [Test case result](#4-test-case-result)
5. [How the team work/effort was divided and managed](#5-how-the-team-workeffort-was-divided-and-managed)
6. [Difficulties encountered, challenges overcome, and lessons learned](#6-difficulties-encountered-challenges-overcome-and-lessons-learned)
7. [Comments/feedback on the lab itself](#7-commentsfeedback-on-the-lab-itself)

# 1 Introduction

This assignment focuses on black-box testing of five methods selected from two different classes: `org.jfree.data.Range` and `org.jfree.data.DataUtilities`

The `Range` class represents an immutable range of numerical values. It includes 15 methods (both instance and static), such as `combine` and `constrain`, which perform different operations on ranges.

The `DataUtilities` class provides utility methods that support various data-related classes. It contains 5 static methods, including `createNumberArray2D` and `createNumberArray`, which help in creating and managing numerical data structures.

# 2 Detailed description of unit test strategy

When designing the test cases, we first identified the valid and invalid inputs for each method parameter. Based on these inputs, we formed equivalence classes and performed boundary value analysis. The detailed test case design for each method is provided below:

**Range class:**
- `contains(double value)` - [Test Case Design](test_case_design/range_class_contains_method_test_case_design.md)
- `getLength()` - [Test Case Design](test_case_design/range_class_getLength_method_test_case_design.md)
- `shift(Range base, double delta, boolean allowZeroCrossing)` - [Test Case Design](test_case_design/range_class_shift_method_test_case_design.md)
- `expandToInclude(Range range, double value)` - [Test Case Design](test_case_design/range_class_%20expandToInclude_method_test_case_design.md)
- `intersects(double lower, double upper)` - [Test Case Design](test_case_design/range_class_%20intersects_method_test_case_design.md)

**DataUtilities class:**
- `createNumberArray(double[] data)` - [Test Case Design](test_case_design/data_utilities_createNumberArray_method_test_case_design.md)
- `createNumberArray2D(double[][] data)` - [Test Case Design](test_case_design/data_utilities_createNumberArray2D_method_test_case_design.md)
- `calculateColumnTotal(Values2D data, int column)` - [Test Case Design](test_case_design/data_utilities_calculateColumnTotal_method_test_case_design.md)
- `calculateRowTotal(Values2D data, int row)` - [Test Case Design](test_case_design/data_utilities_calculateRowTotal_method_test_case_design.md)
- `getCumulativePercentages(KeyedValues data)` - [Test Case Design](test_case_design/data_utilities_getCumulativePercentages_method_test_case_design.md)


# 3 Test cases developed
The detailed descriptions of the test cases developed for each test method are provided below:

**Range class:**
- `contains(double value)` - [Test Cases](test_case_design/range_class_contains_method_test_case_design.md#set-of-test-cases-based-on-equivalence-classes-and-boundary-value-analysis)
- `getLength()` - [Test Cases](test_case_design/range_class_getLength_method_test_case_design.md#set-of-test-cases-based-on-equivalence-classes-and-boundary-value-analysis)
- `shift(Range base, double delta, boolean allowZeroCrossing)` - [Test Cases](test_case_design/range_class_shift_method_test_case_design.md#set-of-test-cases-based-on-equivalence-classes-and-boundary-value-analysis)
- `expandToInclude(Range range, double value)` - [Test Cases](test_case_design/range_class_%20expandToInclude_method_test_case_design.md#set-of-test-cases-based-on-equivalence-classes-and-boundary-value-analysis)
- `intersects(double lower, double upper)` - [Test Cases](test_case_design/range_class_%20intersects_method_test_case_design.md#set-of-test-cases-based-on-equivalence-classes-and-boundary-value-analysis)

**DataUtilities class:**
- `createNumberArray(double[] data)` - [Test Cases](test_case_design/data_utilities_createNumberArray_method_test_case_design.md#set-of-test-cases-based-on-equivalence-classes-and-boundary-value-analysis)
- `createNumberArray2D(double[][] data)` - [Test Cases](test_case_design/data_utilities_createNumberArray2D_method_test_case_design.md#set-of-test-cases-based-on-equivalence-classes-and-boundary-value-analysis)
- `calculateColumnTotal(Values2D data, int column)` - [Test Case](test_case_design/data_utilities_calculateColumnTotal_method_test_case_design.md#test-cases)
- `calculateRowTotal(Values2D data, int row)` - [Test Case](test_case_design/data_utilities_calculateRowTotal_method_test_case_design.md#test-cases)
- `getCumulativePercentages(KeyedValues data)` - [Test Case](test_case_design/data_utilities_getCumulativePercentages_method_test_case_design.md#test-cases)

# 4 Test case result
We totally run 120 unit test cases across 10 methods. Among 120, we observed 63 pass test cases and 57 failure unit cases. 0 error

<img width="482" height="554" alt="image" src="https://github.com/user-attachments/assets/7dc4855e-5e8f-4498-99ae-aa0354e662b6" />

### Test Results for `contains(double value)`

| Test Case Name | Input Partitions | Status |
|---------------|------------------|--------|
| isValidNominalValueWithinRange | 54 |  |
| isValidLowerBoundaryValue | 5  |  |
| isValidUpperBoundaryValue | 95  |  |
| isValidJustBelowUpperBoundary | 94  |  |
| isInvalidBelowLowerBoundary | 4  |  |
| isInvalidAboveUpperBoundary | 96  |  |
| isValidJustAboveLowerBoundary | 6  |  |
| isInvalidDoubleMaximumValue | double maximum value  |  |
| isInvalidDoubleMinimumValue | double minimum value |  |

### Test Results for `getLength()`


| Test Case Name | Input Partitions | Status |
|---------------|------------------|--------|
| getLengthPositiveRange | Range(2.0, 6.0)  |  |
| getLengthCrossZeroRange | Range(-10.0, 10.0)  |  |
| getLengthNegativeOnlyRange | Range(-10.0, -2.0)  |  |
| getLengthZeroLengthRange | Range(5.0, 5.0) |  |
| getLengthLowerBoundAtZero | Range(0.0, 5.0) |  |
| getLengthUpperBoundAtZero | Range(-5.0, 0.0)  |  |
| getLengthBothBoundsAtZero | Range(0.0, 0.0) |  |
| getLengthTinyCrossZeroRange | Range(-0.0001, 0.0001)  |  |
| getLengthUpperJustBelowZero | Range(-1.0, -0.0001) |  |
| getLengthLowerJustAboveZero | Range(0.0001, 1.0)  |  |

### Test Results for `shift(Range base, double delta, boolean allowZeroCrossing)`

| Test Case Name | Input Partitions | Status |
|---------------|------------------|--------|
| shiftNullBaseThrowsInvalidParameterException | null, any, any (U1) |  |
| shiftZeroDeltaAllowZeroCrossingReturnsUnchangedRange | Range(-5,5), 0, true  |  |
| shiftRightLowerJustBelowZeroDisallowZeroCrossing | Range(-5,5), 4.9, false  |  |
| shiftRightLowerAtZeroDisallowZeroCrossing | Range(-5,5), 5, false  |  |
| shiftRightLowerCrossesZeroDisallowZeroCrossing | Range(-5,5), 5.1, false  |  |
| shiftRightUpperJustBelowZeroDisallowZeroCrossing | Range(-6,-2), 1.9, false |  |
| shiftRightUpperAtZeroDisallowZeroCrossing | Range(-6,-2), 2, false  |  |
| shiftRightUpperCrossesZeroDisallowZeroCrossing | Range(-6,-2), 2.1, false |  |
| shiftRightAllowZeroCrossingNormalCase | Range(-5,5), 2, true  |  |
| shiftLeftAllowZeroCrossingMixedRange | Range(-6,4), -2, true  |  |
| shiftLeftAllowZeroCrossingPositiveRangeBecomesMixed | Range(2,6), -5, true  |  |

### Test Results for `expandToInclude(Range range, double value)`

| Test Case Name | Input Partitions | Status |
|---------------|------------------|--------|
| expandNullRangeWithZeroValue | null, 0 |  |
| expandNullRangeWithNegativeValue | null, -10 |  |
| expandLowerBoundJustBelow | Range(-10, 10), -10.00001 |  |
| expandValueEqualsLowerBound | Range(-10, 10), -10 |  |
| expandValueJustAboveLowerBound | Range(-10, 10), -9.99999 |  |
| expandNominalValueWithinRange | Range(-10, 10), 0 |  |
| expandValueJustBelowUpperBound | Range(-10, 10), 9.99999 |  |
| expandValueEqualsUpperBound | Range(-10, 10), 10 |  |
| expandUpperBoundJustAbove | Range(-10, 10), 10.00001 |  |
| expandUpperBoundToDoubleMaxValue | Range(-10, 10), Double.MAX_VALUE |  |
| expandLowerBoundToNegativeDoubleMaxValue | Range(-10, 10), -Double.MAX_VALUE |  |
| expandWithDoubleMinValueInsideRange | Range(-10, 10), Double.MIN_VALUE |  |
| expandNullRangeWithNaNValue | null, Double.NaN |  |

### Test Results for `intersects(double lower, double upper)`

| Test Case Name | Input Partitions | Status |
|---------------|------------------|--------|
| intersectsEndsAtLowerBound | (-10.00001, -10) |  |
| intersectsSlightOverlapAtLowerBound | (-10.00001, -9.99999) |  |
| intersectsFullySpansRange | (-10.00001, 10.00001) |  |
| intersectsStartsAtLowerBound | (-10, -9.99999) |  |
| intersectsExactRangeMatch | (-10, 10) |  |
| intersectsFullyInsideRange | (-1, 1) |  |
| intersectsEndsAtUpperBound | (9.99999, 10) |  |
| intersectsStartsAtUpperBound | (10, 10.00001) |  |
| intersectsWithDoubleMinValue | (Double.MIN_VALUE, 10.00001) |  |
| intersectsWithDoubleMaxValue | (-10.00001, Double.MAX_VALUE) |  |
| intersectsSinglePointInside | (0, 0) |  |
| intersectsWithNaNLowerBound | (Double.NaN, 1) |  |
| intersectsWithNaNUpperBound | (1, Double.NaN) |  |
| intersectsSinglePointAtLowerBound | (-10, -10) |  |
| intersectsSinglePointAtUpperBound | (10, 10) |  |

### Test Results for `createNumberArray(double[] data)`
| Test Case Name | Input Partitions | Status |
|---------------|------------------|--------|
| createNumberArrayAllPositiveValues | {1.0, 2.5, 3.0} |  |
| createNumberArrayAllNegativeValues | {-1.0, -2.5, -3.0} |  |
| createNumberArrayMixedValuesWithZero | {-1.0, 0.0, 3.0} |  |
| createNumberArrayEmptyArray | {} |  |
| createNumberArraySinglePositiveElement | {5.0} |  |
| createNumberArrayNullInput | null |  |
| createNumberArrayWithNaNElement | {Double.NaN} |  |
| createNumberArrayWithPositiveInfinity | {Double.POSITIVE_INFINITY} |  |
| createNumberArrayWithMaxAndMinValues | {Double.MAX_VALUE, Double.MIN_VALUE} |  |
| createNumberArrayVerifyOutputLength | {1.0, 2.5, 3.0} – verify result length |  |
| createNumberArrayVerifyElementType | {1.0, 2.5, 3.0} – verify element type |  |

### Test Results for `createNumberArray2D(double[][] data)`
| Test Case Name | Input Partitions | Status |
|---------------|------------------|--------|
| createNumberArray2DRectangular2x2 | {{1.0, 2.0}, {3.0, 4.0}} |  |
| createNumberArray2DJaggedArray | {{1.0}, {2.0, 3.0}} |  |
| createNumberArray2DSingleRow | {{1.0, 2.0, 3.0}} |  |
| createNumberArray2DSingleColumn | {{1.0}, {2.0}, {3.0}} |  |
| createNumberArray2DEmptyOuterArray | {} |  |
| createNumberArray2DSingleEmptyRow | {{}} |  |
| createNumberArray2DNullInput | null |  |
| createNumberArray2DWithNaNAndInfinity | {{Double.NaN, 1.0}, {2.0, Double.POSITIVE_INFINITY}} |  |
| createNumberArray2DWithMaxAndMinValues | {{Double.MAX_VALUE}, {Double.MIN_VALUE}} |  |
| createNumberArray2DVerifyOuterLength | {{1.0, 2.0}, {3.0, 4.0}} – verify outer length |  |
| createNumberArray2DVerifyInnerLengths | {{1.0, 2.0}, {3.0, 4.0}} – verify inner lengths |  |
| createNumberArray2DVerifyElementType | {{1.0, 2.0}, {3.0, 4.0}} – verify element type |  |

### Test Results for `calculateColumnTotal(Values2D data, int column)`
| Test Case Name | Input Partitions | Status |
|---------------|------------------|--------|
| calculateColumnTotalLowerBoundColumn2x2 | 2x2 table, column 0 |  |
| calculateColumnTotalUpperBoundColumn2x2 | 2x2 table, column 1 |  |
| calculateColumnTotalNominalColumn3x3 | 3x3 table, column 1 |  |
| calculateColumnTotalBeforeUpperBoundColumn3x3 | 3x3 table, column 1 (colCount-2) |  |
| calculateColumnTotalSingleRow | 1x3 table, column 0 |  |
| calculateColumnTotalSingleColumn | 3x1 table, column 0 |  |
| calculateColumnTotalEmptyTable | empty table, column 0 |  |
| calculateColumnTotalAllNullColumn | 2x2 table with all null values in column 0 |  |
| calculateColumnTotalSomeNullValues | 2x2 table with one null in column 0 |  |
| calculateColumnTotalAllZeroValues | 2x2 table with all zeros, column 0 |  |
| calculateColumnTotalValuesCancelOut | 2x2 table with cancelling values, column 0 |  |
| calculateColumnTotalAllNegativeValues | 2x2 table with all negative values, column 0 |  |
| calculateColumnTotalMixedValues | 2x2 table with mixed values, column 1 |  |
| calculateColumnTotalNullData | null data, column 0 |  |

### Test Results for `calculateRowTotal(Values2D data, int row)`

| Test Case Name | Input Partitions | Status |
|---------------|------------------|--------|
| calculateRowTotalLowerBoundRow | 2x2 table, row 0 |  |
| calculateRowTotalAboveLowerBoundRow | 4x2 table, row 1 |  |
| calculateRowTotalBeforeUpperBoundRow | 4x2 table, row 2 |  |
| calculateRowTotalUpperBoundRow | 4x2 table, row 3 |  |
| calculateRowTotalSingleCell | 1x1 table, row 0 |  |
| calculateRowTotalAllZeroValues | 2x2 table, row 0 (all zeros) |  |
| calculateRowTotalNegativeValues | 2x2 table, row 0 (negative values) |  |
| calculateRowTotalMixedValues | 2x2 table, row 1 (mixed values) |  |
| calculateRowTotalNullData | null data, row 0 |  |
| calculateRowTotalBelowLowerBoundIndex | 2x2 table, row -1 |  |
| calculateRowTotalAboveUpperBoundIndex | 2x2 table, row 2 |  |
| calculateRowTotalWithNaNElement | 1x2 table with NaN, row 0 |  |
| calculateRowTotalWithInfinityElement | 1x2 table with POSITIVE_INFINITY, row 0 |  |
| calculateRowTotalWithMaxValueOverflow | 1x2 table with Double.MAX_VALUE values, row 0 |  |

### Test Results for `getCumulativePercentages(KeyedValues data)`
| Test Case Name | Input Partitions | Status |
|---------------|------------------|--------|
| cumulativePercentagesNominalPositiveValues | {0→5, 1→9, 2→2} |  |
| cumulativePercentagesSingleElement | {0→10} |  |
| cumulativePercentagesEmptyInput | {} |  |
| cumulativePercentagesNullInput | null |  |
| cumulativePercentagesWithNegativeValue | {0→5, 1→-2, 2→3} |  |
| cumulativePercentagesEqualValues | {0→4, 1→4, 2→4} |  |
| cumulativePercentagesWithZeroValue | {0→5, 1→0, 2→3} |  |
| cumulativePercentagesAllZeroValues | {0→0, 1→0, 2→0} |  |
| cumulativePercentagesNonIntegerKeys | {"a"→6, "b"→4} |  |
| cumulativePercentagesWithNullValue | {0→null, 1→4} |  |
| cumulativePercentagesWithNaNValue | {0→NaN, 1→5} |  |
| cumulativePercentagesWithInfinityValue | {0→Infinity, 1→5} |  |
| cumulativePercentagesWithMaxValueDominance | {0→Double.MAX_VALUE, 1→1} |  |

# 5 How the team work/effort was divided and managed

We divided the test case design and test generation tasks among ourselves.

After a team member prepared a test case design, the other three members reviewed it. Once the design was successfully approved, the same member proceeded to implement the unit tests based on it. After the unit tests were created, the team conducted another review.

The table below summarizes how the test case design and unit test development tasks were distributed among the team members.

| Method | Member |
|--------|--------|
| `Range.expandToInclude(Range range, double value)` | Minh |
| `Range.intersects(double lower, double upper)` | Minh |
| `Range.contains(double value)` | Thanoshan |
| `Range.shift(Range base, double delta, boolean allowZeroCrossing)` | Thanoshan |
| `Range.getLength()` | Shuvam |
| `DataUtilities.createNumberArray(double[] data)` | Shuvam |
| `DataUtilities.createNumberArray2D(double[][] data)` | Shuvam |
| `DataUtilities.calculateColumnTotal(Values2D data, int column)` | Zohara |
| `DataUtilities.calculateRowTotal(Values2D data, int row)` | Zohara |
| `DataUtilities.getCumulativePercentages(KeyedValues data)` | Zohara |

# 6 Difficulties encountered, challenges overcome, and lessons learned

1. Initially, we added the JAR files using absolute paths when setting up the project. However, when another team member cloned the repository, the project did not work on their machine because the paths were machine-specific. To fix this issue, we created a folder named "lib" inside the project, placed all the required JAR files there, and referenced them using relative paths. After this change, the project worked correctly for everyone.
2. During the initial equivalence class partitioning of the test case design, we considered some unexpected inputs, such as non-numeric values for boolean types. For example, we thought about providing non-boolean values to `allowZeroCrossing` (`boolean`). We included this in the test case design. However, while creating the test cases, we realized that passing non-boolean values to a boolean parameter is not possible in Java due to compile-time type checking.
3. Another challenge was to decide the setup parameters for the instance variables that would ultimately be used by all the test cases. This was overcome by thorough discussions on the setup parameters that would be ideal and would cover requirements for the maximum number of test cases for a given class.
4. One important lesson we learned is that we should not rely only on the information provided in the assignment or documentation. It is important to check the official website or Maven Central repository of the library we are using to ensure that all required dependencies are included. Missing dependencies can cause unexpected errors.
5. Mocking is a useful technique for testing because it allows us to simulate objects. However, when mocking, we often have to guess how the object behaves based only on the API specification. Sometimes, the specification does not fully reflect what the code actually does. For example, the DataUtilities.getCumulativePercentages method states in the API that “The percentages are values between 0.0 and 1.0.” However, in practice, the method can return negative cumulative percentages depending on the input data, which matches our manual calculations. This difference between the API documentation and the actual behavior can confuse testers and make them question whether they implemented the mock correctly.

# 7 Comments/feedback on the lab itself

1. This assignment has given us a great opportunity to practically learn how to do black box testing using approaches like Equivalence Class Testing, Boundary Value Testing, and Robustness Testing.
2. We've learnt about popular Java testing framework
3. The assignment description file is thorough and well-organized, making it easy to understand and follow.
4. Additionally, the project setup guidelines were very helpful. Without them, we might have faced difficulties during setup and encountered compatibility issues.



