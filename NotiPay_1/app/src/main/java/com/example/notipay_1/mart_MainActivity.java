package com.example.notipay_1;


import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import com.google.zxing.integration.android.IntentIntegrator;

public class mart_MainActivity extends AppCompatActivity {
    IntentIntegrator brscan;

    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mart_activity_main);

        brscan = new IntentIntegrator(this);
        final FloatingActionButton fab =  (FloatingActionButton) findViewById(R.id.FAB);
        final ImageButton fix = (ImageButton) findViewById(R.id.accountfix);
        ImageButton list = (ImageButton) findViewById(R.id.shoplist);

        fix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), mart_Accountfix.class);
                startActivity(intent);
            }
        });

        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), mart_ShoppingList.class);
                startActivity(intent);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), mart_ShoppingList.class);
                intent.putExtra("barcode",123);
                startActivity(intent);
            }
        });
    }
}