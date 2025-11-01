package com.testing.task1;

/**
 * ValidationService Interface
 *
 * This interface defines the contract for validating calculator operations.
 * In a real-world scenario, this service might connect to an external system
 * or database to validate operations based on business rules.
 *
 * For testing purposes, we will create a stub implementation that simulates
 * the validation behavior without relying on external dependencies.
 */
public interface ValidationService {

    /**
     * Validates whether the given operation is allowed/supported.
     *
     * @param operation The operation to validate (e.g., "add", "subtract", "multiply", "divide")
     * @return true if the operation is valid, false otherwise
     */
    boolean isValidOperation(String operation);
}
