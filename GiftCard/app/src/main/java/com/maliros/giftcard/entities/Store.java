package com.maliros.giftcard.entities;

/**
 * Created by user on 31/05/2016.
 */
public class Store {
    private int key;
    private String name;
    private boolean isChainStore;

    public Store(String name, boolean isChainStore) {
        this.name = name;
        this.isChainStore = isChainStore;
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

    public boolean isChainStore() {
        return isChainStore;
    }

    public void setIsChainStore(boolean isChainStore) {
        this.isChainStore = isChainStore;
    }
}
