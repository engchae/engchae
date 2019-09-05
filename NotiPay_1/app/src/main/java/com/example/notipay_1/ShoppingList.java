package com.example.notipay_1;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import static android.os.StrictMode.setThreadPolicy;

public class ShoppingList extends AppCompatActivity {
    String getData;
    MyAdapter adapter; //어댑터 변수

    ListView listView;

    Server_chat server_chat;
    String receive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_scan);

        listView = (ListView)findViewById(R.id.listview);

        adapter = new MyAdapter();   // 어댑터 등록

        server_chat = new Server_chat();
        server_chat.connser();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        setThreadPolicy(policy);
    }
    public class MyAdapter extends BaseAdapter {
        // 아이템들을 담기 위한 어레이
        private ArrayList<MyItem> mItems = new ArrayList<>();

        @Override
        public int getCount() {
            return mItems.size();
        }

        @Override
        public MyItem getItem(int position) {
            return mItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public void addItem(MyItem item) {
            mItems.add(item);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            MyItemView view = new MyItemView(getApplicationContext());

            MyItem item = mItems.get(position);
            view.setName(item.getName());
            view.setPrice(item.getPrice());
            view.setAccount(item.getAccount());
            view.setImage(item.getIcon());

            return view;
        }
    }

    //바코드 인식
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { //이 함수를 호출하면 requestcode, resultcode, data를 반환
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //qr,barcode 가 없으면
            if (result.getContents() == null) {
                Toast.makeText(ShoppingList.this, "취소", Toast.LENGTH_SHORT).show(); //toast 메세지
                Intent intent = new Intent(ShoppingList.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                try {
                    JSONObject obj = new JSONObject(result.getContents());
                } catch (JSONException e) {
                    e.printStackTrace();
                    getData = result.getContents(); //바코드 값 저장
                    String bar_code = String.valueOf(getData); //스트링 형으로 변환
                    Log.d(bar_code,"aaaaaaaaaaaaaaaa");
                    if(bar_code.equals("12345670")) {
                        adapter.addItem(new MyItem("당근", 1500, 1, R.drawable.carrot));
                        listView.setAdapter(adapter);
                    }else if(bar_code.equals("23456716")){
                        adapter.addItem(new MyItem("사과", 100, 1, R.drawable.apple));
                        listView.setAdapter(adapter);


                    }else{
                        Toast.makeText(ShoppingList.this,"없는 바코드",Toast.LENGTH_SHORT).show();
                    }
                    server_chat.senddata(bar_code); //server_chat 의 함수 senddata 에 변수 bar_code를 넣어 실행
                    receive = String.valueOf(Server_chat.receive);  // Server_chat의 receive 데이터를 스트링형으로 변환
                    Intent intent = new Intent(ShoppingList.this,ShoppingList.class);   // ShoppingList로 화면전환
                    intent.putExtra("barcode",bar_code); // barcode 에 receive 를 저장해서 데이터를 ShoppingList에 보냄
                    startActivity(intent);
                    finish();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data); //onActivityResult 에서 requestCode,resultCode,data를 받아옴
        }
    }
}
