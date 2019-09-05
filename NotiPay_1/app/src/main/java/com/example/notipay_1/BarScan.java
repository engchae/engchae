package com.example.notipay_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class BarScan extends AppCompatActivity {

    IntentIntegrator brscan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_scan);

        brscan = new IntentIntegrator(this);

        brscan.setPrompt("scanning");
        brscan.initiateScan(); //initiateScan() 함수 호출 (바코드스캐너)
    }
}