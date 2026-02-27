# Test Plan for `calculateColumnTotal(Values2D data, int column)`

## Method Under Test

```java
public static double calculateColumnTotal(Values2D data, int column)
```

Returns the sum of the values in one column of the supplied data table. With invalid input, a total of zero will be returned.

**Parameters:**
- `data` - the table of values (null not permitted)
- `column` - the column index (zero-based)

**Returns:** The sum of the values in the specified column.

**Throws:** `InvalidParameterException` - if an invalid data object is passed in.

---

## Equivalence Classes

**Legend:**
- **E** - Expected inputs
- **U** - Unexpected inputs
- **BLB** - Just below lower bound
- **LB** - On the lower boundary
- **ALB** - Just above the lower boundary
- **NOM** - Nominal value
- **BUB** - Just below upper bound
- **UB** - On the upper boundary
- **AUB** - Just above the upper boundary

### Input Variable: `data` (Values2D)

| Class | Description |
|-------|-------------|
| E1 | Valid Values2D with multiple rows and columns (NOM) |
| E2 | Valid Values2D with a single row (ALB outer) |
| E3 | Valid Values2D with a single column (ALB inner) |
| E4 | Valid Values2D with zero rows - empty table (LB outer) |
| E5 | Values2D where some cell values are `null` |
| U1 | `null` - not permitted; must throw `InvalidParameterException` |

### Input Variable: `column` (int, zero-based index)

| Class | Description |
|-------|-------------|
| E6 | `column = 0` - first column (LB) |
| E7 | `column` is a valid middle index (NOM) |
| E8 | `column` is the last valid index (UB) |
| U2 | `column = -1` - below lower bound (BLB); invalid index |
| U3 | `column` exceeds the number of columns (AUB); invalid index |

### Input Variable: Cell values (double)

| Class | Description |
|-------|-------------|
| E9 | Standard positive and negative double values (NOM) |
| E10 | All values in column are zero |
| E11 | Mix of positive and negative values summing to zero |
| U4 | `Double.MAX_VALUE` - extreme upper boundary element value |
| U5 | `Double.NaN` or `Double.POSITIVE_INFINITY` / `Double.NEGATIVE_INFINITY` |

---

## Boundary Value Analysis

| Symbol | Dimension | Description |
|--------|-----------|-------------|
| LB (column) | 0 | First column index |
| ALB (column) | 1 | Second column index |
| NOM (column) | 1–n/2 | Mid-range column index |
| BUB (column) | colCount − 2 | Second-to-last column |
| UB (column) | colCount − 1 | Last valid column index |
| AUB (column) | colCount | One past the last column - invalid |
| BLB (column) | −1 | One below the first column - invalid |
| LB (rows) | 0 | Empty table (no rows) |
| ALB (rows) | 1 | Single-row table |
| NOM (rows) | 2–3 | Typical row count |

---

## Test Cases

| Test Case | Input `data` | Input `column` | Relevant Conditions | Expected Behavior |
|-----------|-------------|----------------|---------------------|-------------------|
| 1 | 2×2 table: row0=[1.0, 2.0], row1=[3.0, 4.0] | 0 | E1; E6; E9; NOM rectangular, LB column | Returns 4.0 (1.0 + 3.0) |
| 2 | 2×2 table: row0=[1.0, 2.0], row1=[3.0, 4.0] | 1 | E1; E8; E9; UB column | Returns 6.0 (2.0 + 4.0) |
| 3 | 3×3 table with mixed values | 1 | E1; E7; E9; NOM column, NOM rows | Returns correct sum of middle column |
| 4 | 1×3 table: row0=[5.0, 10.0, 15.0] | 0 | E2; E6; E9; ALB outer (single row), LB column | Returns 5.0 |
| 5 | 3×1 table: row0=[2.0], row1=[3.0], row2=[4.0] | 0 | E3; E6; E9; ALB inner (single column) | Returns 9.0 (2.0 + 3.0 + 4.0) |
| 6 | 0-row table (empty) | 0 | E4; E6; LB outer (zero rows) | Returns 0.0 |
| 7 | 2×2 table where cell(0,1) = null, cell(1,1) = 5.0 | 1 | E1; E5; E8; null cell values | Returns 5.0 (null values skipped) |
| 8 | 2×2 table: row0=[0.0, 0.0], row1=[0.0, 0.0] | 0 | E1; E6; E10; all-zero column | Returns 0.0 |
| 9 | 2×2 table: row0=[3.0, -3.0], row1=[-3.0, 3.0] | 0 | E1; E6; E11; values sum to zero | Returns 0.0 (3.0 + (−3.0)) |
| 10 | `null` | 0 | U1; null data | Throws `InvalidParameterException` |
| 11 | 2×2 table | −1 | U2; BLB column (below lower bound) | Returns 0.0 or throws `InvalidParameterException` |
| 12 | 2×2 table | 2 | U3; AUB column (exceeds column count) | Returns 0.0 or throws `InvalidParameterException` |
| 13 | 2×1 table: row0=[Double.MAX_VALUE], row1=[Double.MAX_VALUE] | 0 | E3; U4; extreme boundary element values | Returns sum (may overflow to `Double.POSITIVE_INFINITY`) |
| 14 | 2×2 table: row0=[Double.NaN, 1.0], row1=[Double.POSITIVE_INFINITY, 2.0] | 0 | E1; U5; NaN and Infinity elements | Returns `Double.NaN` or `Double.POSITIVE_INFINITY` depending on JVM arithmetic |
| 15 | 2×2 table: row0=[1.0, 2.0], row1=[3.0, 4.0] - verify return type | 0 | E1; E6; E9; verify result is `double` primitive | Return type is `double`; result == 4.0 |

---
