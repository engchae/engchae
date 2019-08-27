package com.example.barcode5;

public class item {

    String name;
    int Price;
    int Account;
    int resId;

    public  item(String name, int Price) {
        this.name = name;
        this.Price = Price;
    }

    public item(String name, int Price, int Account, int resId) {
        this.name = name;
        this.Price = Price;
        this.Account = Account;
        this.resId = resId;
    }

    public int getAccount() {
        return Account;
    }

    public void setAccount(int Account) {
        this.Account = Account;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int Price) {
        this.Price = Price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}