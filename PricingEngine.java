package com.lab;

import java.util.List;

public class PricingEngine {

    private final DiscountCalculator discountCalculator;
    private final TaxCalculator      taxCalculator;

    public PricingEngine() {
        this.discountCalculator = new DiscountCalculator();
        this.taxCalculator      = new TaxCalculator();
    }

    public PricingEngine(DiscountCalculator discountCalculator,
                         TaxCalculator taxCalculator) {
        this.discountCalculator = discountCalculator;
        this.taxCalculator      = taxCalculator;
    }

    public PriceResult calculate(List<OrderItem> items,
                                  CustomerType customerType,
                                  String discountCode) {
        double subtotal       = calculateSubtotal(items);
        double discountAmount = discountCalculator.calculateTotalDiscount(
                                    subtotal, discountCode, customerType);
        double afterDiscount  = subtotal - discountAmount;
        double tax            = taxCalculator.calculateTax(afterDiscount);
        double finalPrice     = afterDiscount + tax;

        return new PriceResult(subtotal, discountAmount, tax, finalPrice);
    }

    private double calculateSubtotal(List<OrderItem> items) {
        double subtotal = 0;
        for (OrderItem item : items) {
            subtotal += item.getTotal();
        }
        return subtotal;
    }

    public static void main(String[] args) {
        PricingEngine engine = new PricingEngine();

        List<OrderItem> items = List.of(
            new OrderItem("Laptop", 1000.0, 1),
            new OrderItem("Mouse",    25.0, 2)
        );

        System.out.println("=== REGULAR customer, SAVE10 ===");
        PriceResult r1 = engine.calculate(items, CustomerType.REGULAR, "SAVE10");
        System.out.println(r1);

        System.out.println("\n=== VIP customer, SAVE20 ===");
        PriceResult r2 = engine.calculate(items, CustomerType.VIP, "SAVE20");
        System.out.println(r2);
    }
}
