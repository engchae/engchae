package com.example.notipay_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class mart_StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mart_start);

        Button logobtn = (Button)findViewById(R.id.logobtn);

        logobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(getApplicationContext(),mart_AccountActivity.class);   //AccountActivity로 화면전환
                startActivity(intent);
            }
        });

    }
}
