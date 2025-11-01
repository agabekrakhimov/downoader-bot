package com.testing.task1;

/**
 * ValidationServiceStub
 *
 * This is a STUB implementation of the ValidationService interface.
 *
 * WHAT IS A STUB?
 * A stub is a simplified implementation of a component that returns predefined
 * responses. Stubs are used in unit testing to replace real dependencies with
 * controlled, predictable behavior.
 *
 * PURPOSE:
 * - Isolates the Calculator class for testing without relying on external services
 * - Provides controlled responses to test different scenarios
 * - Eliminates dependencies on complex external systems
 * - Makes tests faster, more reliable, and deterministic
 *
 * In this stub, we simulate validation rules by only allowing "add" and "subtract"
 * operations to be valid, while rejecting "multiply" and "divide".
 * This allows us to test how the Calculator handles both valid and invalid operations.
 */
public class ValidationServiceStub implements ValidationService {

    /**
     * Stub implementation that returns fixed responses.
     * For testing purposes, only "add" and "subtract" are considered valid.
     *
     * @param operation The operation to validate
     * @return true if operation is "add" or "subtract", false otherwise
     */
    @Override
    public boolean isValidOperation(String operation) {
        if (operation == null) {
            return false;
        }

        String normalizedOperation = operation.trim().toLowerCase();

        // For testing purposes, only allow add and subtract operations
        return normalizedOperation.equals("add") || normalizedOperation.equals("subtract");
    }
}
