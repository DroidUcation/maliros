package com.maliros.fivethings.entities;

import com.maliros.fivethings.enums.FactName;

/**
 * Created by user on 18/04/2016.
 */
public class Fact {
    private int id;
    private String text;
    private String imageUrl;
    private FactName factName;

    public Fact() {
    }

    public Fact(FactName factName, String text) {
        this.text = text;
        this.factName = factName;
    }

    public FactName getFactName() {
        return factName;
    }

    public void setFactName(FactName factName) {
        this.factName = factName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
