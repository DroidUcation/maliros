package com.maliros.fivethings.enums;

/**
 * Created by user on 04/05/2016.
 */
public enum FactName {
    ARABIC("ARABIC"),
    CAPPUCCINO("CAPPUCCINO"),
    FRUIT("FRUIT"),
    GOATS("GOATS"),
    SPORT("SPORT");

    private final String id;

    FactName(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
