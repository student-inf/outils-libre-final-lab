package com.lab;

public class TaxCalculator {

    private static final double TAX_RATE = 0.15; 

    public double calculateTax(double amountAfterDiscount) {
        return amountAfterDiscount * TAX_RATE;
    }
}
