package com.testing.task2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ShoppingCartTest - Unit Tests for ShoppingCart Class Using Drivers
 *
 * This test class demonstrates unit testing with DRIVERS.
 *
 * WHAT ARE DRIVERS?
 * Drivers are test components that simulate the behavior of external services
 * or modules. They are more sophisticated than stubs and typically include:
 * - Configurable behavior for different scenarios
 * - State management
 * - Detailed logging of actions
 * - Verification capabilities
 *
 * TEST STRATEGY:
 * 1. We use PaymentServiceDriver and InventoryServiceDriver instead of real services
 * 2. The drivers simulate realistic payment and inventory behavior
 * 3. We can configure the drivers to test different scenarios (success, failure, edge cases)
 * 4. The drivers log their actions, making test execution transparent
 * 5. We verify both the checkout result and the driver states
 *
 * EDGE CASES TESTED:
 * - Successful checkout (sufficient inventory, successful payment)
 * - Insufficient inventory
 * - Payment failure
 * - Invalid item counts (zero, negative)
 * - Invalid prices (negative)
 * - Null services
 * - Boundary conditions (exact inventory match, zero price)
 */
@DisplayName("ShoppingCart Unit Tests with Drivers")
public class ShoppingCartTest {

    private ShoppingCart shoppingCart;
    private PaymentServiceDriver paymentServiceDriver;
    private InventoryServiceDriver inventoryServiceDriver;

    /**
     * Set up method that runs before each test.
     * Creates a fresh ShoppingCart instance with drivers for each test.
     */
    @BeforeEach
    public void setUp() {
        // Create drivers that will replace the real services
        paymentServiceDriver = new PaymentServiceDriver(true); // Payment succeeds by default
        inventoryServiceDriver = new InventoryServiceDriver(100); // 100 items in stock by default

        // Inject the drivers into the ShoppingCart
        shoppingCart = new ShoppingCart(paymentServiceDriver, inventoryServiceDriver);

        System.out.println("\n" + "=".repeat(70));
        System.out.println("Test setup complete: ShoppingCart initialized with drivers");
        System.out.println("Initial inventory: " + inventoryServiceDriver.getAvailableStock() + " items");
        System.out.println("=".repeat(70));
    }

    /**
     * Nested class for testing successful checkout scenarios
     */
    @Nested
    @DisplayName("Successful Checkout Tests")
    class SuccessfulCheckoutTests {

        @Test
        @DisplayName("Test successful checkout with sufficient inventory and valid payment")
        public void testSuccessfulCheckout() {
            System.out.println("\n### Test: Successful Checkout (5 items @ $10.00 each) ###");

            boolean result = shoppingCart.checkout(5, 10.0);

            assertTrue(result, "Checkout should succeed with sufficient inventory and valid payment");
            assertEquals(50.0, paymentServiceDriver.getLastProcessedAmount(), 0.01,
                    "Payment amount should be 5 * 10.0 = 50.0");
            assertEquals(1, paymentServiceDriver.getCallCount(), "Payment should be processed once");
            assertEquals(1, inventoryServiceDriver.getCallCount(), "Inventory should be checked once");

            System.out.println("✓ Successful checkout test passed");
        }

        @Test
        @DisplayName("Test checkout with exact inventory match")
        public void testCheckoutWithExactInventory() {
            System.out.println("\n### Test: Checkout with Exact Inventory Match (100 items @ $5.00) ###");

            // Set inventory to exact amount needed
            inventoryServiceDriver.setAvailableStock(100);

            boolean result = shoppingCart.checkout(100, 5.0);

            assertTrue(result, "Checkout should succeed when requesting exact available inventory");
            assertEquals(500.0, paymentServiceDriver.getLastProcessedAmount(), 0.01,
                    "Payment amount should be 100 * 5.0 = 500.0");

            System.out.println("✓ Exact inventory match test passed");
        }

        @Test
        @DisplayName("Test checkout with zero price (free items)")
        public void testCheckoutWithZeroPrice() {
            System.out.println("\n### Test: Checkout with Zero Price (10 free items) ###");

            boolean result = shoppingCart.checkout(10, 0.0);

            assertTrue(result, "Checkout should succeed with zero price (free items)");
            assertEquals(0.0, paymentServiceDriver.getLastProcessedAmount(), 0.01,
                    "Payment amount should be 0.0 for free items");

            System.out.println("✓ Zero price test passed");
        }

        @Test
        @DisplayName("Test checkout with decimal price")
        public void testCheckoutWithDecimalPrice() {
            System.out.println("\n### Test: Checkout with Decimal Price (3 items @ $9.99) ###");

            boolean result = shoppingCart.checkout(3, 9.99);

            assertTrue(result, "Checkout should succeed with decimal price");
            assertEquals(29.97, paymentServiceDriver.getLastProcessedAmount(), 0.01,
                    "Payment amount should be 3 * 9.99 = 29.97");

            System.out.println("✓ Decimal price test passed");
        }

