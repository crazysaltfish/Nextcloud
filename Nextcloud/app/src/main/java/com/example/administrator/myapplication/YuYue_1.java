package com.example.administrator.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import service.Myservice;

/**
 * Created by Administrator on 2018/1/20 0020.
 */

public class YuYue_1 extends AppCompatActivity {
    Toolbar toolbar2 ;
    Button btntextYuyue;
    Spinner spner;
    TextView tv_coursename,tv_teachername,tv_coursecharacter,tv_coursetime;

    Intent getintent;
    String coursename,teachername,courselocation,coursetime,starttime,phonenumber,teacherid,courseid;
    CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);  //隐藏掉系统原先的导航栏
        setContentView(R.layout.yuyue1);

        getintent=getIntent();
        coursename=getintent.getStringExtra("coursename")+"";
        teachername=getintent.getStringExtra("teachername")+"";
        courselocation=getintent.getStringExtra("courselocation")+"";
        coursetime=getintent.getStringExtra("coursetime")+"";
        starttime=getintent.getStringExtra("starttime")+"";
        phonenumber=getintent.getStringExtra("phonenumber")+"";
        teacherid=getintent.getStringExtra("teacherid")+"";
        courseid=getintent.getStringExtra("courseid")+"";

        initView();
        setSupportActionBar(toolbar2);
        ActionBar actionBar =  getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            //actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setTitle(coursename);
        }
        tv_coursename.setText(coursename);
        tv_teachername.setText(teachername);
        tv_coursecharacter.setText(courselocation);
        tv_coursetime.setText(coursetime);
        //设置标题颜色
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.white));

        btntextYuyue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int time;
                time=spner.getSelectedItemPosition();
                switch (time){
                    case 0:
                        time =30;
                        break;
                    case 1:
                        time=60;
                        break;
                    case 2:
                        time=90;
                        break;
                    case 3:
                        time=120;
                        break;
                }
                Toast.makeText(getApplicationContext(),teachername+" "+coursename+" 预约成功，提前 "+time+" 分钟提醒",Toast.LENGTH_LONG).show();
                btntextYuyue.setEnabled(false);
                btntextYuyue.setText("短信预约成功");
                Intent i = new Intent(getApplicationContext(), Myservice.class);
                i.putExtra("coursename",courseid);
                i.putExtra("teachername",teacherid);
                i.putExtra("starttime",starttime);
                i.putExtra("phonenumber",phonenumber);
                i.putExtra("courselocation",courselocation);
                i.putExtra("time",time);
                startService(i);
            }
        });
    }

    //获取View的ID
    private void initView() {
        toolbar2 =findViewById(R.id.toolbar2);
        btntextYuyue=findViewById(R.id.btn_testyuyue);
        spner=findViewById(R.id.spner_time);
        tv_coursename=findViewById(R.id.tv_courseName);
        tv_teachername=findViewById(R.id.tv_teacher);
        tv_coursecharacter=findViewById(R.id.tv_class);
        tv_coursetime=findViewById(R.id.tv_classtime);
        collapsingToolbarLayout=findViewById(R.id.collapsinglayout1);

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
