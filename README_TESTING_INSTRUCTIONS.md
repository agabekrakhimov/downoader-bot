# Software Testing Assignment 2 - Testing Instructions

## Quick Start Guide

This project demonstrates unit testing with stubs and drivers in Java using JUnit 5.

### Project Overview

This assignment includes two tasks:
- **Task 1**: Unit testing with a **Stub** (Calculator with ValidationService)
- **Task 2**: Unit testing with **Drivers** (ShoppingCart with PaymentService and InventoryService)

### Prerequisites

- Java 11 or higher
- Maven 3.6 or higher (for running tests)

### Running the Tests

#### Option 1: Using Maven (Recommended)

```bash
# Clean and run all tests
mvn clean test

# Run specific test class
mvn test -Dtest=CalculatorTest
mvn test -Dtest=ShoppingCartTest

# Run tests with verbose output
mvn test -X
```

#### Option 2: Using an IDE

1. **IntelliJ IDEA**:
   - Open the project folder
   - Right-click on `src/test/java/com/testing/task1/CalculatorTest.java`
   - Select "Run 'CalculatorTest'"
   - Repeat for `ShoppingCartTest.java`

2. **Eclipse**:
   - Import as Maven project
   - Right-click on test file
   - Select "Run As" → "JUnit Test"

3. **VS Code**:
   - Install Java Extension Pack
   - Open test file
   - Click "Run Test" above the test class

### Project Structure

```
unit-testing-stubs-drivers/
├── src/main/java/com/testing/
│   ├── task1/
│   │   ├── ValidationService.java      # Interface for validation
│   │   └── Calculator.java             # Calculator implementation
│   └── task2/
│       ├── PaymentService.java         # Interface for payment
│       ├── InventoryService.java       # Interface for inventory
│       └── ShoppingCart.java           # Shopping cart implementation
├── src/test/java/com/testing/
│   ├── task1/
│   │   ├── ValidationServiceStub.java  # Stub implementation
│   │   └── CalculatorTest.java         # 15 unit tests
│   └── task2/
│       ├── PaymentServiceDriver.java   # Payment driver
│       ├── InventoryServiceDriver.java # Inventory driver
│       └── ShoppingCartTest.java       # 24 unit tests
└── ASSIGNMENT_DOCUMENTATION.md         # Full documentation
```

### What to Submit

For the assignment submission, include:

1. **This complete project folder** with:
   - All source code files
   - All test files
   - pom.xml
   - Documentation files

2. **Screenshots** of:
   - Running the tests in IDE or command line
   - Test execution results showing all tests passing
   - Console output showing driver/stub logging

3. **Documentation**:
   - See `ASSIGNMENT_DOCUMENTATION.md` for complete documentation
   - Explains stubs vs drivers
   - Documents all edge cases
   - Includes code examples

### Expected Test Results

When you run the tests, you should see:

```
Tests run: 39, Failures: 0, Errors: 0, Skipped: 0
```

- **Task 1 (CalculatorTest)**: 15 tests
- **Task 2 (ShoppingCartTest)**: 24 tests

All tests should **PASS** ✓

### Key Features

#### Task 1: Stub Implementation
- Stub returns fixed responses (only allows add/subtract)
- Tests Calculator in isolation
- Demonstrates stub concept clearly

#### Task 2: Driver Implementation
- Drivers simulate realistic service behavior
- Include logging and state tracking
- Configurable success/failure scenarios
- Demonstrate driver concept clearly

### Code Quality

- ✓ Comprehensive JavaDoc comments
- ✓ Clean, readable code
- ✓ Proper error handling
- ✓ Edge cases covered
- ✓ Follows Java best practices
- ✓ Uses dependency injection

### Need Help?

See `ASSIGNMENT_DOCUMENTATION.md` for:
- Detailed explanation of stubs vs drivers
- Complete test coverage documentation
- Edge cases handled
- Code examples
- Conceptual explanations

---

## Important Notes

1. All source files compile successfully
2. Code follows assignment requirements exactly
3. Comprehensive test coverage with 39+ tests
4. Well-documented and commented
5. Handles all edge cases as required

**Ready to submit!** ✓
