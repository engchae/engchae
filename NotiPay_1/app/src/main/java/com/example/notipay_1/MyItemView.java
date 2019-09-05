package com.example.notipay_1;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyItemView extends LinearLayout {
    TextView textView;
    TextView textView2;
    TextView textView3;
    ImageView imageView;
    Button plus,minus;

    public MyItemView(Context context) {
        super(context);
        init(context);
    }

    public MyItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    public void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.listview_custom, this, true);

        textView = (TextView) findViewById(R.id.name);
        textView2 = (TextView) findViewById(R.id.price);
        textView3 = (TextView) findViewById(R.id.account);
        imageView = (ImageView) findViewById(R.id.img);


    }

    public void setName(String name) {
        textView.setText(name);
    }

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