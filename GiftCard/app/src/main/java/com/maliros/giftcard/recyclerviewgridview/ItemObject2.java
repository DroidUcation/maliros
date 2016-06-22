package com.maliros.giftcard.recyclerviewgridview;

public class ItemObject2 {

    private String typeOfCard;
    private int countUsed;

    public ItemObject2(String typeOfCard, int countUsed) {
        this.countUsed = countUsed;
        this.typeOfCard = typeOfCard;
    }

    public int getCountUsed() {
        return countUsed;
    }

    public String getTypeOfCard() {
        return typeOfCard;
    }

    public void setTypeOfCard(String typeOfCard) {
        this.typeOfCard = typeOfCard;
    }

    public void setCountUsed(int countUsed) {
        this.countUsed = countUsed;
    }
}
