**SENG 637 - Software Testing, Reliability, and Quality**

**Lab. Report \#2 – Requirements-Based Test Generation**

| Group 4      |
|-----------------|
| Zohara Kamal            |   
| Thanoshan Vijayanandan          |   
| Minh Le                |   
| Shuvam Agarwala              | 

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


# 3 Test cases developed

Text…

// write down the name of the test methods and classes. Organize the based on
the source code method // they test. identify which tests cover which partitions
you have explained in the test strategy section //above

# 4 How the team work/effort was divided and managed

We divided the test case design and test generation tasks among ourselves.

After a team member prepared a test case design, the other three members reviewed it. Once the design was successfully approved, the same member proceeded to implement the unit tests based on it. After the unit tests were created, the team conducted another review.

The table below summarizes how the test case design and unit test development tasks were distributed among the team members.

| Method | Member |
|--------|--------|
| `Range.expandToInclude()` | Minh |
| `Range.intersects()` | Minh |
| `Range.contains()` | Thanoshan |
| `Range.shift()` | Thanoshan |
| `Range.getLength()` | Shuvam |
| `DataUtilities.createNumberArray()` | Shuvam |
| `DataUtilities.createNumberArray2D()` | Shuvam |

# 5 Difficulties encountered, challenges overcome, and lessons learned

Text…

# 6 Comments/feedback on the lab itself

Text…
