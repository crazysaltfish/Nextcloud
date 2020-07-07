package com.example.administrator.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Administrator on 2018/1/24 0024.
 */

public class Class_Detail extends AppCompatActivity {
    Toolbar toolbar3 ;
    Intent getintent;
    TextView tvCourseName,tvClassDetail,tvTeacher,tvTeacherNum,tvTeacherOffice,tvTeacherEmail;
    TextView tvClass,tvCourseTime;
    EditText edClassJindu,edClassZuoye;

    int flag; //老师还是学生

    String courseName,courseCharacter,teacherName,teacherTel,teacherOffice,teacherEmail,coursePlace
            ,courseTime,courseProceeding,courseHomework;
    CollapsingToolbarLayout collapsingToolbarLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);  //隐藏掉系统原先的导航栏
        setContentView(R.layout.class_detail);
        //判断老师还是学生
        SharedPreferences pref=getSharedPreferences("teacher",MODE_PRIVATE);
        flag=pref.getInt("flag",0);         //获取老师ID



        setGetintent();  //那到intent的数据
        initView();      //初始化view

        setSupportActionBar(toolbar3);
        ActionBar actionBar =  getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            //actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setTitle(courseName);
        }

        setText();    //放置数据
    }

    private void initView() {
        toolbar3 =findViewById(R.id.toolbar3);
        collapsingToolbarLayout=findViewById(R.id.collapsinglayout2);

        tvCourseName=findViewById(R.id.tv_courseName);
        tvClassDetail=findViewById(R.id.tv_classDetail);
        tvTeacher=findViewById(R.id.tv_teacher);
        tvTeacherNum=findViewById(R.id.tv_teacherNum);
        tvTeacherOffice=findViewById(R.id.tv_teacherOffic);
        tvTeacherEmail=findViewById(R.id.tv_teacherEmail);
        tvClass=findViewById(R.id.tv_class);
        tvCourseTime=findViewById(R.id.tv_courseTime);

        edClassJindu=findViewById(R.id.ed_classJindu);
        edClassZuoye=findViewById(R.id.ed_classZuoye);

    }

    private void setGetintent() {
        getintent=getIntent();
        courseName=getintent.getStringExtra("courseName")+"";
        courseCharacter=getintent.getStringExtra("courseCharacter")+"";
        teacherName=getintent.getStringExtra("teacherName")+"";
        teacherTel=getintent.getStringExtra("teacherTel")+"";
        teacherOffice=getintent.getStringExtra("teacherOffice")+"";
        teacherEmail=getintent.getStringExtra("teacherEmail")+"";
        coursePlace=getintent.getStringExtra("coursePlace")+"";
        courseTime=getintent.getStringExtra("courseTime")+"";
        courseProceeding=getintent.getStringExtra("courseProceeding")+"";
        courseHomework=getintent.getStringExtra("courseHomework")+"";
    }

    private void setText() {
        tvCourseName.setText(courseName);
        tvClassDetail.setText(courseCharacter);
        tvTeacher.setText(teacherName);
        tvTeacherNum.setText(teacherTel);
        tvTeacherOffice.setText(teacherOffice);
        tvTeacherEmail.setText(teacherEmail);
        tvClass.setText(coursePlace);
        tvCourseTime.setText(courseTime);

        edClassJindu.setText(courseProceeding);
        edClassZuoye.setText(courseHomework);

        //设置标题颜色
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.white));
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
