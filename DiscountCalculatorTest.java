package com.lab;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class DiscountCalculatorTest {

    private DiscountCalculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new DiscountCalculator();
    }

    // ===== applyDiscountCode =====

    @Test
    void testSave10_applies10PercentDiscount() {
        double discount = calculator.applyDiscountCode(100.0, "SAVE10");
        assertEquals(10.0, discount, 0.001);
    }

    @Test
    void testSave20_applies20PercentDiscount() {
        double discount = calculator.applyDiscountCode(100.0, "SAVE20");
        assertEquals(20.0, discount, 0.001);
    }

    @Test
    void testUnknownCode_returnsZeroDiscount() {
        double discount = calculator.applyDiscountCode(100.0, "INVALID");
        assertEquals(0.0, discount, 0.001);
    }

    @Test
    void testEmptyCode_returnsZeroDiscount() {
        double discount = calculator.applyDiscountCode(100.0, "");
        assertEquals(0.0, discount, 0.001);
    }

    // ===== applyVipDiscount =====

    @Test
    void testVipCustomer_gets5PercentExtra() {
        double discount = calculator.applyVipDiscount(100.0, CustomerType.VIP);
        assertEquals(5.0, discount, 0.001);
    }

    @Test
    void testRegularCustomer_getsNoVipDiscount() {
        double discount = calculator.applyVipDiscount(100.0, CustomerType.REGULAR);
        assertEquals(0.0, discount, 0.001);
    }

    // ===== calculateTotalDiscount =====

    @Test
    void testRegularWithSave10() {
        // subtotal=100, SAVE10=10, no VIP → total discount = 10
        double discount = calculator.calculateTotalDiscount(100.0, "SAVE10", CustomerType.REGULAR);
        assertEquals(10.0, discount, 0.001);
    }

    @Test
    void testVipWithSave20() {
        // subtotal=100, SAVE20=20, afterCode=80, VIP=80*0.05=4 → total=24
        double discount = calculator.calculateTotalDiscount(100.0, "SAVE20", CustomerType.VIP);
        assertEquals(24.0, discount, 0.001);
    }

    @Test
    void testVipWithNoCode() {
        // subtotal=100, no code=0, afterCode=100, VIP=100*0.05=5 → total=5
        double discount = calculator.calculateTotalDiscount(100.0, "NONE", CustomerType.VIP);
        assertEquals(5.0, discount, 0.001);
    }
}
