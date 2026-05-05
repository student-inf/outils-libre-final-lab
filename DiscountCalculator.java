package com.lab;


public class DiscountCalculator {

    private static final double SAVE10_RATE   = 0.10;
    private static final double SAVE20_RATE   = 0.20;
    private static final double VIP_EXTRA_RATE = 0.05;

    public double applyDiscountCode(double subtotal, String discountCode) {
        switch (discountCode.toUpperCase()) {
            case "SAVE10": return subtotal * SAVE10_RATE;
            case "SAVE20": return subtotal * SAVE20_RATE;
            default:       return 0.0;
        }
    }

    public double applyVipDiscount(double subtotalAfterCode, CustomerType customerType) {
        if (customerType == CustomerType.VIP) {
            return subtotalAfterCode * VIP_EXTRA_RATE;
        }
        return 0.0;
    }

    public double calculateTotalDiscount(double subtotal,
                                          String discountCode,
                                          CustomerType customerType) {
        double codeDiscount = applyDiscountCode(subtotal, discountCode);
        double afterCode    = subtotal - codeDiscount;
        double vipDiscount  = applyVipDiscount(afterCode, customerType);
        return codeDiscount + vipDiscount;
    }
}
