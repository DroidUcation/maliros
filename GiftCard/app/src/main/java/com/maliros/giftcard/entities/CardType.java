package com.maliros.giftcard.entities;

/**
 * Created by user on 31/05/2016.
 */
public class CardType {
    private int key;
    private String name;
    private boolean forSpecificStore;

    public CardType(String name, boolean forSpecificStore) {
        this.name = name;
        this.forSpecificStore = forSpecificStore;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isForSpecificStore() {
        return forSpecificStore;
    }

    public void setIsChainStore(boolean isChainStore) {
        this.forSpecificStore = isChainStore;
    }
}
