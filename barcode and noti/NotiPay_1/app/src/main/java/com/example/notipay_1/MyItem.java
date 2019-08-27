package com.example.notipay_1;
import android.graphics.drawable.Drawable;

public class MyItem {

    private Drawable icon;
    private String name,price;
    private int account=1;

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    public int getAccount() {
        return account;
    }

    public void setAccount(int account) {
        this.account = account;
    }
}