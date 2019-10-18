package com.example.notipay_1;
import android.graphics.drawable.Drawable;

public class mart_MyItem {

    private String name;
    private int icon,price, count;

    public mart_MyItem(String name, int Price, int Count, int icon) {
        this.name = name;
        this.price = Price;
        this.count = Count;
        this.icon = icon;
    }

    public int getIcon() {
        return icon;
    }
    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
}