# Test Plan for `getLength()`

## Method to test
`public double getLength()`  

Returns the length of the range, calculated as `upper - lower`.

---

## Equivalence classes for the Range object under test

- **E** - Expected inputs
- **U** - Unexpected inputs
- **BLB**: a value just below the lower bound  
- **LB**: the value on the lower boundary  
- **ALB**: a value just above the lower boundary  
- **NOM**: a nominal value  
- **BUB**: a value just below the upper bound  
- **UB**: the value on the upper boundary  
- **AUB**: a value just above the upper boundary

> Note: `getLength()` takes no parameters. The "inputs" are the `lower` and `upper` fields of the `Range` object on which the method is called.

### Input variable: `lower` (Range lower bound)
- **E1:** lower is a negative value
- **E2:** lower is zero
- **E3:** lower is a positive value

### Input variable: `upper` (Range upper bound)
- **E4:** upper is a negative value
- **E5:** upper is zero
- **E6:** upper is a positive value

### Combined range equivalence classes
- **EC1:** lower < 0, upper > 0 — cross-zero range → length = upper − lower > 0
- **EC2:** lower ≥ 0, upper > 0 — positive (or zero-lower) range → length = upper − lower > 0
- **EC3:** lower < 0, upper ≤ 0 — negative (or zero-upper) range → length = upper − lower > 0
- **EC4:** lower = upper — zero-length range → length = 0
- **EC5:** extreme values near `Double.MAX_VALUE` — overflow risk

---

## Boundary value analysis

Boundary of interest: **zero** for each bound individually.  
Base range for BVA reference: `lower = -1`, `upper = 1`

| Symbol | Value | Description |
|--------|-------|-------------|
| BLB (lower side) | −1 − ε | Just below lower, e.g., −1.0001 |
| LB | −1 | Exactly at lower bound |
| ALB (lower side) | −1 + ε | Just above lower, e.g., −0.9999 |
| NOM | Range(−1, 1) | Typical cross-zero range |
| BUB (upper side) | 1 − ε | Just below upper, e.g., 0.9999 |
| UB | 1 | Exactly at upper bound |
| AUB (upper side) | 1 + ε | Just above upper, e.g., 1.0001 |

Zero boundary cases for `lower`:
- lower BLB (crossing zero): −0.0001 → cross-zero range  
- lower LB (at zero): 0.0 → EC2  
- lower ALB (just above zero): 0.0001 → EC2

Zero boundary cases for `upper`:
- upper BUB (just below zero): −0.0001 → EC3  
- upper UB (at zero): 0.0 → EC3  
- upper AUB (just above zero): 0.0001 → EC1

---

## Set of test cases based on equivalence classes and boundary value analysis

| Test Case | Range (lower, upper) | Relevant Conditions | Expected `getLength()` |
|-----------|----------------------|---------------------|------------------------|
| 1 | Range(2.0, 6.0) | EC2; E3, E6; NOM positive range | 4.0 |
| 2 | Range(−10.0, 10.0) | EC1; E1, E6; NOM cross-zero range | 20.0 |
| 3 | Range(−10.0, −2.0) | EC3; E1, E4; negative-only range | 8.0 |
| 4 | Range(5.0, 5.0) | EC4; E3, E6; LB = UB, zero-length | 0.0 |
| 5 | Range(0.0, 5.0) | EC2; E2, E6; lower = 0 (LB boundary for lower) | 5.0 |
| 6 | Range(−5.0, 0.0) | EC3; E1, E5; upper = 0 (UB boundary for upper) | 5.0 |
| 7 | Range(0.0, 0.0) | EC4; E2, E5; both bounds at zero | 0.0 |
| 8 | Range(−0.0001, 0.0001) | EC1; BLB, AUB; tiny range straddling zero | 0.0002 |
| 9 | Range(−1.0, −0.0001) | EC3; E1, BUB; upper just below zero | 0.9999 |
| 10 | Range(0.0001, 1.0) | EC2; ALB, E6; lower just above zero | 0.9999 |
