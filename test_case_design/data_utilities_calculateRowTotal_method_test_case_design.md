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
| BLB | -1 | One below lower bound - invalid |
| LB | 0 | First row - valid |
| ALB | 1 | Second row - valid |
| NOM | n/2 | Middle row |
| BUB | n-2 | Second-to-last row - valid |
| UB | n-1 | Last row - valid |
| AUB | n | One past the last row - invalid |

### Column count per row

| Symbol | Value | Description |
|--------|-------|-------------|
| LB | 0 | Empty row - sum should be 0.0 |
| ALB | 1 | Single-element row |
| NOM | 2–3 | Typical column count |

---

## Test Cases

| Test Case | Input `data` | Input `row` | Relevant Classes | Expected Behavior |
|-----------|-------------|-------------|-----------------|-------------------|
| 1 | `{{1.0, 2.0}, {3.0, 4.0}}` | `0` | E1, E6, E9; LB row | Returns `3.0` (sum of first row) |
| 2 | `{{1.0, 2.0}, {3.0, 4.0}}` | `1` | E1, E7, E9; ALB row | Returns `7.0` (sum of second row) |
| 3 | `{{1.0, 2.0}, {3.0, 4.0}}` | `1` | E1, E8, E9; UB row (n-1 = 1) | Returns `7.0` |
| 4 | `{{5.0}}` | `0` | E2, E3, E6; single-row single-column | Returns `5.0` |
| 5 | `{{0.0, 0.0}, {0.0, 0.0}}` | `0` | E1, E4, E6; all zeros | Returns `0.0` |
| 6 | `{{-1.0, -2.0}, {3.0, 4.0}}` | `0` | E1, E5, E6, E10; negative values | Returns `-3.0` |
| 7 | `{{-1.5, 2.5}, {3.0, -3.0}}` | `1` | E1, E5, E10; mixed positive and negative | Returns `0.0` |
| 8 | `null` | `0` | U1; null data | Throws `InvalidParameterException` |
| 9 | `{{1.0, 2.0}, {3.0, 4.0}}` | `-1` | E1, U2; BLB row index | Returns `0.0` (invalid input per spec) |
| 10 | `{{1.0, 2.0}, {3.0, 4.0}}` | `2` | E1, U3; AUB row index (n = 2) | Returns `0.0` (invalid input per spec) |
| 11 | `{{Double.NaN, 1.0}}` | `0` | E2, U4; NaN element in row | Result is `NaN` (NaN propagation) |
| 12 | `{{Double.POSITIVE_INFINITY, 1.0}}` | `0` | E2, U5; Infinity element | Returns `POSITIVE_INFINITY` |
| 13 | `{{Double.MAX_VALUE, Double.MAX_VALUE}}` | `0` | E2, U6; UB element values | Returns `POSITIVE_INFINITY` (overflow) or `Double.MAX_VALUE * 2` |
| 14 | `{{Double.MIN_VALUE, 1.0}}` | `0` | E2, U6; LB element value | Returns sum including `Double.MIN_VALUE` |
| 15 | Table with one empty row: `{{}, {1.0, 2.0}}` | `0` | E1, LB inner; empty row | Returns `0.0` |
| 16 | `{{1.0, 2.0}, {3.0, 4.0}}` - verify column count ignored | `0` | E1, E6; confirm only target row summed | Returns `3.0`, not `10.0` |

---
