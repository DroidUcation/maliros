package com.maliros.giftcard.recyclerviewgridview;

public class ItemObject2 {

    private String typeOfCard;
    private String countUsed;

    public ItemObject2(String typeOfCard, String countUsed) {
        this.countUsed = countUsed;
        this.typeOfCard = typeOfCard;
    }



    public String getTypeOfCard() {
        return typeOfCard;
    }

    public void setTypeOfCard(String typeOfCard) {
        this.typeOfCard = typeOfCard;
    }

    public void setCountUsed(String countUsed) {
        this.countUsed = countUsed;
    }
}
