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

1. Initially, we added the JAR files using absolute paths when setting up the project. However, when another team member cloned the repository, the project did not work on their machine because the paths were machine-specific. To fix this issue, we created a folder named "lib" inside the project, placed all the required JAR files there, and referenced them using relative paths. After this change, the project worked correctly for everyone.
2. During the initial equivalence class partitioning of the test case design, we considered some unexpected inputs, such as non-numeric values for boolean types. For example, we thought about providing non-boolean values to `allowZeroCrossing` (`boolean`). We included this in the test case design. However, while creating the test cases, we realized that passing non-boolean values to a boolean parameter is not possible in Java due to compile-time type checking.


# 6 Comments/feedback on the lab itself

1. This assignment has given us a great opportunity to practically learn how to do black box testing using approaches like Equivalence Class Testing, Boundary Value Testing, and Robustness Testing.
2. We've learnt about popular Java testing framework
3. The assignment description file is thorough and well-organized, making it easy to understand and follow.
