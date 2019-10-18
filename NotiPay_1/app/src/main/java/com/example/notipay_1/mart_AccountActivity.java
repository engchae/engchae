package com.example.notipay_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DecimalFormat;

public class mart_AccountActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mart_account);

        final mart_Server_chat server_chat;
        server_chat = new mart_Server_chat();
        server_chat.connser();

        final EditText name_et = (EditText)findViewById(R.id.name_et);
        final Spinner accname_sp = (Spinner)findViewById(R.id.accname_sp);
        final EditText accnum_et = (EditText)findViewById(R.id.accnum_et);
        final EditText pin_et = (EditText)findViewById(R.id.pin_et);
        final EditText money_et = (EditText)findViewById(R.id.money_et);

        final Button finish = (Button)findViewById(R.id.finish);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.mart_accountlist, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accname_sp.setAdapter(adapter);

        final DecimalFormat decimalFormat = new DecimalFormat("#,###");
        final String[] result = {""};

        money_et.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!TextUtils.isEmpty(charSequence.toString()) && !charSequence.toString().equals(result[0])){
                    result[0] = decimalFormat.format(Double.parseDouble(charSequence.toString().replaceAll(",","")));
                    money_et.setText(result[0]);
                    money_et.setSelection(result[0].length());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = name_et.getText().toString();
                String accname = accname_sp.getSelectedItem().toString();
                String accnum = accnum_et.getText().toString();
                String money = money_et.getText().toString();
                String pin = pin_et.getText().toString();
                if(accname_sp.getSelectedItem().toString().equals("은행명을 선택해주세요")){
                    Toast.makeText(getApplicationContext(), "은행명을 선택하세요.", Toast.LENGTH_SHORT).show();
                }
                else if(accnum_et.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "계좌번호를 입력하세요.", Toast.LENGTH_SHORT).show();
                }
                else if(pin_et.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "PIN번호를 입력하세요.", Toast.LENGTH_SHORT).show();
                }
                else if(money_et.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "잔액을 입력하세요.", Toast.LENGTH_SHORT).show();
                }
                else {
                    String account = "add " + name + " " + accname + " " + accnum + " " + pin;
                    server_chat.senddata(account); //server_chat 의 함수 senddata 에 변수 account를 넣어 실행
                    String receive = String.valueOf(mart_Server_chat.receive);
                    finish();
                    Toast.makeText(getApplicationContext(), "등록성공", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), mart_MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
