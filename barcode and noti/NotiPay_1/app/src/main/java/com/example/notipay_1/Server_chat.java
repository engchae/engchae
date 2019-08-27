package com.example.notipay_1;

import android.os.StrictMode;
import android.widget.Toast;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import android.widget.Toast;

import static android.os.StrictMode.setThreadPolicy;

public class Server_chat {

    String IP = "192.168.0.3";
    int PORT = 8201;
    SocketChannel socketChannel = null;
    public static String receive;

    public void connser(){

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        setThreadPolicy(policy);

        try{
            socketChannel = SocketChannel.open(); // 객체 생성
            socketChannel.configureBlocking(true); //
            socketChannel.connect(new InetSocketAddress(IP, PORT));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void senddata(String sendt){
        try {
            ByteBuffer buffer = null;
            Charset charset = Charset.forName("UTF-8");
            buffer = charset.encode(sendt);

            socketChannel.write(buffer);

            buffer = ByteBuffer.allocate(100); //100 자리 byte 만큼 자리확보
            int len = socketChannel.read(buffer); //버퍼에 얼마나 많은 byte 가 기록됐는가
            buffer.flip();

            receive = charset.decode(buffer).toString();

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
