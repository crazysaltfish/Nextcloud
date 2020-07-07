package com.example.administrator.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
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

import Info.Student;
import Info.Teacher;
import URLAndInterface.Interface;

/**
 * Created by Administrator on 2018/3/7 0007.
 */

public class Mysetting extends AppCompatActivity {
    Toolbar toolbar1 ;
    Button btnStart,btnEnd;
    TextView tv_coursename,tv_teachername,tv_coursecharacter,tv_coursetime;
    ActionBar actionBar;

    int flag; //老师还是学生
    String id;  //ID

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


        initView(); //初始化控件
        setSupportActionBar(toolbar1);
        actionBar =  getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            //actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setTitle("");

        }
        btnStart.setVisibility(View.INVISIBLE);
        btnEnd.setVisibility(View.INVISIBLE);

        //设置标题颜色
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.white));

        Interface interface1=new Interface(getApplicationContext());
        if(flag==1){
            interface1.getStudentInfoByID(id, new Interface.VolleyCallback_student() {
                @Override
                public void onSuccess(Student student) {
                    coursename=student.getName_s();
                    teachername=student.getClass_s();
                    coursecharacter=student.getPhone_s();
                    coursetime=student.getPassword_s();
                }

                @Override
                public void onerror(String error) {

                }
            });
        }else{
            interface1.getTeacherInfoById(id, new Interface.VolleyCallback_Teacher() {
                @Override
                public void onSuccess(Teacher teacher) {
                    coursename=teacher.getName_t();
                    teachername=teacher.getOffice_t();
                    coursecharacter=teacher.getPhonenumber_t();
                    coursetime=teacher.getPassword_t();
                }

                @Override
                public void onerror(String error) {

                }
            });

        }


        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tv_coursename.setText("电话："+coursecharacter);
                tv_teachername.setText(id);
                tv_coursecharacter.setText(teachername);
                tv_coursetime.setText("密码："+coursetime);

                if(actionBar != null) {
                    actionBar.setTitle(coursename);
                }
            }
        },500);



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
