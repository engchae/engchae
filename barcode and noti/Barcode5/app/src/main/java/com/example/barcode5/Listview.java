package com.example.barcode5;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static android.os.StrictMode.setThreadPolicy;

public class Listview extends   AppCompatActivity implements BeaconConsumer{

    private static final String IP = "192.168.0.3"; //서버의 아이피
    private static final int PORT = 8201; //서버의 포트
    private BeaconManager beaconManager;
    // 감지된 비콘들을 임시로 담을 리스트
    private List<Beacon> beaconList = new ArrayList<>();

    SocketTask socketTask;
    String msg;

    ListView lv_1,lv_2;
    ItemAdapter adapter;
    Button btn1,sendbt;
    String getData,beid;
    IntentIntegrator brscan;
    TextView textData,recieveData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        lv_1 = (ListView) findViewById(R.id.listview_1);
        btn1 = (Button)findViewById(R.id.btn1);
        sendbt = (Button)findViewById(R.id.sendbt);
        textData = (TextView)findViewById(R.id.textData);
        recieveData = (TextView)findViewById(R.id.recieveData);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        setThreadPolicy(policy);

        socketTask = new SocketTask();

        adapter = new ItemAdapter();

        brscan = new IntentIntegrator(this);

        lv_1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                item item = (item) adapter.getItem(position);
                Toast.makeText(getApplicationContext(), "선택 : " + item.getName(), Toast.LENGTH_LONG).show();
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                brscan.setPrompt("scanning...."); //상태 scanning
                brscan.setOrientationLocked(false);  //
                brscan.initiateScan(); //initiateScan() 함수 호출 (바코드스캐너)
            }
        });

        // 실제로 비콘을 탐지하기 위한 비콘매니저 객체를 초기화
        beaconManager = BeaconManager.getInstanceForApplication(this);

        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));

        // 비콘 탐지를 시작한다. 실제로는 서비스를 시작하는것.
        beaconManager.bind(this);
        handler.sendEmptyMessage(0);

        //socketTask.execute();
    }

    class ItemAdapter extends BaseAdapter {
        ArrayList<item> items = new ArrayList<item>();

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(item item) {
            items.add(item);
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            itemview view = new itemview(getApplicationContext());

            item item = items.get(position);
            view.setName(item.getName());
            view.setPrice(item.getPrice());
            view.setAccount(item.getAccount());
            view.setImage(item.getResId());

            return view;
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data){ //이 함수를 호출하면 requestcode, resultcode, data를 반환
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result!=null){
            //qr,barcode 가 없으면
            if(result.getContents()==null){
                Toast.makeText(Listview.this,"취소",Toast.LENGTH_SHORT).show(); //toast 메세지
            }else {
                try{
                    JSONObject obj = new JSONObject(result.getContents());
                } catch (JSONException e) {
                    e.printStackTrace();
                    getData = result.getContents();
                    String numInt = String.valueOf(getData);
                    if(numInt.equals("12345670")) {
                        adapter.addItem(new item("당근", 1500, 1, R.drawable.carrot));
                        lv_1.setAdapter(adapter);

                    }else if(numInt.equals("23456716")){
                        adapter.addItem(new item("사과", 100, 1, R.drawable.apple));
                        lv_1.setAdapter(adapter);

                    }else if(numInt.equals("34567838")){
                        adapter.addItem(new item("바나나", 4000, 1, R.drawable.banana));
                        lv_1.setAdapter(adapter);

                    }else if(numInt.equals("47723214")){
                        adapter.addItem(new item("옥수수", 1000, 1, R.drawable.corn));
                        lv_1.setAdapter(adapter);

                    }else if(numInt.equals("61234215")){
                        adapter.addItem(new item("포도", 2000, 1, R.drawable.grapes));
                        lv_1.setAdapter(adapter);

                    }else if(numInt.equals("45671111")){
                        adapter.addItem(new item("멜론", 6000, 1, R.drawable.melon));
                        lv_1.setAdapter(adapter);

                    }else if(numInt.equals("14121418")){
                        adapter.addItem(new item("복숭아", 800, 1, R.drawable.peach));
                        lv_1.setAdapter(adapter);

                    }else if(numInt.equals("13461515")){
                        adapter.addItem(new item("싱싱한 오징어", 5000, 1, R.drawable.squid));
                        lv_1.setAdapter(adapter);

                    }else if(numInt.equals("35612124")){
                        adapter.addItem(new item("딸기", 4000, 1, R.drawable.strawberry));
                        lv_1.setAdapter(adapter);

                    }else if(numInt.equals("51231422")){
                        adapter.addItem(new item("즉석떡볶이", 4850, 1, R.drawable.tteokbokki));
                        lv_1.setAdapter(adapter);

                    }else if(numInt.equals("25231137")){
                        adapter.addItem(new item("수박", 7000, 1, R.drawable.watermelon));
                        lv_1.setAdapter(adapter);

                    }else if(numInt.equals("45121234")){
                        adapter.addItem(new item("한우 한 근", 700, 1, R.drawable.hanwoo));
                        lv_1.setAdapter(adapter);

                    }else{
                         Toast.makeText(Listview.this,"없는 바코드",Toast.LENGTH_SHORT).show();
                    }
                    socketTask.SendDataToNetwork(numInt); //data를 소켓방식으로 서버에 전송
                }
            }
        }else {
            super.onActivityResult(requestCode,resultCode,data); //onActivityResult 에서 requestCode,resultCode,data를 받아옴
        }
    }

    class SocketTask extends AsyncTask<Integer, Integer, Integer> {
        private Socket socket = null;
        private BufferedReader in;
        private PrintWriter out;
        String ReceiveMsg;

        InetAddress serverAddr;

        // 작업을 시작할 때
        @Override
        protected void onPreExecute() {
            try{
                serverAddr = InetAddress.getByName(IP); //IP를 어드레스로 serverAddr에 저장
                socket = new Socket(serverAddr, PORT); //서버주소와 포트를 가진 소켓 생성

                socket.setReceiveBufferSize(65536); //받아들이는 소켓의 버퍼 크기

                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true); //플러쉬 기능 자동, 클라이언트에게 보내기위한 준비
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                sendbt.setVisibility(View.VISIBLE); //sendbt 가 보여진다
                textData.setVisibility(View.VISIBLE); //textData가 보여진다
            } catch (Exception e){ //에러처리
                e.printStackTrace(); //에러 메세지의 발생, 근원을 찾아 단계별로 에러 출력
            }
        }

        // 작업을 종료할 때
        @Override
        protected void onPostExecute(Integer integer) {
            try{
                socket.close();
            }catch (IOException e){ //에러처리
                e.printStackTrace(); //에러 메세지의 발생, 근원을 찾아 단계별로 에러 출력
            }
        }

        // 백그라운드 작업 정의
        @Override
        protected Integer doInBackground(Integer... params) {
            while(socket.isConnected()){
                try{
                    ReceiveMsg = in.readLine(); //한줄 씩 읽고 반환
                    publishProgress(); //진행상황을 표시
                }catch (Exception e){ //에러처리
                    e.printStackTrace(); //에러 메세지의 발생, 근원을 찾아 단계별로 에러 출력
                }
            }
            return null;
        }

        // Update 요청이 들어왔을 때
        @Override
        protected void onProgressUpdate(Integer... values) {
            recieveData.append(ReceiveMsg);
        }

        // 데이터 전송에 대한 요청 처리
        public void SendDataToNetwork(String msg){
            try{
                if(socket.isConnected()){
                    out.println(msg); // msg를 출력
                }
            } catch(Exception e){ //에러처리
                e.printStackTrace(); //에러 메세지의 발생, 근원을 찾아 단계별로 에러 출력
            }
        }
    }



