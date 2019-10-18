package com.example.notipay_1;
import android.accounts.Account;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class mart_ShoppingList extends AppCompatActivity {
    String getData;  //바코드 값을 저장할 변수
    MyAdapter adapter; //어댑터 변수

    ListView listView; //리스트뷰 변수

    mart_Server_chat server_chat; //서버 변수
    String receive; //서버로 보내는 변수
    IntentIntegrator brscan; //바코드를 스캔하는 변수
    String bar_code;

    ArrayList<mart_MyItem> mItems = new ArrayList<>(); // 아이템들을 담기 위한 어레이

    public void onBackPressed() {  //뒤로가기 메소드
        finish(); //mart_ShoppingList 액티비티 종료
    }

    public class MyAdapter extends BaseAdapter { //MyAdpater 클래스, BaseAdapter 상속

        @Override
        public int getCount() {
            return mItems.size();
        } //리스트뷰가 어댑터에게 데이터가 몇 개있는지 물어보는 함수

        @Override
        public mart_MyItem getItem(int position) {
            return mItems.get(position);
        } // 넘겨받은 index의 값을 리턴

        @Override
        public long getItemId(int position) {
            return position;
        }

        public void addItem(mart_MyItem item) {
            mItems.add(item);
        } //mart_MyItem에 값을 넣어주는 메소드
        @Override
        public View getView(final int position, View convertView, ViewGroup viewGroup) {
            mart_MyItemView view = new mart_MyItemView(getApplicationContext()); //생성자 선언

            mart_MyItem item = mItems.get(position); //현재의 인덱스에서의 mart_MyItem
            view.setName(item.getName()); //
            view.setPrice(item.getPrice());
            view.setCount(item.getCount());
            view.setImage(item.getIcon());
            //리턴되는 객체가 각각의 아이템으로 보인다.
            return view;
        }
    }

    void show(){ //결제버튼을 눌렀을 때 팝업창을 띄워줌
        final EditText et = new EditText(getApplicationContext()); //EditText를 쓰기위해 생성자선언
        AlertDialog.Builder builder = new AlertDialog.Builder(this); //팝업창을 띄우기 위한 생성자선언
        //타이틀설정
        builder.setTitle("PIN 입력").setMessage("PIN번호를 입력하세요").setView(et);
        //내용설정
        builder.setPositiveButton("전송", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"전송완료",Toast.LENGTH_LONG).show();
            }
        });

        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"취소완료",Toast.LENGTH_LONG).show();
            }
        });
        //팝업창을 띄워줌
        builder.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mart_activity_shopping_list);

        //xml의 id들을 써줌
        ImageButton home = (ImageButton) findViewById(R.id.home);
        Button pay = (Button) findViewById(R.id.pay);
        FloatingActionButton fab =  (FloatingActionButton) findViewById(R.id.FAB);
        listView = (ListView) findViewById(R.id.listview);

        adapter = new MyAdapter();   // 어댑터 등록
        server_chat = new mart_Server_chat(); //서버 등록
        server_chat.connser();
        brscan = new IntentIntegrator(this); //바코드 등록

        final Intent intent = getIntent(); //메인액티비로부터 정보를 받아오는 함수
        int data = intent.getIntExtra("barcode",1); //data에 받아온 데이터를 넣어줌

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
//                Intent intent1 = new Intent(getApplicationContext(), mart_MainActivity.class);
//                startActivity(intent1);
            }
        }); //home버튼을 눌렀을 때 mart_ShoppingList 액티비티가 종료됨

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show();
            }
        }); //pay(결제)버튼을 눌렀을 때 팝업창을 띄움

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //바코드 버튼을 눌렀을 때 스캐너가 띄워짐
                brscan.setPrompt("scanning...."); //상태 scanning
                brscan.setOrientationLocked(false);  //
                brscan.initiateScan(); //initiateScan() 함수 호출 (바코드스캐너)
            }
        });
        if(data==123){ //data와 받아온 정보가 123과 같다면
            fab.performClick(); //바코드 스캐너를 자동으로 실행시킴
        }
    }
    int count=0;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { //이 함수를 호출하면 requestcode, resultcode, data를 반환
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //qr,barcode 가 없으면
            if (result.getContents() == null) {
                onBackPressed(); //뒤로가기를 눌렀을 때 실행
                Toast.makeText(getApplicationContext(), "취소", Toast.LENGTH_SHORT).show(); //toast 메세지
            } else {
                try {
                    JSONObject obj = new JSONObject(result.getContents());
                } catch (JSONException e) {
                    e.printStackTrace();
                    getData = result.getContents();//바코드 값 저장
                    bar_code = String.valueOf(getData); //스트링 형으로 변환
                    //찍힌 바코드가 값과 일치하면 리스트에 상품을 추가함
                    int carrot = R.drawable.mart_carrot;
                    if (bar_code.equals("12345670")) {
                        mItems.add(new mart_MyItem("당근", 1500, 1, carrot));
                        listView.setAdapter(adapter);
//                        count++;
//                        if (count==1){
//                            mItems.add(new mart_MyItem("당근", 1500, count, R.drawable.mart_carrot));
//                            listView.setAdapter(adapter);
//                        }else if (count>1){
//                            mItems.remove(adapter);
//                            mItems.add(new mart_MyItem("당근", 1500, count, R.drawable.mart_carrot));
//                            listView.setAdapter(adapter);
//                        }
                    } else if (bar_code.equals("23456716")) {
                        adapter.addItem(new mart_MyItem("사과", 100, 1, R.drawable.mart_apple));
                        listView.setAdapter(adapter);
                    } else if (bar_code.equals("34567838")) {
                        adapter.addItem(new mart_MyItem("바나나", 4000, 1, R.drawable.mart_banana));
                        listView.setAdapter(adapter);
                    } else if (bar_code.equals("47723214")) {
                        adapter.addItem(new mart_MyItem("옥수수", 1000, 1, R.drawable.mart_corn));
                        listView.setAdapter(adapter);
                    } else if (bar_code.equals("61234215")) {
                        adapter.addItem(new mart_MyItem("포도", 2000, 1, R.drawable.mart_grapes));
                        listView.setAdapter(adapter);
                    } else if (bar_code.equals("45671111")) {
                        adapter.addItem(new mart_MyItem("멜론", 6000, 1, R.drawable.mart_melon));
                        listView.setAdapter(adapter);
                    } else if (bar_code.equals("14121418")) {
                        adapter.addItem(new mart_MyItem("복숭아", 800, 1, R.drawable.mart_peach));
                        listView.setAdapter(adapter);
                    } else if (bar_code.equals("13461515")) {
                        adapter.addItem(new mart_MyItem("싱싱한 오징어", 5000, 1, R.drawable.mart_squid));
                        listView.setAdapter(adapter);
                    } else if (bar_code.equals("35612124")) {
                        adapter.addItem(new mart_MyItem("딸기", 4000, 1, R.drawable.mart_strawberry));
                        listView.setAdapter(adapter);
                    } else if (bar_code.equals("51231422")) {
                        adapter.addItem(new mart_MyItem("즉석떡볶이", 4850, 1, R.drawable.mart_tteokbokki));
                        listView.setAdapter(adapter);
                    } else if (bar_code.equals("25231137")) {
                        adapter.addItem(new mart_MyItem("수박", 7000, 1, R.drawable.mart_watermelon));
                        listView.setAdapter(adapter);
                    } else if (bar_code.equals("45121234")) {
                        adapter.addItem(new mart_MyItem("한우 한 근", 30000, 1, R.drawable.mart_hanwoo));
                        listView.setAdapter(adapter);
                    } else {
                        Toast.makeText(this, "없는 바코드", Toast.LENGTH_SHORT).show(); //일치하는 바코드가 없다면 토스트 출력
                    }
                    server_chat.senddata(bar_code); //server_chat 의 함수 senddata 에 변수 bar_code를 넣어 실행
                    receive = String.valueOf(mart_Server_chat.receive);  // Server_chat의 receive 데이터를 스트링형으로 변환
                }
            }
        }
    }
}