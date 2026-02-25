# Test Plan for `intersects(double lower, double upper)`

## Method to Test

`public boolean intersects(double lower, double upper)`

Returns `true` if the range intersects (overlaps) with the specified range, and `false` otherwise.

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

### Lower (`double`)

- **E1:** lower < range lower bound - no overlap possible from below
- **E2:** lower = range lower bound - touching at lower edge
- **E3:** lower within range - overlaps
- **E4:** lower = range upper bound - touching at upper edge
- **E5:** lower > range upper bound - no overlap possible from above
- **U1:** `Double.NaN` - undefined behavior
- **U3**: `Double.MIN_VALUE` / `Double.MAX_VALUE` - extreme values

### Upper (`double`)

- **E6:** upper < range lower bound - no overlap
- **E7:** upper = range lower bound - touching at lower edge
- **E8:** upper within range - overlaps
- **E9:** upper = range upper bound - touching at upper edge
- **E10:** upper > range upper bound - overlaps beyond
- **U2:** `Double.NaN` - undefined behavior
- **U3:** `Double.MIN_VALUE` / `Double.MAX_VALUE` - extreme values

---

## Set of test cases based on equivalence classes and boundary value analysis

| Test Case | Input Values (lower, upper)   | Relevant Conditions | Expected Behavior                                         |
| --------- | ----------------------------- | ------------------- | --------------------------------------------------------- |
| 1         | (-10.00001, -10)              | E1; E6; BLB, LB     | `true` – input ends exactly at lower bound → intersects   |
| 2         | (-10.00001, -9.99999)         | E1; E8; BLB, ALB    | `true` – input slightly overlaps lower bound              |
| 3         | (-10.00001, 10.00001)         | E1; E10; BLB, AUB   | `true` – input fully spans the range                      |
| 4         | (-10, -9.99999)               | E2; E8; LB, ALB     | `true` – input starts exactly at lower bound              |
| 5         | (-10, 10)                     | E2; E9; LB, UB      | `true` – input exactly matches the range                  |
| 6         | (-1, 1)                       | E3; E8; NOM, NOM    | `true` – input fully within the range                     |
| 7         | (9.99999, 10)                 | E3; E9; BUB, UB     | `true` – input ends exactly at upper bound                |
| 8         | (10, 10.00001)                | E4; E10; UB, AUB    | `true` – input starts exactly at upper bound → intersects |
| 9         | (Double.MIN_VALUE, 10.00001)  | E3; E10; MIN, AUB   | `true` – input overlaps within the range                  |
| 10        | (-10.00001, Double.MAX_VALUE) | E1; E10; BLB, MAX   | `true` – input spans beyond both bounds                   |
| 11        | (0, 0)                        | E3; E8; NOM, NOM    | `true` – single point within the range                    |
| 12        | (Double.NaN, 1)               | U1; E8              | `false` – NaN lower bound is undefined                    |
| 13        | (1, Double.NaN)               | E3; U2              | `false` – NaN upper bound is undefined                    |
| 14        | (-10, -10)                    | E2; E7; LB, LB      | `true` – single point exactly at lower bound              |
| 15        | (10, 10)                      | E4; E9; UB, UB      | `true` – single point exactly at upper bound              |

