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

<img width="457" height="600" alt="image" src="https://github.com/user-attachments/assets/7b975c22-15a2-4775-8809-166f6e8473e1" />


### Test Results for `contains(double value)`

| Test Case Number | Input Partitions | Status |
|---------------|------------------|--------|
| 1 | 54 | pass |
| 2 | 5  | pass |
| 3 | 95  | pass |
| 4 | 94  | pass |
| 5 | 4  | pass |
| 6 | 96  | pass|
| 7 | 6  | pass |
| 8 | double maximum value  | pass |
| 9 | double minimum value | pass |

### Test Results for `getLength()`


| Test Case Number | Input Partitions | Status |
|---------------|------------------|--------|
| 1 | Range(2.0, 6.0)  | pass |
| 2 | Range(-10.0, 10.0)  | pass |
| 3 | Range(-10.0, -2.0)  | pass |
| 4 | Range(5.0, 5.0) | pass |
| 5 | Range(0.0, 5.0) | pass |
| 6 | Range(-5.0, 0.0)  |pass  |
| 7 | Range(0.0, 0.0) | pass |
| 8 | Range(-0.0001, 0.0001)  | pass |
| 9 | Range(-1.0, -0.0001) | pass |
| 10 | Range(0.0001, 1.0)  | pass |

### Test Results for `shift(Range base, double delta, boolean allowZeroCrossing)`

| Test Case Number | Input Partitions | Status |
|---------------|------------------|--------|
| 1 | null, any, any (U1) | pass |
| 2 | Range(-5,5), 0, true  | fail  |
| 3 | Range(-5,5), 4.9, false  | fail |
| 4 | Range(-5,5), 5, false  | fail |
| 5 | Range(-5,5), 5.1, false  | fail |
| 6 | Range(-6,-2), 1.9, false | fail |
| 7 | Range(-6,-2), 2, false  |fail  |
| 8 | Range(-6,-2), 2.1, false | fail |
| 9 | Range(-5,5), 2, true  | fail |
| 10 | Range(-6,4), -2, true  | fail |
| 11 | Range(2,6), -5, true  | fail |

### Test Results for `expandToInclude(Range range, double value)`

| Test Case Number | Input Partitions | Status |
|---------------|------------------|--------|
| 1 | null, 0 | pass |
| 2 | null, -10 | pass |
| 3 | Range(-10, 10), -10.00001 |fail  |
| 4 | Range(-10, 10), -10 | pass |
| 5 | Range(-10, 10), -9.99999 | fail |
| 6 | Range(-10, 10), 0 | fail |
| 7 | Range(-10, 10), 9.99999 | fail |
| 8 | Range(-10, 10), 10 | fail |
| 9 | Range(-10, 10), 10.00001 | fail |
| 10 | Range(-10, 10), Double.MAX_VALUE |fail  |
| 11 | Range(-10, 10), -Double.MAX_VALUE | fail |
| 12 | Range(-10, 10), Double.MIN_VALUE | fail |
| 13 | null, Double.NaN | pass |

### Test Results for `intersects(double lower, double upper)`

| Test Case Number | Input Partitions | Status |
|---------------|------------------|--------|
| 1 | (-10.00001, -10) | pass |
| 2 | (-10.00001, -9.99999) | pass |
| 3 | (-10.00001, 10.00001) | pass |
| 4 | (-10, -9.99999) | pass |
| 5 | (-10, 10) | pass |
| 6 | (-1, 1) |  |
| 7 | (9.99999, 10) | fail |
| 8 | (10, 10.00001) |  fail|
| 9 | (Double.MIN_VALUE, 10.00001) |fail  |
| 10 | (-10.00001, Double.MAX_VALUE) |  pass|
| 11 | (0, 0) | pass |
| 12 | (Double.NaN, 1) | pass |
| 13 | (1, Double.NaN) | pass |
| 14 | (-10, -10) | pass |
| 15 | (10, 10) | fail |

