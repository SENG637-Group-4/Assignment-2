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

| Symbol | Meaning |
|--------|---------|
| **BLB** | A value just below the lower bound |
| **LB**  | The value on the lower boundary |
| **ALB** | A value just above the lower boundary |
| **NOM** | A nominal value |
| **BUB** | A value just below the upper bound |
| **UB**  | The value on the upper boundary |
| **AUB** | A value just above the upper boundary |

---

### Input Variable: Number of Entries (`int`)

| ID | Description |
|----|-------------|
| E1 | `itemCount > 1` - multiple entries (NOM) |
| E2 | `itemCount = 1` - single entry (ALB) |
| E3 | `itemCount = 0` - empty `KeyedValues` (LB); valid per spec |
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
| U2 | A value is `null` - missing entry within a valid `KeyedValues` |
| U3 | `Double.NaN`, `Double.POSITIVE_INFINITY`, or `Double.NEGATIVE_INFINITY` - extreme/invalid double values |
| U4 | `Double.MAX_VALUE` - extreme boundary value (UB for element) |

---

### Input Variable: Keys

| ID  | Description |
|-----|-------------|
| E9  | Integer keys in order (0, 1, 2, …) - standard ordered case |
| E10 | Non-sequential or non-integer keys (e.g., strings or sparse integers) - arbitrary key types |

---

## Boundary Value Analysis

For the number of entries `n` in the `KeyedValues` object:

| Symbol  | n | Description              |
|---------|---|--------------------------|
| LB (n)  | 0 | Empty `KeyedValues`      |
| ALB (n) | 1 | Single-entry `KeyedValues` |
| NOM (n) | 3 | Typical entry count      |

For individual entry values `v`:

| Symbol  | v                        | Description                   |
|---------|--------------------------|-------------------------------|
| LB (v)  | `Double.MIN_VALUE`       | Smallest positive double      |
| NOM (v) | Standard positive double | Typical numeric input         |
| UB (v)  | `Double.MAX_VALUE`       | Largest positive double       |

---

## Test Cases

| TC | Input | Conditions | Expected Behaviour |
|----|-------|------------|--------------------|
| 1  | `{0→5, 1→9, 2→2}` | E1, E4, E9 | Returns `{0→0.3125, 1→0.875, 2→1.0}`; keys preserved in order; each value is a `Double` |
| 2  | `{0→10}` | E2, E4 | Returns `{0→1.0}`; key preserved; value is a `Double` |
| 3  | `{}` | E3 | Returns empty `KeyedValues` with `itemCount = 0` |
| 4  | `null` | U1 | Throws **`InvalidParameterException`** |
| 5  | `{0→5, 1→-2, 2→3}` | E1, E5 | Total = 6; returns `{0→0.8333…, 1→0.5, 2→1.0}` |
| 6  | `{0→4, 1→4, 2→4}` | E1, E6 | Returns `{0→0.3333…, 1→0.6666…, 2→1.0}` |
| 7  | `{0→5, 1→0, 2→3}` | E1, E7 | Returns `{0→0.625, 1→0.625, 2→1.0}` |
| 8  | `{0→0, 1→0, 2→0}` | E1, E8 | All returned values are **NaN** (0.0 / 0.0); division-by-zero case |
| 9  | `{"a"→6, "b"→4}` | E1, E10 | Returns `{"a"→0.6, "b"→1.0}`; non-integer keys preserved in order |
| 10 | `{0→null, 1→4}` | U2 | Throws **`NullPointerException`** *(observed behaviour; not spec-guaranteed)* |
| 11 | `{0→NaN, 1→5}` | U3 | First cumulative = NaN; subsequent values also NaN (IEEE 754 propagation) *(observe and document)* |
| 12 | `{0→Infinity, 1→5}` | U3 | Total = Infinity; first value = NaN (Inf/Inf); propagation follows IEEE 754 rules *(observe and document)* |
| 13 | `{0→Double.MAX_VALUE, 1→1}` | U4 | First ≈ 1.0; second = 1.0 due to floating-point precision dominance |


