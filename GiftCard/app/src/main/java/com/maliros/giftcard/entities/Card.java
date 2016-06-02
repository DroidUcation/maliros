package com.maliros.giftcard.entities;

import java.util.Date;

/**
 * Created by user on 31/05/2016.
 */
public class Card {
    private int key;
    private int cardTypeKey;
    private int userKey;
    private boolean isForUniqueStore;
    private String uniqueStoreName;
    private Double balance;
    private Date expirationDate;

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getCardTypeKey() {
        return cardTypeKey;
    }

    public void setCardTypeKey(int cardTypeKey) {
        this.cardTypeKey = cardTypeKey;
    }

    public int getUserKey() {
        return userKey;
    }

    public void setUserKey(int userKey) {
        this.userKey = userKey;
    }

    public boolean isForUniqueStore() {
        return isForUniqueStore;
    }

    public void setIsForUniqueStore(boolean isForUniqueStore) {
        this.isForUniqueStore = isForUniqueStore;
    }

    public String getUniqueStoreName() {
        return uniqueStoreName;
    }

    public void setUniqueStoreName(String uniqueStoreName) {
        this.uniqueStoreName = uniqueStoreName;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}
