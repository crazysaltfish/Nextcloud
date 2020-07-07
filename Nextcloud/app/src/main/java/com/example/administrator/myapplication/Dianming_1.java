package com.example.administrator.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cleveroad.pulltorefresh.firework.FireworkyPullToRefreshLayout;

import Info.Student;
import URLAndInterface.Interface;
import myclass.DatabaseHelper;

/**
 * Created by Administrator on 2018/1/19 0019.
 */

public class Dianming_1 extends AppCompatActivity {
    Toolbar toolbar1 ;
    Button btnStart,btnEnd;
    TextView tv_coursename,tv_teachername,tv_coursecharacter,tv_coursetime;
    private MyDatabaseHelper databaseHelper = new MyDatabaseHelper(this, "mydatabase.db", null, 1); //SQLite Helper类

    int flag; //老师还是学生
    String id;  //ID
    String mac;
    String[] studengt_notsigned=null;
    String notsigned_id="";

    Intent getintent;
    String courseid,coursename,teachername,coursecharacter,coursetime;
    CollapsingToolbarLayout collapsingToolbarLayout;

    Handler mHandler=new Handler();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);  //隐藏掉系统原先的导航栏
        setContentView(R.layout.dianming_1);

        //判断老师还是学生
        SharedPreferences pref=getSharedPreferences("teacher",MODE_PRIVATE);
        flag=pref.getInt("flag",0);         //获取老师还是学生
        id=pref.getString("t_id","");         //获取ID
        mac=pref.getString("mac","");       //获取mac地址

        getintent=getIntent();
        courseid=getintent.getStringExtra("courseid");
        coursename=getintent.getStringExtra("coursename")+"";
        teachername=getintent.getStringExtra("teachername")+"";
        coursecharacter=getintent.getStringExtra("courselocation")+"";
        coursetime=getintent.getStringExtra("coursetime")+"";
        initView();
        setSupportActionBar(toolbar1);
        ActionBar actionBar =  getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            //actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setTitle(coursename);

        }
        tv_coursename.setText(coursename);
        tv_teachername.setText(teachername);
        tv_coursecharacter.setText(coursecharacter);
        tv_coursetime.setText(coursetime);
        //设置标题颜色
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.white));


        if(flag==1){
            btnStart.setText("开始签到");
            btnEnd.setVisibility(View.INVISIBLE);

            btnStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    WifiManager wifiManager = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                    WifiInfo wifiInfo=wifiManager.getConnectionInfo();  //当前wifi连接信息
                    //注意获得的Mac地址会在第二位上加2！
                    @SuppressLint("HardwareIds") String netMac=wifiInfo.getBSSID();
                    //@SuppressLint("HardwareIds") String netMac=wifiInfo.getMacAddress();

                        //String netName = wifiInfo.getSSID(); //获取被连接网络的名称
                        //String netMac =  wifiInfo.getBSSID(); //获取被连接网络的mac地址
                        //String localMac = wifiInfo.getMacAddress();// 获得本机的MAC地址
                    if (netMac!=null){
                        Interface interface1=new Interface(getApplicationContext());
                        interface1.StudentSignIn(netMac, courseid, id, new Interface.VolleyCallback() {
                            @Override
                            public void onSuccess(String result) {
                                if (result.equals("Y")){
                                    btnStart.setEnabled(false);
                                    btnStart.setText("已完成签到");
                                    Toast.makeText(getApplicationContext(),"签到成功 ",Toast.LENGTH_LONG).show();
                                }
                                else{
                                    Toast.makeText(getApplicationContext(),"签到失败  "+result,Toast.LENGTH_LONG).show();
                                }
                            }
                            @Override
                            public void onerror(String error) {
                                Toast.makeText(getApplicationContext(),"未连接到服务器",Toast.LENGTH_LONG).show();
                            }
                        });
                        Toast.makeText(getApplicationContext(),"wifi的mac: "+netMac,Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getApplicationContext(),"未连接上wifi",Toast.LENGTH_LONG).show();
                    }

                }
            });
        }else{
            btnEnd.setEnabled(false);
            //开始点名
            btnStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Interface interface1=new Interface(getApplicationContext());
                    interface1.TeacherStartSignIn(mac, courseid, id, new Interface.VolleyCallback() {
                        @Override
                        public void onSuccess(String result) {
                            if (result.equals("Y")){
                                btnStart.setEnabled(false);
                                btnStart.setText("点名进行中...");
                                btnEnd.setEnabled(true);
                                Toast.makeText(getApplicationContext(),"点名开始 ",Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"点名失败 ",Toast.LENGTH_LONG).show();
                            }
                            Toast.makeText(getApplicationContext(),"我的mac： "+mac,Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onerror(String error) {
                            Toast.makeText(getApplicationContext(),"未连接到服务器",Toast.LENGTH_LONG).show();
                        }
                    });

                    //延时0.5S
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Interface interface4=new Interface(getApplicationContext());
                            interface4.StudentSignInByTeacher(courseid, "S20002234", new Interface.VolleyCallback() {
                                @Override
                                public void onSuccess(String result) {
                                    Toast.makeText(getApplicationContext(),"学生1 老师已帮助签到",Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onerror(String error) {

                                }
                            });
                        }
                    },500);

                }
            });
            //结束点名
            btnEnd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Interface interface1=new Interface(getApplicationContext());
                    interface1.TeacherStopSignIn(courseid, new Interface.VolleyCallback_student_stopsignin() {
                        @Override
                        public void onSuccess(String all, String signined, String notsigned, String[] idofnotsigned) {
                            //延时！！！
                            btnStart.setEnabled(true);
                            btnEnd.setEnabled(false);
                            btnStart.setText("开始点名");
                            Toast.makeText(getApplicationContext(),"点名结束,共 "+all+" 人，签到 "
                                            +signined+"人，未到 "+notsigned+"人",Toast.LENGTH_LONG).show();

                            //sqLiteDatabase.execSQL();
                            studengt_notsigned=idofnotsigned;

                            //将未到学生添加进数据库
                            for( String anotsigned : idofnotsigned) {
                                Interface interface4 = new Interface(getApplicationContext());
                                interface4.getStudentInfoByID(anotsigned, new Interface.VolleyCallback_student() {
                                    @Override
                                    public void onSuccess(Student student) {
                                        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
                                        sqLiteDatabase.execSQL("insert into attendance1(id_s, name, password, phone, class, nowclass) " +
                                                "values(?, ?, ?, ?, ?, ?)", new String[]{student.getID_s(), student.getName_s(), student.getPassword_s(),
                                                student.getPhone_s(), student.getClass_s(), coursename});
                                    }

                                    @Override
                                    public void onerror(String error) {
                                        //Toast.makeText(getApplicationContext(),"连接服务器失败",Toast.LENGTH_LONG).show();
                                    }
                                });
                            }

                        }

                        @Override
                        public void onerror(String error) {
                            Toast.makeText(getApplicationContext(),"未连接到服务器",Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
        }

    }


    //获取View的ID
    private void initView() {
        toolbar1 =findViewById(R.id.toolbar1);
        btnStart=findViewById(R.id.btn_start);
        btnEnd=findViewById(R.id.btn_end);
        tv_coursename=findViewById(R.id.tv_courseName);
        tv_teachername=findViewById(R.id.tv_teacher);
        tv_coursecharacter=findViewById(R.id.tv_class);
        tv_coursetime=findViewById(R.id.tv_classtime);
        collapsingToolbarLayout=findViewById(R.id.collapsinglayout);

    }


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
