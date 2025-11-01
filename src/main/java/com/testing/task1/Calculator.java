package com.testing.task1;

/**
 * Calculator Class
 *
 * This class performs basic arithmetic operations (add, subtract, multiply, divide).
 * It depends on an external ValidationService to verify if operations are valid
 * before performing them.
 *
 * This design demonstrates dependency injection and the need for unit testing
 * with stubs to isolate the Calculator logic from external dependencies.
 */
public class Calculator {

    private final ValidationService validationService;

    /**
     * Constructor that accepts a ValidationService dependency.
     * This allows us to inject different implementations (real service or stub) for testing.
     *
     * @param validationService The validation service to use for checking operation validity
     */
    public Calculator(ValidationService validationService) {
        if (validationService == null) {
            throw new IllegalArgumentException("ValidationService cannot be null");
        }
        this.validationService = validationService;
    }

    /**
     * Performs an arithmetic operation on two integers.
     *
     * @param a         The first operand
     * @param b         The second operand
     * @param operation The operation to perform ("add", "subtract", "multiply", "divide")
     * @return The result of the operation
     * @throws IllegalArgumentException if the operation is invalid or not supported
     * @throws ArithmeticException     if division by zero is attempted
     */
    public double performOperation(int a, int b, String operation) {
        // Validate the operation parameter
        if (operation == null || operation.trim().isEmpty()) {
            throw new IllegalArgumentException("Operation cannot be null or empty");
        }

        // Normalize the operation to lowercase for case-insensitive comparison
        String normalizedOperation = operation.trim().toLowerCase();

        // Use the validation service to check if the operation is valid
        if (!validationService.isValidOperation(normalizedOperation)) {
            throw new IllegalArgumentException("Invalid operation: " + operation);
        }

        // Perform the appropriate arithmetic operation
        switch (normalizedOperation) {
            case "add":
                return add(a, b);
            case "subtract":
                return subtract(a, b);
            case "multiply":
                return multiply(a, b);
            case "divide":
                return divide(a, b);
            default:
                // This should not be reached if validation works correctly
                throw new IllegalArgumentException("Unsupported operation: " + operation);
        }
    }

    /**
     * Adds two integers.
     *
     * @param a First number
     * @param b Second number
     * @return Sum of a and b
     */
    private double add(int a, int b) {
        return a + b;
    }

    /**
     * Subtracts the second integer from the first.
     *
     * @param a First number
     * @param b Second number
     * @return Difference of a and b
     */
    private double subtract(int a, int b) {
        return a - b;
    }

    /**
     * Multiplies two integers.
     *
     * @param a First number
     * @param b Second number
     * @return Product of a and b
     */
    private double multiply(int a, int b) {
        return a * b;
    }

    /**
     * Divides the first integer by the second.
     *
     * @param a First number (dividend)
     * @param b Second number (divisor)
     * @return Quotient of a divided by b
     * @throws ArithmeticException if b is zero
     */
    private double divide(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("Cannot divide by zero");
        }
        return (double) a / b;
    }
}
