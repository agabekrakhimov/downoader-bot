package com.testing.task2;

/**
 * InventoryServiceDriver
 *
 * This is a DRIVER implementation of the InventoryService interface.
 *
 * WHAT IS A DRIVER?
 * A driver is a test component that simulates the behavior of a real service.
 * It provides more sophisticated behavior than a stub, often including
 * configurable responses and detailed logging.
 *
 * PURPOSE:
 * - Simulates inventory checking without connecting to a real database
 * - Provides configurable behavior for different test scenarios
 * - Logs actions for visibility into the test execution flow
 * - Allows testing of the ShoppingCart logic in isolation
 *
 * BEHAVIOR:
 * - Maintains a simulated stock level
 * - Returns true if requested items <= available stock
 * - Returns false if requested items > available stock
 * - Logs all inventory check attempts
 * - Can be configured with different stock levels for testing
 */
public class InventoryServiceDriver implements InventoryService {

    private int availableStock;
    private int lastCheckedItemCount;
    private int callCount;

    /**
     * Default constructor - initializes with 100 items in stock.
     */
    public InventoryServiceDriver() {
        this(100);
    }

    /**
     * Constructor that allows configuring the available stock level.
     *
     * @param availableStock The number of items available in inventory
     */
    public InventoryServiceDriver(int availableStock) {
        if (availableStock < 0) {
            throw new IllegalArgumentException("Available stock cannot be negative");
        }
        this.availableStock = availableStock;
        this.lastCheckedItemCount = 0;
        this.callCount = 0;
    }

    /**
     * Simulates inventory availability checking with realistic behavior.
     *
     * @param itemCount The number of items to check
     * @return true if requested items are available, false otherwise
     */
    @Override
    public boolean isInventoryAvailable(int itemCount) {
        callCount++;

        System.out.println("\n[InventoryServiceDriver] Checking inventory...");
        System.out.println("[InventoryServiceDriver] Check #" + callCount);
        System.out.println("[InventoryServiceDriver] Requested items: " + itemCount);
        System.out.println("[InventoryServiceDriver] Available stock: " + availableStock);

        // Store for testing verification
        lastCheckedItemCount = itemCount;

        // Validate item count
        if (itemCount <= 0) {
            System.out.println("[InventoryServiceDriver] ❌ Invalid request - Item count must be positive");
            return false;
        }

        // Check if sufficient inventory is available
        boolean isAvailable = itemCount <= availableStock;

        if (isAvailable) {
            System.out.println("[InventoryServiceDriver] ✓ Inventory available - Sufficient stock");
        } else {
            int shortage = itemCount - availableStock;
            System.out.println("[InventoryServiceDriver] ❌ Inventory unavailable - Short by " + shortage + " items");
        }

        return isAvailable;
    }

    /**
     * Sets the available stock level.
     * Useful for testing different inventory scenarios.
     *
     * @param availableStock The new stock level
     */
    public void setAvailableStock(int availableStock) {
        if (availableStock < 0) {
            throw new IllegalArgumentException("Available stock cannot be negative");
        }
        this.availableStock = availableStock;
        System.out.println("[InventoryServiceDriver] Stock level updated to: " + availableStock);
    }

    /**
     * Gets the current available stock level.
     *
     * @return The available stock
     */
    public int getAvailableStock() {
        return availableStock;
    }

    /**
     * Gets the last item count that was checked.
     * Useful for test verification.
     *
     * @return The last checked item count
     */
    public int getLastCheckedItemCount() {
        return lastCheckedItemCount;
    }

    /**
     * Gets the number of times isInventoryAvailable was called.
     * Useful for test verification.
     *
     * @return The number of calls
     */
    public int getCallCount() {
        return callCount;
    }

    /**
     * Simulates consuming inventory (reducing stock).
     * Useful for testing sequential operations.
     *
     * @param itemCount The number of items to consume
     */
    public void consumeInventory(int itemCount) {
        if (itemCount > availableStock) {
            throw new IllegalStateException("Cannot consume more items than available");
        }
        availableStock -= itemCount;
        System.out.println("[InventoryServiceDriver] Consumed " + itemCount + " items. Remaining: " + availableStock);
    }

    /**
     * Resets the driver state for fresh testing.
     */
    public void reset() {
        this.callCount = 0;
        this.lastCheckedItemCount = 0;
        this.availableStock = 100; // Reset to default
    }
}
