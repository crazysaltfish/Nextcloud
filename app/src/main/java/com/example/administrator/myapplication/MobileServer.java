package com.example.administrator.myapplication;

import android.os.Handler;
import android.os.Message;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Administrator on 2017/12/5 0005.
 */

//新建一个异步接口，用Thead来运行， 没有用到手机的后台服务器Service;
public class MobileServer implements Runnable {
    private ServerSocket server;
    private DataInputStream in;
    private byte[] receice;

    private Handler handler ;

    public MobileServer() {
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {

        try {
            //5000是手机端开启的服务器的端口号，ESP8266进行TCP连接时使用的端口，而IP也是通过指令查询的联入设备的IP
            server = new ServerSocket(5000);
            while (true) {
                Socket client = server.accept();  //socket接收
                in = new DataInputStream(client.getInputStream()); //socket转成输入流
                receice = new byte[50];
                in.read(receice);                     //输入流读进数组
                in.close();

                Message message = new Message();    //数据打包放进Message
                message.what = 1;
                message.obj = new String(receice);
                handler.sendMessage(message);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}