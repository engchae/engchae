package com.example.barcode5;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class itemview extends LinearLayout {
    TextView textView;
    TextView textView2;
    TextView textView3;
    ImageView imageView;
    Button plus,minus;

    public itemview(Context context) {
        super(context);
        init(context);
    }

    public itemview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    public void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.activity_itemview, this, true);

        textView = (TextView) findViewById(R.id.name);
        textView2 = (TextView) findViewById(R.id.price);
        textView3 = (TextView) findViewById(R.id.account);
        imageView = (ImageView) findViewById(R.id.imageView);
        plus = (Button)findViewById(R.id.plus);
        minus = (Button)findViewById(R.id.minus);


    }

    public void setName(String name) { textView.setText(name); }

    public void setPrice(int price) {
        textView2.setText(String.valueOf(price));
    }

    public void setAccount(int account) {
        textView3.setText(String.valueOf(account));
    }

    public void setImage(int resId) {
        imageView.setImageResource(resId);
    }
}