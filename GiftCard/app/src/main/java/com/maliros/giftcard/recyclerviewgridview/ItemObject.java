package com.maliros.giftcard.recyclerviewgridview;


public class ItemObject {

    private String name;
    private String balance;
    private int photo;

    public ItemObject(String balance,String name, int photo) {

        this.balance = balance;
        this.name = name;
        this.photo = photo;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }
}
