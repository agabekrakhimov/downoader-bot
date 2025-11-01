package com.testing.task2;

/**
 * PaymentService Interface
 *
 * This interface defines the contract for processing payments in the shopping cart system.
 * In a real-world application, this service would integrate with payment gateways
 * (e.g., Stripe, PayPal) to process actual transactions.
 *
 * For testing purposes, we will create a driver implementation that simulates
 * payment processing without connecting to external payment systems.
 */
public interface PaymentService {

    /**
     * Processes a payment for the specified amount.
     *
     * @param amount The amount to charge (must be positive)
     * @return true if the payment was processed successfully, false otherwise
     */
    boolean processPayment(double amount);
}
