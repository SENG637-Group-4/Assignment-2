# Test Plan for `createNumberArray(double[] data)`

## Method to test
`public static Number[] createNumberArray(double[] data)`  

Constructs an array of `Number` objects (specifically `Double`) from an array of `double` primitives. The `data` parameter must not be `null`.

---

## Equivalence classes for the input — `data` (`double[]`)

- **E** - Expected inputs
- **U** - Unexpected inputs
- **BLB**: a value just below the lower bound  
- **LB**: the value on the lower boundary  
- **ALB**: a value just above the lower boundary  
- **NOM**: a nominal value  
- **BUB**: a value just below the upper bound  
- **UB**: the value on the upper boundary  
- **AUB**: a value just above the upper boundary

### Input variable: array length (`int`)
- **E1:** length > 1 — standard non-empty array (NOM)
- **E2:** length = 1 — single-element array (ALB)
- **E3:** length = 0 — empty array (LB); valid per spec, should return empty `Number[]`
- **U1:** `null` — not permitted; must throw `InvalidParameterException`

### Input variable: element values (`double`)
- **E4:** all-positive `double` values
- **E5:** all-negative `double` values
- **E6:** mixed values (positive, negative, and zero)
- **U2:** `Double.NaN` — special floating-point value
- **U3:** `Double.POSITIVE_INFINITY` or `Double.NEGATIVE_INFINITY` — infinity values
- **U4:** `Double.MAX_VALUE` or `Double.MIN_VALUE` — extreme boundary values

---

## Boundary value analysis

For array length, boundaries are on the size of the input array:

| Symbol | Array size | Description |
|--------|------------|-------------|
| LB | 0 | Empty array — minimum valid input |
| ALB | 1 | Single-element array |
| NOM | 3 | Typical array size |

For element values, with NOM input `{1.0, 2.5, −3.0}`:

| Symbol | Element value | Description |
|--------|---------------|-------------|
| LB (element) | `Double.MIN_VALUE` ≈ 4.9e−324 | Smallest positive double |
| UB (element) | `Double.MAX_VALUE` ≈ 1.8e+308 | Largest positive double |
| NOM (element) | 1.0, −1.0 | Typical positive and negative values |

---

## Set of test cases based on equivalence classes and boundary value analysis

| Test Case | Input `data` | Relevant Conditions | Expected Behavior |
|-----------|-------------|---------------------|-------------------|
| 1 | `{1.0, 2.5, 3.0}` | E1; E4; NOM length, all-positive | Returns `Number[]{1.0, 2.5, 3.0}` |
| 2 | `{−1.0, −2.5, −3.0}` | E1; E5; NOM length, all-negative | Returns `Number[]{−1.0, −2.5, −3.0}` |
| 3 | `{−1.0, 0.0, 3.0}` | E1; E6; NOM length, mixed values with zero | Returns `Number[]{−1.0, 0.0, 3.0}` |
| 4 | `{}` | E3; LB length = 0 | Returns empty `Number[]` of length 0 |
| 5 | `{5.0}` | E2; E4; ALB length = 1, single positive element | Returns `Number[]{5.0}` |
| 6 | `null` | U1; null not permitted | Throws `InvalidParameterException` |
| 7 | `{Double.NaN}` | E2; U2; NaN element | Returns `Number[]` containing `NaN` |
| 8 | `{Double.POSITIVE_INFINITY}` | E2; U3; positive infinity element | Returns `Number[]` containing `POSITIVE_INFINITY` |
| 9 | `{Double.MAX_VALUE, Double.MIN_VALUE}` | E1; U4; UB and LB element values | Returns `Number[]{MAX_VALUE, MIN_VALUE}` |
| 10 | `{1.0, 2.5, 3.0}` — check output length | E1; E4; verify `result.length == input.length` | `result.length == 3` |
| 11 | `{1.0, 2.5, 3.0}` — check element type | E1; E4; verify all returned elements are `Double` | Each element is `instanceof Double` |