//비콘 스캐너 부분
    @Override
    protected void onDestroy() {
        super.onDestroy();
        beaconManager.unbind(this);
    }

    @Override
    public void onBeaconServiceConnect() {
        beaconManager.setRangeNotifier(new RangeNotifier() {
            @Override
            // 비콘이 감지되면 해당 함수가 호출된다. Collection<Beacon> beacons에는 감지된 비콘의 리스트가,
            // region에는 비콘들에 대응하는 Region 객체가 들어온다.
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                if (beacons.size() > 0) {
                    beaconList.clear();
                    for (Beacon beacon : beacons) {
                        beaconList.add(beacon);
                    }
                }
            }

        });

        try {
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
        } catch (RemoteException e) {   }
    }


    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            textData.setText("");

            // 비콘의 아이디와 거리를 측정하여 textView에 넣는다.
            for(Beacon beacon : beaconList){
                textData.append("ID : " + beacon.getRssi() + " / " + "Distance : " + Double.parseDouble(String.format("%.3f", beacon.getDistance())) + "m\n");
                int rssi = 100+beacon.getRssi();
                if(rssi>=70)
                    socketTask.SendDataToNetwork(beacon.getBluetoothName()); //data를 소켓방식으로 서버에 전송

            }

            // 자기 자신을 1초마다 호출
            handler.sendEmptyMessageDelayed(0, 5000);
        }
    };
}
