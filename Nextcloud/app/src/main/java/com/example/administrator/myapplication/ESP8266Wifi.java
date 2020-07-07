package com.example.administrator.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import TempControlView.TempControlView;

/**
 * Created by Administrator on 2017/12/5 0005.
 */

public class ESP8266Wifi extends AppCompatActivity {

    private TextView tv_content, tv_send_text;
    private Button bt_send;
    Button btnhellocharts;
    Switch swt_led1,swt_led2,swt_beep,swt_voice,swt_guangmin,swt_hongwai;
    String str;
    TempControlView tempControl,humiControl;
    Toolbar toolbar1 ;
    CollapsingToolbarLayout collapsingToolbarLayout;
    Handler mHandler=new Handler();
    int i;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);  //隐藏掉系统原先的导航栏
        setContentView(R.layout.esp8266_wifi);


        InitView();
        setSupportActionBar(toolbar1);
        ActionBar actionBar =  getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            //actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setTitle("智能家居系统");

        }
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.white));
       /* new Thread(){

        };*/

        //开启服务器
        MobileServer mobileServer = new MobileServer();
        mobileServer.setHandler(handler);
        new Thread(mobileServer).start();

    }

    private void InitView() {
        tv_content = (TextView) findViewById(R.id.tv_content);
        tv_send_text = (TextView) findViewById(R.id.tv_send_text);
        bt_send = (Button) findViewById(R.id.bt_send);
        btnhellocharts=findViewById(R.id.btnback2);
        swt_beep=findViewById(R.id.swt_beep);
        tempControl=findViewById(R.id.tempControlView);
        humiControl=findViewById(R.id.humiControlView);

        swt_led1=findViewById(R.id.swt_led1);
        swt_led2=findViewById(R.id.swt_led2);
        swt_voice=findViewById(R.id.swt_voice);
        swt_guangmin=findViewById(R.id.swt_guangmin);
        swt_hongwai=findViewById(R.id.swt_hongwai);

        toolbar1=findViewById(R.id.toolbar1);
        collapsingToolbarLayout=findViewById(R.id.collapsinglayout);

        btnhellocharts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),HelloCharts.class));

            }
        });

        bt_send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String str = "3x";
                new SendAsyncTask().execute(str);
            }
        });


        swt_beep.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    String str = "4x";
                    new SendAsyncTask().execute(str);
                }else {
                    String str = "4x";
                    new SendAsyncTask().execute(str);
                }
            }
        });

        swt_led1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    String str = "1x";
                    new SendAsyncTask().execute(str);
                }else {
                    String str = "1x";
                    new SendAsyncTask().execute(str);
                }
            }
        });
        swt_led2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    String str = "2x";
                    new SendAsyncTask().execute(str);
                }else {
                    String str = "2x";
                    new SendAsyncTask().execute(str);
                }
            }
        });

        swt_voice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    String str = "5x";
                    new SendAsyncTask().execute(str);
                }else {
                    String str = "5x";
                    new SendAsyncTask().execute(str);
                }
            }
        });

        swt_guangmin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    String str = "6x";
                    new SendAsyncTask().execute(str);
                }else {
                    String str = "6x";
                    new SendAsyncTask().execute(str);
                }
            }
        });

        swt_hongwai.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    String str = "7x";
                    new SendAsyncTask().execute(str);
                }else {
                    String str = "7x";
                    new SendAsyncTask().execute(str);
                }
            }
        });

        // 设置三格代表温度1度
        tempControl.setAngleRate(3);
        tempControl.setTemp(16, 37, 16);

        tempControl.setOnTempChangeListener(new TempControlView.OnTempChangeListener() {
            @Override
            public void change(int temp) {
                String str = "4x";
                new SendAsyncTask().execute(str);
                Toast.makeText(getApplicationContext(), temp + "°", Toast.LENGTH_SHORT).show();
            }
        });

        tempControl.setOnClickListener(new TempControlView.OnClickListener() {
            @Override
            public void onClick(int temp) {
                Toast.makeText(getApplicationContext(), temp + "°", Toast.LENGTH_SHORT).show();
            }
        });

        //湿度设置
        humiControl.setAngleRate(1);
        humiControl.setHumi(20,100,48);
        humiControl.setTitle("湿度设置");
        humiControl.setOnTempChangeListener(new TempControlView.OnTempChangeListener() {
            @Override
            public void change(int temp) {
                String str = "4x";
                new SendAsyncTask().execute(str);
                Toast.makeText(getApplicationContext(), temp + "%", Toast.LENGTH_SHORT).show();
            }
        });

        humiControl.setOnClickListener(new TempControlView.OnClickListener() {
            @Override
            public void onClick(int temp) {
                Toast.makeText(getApplicationContext(), temp + "%", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @SuppressLint("HandlerLeak")
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    String str=msg.obj+"";
                    if(str.contains("notify")){
                        tv_content.setText(str);
                        Toast.makeText(getApplicationContext(), "接收到信息", Toast.LENGTH_SHORT)
                                .show();
                    }else if(str.contains("voice close")){
                        tv_content.setText(str);
                        Toast.makeText(getApplicationContext(), "接收到信息", Toast.LENGTH_SHORT)
                                .show();
                    }else{
                        String str1="" + msg.obj;
                        tv_content.setText(str1);
                        String []info=str1.split("\\s+");
                        tempControl.setTemp(Integer.parseInt(info[0]));
                        humiControl.setHumi(Integer.parseInt(info[1]));
                        Toast.makeText(getApplicationContext(), "接收到信息", Toast.LENGTH_SHORT)
                                .show();
                    }

            }
        }
    };


    public boolean onOptionsItemSelected(MenuItem item) {
        //点击back键finish当前activity
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }




}


