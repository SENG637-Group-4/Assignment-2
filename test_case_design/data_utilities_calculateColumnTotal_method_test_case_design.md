# Test Plan for `calculateColumnTotal(Values2D data, int column)`

## Method Under Test

```java
public static double calculateColumnTotal(Values2D data, int column)
```

Returns the sum of the values in one column of the supplied data table. With invalid input, a total of zero will be returned.

**Parameters:**
- `data` — the table of values (`null` not permitted)
- `column` — the column index (zero-based)

**Returns:** The sum of the values in the specified column.

**Throws:** `InvalidParameterException` — if an invalid data object is passed in.

---

## Equivalence Classes

**Legend:**

| Symbol | Meaning |
|--------|---------|
| **E**   | Expected input |
| **U**   | Unexpected input |
| **BLB** | Just below the lower bound |
| **LB**  | On the lower boundary |
| **ALB** | Just above the lower boundary |
| **NOM** | Nominal value |
| **BUB** | Just below the upper bound |
| **UB**  | On the upper boundary |
| **AUB** | Just above the upper boundary |

---

### Input Variable: `data` (Values2D)

| ID  | Class | Description |
|-----|-------|-------------|
| E1  | E     | Valid `Values2D` with multiple rows and columns (NOM) |
| E2  | E     | Valid `Values2D` with a single row (ALB outer) |
| E3  | E     | Valid `Values2D` with a single column (ALB inner) |
| E4  | E     | Valid `Values2D` with zero rows — empty table (LB outer) |
| E5  | E     | `Values2D` where **some** cell values in the target column are `null` |
| E6  | E     | `Values2D` where **all** cell values in the target column are `null` |
| U1  | U     | `null` — not permitted; must throw `InvalidParameterException` |

---

### Input Variable: `column` (int, zero-based index)

| ID  | Class | Description |
|-----|-------|-------------|
| E7  | E     | `column = 0` — first column (LB) |
| E8  | E     | `column` is a valid middle index (NOM) |
| E9  | E     | `column` is the second-to-last valid index (BUB) |
| E10 | E     | `column` is the last valid index (UB) |
| U2  | U     | `column = -1` — below lower bound (BLB); invalid index |
| U3  | U     | `column = colCount` — one past the last column (AUB); invalid index |

---

### Input Variable: Cell Values (double)

| ID  | Class | Description |
|-----|-------|-------------|
| E11 | E     | All positive values (NOM) |
| E12 | E     | All negative values |
| E13 | E     | Mix of positive and negative values with non-zero sum |
| E14 | E     | All values in column are zero |
| E15 | E     | Mix of positive and negative values summing to zero |
| U4  | U     | `Double.MAX_VALUE` — extreme upper boundary element value |
| U5  | U     | `Double.NaN` — propagates through arithmetic |
| U6  | U     | `Double.POSITIVE_INFINITY` or `Double.NEGATIVE_INFINITY` |

---

## Boundary Value Analysis

### `column` index

| Symbol      | Value          | Description                          |
|-------------|----------------|--------------------------------------|
| BLB         | -1             | One below the first column — invalid |
| LB          | 0              | First column index                   |
| ALB         | 1              | Second column index                  |
| NOM         | colCount / 2   | Mid-range column index               |
| BUB         | colCount - 2   | Second-to-last column index          |
| UB          | colCount - 1   | Last valid column index              |
| AUB         | colCount       | One past the last column — invalid   |

### Row count (outer dimension of `data`)

| Symbol      | Value | Description             |
|-------------|-------|-------------------------|
| LB (rows)   | 0     | Empty table (no rows)   |
| ALB (rows)  | 1     | Single-row table        |
| NOM (rows)  | 3     | Typical row count       |

---

## Test Cases

| TC | Input `data`                                                       | `column` | Conditions      | Expected Result |
|----|--------------------------------------------------------------------|----------|-----------------|-----------------|
| 1  | 2×2: row0=[1.0, 2.0], row1=[3.0, 4.0]                             | 0        | E1, E7, E11     | `4.0` (1.0 + 3.0) — LB column |
| 2  | 2×2: row0=[1.0, 2.0], row1=[3.0, 4.0]                             | 1        | E1, E10, E11    | `6.0` (2.0 + 4.0) — UB column |
| 3  | 3×3: row0=[1.0, 2.0, 3.0], row1=[4.0, 5.0, 6.0], row2=[7.0, 8.0, 9.0] | 1   | E1, E8, E11     | `15.0` (2.0 + 5.0 + 8.0) — NOM column |
| 4  | 3×3: row0=[1.0, 2.0, 3.0], row1=[4.0, 5.0, 6.0], row2=[7.0, 8.0, 9.0] | 1   | E1, E9, E11     | `15.0` (2.0 + 5.0 + 8.0) — BUB column (colCount-2 = 1 in 3-col table) |
| 5  | 1×3: row0=[5.0, 10.0, 15.0]                                        | 0        | E2, E7, E11     | `5.0` — single row, LB column |
| 6  | 3×1: row0=[2.0], row1=[3.0], row2=[4.0]                            | 0        | E3, E7, E11     | `9.0` (2.0 + 3.0 + 4.0) — single column |
| 7  | 0-row table (empty)                                                | 0        | E4, E7          | `0.0` — no rows to sum |
| 8  | 2×2: row0=[null, 5.0], row1=[null, 3.0] (column 0 all null)       | 0        | E1, E6, E7      | `0.0` — all null values skipped |
| 9  | 2×2: row0=[null, 5.0], row1=[4.0, 3.0] (cell(0,0) null)           | 0        | E1, E5, E7      | `4.0` — null value skipped, 0.0 + 4.0 |
| 10 | 2×2: row0=[0.0, 0.0], row1=[0.0, 0.0]                             | 0        | E1, E7, E14     | `0.0` — all-zero column |
| 11 | 2×2: row0=[3.0, -3.0], row1=[-3.0, 3.0]                           | 0        | E1, E7, E15     | `0.0` (3.0 + (-3.0)) — values cancel |
| 12 | 2×2: row0=[-2.0, -3.0], row1=[-4.0, -5.0]                        | 0        | E1, E7, E12     | `-6.0` (-2.0 + (-4.0)) — all negative |
| 13 | 2×2: row0=[5.0, -2.0], row1=[-1.0, 8.0]                           | 1        | E1, E10, E13    | `6.0` (-2.0 + 8.0) — mixed values, non-zero sum |
| 14 | `null`                                                             | 0        | U1              | Throws `InvalidParameterException` |

