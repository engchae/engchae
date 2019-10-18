package com.example.notipay_1;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class mart_MyItemView extends LinearLayout {
    TextView textView;
    TextView textView2;
    TextView textView3;
    ImageView imageView;

    int ccount = 1;

    public mart_MyItemView(Context context) {
        super(context);
        init(context);
    }

    public mart_MyItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(final Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.mart_listview_custom, this, true);

        textView = (TextView) findViewById(R.id.name);
        textView2 = (TextView) findViewById(R.id.price);
        textView3 = (TextView) findViewById(R.id.count);
        imageView = (ImageView) findViewById(R.id.img);
        Button btn1 = (Button) findViewById(R.id.plus);
        Button btn2 = (Button) findViewById(R.id.minus);

        btn1.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                ccount++;
                textView3.setText("" + ccount);
                Toast.makeText(getContext(), "추가", Toast.LENGTH_LONG).show();
            }
        });

        btn2.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                ccount--;
                if(ccount == 0){
                    ccount=1;
                    Toast.makeText(getContext(), "더 뺄 수 없습니다", Toast.LENGTH_LONG).show();
                }
                textView3.setText("" + ccount);
            }
        });

    }

    public void setName(String name) {
        textView.setText(name);
    }

    public void setPrice(int price) {
        textView2.setText(String.valueOf(price));
    }

    public void setCount(int count) {
        textView3.setText(String.valueOf(count));
    }

    public void setImage(int resId) {
        imageView.setImageResource(resId);
    }
}