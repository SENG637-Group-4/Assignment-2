# Test Plan for `expandToInclude(Range range, double value)`

## Method to Test
`public static Range expandToInclude(Range range, double value)` Returns a range that includes all the values in the specified range AND contains the specified value

Parameters:
- range - the range (null permitted).
- value - the value that must be included.

Returns:
A range which spans over the input range, and has been expanded to contain the input value.

---

## Equivalence Classes for Inputs

- **E** - Expected inputs
- **U** - Unexpected inputs
- **BLB**: a value just below the lower bound
- **LB**: the value on the lower boundary
- **ALB**: a value just above the lower boundary
- **NOM**: a nominal value
- **BUB**: a value just below the upper bound
- **UB**: the value on the upper boundary
- **AUB**: a value just above the upper boundary
- Assume the Range under test is closed interval `[-10, 10]`

### Range (first parameter)
- **E1:** `range` is a valid range - normal case
- **U1:** `range` is `null` - permitted per the contract; should return a range containing only `value`

### Value (`double`)
- **E2:** value < range lower bound - must expand lower bound
- **E3:** value = range lower bound - already included, no change
- **E4:** value within range - already included, no change
- **E5:** value = range upper bound - already included, no change
- **E6:** value > range upper bound - must expand upper bound
- **U2:** `Double.NaN` - undefined behavior
- **U3:** `Double.MIN_VALUE` / `Double.MAX_VALUE` - extreme values

---

## Set of Test Cases Based on Equivalence Classes and Boundary Value Analysis

| Test Case | Input Values (range, value)       | Relevant Conditions | Expected Behavior                                                                      |
| --------- | --------------------------------- | ------------------- | -------------------------------------------------------------------------------------- |
| 1         | `null`, `0`                       | U1; E4              | Returns `[0, 0]` - null range, value becomes entire range                              |
| 2         | `null`, `-10`                     | U1; E3              | Returns `[-10, -10]` - null range, value becomes entire range                          |
| 3         | `Range(-10, 10)`, `-10.00001`          | E1; E2; BLB         | Returns `Range(-10.00001, 10)` - lower bound expands to include value just below lower      |
| 4         | `Range(-10, 10)`, `-10`                | E1; E3; LB          | Returns `Range(-10, 10)` - value equals lower bound, no change                              |
| 5         | `Range(-10, 10)`, `-9.99999`           | E1; E4; ALB         | Returns `Range(-10, 10)` - value just above lower bound, already included, no change        |
| 6         | `Range(-10, 10)`, `0`                  | E1; E4; NOM         | Returns `Range(-10, 10)` - nominal value within range, no change                            |
| 7         | `Range(-10, 10)`, `9.99999`            | E1; E4; BUB         | Returns `Range(-10, 10)` - value just below upper bound, already included, no change        |
| 8         | `Range(-10, 10)`, `10`                 | E1; E5; UB          | Returns `Range(-10, 10)` - value equals upper bound, no change                              |
| 9         | `Range(-10, 10)`, `10.00001`           | E1; E6; AUB         | Returns `Range(-10, 10.00001)` - upper bound expands to include value just above upper      |
| 10        | `Range(-10, 10)`, `Double.MAX_VALUE`   | E1; E6; U3          | Returns `Range(-10, Double.MAX_VALUE)` - upper bound expands to extreme maximum             |
| 11        | `Range(-10, 10)`, `-Double.MAX_VALUE`  | E1; E2; U3          | Returns `Range(-Double.MAX_VALUE, 10)` - lower bound expands to extreme minimum             |
| 12        | `Range(-10, 10)`, `Double.MIN_VALUE`   | E1; E4; U3          | Returns `Range(-10, 10)` - MIN_VALUE is a tiny positive value within range, no change       |
| 13        | `null`, `Double.NaN`              | U1; U2              | Undefined - both inputs are edge cases; result should be investigated                  |
