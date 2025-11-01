package com.testing.task2;

/**
 * PaymentServiceDriver
 *
 * This is a DRIVER implementation of the PaymentService interface.
 *
 * WHAT IS A DRIVER?
 * A driver is a test component that simulates the behavior of a real service
 * or module. Unlike stubs (which return fixed responses), drivers typically
 * contain more logic to simulate realistic behavior and often include logging
 * to demonstrate their execution.
 *
 * PURPOSE:
 * - Simulates payment processing without connecting to real payment gateways
 * - Provides configurable behavior for different test scenarios
 * - Logs actions for visibility into the test execution flow
 * - Allows testing of the ShoppingCart logic in isolation
 *
 * BEHAVIOR:
 * - Accepts payments above a minimum threshold ($0.01)
 * - Rejects payments of $0.00 or negative amounts
 * - Can be configured to simulate payment failures
 * - Logs all payment processing attempts
 */
public class PaymentServiceDriver implements PaymentService {

    private boolean shouldSucceed;
    private double lastProcessedAmount;
    private int callCount;

    /**
     * Default constructor - payment processing will succeed by default.
     */
    public PaymentServiceDriver() {
        this(true);
    }

    /**
     * Constructor that allows configuring success/failure behavior.
     *
     * @param shouldSucceed true to simulate successful payments, false to simulate failures
     */
    public PaymentServiceDriver(boolean shouldSucceed) {
        this.shouldSucceed = shouldSucceed;
        this.lastProcessedAmount = 0.0;
        this.callCount = 0;
    }

    /**
     * Simulates payment processing with realistic behavior.
     *
     * @param amount The amount to charge
     * @return true if payment is successful, false otherwise
     */
    @Override
    public boolean processPayment(double amount) {
        callCount++;

        System.out.println("\n[PaymentServiceDriver] Processing payment...");
        System.out.println("[PaymentServiceDriver] Attempt #" + callCount);
        System.out.println("[PaymentServiceDriver] Amount: $" + String.format("%.2f", amount));

        // Store for testing verification
        lastProcessedAmount = amount;

        // Validate amount
        if (amount <= 0) {
            System.out.println("[PaymentServiceDriver] ❌ Payment rejected - Invalid amount (must be positive)");
            return false;
        }

        // Simulate payment processing based on configuration
        if (!shouldSucceed) {
            System.out.println("[PaymentServiceDriver] ❌ Payment failed - Simulated failure");
            return false;
        }

        // Simulate successful payment
        System.out.println("[PaymentServiceDriver] ✓ Payment processed successfully");
        System.out.println("[PaymentServiceDriver] Transaction ID: TXN-" + System.currentTimeMillis());

        return true;
    }

    /**
     * Configures whether payments should succeed or fail.
     * Useful for testing different scenarios.
     *
     * @param shouldSucceed true to simulate success, false to simulate failure
     */
    public void setShouldSucceed(boolean shouldSucceed) {
        this.shouldSucceed = shouldSucceed;
    }

    /**
     * Gets the last amount that was attempted to be processed.
     * Useful for test verification.
     *
     * @return The last processed amount
     */
    public double getLastProcessedAmount() {
        return lastProcessedAmount;
    }

    /**
     * Gets the number of times processPayment was called.
     * Useful for test verification.
     *
     * @return The number of calls
     */
    public int getCallCount() {
        return callCount;
    }

    /**
     * Resets the driver state for fresh testing.
     */
    public void reset() {
        this.callCount = 0;
        this.lastProcessedAmount = 0.0;
        this.shouldSucceed = true;
    }
}