        @Test
        @DisplayName("Test checkout with single item")
        public void testCheckoutWithSingleItem() {
            System.out.println("\n### Test: Checkout with Single Item (1 item @ $100.00) ###");

            boolean result = shoppingCart.checkout(1, 100.0);

            assertTrue(result, "Checkout should succeed with single item");
            assertEquals(100.0, paymentServiceDriver.getLastProcessedAmount(), 0.01,
                    "Payment amount should be 1 * 100.0 = 100.0");

            System.out.println("✓ Single item test passed");
        }
    }

    /**
     * Nested class for testing insufficient inventory scenarios
     */
    @Nested
    @DisplayName("Insufficient Inventory Tests")
    class InsufficientInventoryTests {

        @Test
        @DisplayName("Test checkout fails when inventory is insufficient")
        public void testInsufficientInventory() {
            System.out.println("\n### Test: Insufficient Inventory (150 items requested, 100 available) ###");

            // Set inventory lower than requested
            inventoryServiceDriver.setAvailableStock(100);

            boolean result = shoppingCart.checkout(150, 10.0);

            assertFalse(result, "Checkout should fail when inventory is insufficient");
            assertEquals(0, paymentServiceDriver.getCallCount(),
                    "Payment should not be attempted when inventory is insufficient");
            assertEquals(1, inventoryServiceDriver.getCallCount(),
                    "Inventory should be checked once");

            System.out.println("✓ Insufficient inventory test passed");
        }

        @Test
        @DisplayName("Test checkout fails when inventory is zero")
        public void testZeroInventory() {
            System.out.println("\n### Test: Zero Inventory (10 items requested, 0 available) ###");

            inventoryServiceDriver.setAvailableStock(0);

            boolean result = shoppingCart.checkout(10, 5.0);

            assertFalse(result, "Checkout should fail when inventory is zero");
            assertEquals(0, paymentServiceDriver.getCallCount(),
                    "Payment should not be attempted when inventory is zero");

            System.out.println("✓ Zero inventory test passed");
        }

        @Test
        @DisplayName("Test checkout fails when one item short")
        public void testOneItemShort() {
            System.out.println("\n### Test: One Item Short (10 items requested, 9 available) ###");

            inventoryServiceDriver.setAvailableStock(9);

            boolean result = shoppingCart.checkout(10, 5.0);

            assertFalse(result, "Checkout should fail when even one item short");
            assertEquals(9, inventoryServiceDriver.getLastCheckedItemCount(),
                    "Should have checked for 10 items");

            System.out.println("✓ One item short test passed");
        }
    }

    /**
     * Nested class for testing payment failure scenarios
     */
    @Nested
    @DisplayName("Payment Failure Tests")
    class PaymentFailureTests {

        @Test
        @DisplayName("Test checkout fails when payment processing fails")
        public void testPaymentFailure() {
            System.out.println("\n### Test: Payment Failure (5 items @ $10.00, payment fails) ###");

            // Configure driver to simulate payment failure
            paymentServiceDriver.setShouldSucceed(false);

            boolean result = shoppingCart.checkout(5, 10.0);

            assertFalse(result, "Checkout should fail when payment processing fails");
            assertEquals(1, inventoryServiceDriver.getCallCount(),
                    "Inventory should be checked before payment");
            assertEquals(1, paymentServiceDriver.getCallCount(),
                    "Payment should be attempted once");
            assertEquals(50.0, paymentServiceDriver.getLastProcessedAmount(), 0.01,
                    "Payment should have been attempted with correct amount");

            System.out.println("✓ Payment failure test passed");
        }

        @Test
        @DisplayName("Test checkout handles payment rejection for large amounts")
        public void testLargeAmountPaymentFailure() {
            System.out.println("\n### Test: Large Amount Payment Failure (1000 items @ $1000.00) ###");

            inventoryServiceDriver.setAvailableStock(1000);
            paymentServiceDriver.setShouldSucceed(false);

            boolean result = shoppingCart.checkout(1000, 1000.0);

            assertFalse(result, "Checkout should fail when payment fails");
            assertEquals(1000000.0, paymentServiceDriver.getLastProcessedAmount(), 0.01,
                    "Should have attempted to process $1,000,000");

            System.out.println("✓ Large amount payment failure test passed");
        }
    }

    /**
     * Nested class for testing edge cases and error conditions
     */
    @Nested
    @DisplayName("Edge Cases and Error Handling Tests")
    class EdgeCasesTests {

        @Test
        @DisplayName("Test checkout rejects negative item count")
        public void testNegativeItemCount() {
            System.out.println("\n### Test: Negative Item Count (-5 items) ###");

            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> shoppingCart.checkout(-5, 10.0),
                    "Should throw exception for negative item count"
            );

            System.out.println("Exception message: " + exception.getMessage());
            assertTrue(exception.getMessage().contains("positive"));
            assertEquals(0, inventoryServiceDriver.getCallCount(),
                    "Services should not be called for invalid input");

            System.out.println("✓ Negative item count test passed");
        }

