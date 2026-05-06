package com.lab;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class PricingEngineTest {

    private PricingEngine engine;

    @BeforeEach
    void setUp() {
        engine = new PricingEngine();
    }

    // ===== Subtotal tests =====

    @Test
    void testSubtotal_singleItem() {
        List<OrderItem> items = List.of(new OrderItem("Book", 50.0, 2));
        // subtotal = 100, no discount, tax=15 → final=115
        PriceResult result = engine.calculate(items, CustomerType.REGULAR, "NONE");
        assertEquals(100.0, result.getSubtotal(), 0.001);
    }

    @Test
    void testSubtotal_multipleItems() {
        List<OrderItem> items = List.of(
            new OrderItem("Laptop", 1000.0, 1),
            new OrderItem("Mouse",    25.0, 2)
        );
        // subtotal = 1000 + 50 = 1050
        PriceResult result = engine.calculate(items, CustomerType.REGULAR, "NONE");
        assertEquals(1050.0, result.getSubtotal(), 0.001);
    }

    // ===== Discount tests =====

    @Test
    void testRegularWithSave10_correctDiscount() {
        List<OrderItem> items = List.of(new OrderItem("Item", 100.0, 1));
        PriceResult result = engine.calculate(items, CustomerType.REGULAR, "SAVE10");
        assertEquals(10.0, result.getDiscountAmount(), 0.001);
    }

    @Test
    void testVipWithSave20_correctDiscount() {
        List<OrderItem> items = List.of(new OrderItem("Item", 100.0, 1));
        // SAVE20=20, afterCode=80, VIP=80*0.05=4 → total discount=24
        PriceResult result = engine.calculate(items, CustomerType.VIP, "SAVE20");
        assertEquals(24.0, result.getDiscountAmount(), 0.001);
    }

    // ===== Tax tests =====

    @Test
    void testTax_appliedAfterDiscount() {
        List<OrderItem> items = List.of(new OrderItem("Item", 100.0, 1));
        // SAVE10=10, afterDiscount=90, tax=90*0.15=13.5
        PriceResult result = engine.calculate(items, CustomerType.REGULAR, "SAVE10");
        assertEquals(13.5, result.getTax(), 0.001);
    }

    // ===== Final price tests =====

    @Test
    void testFinalPrice_regularNoDiscount() {
        List<OrderItem> items = List.of(new OrderItem("Item", 100.0, 1));
        // subtotal=100, discount=0, tax=15, final=115
        PriceResult result = engine.calculate(items, CustomerType.REGULAR, "NONE");
        assertEquals(115.0, result.getFinalPrice(), 0.001);
    }

    @Test
    void testFinalPrice_regularSave10() {
        List<OrderItem> items = List.of(new OrderItem("Item", 100.0, 1));
        // subtotal=100, discount=10, afterDiscount=90, tax=13.5, final=103.5
        PriceResult result = engine.calculate(items, CustomerType.REGULAR, "SAVE10");
        assertEquals(103.5, result.getFinalPrice(), 0.001);
    }

    @Test
    void testFinalPrice_vipSave20() {
        List<OrderItem> items = List.of(new OrderItem("Item", 100.0, 1));
        // subtotal=100, discount=24, afterDiscount=76, tax=11.4, final=87.4
        PriceResult result = engine.calculate(items, CustomerType.VIP, "SAVE20");
        assertEquals(87.4, result.getFinalPrice(), 0.001);
    }

    @Test
    void testFinalPrice_realOrderLaptopMouse() {
        List<OrderItem> items = List.of(
            new OrderItem("Laptop", 1000.0, 1),
            new OrderItem("Mouse",    25.0, 2)
        );
        // subtotal=1050, SAVE10=105, afterDiscount=945, tax=141.75, final=1086.75
        PriceResult result = engine.calculate(items, CustomerType.REGULAR, "SAVE10");
        assertEquals(1050.0,   result.getSubtotal(),       0.001);
        assertEquals(105.0,    result.getDiscountAmount(), 0.001);
        assertEquals(141.75,   result.getTax(),            0.001);
        assertEquals(1086.75,  result.getFinalPrice(),     0.001);
    }
}
