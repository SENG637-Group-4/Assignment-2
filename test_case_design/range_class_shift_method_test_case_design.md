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
|-----------|-----------------------------------------------|--------------------|------------------|
| 1 | null, any, any | U1 | Throws `InvalidParameterException` |
| 2 | Range(-5,5), 0, true | E1; E2; E5; NOM | Range unchanged: Range(-5,5) |
| 3 | Range(-5,5), 4.9, false | E1; E3; E6; Lower just below 0 (BLB) | Range(-0.1,9.9) |
| 4 | Range(-5,5), 5, false | E1; E3; E6; Lower at 0 (LB boundary) | Range(0,10) |
| 5 | Range(-5,5), 5.1, false | E1; E3; E6; Lower just above 0 (ALB; crosses) | Range(0,10.1) |
| 6 | Range(-6,-2), 1.9, false | E1; E3; E6; Upper just below 0 (BUB) | Range(-4.1,-0.1) |
| 7 | Range(-6,-2), 2, false | E1; E3; E6; Upper at 0 (UB boundary) | Range(-4,0) |
| 8 | Range(-6,-2), 2.1, false | E1; E3; E6; Upper just above 0 (AUB; crosses) | Range(-3.9,0) |
| 9 | Range(-5,5), 2, true | E1; E3; E5; NOM | Range(-3,7) |
| 10 | Range(-6,4), -2, true | E1; E4; E5; NOM | Range(-8,2) |
| 11 | Range(2,6), -5, true | E1; E4; E5; NOM | Range(-3,1) |