# Test Plan for `shift(Range base, double delta, boolean allowZeroCrossing)`

## Method to test
`public static Range shift(Range base, double delta, boolean allowZeroCrossing)`  

Returns a range the size of the input range, which has been moved positively (to the right) by the delta value. If allowZeroCrossing is false, any bound which crosses the zero mark after shifting (either from negative to positive, or positive to negative), will become zero.

---

## Equivalence classes for inputs

- **E** - Expected inputs
- **U** - Unexpected inputs
- **BLB**: a value just below the lower bound  
- **LB**: the value on the lower boundary  
- **ALB**: a value just above the lower boundary  
- **NOM**: a nominal value  
- **BUB**: a value just below the upper bound  
- **UB**: the value on the upper boundary  
- **AUB**: a value just above the upper boundary

### Base (`Range` object)
- **E1:** valid non-null Range  
- **U1:** null - throws `InvalidParameterException`  

### Delta (`double`)
- **E2:** delta = 0 - range does not move  
- **E3:** delta > 0 - shift to the right  
- **E4:** delta < 0 - shift to the left  
- **U2:** non-number values (not possible in Java due to compile-time type check)

### allowZeroCrossing (`boolean`)
- **E5:** allowZeroCrossing = true - bounds allowed to cross zero  
- **E6:** allowZeroCrossing = false - bounds cannot cross zero; crossing bounds become 0  
- **U3:** non-boolean values (not possible in Java due to compile-time type check)  

---

## Set of test cases based on equivalence classes and boundary value analysis

| Test Case | Input Values (base, delta, allowZeroCrossing) | Relevant Conditions | Expected Behavior |
|-----------|-----------------------------------------------|-------------------|-----------------|
| 1 | null, any, any | U1 | Throws `InvalidParameterException` |
| 2 | Range(-5,5), 0, true | E1; E2; E5; NOM | Range unchanged |
| 3 | Range(-5,5), 0, false | E1; E2; E6; NOM | Range unchanged |
| 4 | Range(-5,5), 5, false | E1; E4; E6; LB | Lower bound moves to 0 (crosses zero) |
| 5 | Range(-5,5), 4.9, false | E1; E4; E6; BLB | Lower bound just below 0 - becomes 0 |
| 6 | Range(-5,5), 5.1, false | E1; E4; E6; ALB | Lower bound just above 0 - shifts normally |
| 7 | Range(-5,5), 5, false | E1; E4; E6; UB | Upper bound moves to 0 - becomes 0 |
| 8 | Range(-5,5), 4.9, false | E1; E4; E6; BUB | Upper bound just below 0 - becomes 0 |
| 9 | Range(-5,5), 5.1, false | E1; E4; E6; AUB | Upper bound just above 0 - shifts normally |
| 10 | Range(-5,5), 2, true | E1; E3; E5; NOM | Range shifts right normally |
| 11 | Range(-5,5), -2, true | E1; E4; E5; NOM | Range shifts left normally |
| 12 | Range(-5,5), 2, false | E1; E3; E6; NOM | Range shifts right normally (no crossing) |
| 13 | Range(-5,5), -2, false | E1; E4; E6; NOM | Range shifts left normally (no crossing) |