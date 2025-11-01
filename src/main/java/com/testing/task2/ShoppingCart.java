package com.testing.task2;

/**
 * ShoppingCart Class
 *
 * This class manages the checkout process for a shopping cart system.
 * It depends on two external services:
 * 1. InventoryService - to verify product availability
 * 2. PaymentService - to process payments
 *
 * This design demonstrates dependency injection and the use of drivers
 * for unit testing to simulate external service behavior.
 *
 * BUSINESS LOGIC:
 * - First check if inventory is available
 * - If inventory is available, calculate total amount
 * - Then process payment
 * - Return true only if both checks pass
 */
public class ShoppingCart {

    private final PaymentService paymentService;
    private final InventoryService inventoryService;

    /**
     * Constructor that accepts dependencies via dependency injection.
     * This allows us to inject different implementations (real services or drivers) for testing.
     *
     * @param paymentService   The payment service to use for processing payments
     * @param inventoryService The inventory service to use for checking stock
     */
    public ShoppingCart(PaymentService paymentService, InventoryService inventoryService) {
        if (paymentService == null) {
            throw new IllegalArgumentException("PaymentService cannot be null");
        }
        if (inventoryService == null) {
            throw new IllegalArgumentException("InventoryService cannot be null");
        }
        this.paymentService = paymentService;
        this.inventoryService = inventoryService;
    }

    /**
     * Processes checkout for the shopping cart.
     *
     * The checkout process follows these steps:
     * 1. Validate input parameters
     * 2. Check if the requested items are available in inventory
     * 3. Calculate the total amount (itemCount * price)
     * 4. Process the payment
     * 5. Return true only if all steps succeed
     *
     * @param itemCount The number of items to purchase (must be positive)
     * @param price     The price per item (must be positive)
     * @return true if checkout is successful (inventory available AND payment processed),
     * false otherwise
     * @throws IllegalArgumentException if itemCount or price is invalid
     */
    public boolean checkout(int itemCount, double price) {
        // Validate input parameters
        if (itemCount <= 0) {
            throw new IllegalArgumentException("Item count must be positive. Provided: " + itemCount);
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative. Provided: " + price);
        }

        // Step 1: Check inventory availability
        System.out.println("Checking inventory for " + itemCount + " items...");
        boolean inventoryAvailable = inventoryService.isInventoryAvailable(itemCount);

        if (!inventoryAvailable) {
            System.out.println("Checkout failed: Insufficient inventory");
            return false;
        }

        System.out.println("Inventory check passed");

        // Step 2: Calculate total amount
        double totalAmount = itemCount * price;
        System.out.println("Total amount to charge: $" + String.format("%.2f", totalAmount));

        // Step 3: Process payment
        System.out.println("Processing payment...");
        boolean paymentSuccessful = paymentService.processPayment(totalAmount);

        if (!paymentSuccessful) {
            System.out.println("Checkout failed: Payment processing failed");
            return false;
        }

        System.out.println("Checkout completed successfully!");
        return true;
    }

    /**
     * Gets the payment service instance (for testing purposes).
     *
     * @return The payment service
     */
    public PaymentService getPaymentService() {
        return paymentService;
    }

    /**
     * Gets the inventory service instance (for testing purposes).
     *
     * @return The inventory service
     */
    public InventoryService getInventoryService() {
        return inventoryService;
    }
}
