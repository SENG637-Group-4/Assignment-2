# Test Plan for `contains(double value)`

## Method to test
`public boolean contains(double value)`  

Returns true if the specified value is within the range and false otherwise.

---

## Equivalence classes for the input - `value`

- **E** - Expected inputs
- **U** - Unexpected inputs
- **BLB**: a value just below the lower bound  
- **LB**: the value on the lower boundary  
- **ALB**: a value just above the lower boundary  
- **NOM**: a nominal value  
- **BUB**: a value just below the upper bound  
- **UB**: the value on the upper boundary  
- **AUB**: a value just above the upper boundary

### Expected inputs (E)
- **E1:** double values within the range `(lower <= value <= upper)`

### Unexpected inputs (U)
- **U1:** double values not within the lower bound `(value < lower)`  
- **U2:** double values not within the upper bound `(value > upper)`  
- **U3:** non-double values (non-numbers, symbols; however, not possible in Java due to compile-time type check)

---

## Boundary value analysis

- Range: `lower = 5`, `upper = 95`  
- NOM: 54  
- LB: 5  
- UB: 95  
- BUB: 94  
- BLB: 4  
- AUB: 96  
- ALB: 6  

---

## Set of test cases based on equivalence classes and boundary value analysis

| Case | Input value | Relevant conditions | Expected Behavior |
|------|-------------|-------------------|-----------------|
| 1 | 54 | E1; NOM | Returns true |
| 2 | 5 | E1; LB | Returns true |
| 3 | 95 | E1; UB | Returns true |
| 4 | 94 | E1; BUB | Returns true |
| 5 | 4 | U1; BLB | Returns false |
| 6 | 96 | U2; AUB | Returns false |
| 7 | 6 | E1; ALB | Returns true |
| 8 | double maximum value | U2 | Returns false |
| 9 | double minimum value | U1 | Returns false |