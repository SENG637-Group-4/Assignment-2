# Test Plan for `getCumulativePercentages(KeyedValues data)`

## Method to Test

```java
public static KeyedValues getCumulativePercentages(KeyedValues data)
```

Returns a `KeyedValues` instance that contains the cumulative percentage values for the data in another `KeyedValues` instance. The cumulative percentage is each value's cumulative sum's portion of the sum of all the values.

**Example:**

| Key | Value |
|-----|-------|
| 0   | 5     |
| 1   | 9     |
| 2   | 2     |

**Returns:**

| Key | Value                  |
|-----|------------------------|
| 0   | 0.3125 (5 / 16)        |
| 1   | 0.875 ((5 + 9) / 16)   |
| 2   | 1.0 ((5 + 9 + 2) / 16) |

The percentages are values between 0.0 and 1.0 (where 1.0 = 100%).

**Parameters:** `data` - the data (null not permitted).  
**Returns:** The cumulative percentages.  
**Throws:** `InvalidParameterException` - if an invalid data object is passed in.

---

## Equivalence Classes for the Input - `data` (KeyedValues)

**E - Expected inputs**  
**U - Unexpected inputs**

**Boundary value notation:**
- **BLB:** a value just below the lower bound
- **LB:** the value on the lower boundary
- **ALB:** a value just above the lower boundary
- **NOM:** a nominal value
- **BUB:** a value just below the upper bound
- **UB:** the value on the upper boundary
- **AUB:** a value just above the upper boundary

---

### Input Variable: Number of Entries (int)

| ID | Description |
|----|-------------|
| E1 | `itemCount > 1` - multiple entries (NOM) |
| E2 | `itemCount = 1` - single entry (ALB) |
| E3 | `itemCount = 0` - empty KeyedValues (LB); valid per spec |
| U1 | `null` - not permitted; must throw `InvalidParameterException` |

---

### Input Variable: Entry Values (numeric)

| ID | Description |
|----|-------------|
| E4 | All positive values - standard case (NOM) |
| E5 | Mixed positive and negative values - sum may be non-trivial |
| E6 | All values equal - uniform distribution |
| E7 | One value is zero - partial contribution of 0 to cumulative |
| E8 | All values are zero - sum is 0; division-by-zero risk |
| U2 | A value is `null` - missing entry within a valid KeyedValues |
| U3 | `Double.NaN`, `Double.POSITIVE_INFINITY`, or `Double.NEGATIVE_INFINITY` - extreme/invalid double values |
| U4 | `Double.MAX_VALUE` - extreme boundary value (UB for element) |

---

### Input Variable: Keys

| ID | Description |
|----|-------------|
| E9  | Integer keys in order (0, 1, 2, …) - standard ordered case |
| E10 | Non-sequential or non-integer keys (e.g., strings or sparse integers) - arbitrary key types |

---

## Boundary Value Analysis

For the number of entries `n` in the `KeyedValues` object:

| Symbol       | n   | Description                       |
|--------------|-----|-----------------------------------|
| LB (n)       | 0   | Empty KeyedValues                 |
| ALB (n)      | 1   | Single-entry KeyedValues          |
| NOM (n)      | 3   | Typical entry count               |

For individual entry values `v`:

| Symbol       | v                        | Description                   |
|--------------|--------------------------|-------------------------------|
| LB (v)       | `Double.MIN_VALUE`       | Smallest positive double      |
| NOM (v)      | Standard positive double | Typical numeric input         |
| UB (v)       | `Double.MAX_VALUE`       | Largest positive double       |

---

## Set of Test Cases Based on Equivalence Classes and Boundary Value Analysis

| Test Case | Input `data`                                            | Relevant Conditions                                  | Expected Behavior                                                                                 |
|-----------|---------------------------------------------------------|------------------------------------------------------|---------------------------------------------------------------------------------------------------|
| 1         | Keys: {0→5, 1→9, 2→2}                                  | E1; E4; E9; NOM - 3 entries, all positive, ordered keys | Returns {0→0.3125, 1→0.875, 2→1.0}; last cumulative value equals exactly 1.0                   |
| 2         | Keys: {0→10}                                            | E2; E4; ALB - single entry                           | Returns {0→1.0}; single entry always yields 1.0                                                  |
| 3         | Keys: {} (empty KeyedValues)                            | E3; LB - itemCount = 0                               | Returns an empty `KeyedValues` instance; `getItemCount() == 0`                                   |
| 4         | `null`                                                  | U1; null not permitted                               | Throws `InvalidParameterException`                                                                |
| 5         | Keys: {0→5, 1→-2, 2→3}                                 | E1; E5; mixed positive and negative values           | Cumulative values computed correctly: {0→5/6≈0.833, 1→3/6=0.5, 2→6/6=1.0}; result[last]=1.0    |
| 6         | Keys: {0→4, 1→4, 2→4}                                  | E1; E6; all values equal                             | Returns {0→0.333…, 1→0.666…, 2→1.0}; uniform distribution                                      |
| 7         | Keys: {0→5, 1→0, 2→3}                                  | E1; E7; one value is zero                            | Returns {0→0.625, 1→0.625, 2→1.0}; zero entry does not advance cumulative                       |
| 8         | Keys: {0→0, 1→0, 2→0}                                  | E1; E8; all values zero - sum = 0                    | Throws `InvalidParameterException` or returns `NaN` values (division by zero edge case)          |
| 9         | Keys: {"a"→6, "b"→4}                                   | E1; E10; non-integer string keys                     | Returns `KeyedValues` with same keys {"a"→0.6, "b"→1.0}; keys preserved in result               |
| 10        | Keys: {0→`null`, 1→4}                                  | E1; U2; null value within valid KeyedValues          | Throws `InvalidParameterException` or handles gracefully per spec                                 |
| 11        | Keys: {0→`Double.NaN`, 1→5.0}                          | E1; U3; NaN element value                            | Result for key 0 is `NaN`; cumulative propagation of NaN is verified                             |
| 12        | Keys: {0→`Double.POSITIVE_INFINITY`, 1→5.0}            | E1; U3; POSITIVE_INFINITY element                    | Result for key 0 is `POSITIVE_INFINITY`; cumulative propagation of infinity is verified           |
| 13        | Keys: {0→`Double.MAX_VALUE`, 1→1.0}                    | E1; U4; UB element value                             | Result correctly handles large sum; key 1 returns 1.0 or near-1.0 depending on precision         |
| 14        | Keys: {0→5, 1→9, 2→2} - verify keys preserved          | E1; E4; E9; verify result keys match input keys      | Result `KeyedValues` has keys {0, 1, 2} in same order                                            |
| 15        | Keys: {0→5, 1→9, 2→2} - verify element types           | E1; E4; E9; verify result values are `Number`/`Double` | Each returned value is a `Number` (specifically `Double`) instance                              |
