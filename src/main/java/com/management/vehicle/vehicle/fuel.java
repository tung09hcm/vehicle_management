package com.management.vehicle.vehicle;

public enum fuel {
    NONE(0),
    RON97(20000),
    DIESEL(18000),
    RON95(19000);

    private final int pricePerLiter;

    fuel(int pricePerLiter) {
        this.pricePerLiter = pricePerLiter;
    }

    public int getPricePerLiter() {
        return pricePerLiter;
    }
}
