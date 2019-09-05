package com.example.notipay_1;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static android.os.StrictMode.setThreadPolicy;

public class MainActivity extends AppCompatActivity implements BeaconConsumer{

    private BeaconManager beaconManager; //비콘 매니저 객체
    private List<Beacon> beaconList = new ArrayList<>(); // 감지된 비콘들을 임시로 담을 리스트

    // FrameLayout에 각 메뉴의 Fragment를 바꿔 줌
    private FragmentManager fragmentManager = getSupportFragmentManager();
    // 4개의 메뉴에 들어갈 Fragment들
    private Menu1Fragment menu1Fragment = new Menu1Fragment();
    private Menu2Fragment menu2Fragment = new Menu2Fragment();
    private Menu3Fragment menu3Fragment = new Menu3Fragment();
    private Menu4Fragment menu4Fragment = new Menu4Fragment();

    Server_chat server_chat;

    String barcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        server_chat = new Server_chat();
        server_chat.connser();

        beaconManager = BeaconManager.getInstanceForApplication(this);
        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));

        // 비콘 탐지
        beaconManager.bind((BeaconConsumer) this);
        handler.sendEmptyMessage(0);



        try {
            Intent intent = getIntent();
            barcode = intent.getExtras().getString("barcode");
            if (barcode != null) {
                Toast.makeText(this,"바코드받음" + barcode,Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        //프래그먼트------------------------
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);

        // 첫 화면 지정
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout, menu1Fragment).commitAllowingStateLoss();

        // bottomNavigationView의 아이템이 선택될 때 호출될 리스너 등록
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                switch (item.getItemId()) {
                    case R.id.navigation_menu1: {
                        transaction.replace(R.id.frame_layout, menu1Fragment).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.navigation_menu2: {
                        Intent intent = new Intent();
                        intent.putExtra("barcode",barcode);
                        transaction.replace(R.id.frame_layout, menu2Fragment).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.navigation_menu3: {
                        transaction.replace(R.id.frame_layout, menu3Fragment).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.navigation_menu4: {
                        transaction.replace(R.id.frame_layout, menu4Fragment).commitAllowingStateLoss();
                        break;
                }}
                return true;
            }
        });
        //프래그먼트-----------------------------



    }

    //비콘 스캐너 부분
    @Override
    protected void onDestroy() {
        super.onDestroy();
        beaconManager.unbind( this);
    }

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
        } catch (RemoteException e) {
        }
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            // 비콘의 거리 측정
            for (Beacon beacon : beaconList) {
                Log.i("rssi : ", String.valueOf(beacon.getRssi()));
                int rssi = 100 + beacon.getRssi();
                if (rssi >= 70){
                    String data =beacon.getBluetoothName();
                    server_chat.senddata(data);
                    Toast.makeText(MainActivity.this,"받음  : " + Server_chat.receive, Toast.LENGTH_SHORT).show();

                }

            }

            // 자기 자신을 1초마다 호출
            handler.sendEmptyMessageDelayed(0, 1000);

        }
    };
}

