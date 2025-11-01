package com.testing.task2;

/**
 * InventoryService Interface
 *
 * This interface defines the contract for checking inventory availability
 * in the shopping cart system.
 *
 * In a real-world application, this service would query a database or
 * warehouse management system to check actual stock levels.
 *
 * For testing purposes, we will create a driver implementation that simulates
 * inventory checks without connecting to external systems.
 */
public interface InventoryService {

    /**
     * Checks if the requested number of items is available in inventory.
     *
     * @param itemCount The number of items to check (must be positive)
     * @return true if the requested quantity is available, false otherwise
     */
    boolean isInventoryAvailable(int itemCount);
}
