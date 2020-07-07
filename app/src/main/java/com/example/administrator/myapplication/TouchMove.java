package com.example.administrator.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.linroid.filtermenu.library.FilterMenu;
import com.linroid.filtermenu.library.FilterMenuLayout;

import URLAndInterface.Interface;
import btmBitmap.GameView;
import service.Myservice;

/**
 * Created by Administrator on 2017/11/9 0009.
 */

public class TouchMove extends Activity {
    GameView gameView;
    Button btnTouceMove,btnListView,btnWifi,btnTabhost,btnservice,btnnotify;
    Button btnLogin,btnwifiMac,btndianming;

    private MyDatabaseHelper databaseHelper = new MyDatabaseHelper(this, "mydatabase.db", null, 1); //SQLite Helper类
    //WifiManager wifiManager = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.touch_move);
        gameView=findViewById(R.id.gameView10);
        btnTouceMove=findViewById(R.id.jump);
        btnListView=findViewById(R.id.jump2);
        btnWifi=findViewById(R.id.btnWifi);
        btnTabhost=findViewById(R.id.btn_TabHost);
        btnservice=findViewById(R.id.btn_service);
        btnnotify=findViewById(R.id.btn_notify);
        btnLogin=findViewById(R.id.btn_login);
        btnwifiMac=findViewById(R.id.btn_wifi_mac);
        btndianming=findViewById(R.id.btn_dianming);

        FilterMenuinit();


        btnTouceMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Intent intent=new Intent("这里面放action的 name");
                // intent.putExtra("key","参数");
                //可以传入多个参数 给Intent, 用键去找参数；

                startActivity(new Intent(getApplicationContext(),MainActivity.class));

            }
        });
        //短信测试，直接发送给我自己
        btnListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(getApplicationContext(),RecyclerViewdemo.class));
                Interface interface0=new Interface(getApplicationContext());
                interface0.sendMessage("T30024641", "N0330014", "18861816303"
                        , "30", "1", new Interface.VolleyCallback() {
                            @Override
                            public void onSuccess(String result) {
                                if (result.equals("111")){
                                    Toast.makeText(getApplicationContext(),"发送成功",Toast.LENGTH_LONG).show();
                                }else{
                                    Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onerror(String error) {
                                Toast.makeText(getApplicationContext(),"未连接上服务器",Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });
        btnWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ESP8266Wifi.class));
            }
        });
        btnTabhost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),TabHostTest.class));
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),material_design_login.class));
            }
        });
        btndianming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),DianMing.class));
            }
        });

        //获取连接WIFI的Mac地址
        btnwifiMac.setOnClickListener(new View.OnClickListener() {

            WifiManager wifiManager = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            @Override
            public void onClick(View view) {
                WifiManager wifiManager = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                if(wifiManager != null){
                    WifiInfo wifiInfo=wifiManager.getConnectionInfo();  //当前wifi连接信息
                    //注意获得的Mac地址会在第二位上加2！
                    @SuppressLint("HardwareIds") String netMac=wifiInfo.getBSSID();
                    //@SuppressLint("HardwareIds") String netMac=wifiInfo.getMacAddress();

                    //String netName = wifiInfo.getSSID(); //获取被连接网络的名称
                    //String netMac =  wifiInfo.getBSSID(); //获取被连接网络的mac地址
                    //String localMac = wifiInfo.getMacAddress();// 获得本机的MAC地址

                    Toast.makeText(getApplicationContext(),"wifi的mac: "+netMac,Toast.LENGTH_LONG).show();

                }else{
                    Toast.makeText(getApplicationContext(),"未连接wifi",Toast.LENGTH_LONG).show();
                }



            }
        });

        //       测试专用按钮！
        btnservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Interface interface1= new Interface(getApplicationContext());
                interface1.facecheck("imga", "imgb", new Interface.VolleyCallback() {
                    @Override
                    public void onSuccess(String result) {
                        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onerror(String error) {
                        Toast.makeText(getApplicationContext(),"连接错误",Toast.LENGTH_LONG).show();
                    }
                });*/
                Intent intent1=new Intent(getApplicationContext(),photocheckapp.Login.class);
                startActivity(intent1);
            }
        });
        btnnotify.setOnClickListener(new View.OnClickListener() {     //消息通知测试
            @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onClick(View view) {


                Notification.Builder builder=new Notification.Builder(getApplicationContext());
                builder.setContentTitle("通知")
                        .setContentText("消息通知测试成功,点击跳转")
                        .setSmallIcon(R.mipmap.ic_launcher);

                PendingIntent pi=PendingIntent.getActivity(getApplicationContext(),0,
                        new Intent(getApplicationContext(),RecyclerViewdemo.class),
                        PendingIntent.FLAG_CANCEL_CURRENT);

                builder.setContentIntent(pi); //当消息被点击时的响应事件。
                NotificationManager manager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                manager.notify(1,builder.getNotification());
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.filtermenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //将手机IP转换成一般形式字符串
    private String intToIp(int ip)
    {
        return (ip & 0xFF) + "." + ((ip >> 8) & 0xFF) + "." + ((ip >> 16) & 0xFF) + "."
                + ((ip >> 24) & 0xFF);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Intent intent1=new Intent(getApplicationContext(), Myservice.class);
        stopService(intent1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
    private void FilterMenuinit() {
        FilterMenuLayout layout = findViewById(R.id.filter_menu);
        FilterMenu menu = new FilterMenu.Builder(this)
                .addItem(R.mipmap.ic_launcher)
                //.inflate(R.menu.filtermenu)//inflate  menu resource
                .attach(layout)
                .withListener(new FilterMenu.OnMenuChangeListener() {
                    @Override
                    public void onMenuItemClick(View view, int position) {
                    }
                    @Override
                    public void onMenuCollapse() {
                    }
                    @Override
                    public void onMenuExpand() {
                    }
                })
                .build();
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x=event.getX();
        float y=event.getY();
        gameView.setPlanex((int)x);
        gameView.setPlaney((int)y);

        gameView.invalidate();

        return super.onTouchEvent(event);
    }
}
