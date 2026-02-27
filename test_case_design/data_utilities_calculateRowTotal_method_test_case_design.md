# Test Plan for `calculateRowTotal(Values2D data, int row)`

## Method to Test

```java
public static double calculateRowTotal(Values2D data, int row)
```

Returns the sum of the values in one row of the supplied data table. With invalid input, a total of zero will be returned.

**Parameters:**
- `data` - the table of values (`null` not permitted)
- `row` - the row index (zero-based)

**Returns:** The total of the values in the specified row.

**Throws:** `InvalidParameterException` if an invalid data object is passed in.

> **Note on Implementation:** Since `Values2D` is an interface (used in JFreeChart), all test data must be constructed using a mocking framework (JMock). 

---

## Equivalence Classes for the Inputs

### Legend

| Symbol | Meaning |
|--------|---------|
| E | Expected (valid) input |
| U | Unexpected (invalid) input |
| BLB | Just below the lower bound |
| LB | On the lower boundary |
| ALB | Just above the lower boundary |
| NOM | Nominal value |
| BUB | Just below the upper bound |
| UB | On the upper boundary |
| AUB | Just above the upper boundary |

---

### Input Variable: `data` (Values2D)

| Class | Description |
|-------|-------------|
| E1 | A valid `Values2D` table with multiple rows and columns (NOM) |
| E2 | A valid `Values2D` table with a single row (ALB outer) |
| E3 | A valid `Values2D` table with a single column (ALB inner) |
| E4 | A valid `Values2D` table where all values in the target row are zero |
| E5 | A valid `Values2D` table where some values in the target row are negative |
| U1 | `null` - not permitted; must throw `InvalidParameterException` |

---

### Input Variable: `row` (int, zero-based index)

| Class | Description |
|-------|-------------|
| E6 | `row = 0` - first row (LB) |
| E7 | `row = 1` - second row (ALB) |
| E8 | `row = n-1` - last valid row (UB), where n = total row count |
| U2 | `row = -1` - one below lower bound (BLB); invalid index |
| U3 | `row = n` - one above upper bound (AUB); out-of-bounds index |

---

### Input Variable: Element Values in the Target Row (double)

| Class | Description |
|-------|-------------|
| E9 | Standard positive double values (NOM) |
| E10 | Mix of positive and negative double values (NOM) |
| E11 | All values are zero |
| U4 | Values include `Double.NaN` |
| U5 | Values include `Double.POSITIVE_INFINITY` or `Double.NEGATIVE_INFINITY` |
| U6 | Values include `Double.MAX_VALUE` or `Double.MIN_VALUE` (extreme boundaries) |

---

## Boundary Value Analysis

### `row` index (for a table with `n` rows, zero-based)

| Symbol | Value | Description |
|--------|-------|-------------|
| BLB | -1 | One below lower bound — invalid |
| LB | 0 | First row — valid |
| ALB | 1 | Second row — valid (requires n ≥ 2) |
| NOM | n/2 | Middle row |
| BUB | n-2 | Second-to-last row — valid (requires n ≥ 3) |
| UB | n-1 | Last row — valid |
| AUB | n | One past the last row — invalid |

> **Note:** To meaningfully distinguish ALB, BUB, and UB as separate boundary cases, use a table with at least **4 rows** (e.g., n = 4 gives ALB = 1, BUB = 2, UB = 3).

### Column count per row

| Symbol | Value | Description |
|--------|-------|-------------|
| LB | 0 | Empty row — sum should be 0.0 |
| ALB | 1 | Single-element row |
| NOM | 2–3 | Typical column count |

---

## Test Cases

| Test Case | Input `data` | Input `row` | Relevant Classes | Expected Behavior |
|-----------|-------------|-------------|-----------------|-------------------|
| 1 | `{{1.0, 2.0}, {3.0, 4.0}}` | `0` | E1, E6, E9; LB row | Returns `3.0` (sum of first row) |
| 2 | `{{1.0, 2.0}, {3.0, 4.0}, {5.0, 6.0}, {7.0, 8.0}}` | `1` | E1, E7, E9; ALB row | Returns `7.0` (sum of second row) |
| 3 | `{{1.0, 2.0}, {3.0, 4.0}, {5.0, 6.0}, {7.0, 8.0}}` | `2` | E1, E9; BUB row (n-2 = 2) | Returns `11.0` (sum of third row) |
| 4 | `{{1.0, 2.0}, {3.0, 4.0}, {5.0, 6.0}, {7.0, 8.0}}` | `3` | E1, E8, E9; UB row (n-1 = 3) | Returns `15.0` (sum of last row) |
| 5 | `{{5.0}}` | `0` | E2, E3, E6; single-row single-column | Returns `5.0` |
| 6 | `{{0.0, 0.0}, {0.0, 0.0}}` | `0` | E1, E4, E6, E11; all zeros | Returns `0.0` |
| 7 | `{{-1.0, -2.0}, {3.0, 4.0}}` | `0` | E1, E5, E6, E10; negative values | Returns `-3.0` |
| 8 | `{{-1.5, 2.5}, {3.0, -3.0}}` | `1` | E1, E5, E10; mixed positive and negative | Returns `0.0` |
| 9 | `null` | `0` | U1; null data | Throws `InvalidParameterException` |
| 10 | `{{1.0, 2.0}, {3.0, 4.0}}` | `-1` | E1, U2; BLB row index | Returns `0.0` (invalid input per spec) or throws — **behavior should be verified against implementation** |
| 11 | `{{1.0, 2.0}, {3.0, 4.0}}` | `2` | E1, U3; AUB row index (n = 2) | Returns `0.0` (invalid input per spec) or throws — **behavior should be verified against implementation** |
| 12 | `{{Double.NaN, 1.0}}` | `0` | E2, U4; NaN element in row | Result is `NaN` (NaN propagation in floating-point arithmetic) |
| 13 | `{{Double.POSITIVE_INFINITY, 1.0}}` | `0` | E2, U5; Infinity element | Returns `POSITIVE_INFINITY` |
| 14 | `{{Double.MAX_VALUE, Double.MAX_VALUE}}` | `0` | E2, U6; UB element values | Returns `POSITIVE_INFINITY` (overflow — `MAX_VALUE + MAX_VALUE` exceeds double range) |
