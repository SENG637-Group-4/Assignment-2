# Test Plan for `createNumberArray2D(double[][] data)`

## Method to test
`public static Number[][] createNumberArray2D(double[][] data)`  

Constructs a two-dimensional array of `Number` objects (specifically `Double`) from a corresponding structure of `double` primitives. The `data` parameter must not be `null`.

---

## Equivalence classes for the input — `data` (`double[][]`)

- **E** - Expected inputs
- **U** - Unexpected inputs
- **BLB**: a value just below the lower bound  
- **LB**: the value on the lower boundary  
- **ALB**: a value just above the lower boundary  
- **NOM**: a nominal value  
- **BUB**: a value just below the upper bound  
- **UB**: the value on the upper boundary  
- **AUB**: a value just above the upper boundary

### Input variable: outer array length (number of rows, `int`)
- **E1:** outer length > 1 — multiple rows (NOM)
- **E2:** outer length = 1 — single-row array (ALB)
- **E3:** outer length = 0 — empty outer array (LB); valid per spec
- **U1:** `null` — not permitted; must throw `IllegalArgumentException`

### Input variable: inner array length (number of columns per row, `int`)
- **E4:** all rows have equal length — rectangular array
- **E5:** rows have different lengths — jagged (non-rectangular) array
- **E6:** inner length = 1 — single-column array (ALB for inner)
- **E7:** inner length = 0 — row is itself empty (LB for inner)

### Input variable: element values (`double`)
- **E8:** standard positive and negative `double` values (NOM)
- **U2:** `Double.NaN` or `Double.POSITIVE_INFINITY` / `Double.NEGATIVE_INFINITY`
- **U3:** `Double.MAX_VALUE` or `Double.MIN_VALUE` — extreme boundary values

---

## Boundary value analysis

For the outer array (number of rows `m`) and inner arrays (number of columns `n`):

| Symbol | Dimension | Description |
|--------|-----------|-------------|
| LB (outer, m) | 0 | Empty outer array |
| ALB (outer, m) | 1 | Single-row array |
| NOM (outer, m) | 2–3 | Typical row count |
| LB (inner, n) | 0 | Empty row |
| ALB (inner, n) | 1 | Single element per row |
| NOM (inner, n) | 2–3 | Typical column count |

---

## Set of test cases based on equivalence classes and boundary value analysis

| Test Case | Input `data` | Relevant Conditions | Expected Behavior |
|-----------|-------------|---------------------|-------------------|
| 1 | `{{1.0, 2.0}, {3.0, 4.0}}` | E1; E4; E8; NOM rectangular 2×2 | Returns `Number[][]` with matching values |
| 2 | `{{1.0}, {2.0, 3.0}}` | E1; E5; jagged array | Returns jagged `Number[][]`; `result[0].length = 1`, `result[1].length = 2` |
| 3 | `{{1.0, 2.0, 3.0}}` | E2; E4; ALB outer = 1, single row | Returns `Number[][]` with one row of 3 elements |
| 4 | `{{1.0}, {2.0}, {3.0}}` | E1; E6; single column, ALB inner = 1 | Returns `Number[][]` with 3 rows of 1 element each |
| 5 | `{}` | E3; LB outer length = 0 | Returns empty `Number[][]`, `result.length == 0` |
| 6 | `{{}}` | E2; E7; single row that is empty (LB inner = 0) | Returns `Number[][]` with one empty row |
| 7 | `null` | U1; null not permitted | Throws `InvalidParameterException` |
| 8 | `{{Double.NaN, 1.0}, {2.0, Double.POSITIVE_INFINITY}}` | E1; E4; U2; NaN and infinity elements | NaN and `POSITIVE_INFINITY` preserved in result |
| 9 | `{{Double.MAX_VALUE}, {Double.MIN_VALUE}}` | E1; E6; U3; UB and LB element values | `MAX_VALUE` and `MIN_VALUE` preserved in result |
| 10 | `{{1.0, 2.0}, {3.0, 4.0}}` — check outer length | E1; E4; verify `result.length == 2` | `result.length == 2` |
| 11 | `{{1.0, 2.0}, {3.0, 4.0}}` — check inner lengths | E1; E4; verify each row's length matches | `result[0].length == 2` and `result[1].length == 2` |
| 12 | `{{1.0, 2.0}, {3.0, 4.0}}` — check element type | E1; E4; E8; verify all elements are `Double` | Each element is `instanceof Double` |
