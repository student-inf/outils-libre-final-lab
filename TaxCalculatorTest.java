package com.lab;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class TaxCalculatorTest {

    private TaxCalculator taxCalculator;

    @BeforeEach
    void setUp() {
        taxCalculator = new TaxCalculator();
    }

    @Test
    void testTax_on100() {
        double tax = taxCalculator.calculateTax(100.0);
        assertEquals(15.0, tax, 0.001);
    }

    @Test
    void testTax_on200() {
        double tax = taxCalculator.calculateTax(200.0);
        assertEquals(30.0, tax, 0.001);
    }

    @Test
    void testTax_onZero() {
        double tax = taxCalculator.calculateTax(0.0);
        assertEquals(0.0, tax, 0.001);
    }

    @Test
    void testTax_on80() {
        // after SAVE20 on 100: 100-20=80, tax=80*0.15=12
        double tax = taxCalculator.calculateTax(80.0);
        assertEquals(12.0, tax, 0.001);
    }
}
