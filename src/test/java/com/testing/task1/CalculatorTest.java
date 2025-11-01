package com.testing.task1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import static org.junit.jupiter.api.Assertions.*;

/**
 * CalculatorTest - Unit Tests for Calculator Class Using a Stub
 *
 * This test class demonstrates unit testing with STUBS.
 *
 * TEST STRATEGY:
 * 1. We use ValidationServiceStub instead of the real ValidationService
 * 2. The stub only allows "add" and "subtract" operations (controlled behavior)
 * 3. This allows us to test the Calculator in isolation
 * 4. We can verify both successful operations and error handling
 *
 * EDGE CASES TESTED:
 * - Valid operations (add, subtract)
 * - Invalid operations (multiply, divide - rejected by stub)
 * - Division by zero
 * - Null and empty operation strings
 * - Null validation service
 * - Case insensitivity
 * - Whitespace handling
 */
@DisplayName("Calculator Unit Tests with Stub")
public class CalculatorTest {

    private Calculator calculator;
    private ValidationService validationServiceStub;

    /**
     * Set up method that runs before each test.
     * Creates a fresh Calculator instance with the stub for each test.
     */
    @BeforeEach
    public void setUp() {
        // Create the stub that will replace the real ValidationService
        validationServiceStub = new ValidationServiceStub();

        // Inject the stub into the Calculator
        calculator = new Calculator(validationServiceStub);

        System.out.println("Test setup complete: Calculator initialized with ValidationServiceStub");
    }

    /**
     * Nested class for testing valid operations
     */
    @Nested
    @DisplayName("Valid Operations Tests")
    class ValidOperationsTests {

        @Test
        @DisplayName("Test addition with positive numbers")
        public void testAddPositiveNumbers() {
            System.out.println("\n=== Testing Addition (5 + 3) ===");

            double result = calculator.performOperation(5, 3, "add");

            System.out.println("Expected: 8.0, Actual: " + result);
            assertEquals(8.0, result, 0.001, "5 + 3 should equal 8");

            System.out.println("✓ Addition test passed");
        }

        @Test
        @DisplayName("Test addition with negative numbers")
        public void testAddNegativeNumbers() {
            System.out.println("\n=== Testing Addition with Negatives (-5 + -3) ===");

            double result = calculator.performOperation(-5, -3, "add");

            System.out.println("Expected: -8.0, Actual: " + result);
            assertEquals(-8.0, result, 0.001, "-5 + -3 should equal -8");

            System.out.println("✓ Addition with negatives test passed");
        }

        @Test
        @DisplayName("Test subtraction with positive numbers")
        public void testSubtractPositiveNumbers() {
            System.out.println("\n=== Testing Subtraction (10 - 4) ===");

            double result = calculator.performOperation(10, 4, "subtract");

            System.out.println("Expected: 6.0, Actual: " + result);
            assertEquals(6.0, result, 0.001, "10 - 4 should equal 6");

            System.out.println("✓ Subtraction test passed");
        }

        @Test
        @DisplayName("Test subtraction resulting in negative")
        public void testSubtractResultingInNegative() {
            System.out.println("\n=== Testing Subtraction (3 - 10) ===");

            double result = calculator.performOperation(3, 10, "subtract");

            System.out.println("Expected: -7.0, Actual: " + result);
            assertEquals(-7.0, result, 0.001, "3 - 10 should equal -7");

            System.out.println("✓ Subtraction with negative result test passed");
        }

        @Test
        @DisplayName("Test case insensitivity for operations")
        public void testCaseInsensitivity() {
            System.out.println("\n=== Testing Case Insensitivity (ADD, Add, add) ===");

            double result1 = calculator.performOperation(5, 3, "ADD");
            double result2 = calculator.performOperation(5, 3, "Add");
            double result3 = calculator.performOperation(5, 3, "add");

            System.out.println("All variants produced: " + result1);
            assertEquals(8.0, result1, 0.001);
            assertEquals(8.0, result2, 0.001);
            assertEquals(8.0, result3, 0.001);

            System.out.println("✓ Case insensitivity test passed");
        }

        @Test
        @DisplayName("Test operations with whitespace")
        public void testOperationsWithWhitespace() {
            System.out.println("\n=== Testing Whitespace Handling (' add ') ===");

            double result = calculator.performOperation(5, 3, " add ");

            System.out.println("Expected: 8.0, Actual: " + result);
            assertEquals(8.0, result, 0.001, "Operation with whitespace should work");

            System.out.println("✓ Whitespace handling test passed");
        }
    }

    /**
     * Nested class for testing invalid operations (rejected by stub)
     */
    @Nested
    @DisplayName("Invalid Operations Tests")
    class InvalidOperationsTests {

