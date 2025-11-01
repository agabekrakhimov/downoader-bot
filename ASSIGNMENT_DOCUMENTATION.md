# Software Testing Assignment 2: Unit Testing with Stubs and Drivers

**Student Assignment Documentation**

## Table of Contents
1. [Assignment Overview](#assignment-overview)
2. [Project Structure](#project-structure)
3. [Task 1: Unit Testing with Stubs](#task-1-unit-testing-with-stubs)
4. [Task 2: Unit Testing with Drivers](#task-2-unit-testing-with-drivers)
5. [Running the Tests](#running-the-tests)
6. [Test Results](#test-results)
7. [Key Concepts](#key-concepts)
8. [Edge Cases Handled](#edge-cases-handled)
9. [Code Quality and Documentation](#code-quality-and-documentation)

---

## Assignment Overview

This assignment demonstrates proficiency in **unit testing using stubs and drivers** in Java. The project includes two separate tasks:

- **Task 1**: Testing a Calculator class using a **Stub** for the ValidationService
- **Task 2**: Testing a ShoppingCart class using **Drivers** for PaymentService and InventoryService

### Technologies Used
- **Java 11**
- **JUnit 5** (Jupiter)
- **Maven** for build management
- Test-Driven Development (TDD) principles

---

## Project Structure

```
unit-testing-stubs-drivers/
├── pom.xml                                 # Maven configuration
├── src/
│   ├── main/java/com/testing/
│   │   ├── task1/
│   │   │   ├── ValidationService.java      # Interface for validation
│   │   │   └── Calculator.java             # Calculator implementation
│   │   └── task2/
│   │       ├── PaymentService.java         # Interface for payment
│   │       ├── InventoryService.java       # Interface for inventory
│   │       └── ShoppingCart.java           # Shopping cart implementation
│   └── test/java/com/testing/
│       ├── task1/
│       │   ├── ValidationServiceStub.java  # Stub implementation
│       │   └── CalculatorTest.java         # Unit tests for Calculator
│       └── task2/
│           ├── PaymentServiceDriver.java   # Driver for payment
│           ├── InventoryServiceDriver.java # Driver for inventory
│           └── ShoppingCartTest.java       # Unit tests for ShoppingCart
└── ASSIGNMENT_DOCUMENTATION.md             # This file
```

---

## Task 1: Unit Testing with Stubs

### Overview
Task 1 involves testing a **Calculator** class that depends on an external **ValidationService** to verify if operations are valid before performing them.

### Components

#### 1. ValidationService Interface
```java
public interface ValidationService {
    boolean isValidOperation(String operation);
}
```

**Purpose**: Defines the contract for validating calculator operations. In a real-world scenario, this might connect to an external system.

#### 2. Calculator Class
```java
public class Calculator {
    private final ValidationService validationService;

    public double performOperation(int a, int b, String operation) {
        // Validates and performs arithmetic operations
    }
}
```

**Features**:
- Supports four operations: add, subtract, multiply, divide
- Uses ValidationService to check operation validity
- Handles edge cases (division by zero, null operations, etc.)
- Uses dependency injection for testability

#### 3. ValidationServiceStub
```java
public class ValidationServiceStub implements ValidationService {
    @Override
    public boolean isValidOperation(String operation) {
        // Only allows "add" and "subtract" for testing
        return operation.equals("add") || operation.equals("subtract");
    }
}
```

**What is a Stub?**
- A **stub** is a simplified implementation that returns predefined responses
- Used to replace real dependencies during testing
- Provides controlled, predictable behavior
- Makes tests faster, more reliable, and deterministic

**Stub Behavior**:
- Allows: `add` and `subtract` operations
- Rejects: `multiply`, `divide`, and any other operations
- This allows testing both valid and invalid operation scenarios

### Test Coverage

The `CalculatorTest` class includes comprehensive tests:

1. **Valid Operations**:
   - Addition with positive numbers
   - Addition with negative numbers
   - Subtraction with positive numbers
   - Subtraction resulting in negatives
   - Case insensitivity testing
   - Whitespace handling

2. **Invalid Operations** (rejected by stub):
   - Multiply operation
   - Divide operation
   - Unknown operations

3. **Edge Cases**:
   - Null operation parameter
   - Empty operation string
   - Division by zero (with custom stub)
   - Null validation service
   - Zero operands

### Key Test Examples

```java
@Test
public void testAddPositiveNumbers() {
    // Uses stub - only add/subtract allowed
    double result = calculator.performOperation(5, 3, "add");
    assertEquals(8.0, result, 0.001);
}

@Test
public void testMultiplyRejectedByStub() {
    // Multiply is rejected by the stub
    assertThrows(IllegalArgumentException.class,
        () -> calculator.performOperation(5, 3, "multiply"));
}
```

---

## Task 2: Unit Testing with Drivers

### Overview
Task 2 involves testing a **ShoppingCart** class that depends on two external services: **PaymentService** and **InventoryService**.

### Components

#### 1. PaymentService Interface
```java
public interface PaymentService {
    boolean processPayment(double amount);
}
```

**Purpose**: Processes payments for shopping cart checkouts.

#### 2. InventoryService Interface
```java
public interface InventoryService {
    boolean isInventoryAvailable(int itemCount);
}
```

**Purpose**: Checks if requested items are available in inventory.

#### 3. ShoppingCart Class
```java
public class ShoppingCart {
    private final PaymentService paymentService;
    private final InventoryService inventoryService;

    public boolean checkout(int itemCount, double price) {
        // 1. Check inventory
        // 2. Calculate total
        // 3. Process payment
        // 4. Return true only if both checks pass
    }
}
```

**Checkout Logic**:
1. Validates input parameters (positive item count, non-negative price)
2. Checks inventory availability
3. Calculates total amount (itemCount × price)
4. Processes payment
5. Returns `true` only if all steps succeed

#### 4. PaymentServiceDriver
```java
public class PaymentServiceDriver implements PaymentService {
    private boolean shouldSucceed;
    private double lastProcessedAmount;
    private int callCount;

    @Override
    public boolean processPayment(double amount) {
        // Simulates payment processing with logging
    }
}
```

**What is a Driver?**
- A **driver** is a test component that simulates the behavior of a real service
- More sophisticated than stubs - includes logic, state, and logging
- Provides configurable behavior for different test scenarios
- Tracks calls and state for verification

**Driver Features**:
- Configurable success/failure behavior
- Validates payment amounts (must be positive)
- Logs all payment processing attempts
- Tracks call count and last processed amount
- Provides reset functionality for test isolation

#### 5. InventoryServiceDriver
```java
public class InventoryServiceDriver implements InventoryService {
    private int availableStock;
    private int lastCheckedItemCount;
    private int callCount;

    @Override
    public boolean isInventoryAvailable(int itemCount) {
        // Simulates inventory checking with logging
    }
}
```

**Driver Features**:
- Maintains simulated stock level (default: 100 items)
- Returns true if requested items ≤ available stock
- Logs all inventory check attempts
- Tracks call count and last checked item count
- Provides methods to update stock levels

### Test Coverage

The `ShoppingCartTest` class includes comprehensive tests organized in nested test classes:

1. **Successful Checkout Tests**:
   - Successful checkout with sufficient inventory
   - Checkout with exact inventory match
   - Checkout with zero price (free items)
   - Checkout with decimal prices
   - Single item checkout

2. **Insufficient Inventory Tests**:
   - Insufficient inventory scenario
   - Zero inventory scenario
   - One item short scenario

3. **Payment Failure Tests**:
   - Payment processing failure
   - Large amount payment failure

4. **Edge Cases Tests**:
   - Negative item count rejection
   - Zero item count rejection
   - Negative price rejection
   - Null PaymentService rejection
   - Null InventoryService rejection
   - Very large values handling

5. **Driver Behavior Tests**:
   - PaymentServiceDriver behavior verification
   - InventoryServiceDriver behavior verification
   - Driver state persistence across calls

### Key Test Examples

```java
@Test
public void testSuccessfulCheckout() {
    // Both services succeed
    boolean result = shoppingCart.checkout(5, 10.0);
    assertTrue(result);
    assertEquals(50.0, paymentServiceDriver.getLastProcessedAmount(), 0.01);
}

@Test
public void testInsufficientInventory() {
    // Set inventory lower than requested
    inventoryServiceDriver.setAvailableStock(100);
    boolean result = shoppingCart.checkout(150, 10.0);

    assertFalse(result);
    // Payment should not be attempted
    assertEquals(0, paymentServiceDriver.getCallCount());
}

@Test
public void testPaymentFailure() {
    // Configure driver to simulate payment failure
    paymentServiceDriver.setShouldSucceed(false);
    boolean result = shoppingCart.checkout(5, 10.0);

    assertFalse(result);
    assertEquals(1, paymentServiceDriver.getCallCount());
}
```

---

## Running the Tests

### Prerequisites
- Java 11 or higher
- Maven 3.6 or higher

### Using Maven

1. **Compile the project**:
   ```bash
   mvn clean compile
   ```

2. **Run all tests**:
   ```bash
   mvn test
   ```

3. **Run tests with detailed output**:
   ```bash
   mvn test -X
   ```

4. **Run specific test class**:
   ```bash
   mvn test -Dtest=CalculatorTest
   mvn test -Dtest=ShoppingCartTest
   ```

### Using IDE (IntelliJ IDEA, Eclipse, VS Code)

1. Import the project as a Maven project
2. Right-click on test class or test method
3. Select "Run" or "Debug"

---

## Test Results

### Expected Output

When running the tests, you should see output similar to:

```
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running com.testing.task1.CalculatorTest

=== Testing Addition (5 + 3) ===
Expected: 8.0, Actual: 8.0
✓ Addition test passed

=== Testing Multiply (rejected by stub) ===
Exception message: Invalid operation: multiply
✓ Multiply rejection test passed

[INFO] Tests run: 15, Failures: 0, Errors: 0, Skipped: 0

[INFO] Running com.testing.task2.ShoppingCartTest

### Test: Successful Checkout (5 items @ $10.00 each) ###
[InventoryServiceDriver] Checking inventory...
[InventoryServiceDriver] ✓ Inventory available
[PaymentServiceDriver] Processing payment...
[PaymentServiceDriver] ✓ Payment processed successfully
✓ Successful checkout test passed

### Test: Insufficient Inventory (150 items requested, 100 available) ###
[InventoryServiceDriver] ❌ Inventory unavailable - Short by 50 items
✓ Insufficient inventory test passed

[INFO] Tests run: 24, Failures: 0, Errors: 0, Skipped: 0

[INFO] BUILD SUCCESS
```

### Test Metrics

- **Total Tests**: 39+
- **Task 1 (Calculator)**: 15 tests
- **Task 2 (ShoppingCart)**: 24 tests
- **Code Coverage**: Comprehensive coverage of all methods and edge cases
- **All tests**: PASS ✓

---

## Key Concepts

### Stubs vs Drivers

| Aspect | Stub | Driver |
|--------|------|--------|
| **Complexity** | Simple, minimal implementation | More sophisticated with logic |
| **Purpose** | Return predefined responses | Simulate realistic behavior |
| **State** | Usually stateless | Often maintains state |
| **Logging** | Minimal or none | Detailed logging |
| **Configuration** | Fixed behavior | Configurable behavior |
| **Example** | ValidationServiceStub | PaymentServiceDriver, InventoryServiceDriver |

### Dependency Injection

Both tasks demonstrate **dependency injection**, a design pattern where:
- Dependencies are passed into a class rather than created internally
- Enables easy swapping of real services with test doubles (stubs/drivers)
- Improves testability and maintainability
- Follows the Dependency Inversion Principle

```java
// Good: Uses dependency injection
public Calculator(ValidationService validationService) {
    this.validationService = validationService;
}

// Bad: Creates dependency internally (hard to test)
public Calculator() {
    this.validationService = new RealValidationService();
}
```

### Test Isolation

- Each test is independent and doesn't affect others
- `@BeforeEach` creates fresh instances for each test
- Drivers can be reset between tests if needed
- No shared state between tests

### Test Organization

Tests are organized using JUnit 5 `@Nested` classes:
- Groups related tests together
- Improves readability and maintainability
- Allows for better test output organization

---

## Edge Cases Handled

### Task 1 (Calculator) Edge Cases

1. **Null Operation**: Throws `IllegalArgumentException`
2. **Empty Operation**: Throws `IllegalArgumentException`
3. **Invalid Operations**: Rejected by stub with exception
4. **Division by Zero**: Throws `ArithmeticException`
5. **Null Validation Service**: Constructor throws exception
6. **Case Sensitivity**: Operations are case-insensitive
7. **Whitespace**: Leading/trailing whitespace is trimmed
8. **Zero Operands**: Handles correctly

### Task 2 (ShoppingCart) Edge Cases

1. **Negative Item Count**: Throws `IllegalArgumentException`
2. **Zero Item Count**: Throws `IllegalArgumentException`
3. **Negative Price**: Throws `IllegalArgumentException`
4. **Zero Price**: Allowed (free items)
5. **Null Services**: Constructor throws exception
6. **Insufficient Inventory**: Checkout fails, payment not attempted
7. **Payment Failure**: Checkout fails after inventory check
8. **Exact Inventory Match**: Handles boundary condition
9. **Very Large Values**: Correctly calculates large amounts
10. **Invalid Payment Amount**: Driver rejects invalid amounts

---

## Code Quality and Documentation

### Code Quality Features

1. **Comprehensive Comments**:
   - Every class has detailed JavaDoc comments
   - Methods include parameter descriptions and return values
   - Complex logic is explained inline

2. **Meaningful Names**:
   - Classes, methods, and variables use descriptive names
   - Follows Java naming conventions
   - Self-documenting code

3. **Clean Code Principles**:
   - Single Responsibility Principle
   - DRY (Don't Repeat Yourself)
   - Proper encapsulation
   - Consistent formatting

4. **Error Handling**:
   - Validates all input parameters
   - Provides clear error messages
   - Uses appropriate exception types

5. **Test Quality**:
   - Descriptive test names using `@DisplayName`
   - AAA pattern (Arrange, Act, Assert)
   - Comprehensive assertions
   - Informative console output

### Documentation

1. **Code Comments**:
   - Every class and method is documented
   - Complex logic is explained
   - Examples provided where helpful

2. **Test Documentation**:
   - Each test explains what it's testing
   - Console output shows test progress
   - Clear assertion messages

3. **This Document**:
   - Comprehensive overview of the assignment
   - Explains concepts (stubs, drivers, dependency injection)
   - Provides running instructions
   - Documents edge cases and test coverage

---

## Conclusion

This assignment successfully demonstrates:

✓ **Understanding of Stubs**: Created a stub that returns fixed responses for testing
✓ **Understanding of Drivers**: Created drivers that simulate realistic service behavior
✓ **Comprehensive Testing**: 39+ tests covering normal cases, edge cases, and error conditions
✓ **Code Quality**: Clean, well-documented, and maintainable code
✓ **JUnit Expertise**: Proper use of JUnit 5 features and best practices
✓ **Edge Case Handling**: Thorough handling of boundary conditions and error cases
✓ **Test Organization**: Well-structured tests using nested classes
✓ **Dependency Injection**: Proper use of DI for testability

All tests pass successfully, demonstrating correct implementation of both tasks.

---

## Author Information

**Assignment**: Software Testing Assignment 2 - Stubs and Drivers
**Framework**: JUnit 5
**Build Tool**: Maven
**Java Version**: 11
**Date**: November 2025
