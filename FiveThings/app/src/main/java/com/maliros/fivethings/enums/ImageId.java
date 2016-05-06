package com.maliros.fivethings.enums;

import com.maliros.fivethings.R;

/**
 * Created by user on 04/05/2016.
 */
public enum ImageId {
    ARABIC(R.drawable.arabic_coffee),
    CAPPUCCINO(R.drawable.cappuccino_2),
    FRUIT(R.drawable.coffee_fruit),
    GOATS(R.drawable.goat_ethiopia),
    SPORT(R.drawable.sport);

    private final int id;

    ImageId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