        @Test
        @DisplayName("Test checkout rejects zero item count")
        public void testZeroItemCount() {
            System.out.println("\n### Test: Zero Item Count (0 items) ###");

            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> shoppingCart.checkout(0, 10.0),
                    "Should throw exception for zero item count"
            );

            System.out.println("Exception message: " + exception.getMessage());
            assertTrue(exception.getMessage().contains("positive"));

            System.out.println("✓ Zero item count test passed");
        }

        @Test
        @DisplayName("Test checkout rejects negative price")
        public void testNegativePrice() {
            System.out.println("\n### Test: Negative Price (5 items @ -$10.00) ###");

            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> shoppingCart.checkout(5, -10.0),
                    "Should throw exception for negative price"
            );

            System.out.println("Exception message: " + exception.getMessage());
            assertTrue(exception.getMessage().contains("negative"));

            System.out.println("✓ Negative price test passed");
        }

        @Test
        @DisplayName("Test constructor rejects null PaymentService")
        public void testNullPaymentService() {
            System.out.println("\n### Test: Null PaymentService ###");

            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> new ShoppingCart(null, inventoryServiceDriver),
                    "Should throw exception for null PaymentService"
            );

            System.out.println("Exception message: " + exception.getMessage());
            assertTrue(exception.getMessage().contains("PaymentService"));

            System.out.println("✓ Null PaymentService test passed");
        }

        @Test
        @DisplayName("Test constructor rejects null InventoryService")
        public void testNullInventoryService() {
            System.out.println("\n### Test: Null InventoryService ###");

            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> new ShoppingCart(paymentServiceDriver, null),
                    "Should throw exception for null InventoryService"
            );

            System.out.println("Exception message: " + exception.getMessage());
            assertTrue(exception.getMessage().contains("InventoryService"));

            System.out.println("✓ Null InventoryService test passed");
        }

        @Test
        @DisplayName("Test very large item count and price")
        public void testVeryLargeValues() {
            System.out.println("\n### Test: Very Large Values (10000 items @ $999.99) ###");

            inventoryServiceDriver.setAvailableStock(10000);

            boolean result = shoppingCart.checkout(10000, 999.99);

            assertTrue(result, "Should handle large values correctly");
            assertEquals(9999900.0, paymentServiceDriver.getLastProcessedAmount(), 0.01,
                    "Should calculate large amounts correctly");

            System.out.println("✓ Very large values test passed");
        }
    }

    /**
     * Test to verify driver behavior independently
     */
    @Nested
    @DisplayName("Driver Behavior Verification Tests")
    class DriverBehaviorTests {

        @Test
        @DisplayName("Verify PaymentServiceDriver behavior")
        public void testPaymentDriverBehavior() {
            System.out.println("\n### Test: PaymentServiceDriver Behavior ###");

            PaymentServiceDriver driver = new PaymentServiceDriver(true);

            assertTrue(driver.processPayment(100.0), "Should process valid payment");
            assertEquals(100.0, driver.getLastProcessedAmount(), 0.01);
            assertEquals(1, driver.getCallCount());

            assertFalse(driver.processPayment(-50.0), "Should reject negative amount");
            assertEquals(2, driver.getCallCount());

            driver.setShouldSucceed(false);
            assertFalse(driver.processPayment(100.0), "Should fail when configured to fail");

            System.out.println("✓ PaymentServiceDriver behavior test passed");
        }

        @Test
        @DisplayName("Verify InventoryServiceDriver behavior")
        public void testInventoryDriverBehavior() {
            System.out.println("\n### Test: InventoryServiceDriver Behavior ###");

            InventoryServiceDriver driver = new InventoryServiceDriver(50);

            assertTrue(driver.isInventoryAvailable(30), "Should have sufficient inventory");
            assertEquals(30, driver.getLastCheckedItemCount());
            assertEquals(1, driver.getCallCount());

            assertFalse(driver.isInventoryAvailable(100), "Should not have sufficient inventory");
            assertEquals(2, driver.getCallCount());

            driver.setAvailableStock(200);
            assertTrue(driver.isInventoryAvailable(100), "Should have sufficient inventory after update");

            System.out.println("✓ InventoryServiceDriver behavior test passed");
        }

        @Test
        @DisplayName("Test driver state persistence across calls")
        public void testDriverStatePersistence() {
            System.out.println("\n### Test: Driver State Persistence ###");

            // Make multiple checkout attempts
            shoppingCart.checkout(5, 10.0);
            shoppingCart.checkout(3, 20.0);
            shoppingCart.checkout(10, 5.0);

            assertEquals(3, paymentServiceDriver.getCallCount(),
                    "Payment driver should track all calls");
            assertEquals(3, inventoryServiceDriver.getCallCount(),
                    "Inventory driver should track all calls");
            assertEquals(50.0, paymentServiceDriver.getLastProcessedAmount(), 0.01,
                    "Should remember last payment amount");

            System.out.println("✓ Driver state persistence test passed");
        }
    }
}