### Test Results for `createNumberArray(double[] data)`
| Test Case Number | Input Partitions | Status |
|---------------|------------------|--------|
| 1 | {1.0, 2.5, 3.0} | fail  |
| 2 | {-1.0, -2.5, -3.0} | fail |
| 3 | {-1.0, 0.0, 3.0} | fail |
| 4 | {} | pass |
| 5 | {5.0} | fail |
| 6 | null | pass |
| 7 | {Double.NaN} |  fail|
| 8 | {Double.POSITIVE_INFINITY} |  fail|
| 9 | {Double.MAX_VALUE, Double.MIN_VALUE} | fail |
| 10 | {1.0, 2.5, 3.0} – verify result length | pass |
| 11 | {1.0, 2.5, 3.0} – verify element type |fail  |

### Test Results for `createNumberArray2D(double[][] data)`
| Test Case Number | Input Partitions | Status |
|---------------|------------------|--------|
| 1 | {{1.0, 2.0}, {3.0, 4.0}} | fail |
| 2 | {{1.0}, {2.0, 3.0}} | fail |
| 3 | {{1.0, 2.0, 3.0}} | fail |
| 4 | {{1.0}, {2.0}, {3.0}} |fail  |
| 5 | {} |  pass|
| 6 | {{}} | pass |
| 7 | null | pass |
| 8 | {{Double.NaN, 1.0}, {2.0, Double.POSITIVE_INFINITY}} |fail  |
| 9 | {{Double.MAX_VALUE}, {Double.MIN_VALUE}} | fail |
| 10 | {{1.0, 2.0}, {3.0, 4.0}} – verify outer length |pass  |
| 11 | {{1.0, 2.0}, {3.0, 4.0}} – verify inner lengths | pass |
| 12 | {{1.0, 2.0}, {3.0, 4.0}} – verify element type | fail |

### Test Results for `calculateColumnTotal(Values2D data, int column)`
| Test Case Number | Input Partitions | Status |
|---------------|------------------|--------|
| 1 | 2x2 table, column 0 | pass  |
| 2 | 2x2 table, column 1 | pass |
| 3 | 3x3 table, column 1 | pass |
| 4 | 3x3 table, column 1 (colCount-2) | pass |
| 5 | 1x3 table, column 0 | pass |
| 6 | 3x1 table, column 0 | pass |
| 7 | empty table, column 0 | pass |
| 8 | 2x2 table with all null values in column 0 | fail |
| 9 | 2x2 table with one null in column 0 |fail  |
| 10 | 2x2 table with all zeros, column 0 | pass |
| 11 | 2x2 table with cancelling values, column 0 |  pass|
| 12 | 2x2 table with all negative values, column 0 |  pass|
| 13 | 2x2 table with mixed values, column 1 |pass  |
| 14 | null data, column 0 | pass |

### Test Results for `calculateRowTotal(Values2D data, int row)`

| Test Case Number | Input Partitions | Status |
|---------------|------------------|--------|
| 1 | 2x2 table, row 0 | fail |
| 2 | 4x2 table, row 1 |  fail|
| 3 | 4x2 table, row 2 | fail |
| 4 | 4x2 table, row 3 | fail |
| 5 | 1x1 table, row 0 | fail |
| 6 | 2x2 table, row 0 (all zeros) | pass |
| 7 | 2x2 table, row 0 (negative values) | fail |
| 8 | 2x2 table, row 1 (mixed values) | fail |
| 9 | null data, row 0 |pass  |
| 10 | 2x2 table, row -1 |pass  |
| 11 | 2x2 table, row 2 | pass |
| 12 | 1x2 table with NaN, row 0 | pass |
| 13 | 1x2 table with POSITIVE_INFINITY, row 0 | pass |
| 14 | 1x2 table with Double.MAX_VALUE values, row 0 |  fail|

### Test Results for `getCumulativePercentages(KeyedValues data)`
| Test Case Number | Input Partitions | Status |
|---------------|------------------|--------|
| 1 | {0→5, 1→9, 2→2} | fail  |
| 2 | {0→10} |  fail|
| 3 | {} |  pass |
| 4 | null |pass  |
| 5 | {0→5, 1→-2, 2→3} |fail  |
| 6 | {0→4, 1→4, 2→4} | fail |
| 7 | {0→5, 1→0, 2→3} | fail |
| 8 | {0→0, 1→0, 2→0} | pass |
| 9 | {"a"→6, "b"→4} | fail |
| 10 | {0→null, 1→4} |  fail|
| 11 | {0→NaN, 1→5} | pass |
| 12 | {0→Infinity, 1→5} | fail |
| 13 | {0→Double.MAX_VALUE, 1→1} | fail |

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