        @Test
        @DisplayName("Test multiply operation (rejected by stub)")
        public void testMultiplyRejectedByStub() {
            System.out.println("\n=== Testing Multiply (rejected by stub) ===");

            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> calculator.performOperation(5, 3, "multiply"),
                    "Multiply should be rejected by the stub"
            );

            System.out.println("Exception message: " + exception.getMessage());
            assertTrue(exception.getMessage().contains("Invalid operation"));

            System.out.println("✓ Multiply rejection test passed");
        }

        @Test
        @DisplayName("Test divide operation (rejected by stub)")
        public void testDivideRejectedByStub() {
            System.out.println("\n=== Testing Divide (rejected by stub) ===");

            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> calculator.performOperation(10, 2, "divide"),
                    "Divide should be rejected by the stub"
            );

            System.out.println("Exception message: " + exception.getMessage());
            assertTrue(exception.getMessage().contains("Invalid operation"));

            System.out.println("✓ Divide rejection test passed");
        }

        @Test
        @DisplayName("Test with unknown operation")
        public void testUnknownOperation() {
            System.out.println("\n=== Testing Unknown Operation ===");

            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> calculator.performOperation(5, 3, "modulus"),
                    "Unknown operation should throw exception"
            );

            System.out.println("Exception message: " + exception.getMessage());
            assertTrue(exception.getMessage().contains("Invalid operation"));

            System.out.println("✓ Unknown operation test passed");
        }
    }

    /**
     * Nested class for testing edge cases
     */
    @Nested
    @DisplayName("Edge Cases Tests")
    class EdgeCasesTests {

        @Test
        @DisplayName("Test with null operation")
        public void testNullOperation() {
            System.out.println("\n=== Testing Null Operation ===");

            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> calculator.performOperation(5, 3, null),
                    "Null operation should throw exception"
            );

            System.out.println("Exception message: " + exception.getMessage());
            assertTrue(exception.getMessage().contains("cannot be null"));

            System.out.println("✓ Null operation test passed");
        }

        @Test
        @DisplayName("Test with empty operation")
        public void testEmptyOperation() {
            System.out.println("\n=== Testing Empty Operation ===");

            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> calculator.performOperation(5, 3, ""),
                    "Empty operation should throw exception"
            );

            System.out.println("Exception message: " + exception.getMessage());

            System.out.println("✓ Empty operation test passed");
        }

        @Test
        @DisplayName("Test division by zero")
        public void testDivisionByZero() {
            System.out.println("\n=== Testing Division by Zero ===");

            // Note: Division is not allowed by the stub, but we can test with a custom stub
            ValidationService allowAllStub = operation -> true;
            Calculator calcWithAllOps = new Calculator(allowAllStub);

            ArithmeticException exception = assertThrows(
                    ArithmeticException.class,
                    () -> calcWithAllOps.performOperation(10, 0, "divide"),
                    "Division by zero should throw ArithmeticException"
            );

            System.out.println("Exception message: " + exception.getMessage());
            assertTrue(exception.getMessage().contains("divide by zero"));

            System.out.println("✓ Division by zero test passed");
        }

        @Test
        @DisplayName("Test null validation service")
        public void testNullValidationService() {
            System.out.println("\n=== Testing Null ValidationService ===");

            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> new Calculator(null),
                    "Null validation service should throw exception"
            );

            System.out.println("Exception message: " + exception.getMessage());
            assertTrue(exception.getMessage().contains("cannot be null"));

            System.out.println("✓ Null validation service test passed");
        }

        @Test
        @DisplayName("Test with zero operands")
        public void testZeroOperands() {
            System.out.println("\n=== Testing Zero Operands ===");

            double result = calculator.performOperation(0, 0, "add");

            System.out.println("Expected: 0.0, Actual: " + result);
            assertEquals(0.0, result, 0.001, "0 + 0 should equal 0");

            System.out.println("✓ Zero operands test passed");
        }
    }

    /**
     * Test to verify stub behavior
     */
    @Test
    @DisplayName("Verify stub behavior independently")
    public void testStubBehavior() {
        System.out.println("\n=== Testing Stub Behavior ===");

        assertTrue(validationServiceStub.isValidOperation("add"), "Stub should allow 'add'");
        assertTrue(validationServiceStub.isValidOperation("subtract"), "Stub should allow 'subtract'");
        assertFalse(validationServiceStub.isValidOperation("multiply"), "Stub should reject 'multiply'");
        assertFalse(validationServiceStub.isValidOperation("divide"), "Stub should reject 'divide'");
        assertFalse(validationServiceStub.isValidOperation(null), "Stub should reject null");

        System.out.println("✓ Stub behavior verification passed");
    }
}
