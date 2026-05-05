package com.lab;

import java.util.List;


public class PricingEngineBad {

    public double calc(List<Double> p, List<Integer> q,
                       String cust, String code) {
        double sub = 0;
        for (int i = 0; i < p.size(); i++) {
            sub += p.get(i) * q.get(i);
        }

        double disc = 0;
        if (code.equals("SAVE10")) {
            disc = sub * 0.1;
        } else if (code.equals("SAVE20")) {
            disc = sub * 0.2;
        }

        if (cust.equals("VIP")) {
            disc += (sub - disc) * 0.05;
        }

        double tax = (sub - disc) * 0.15;
        return (sub - disc) + tax;
    }
}
