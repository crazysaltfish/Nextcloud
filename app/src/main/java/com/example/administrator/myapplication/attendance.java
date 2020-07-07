package com.example.administrator.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.cleveroad.pulltorefresh.firework.FireworkyPullToRefreshLayout;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import Info.Course;
import Info.Student;

/**
 * Created by Administrator on 2018/3/3 0003.
 */

public class attendance extends AppCompatActivity {
    Toolbar toolbar ;
    DrawerLayout drawable;
    ActionBarDrawerToggle mDrawerToggle;
    SearchView searchView;
    private MyDatabaseHelper databaseHelper = new MyDatabaseHelper(this, "mydatabase.db", null, 1); //SQLite Helper类

    private ListView mLvList;
    String[] mMenuTitles;   //菜单栏

    int flag; //老师还是学生
    String id;  //ID

    RecyclerView recyclerView;

    List<Student> studentList=new ArrayList<>();
    List<Course> courseList=new ArrayList<>();     //所有课程

    FireworkyPullToRefreshLayout mpullrefresh;
    Handler mHandler=new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);  //隐藏掉系统原先的导航栏
        setContentView(R.layout.dianming);

        //判断老师还是学生
        SharedPreferences pref=getSharedPreferences("teacher",MODE_PRIVATE);
        flag=pref.getInt("flag",0);         //获取老师还是学生
        id=pref.getString("t_id","");         //获取ID

        initView();
        getstudent(); //获取学生信息

        initToolBar();
        initMenuTitles();
        //initFragments();  //使用碎片布局
        initDrawerLayout();
        initRecyclerView();


        //刷新监听
        mpullrefresh.setOnRefreshListener(new FireworkyPullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mpullrefresh.setRefreshing(false);
                        Toast.makeText(getApplicationContext(),"刷新成功",Toast.LENGTH_LONG).show();
                    }
                },2000);

            }
        });
    }

    //获取View的ID
    private void initView() {
        toolbar =findViewById(R.id.toolbar);
        drawable = findViewById(R.id.drawlayout);
        recyclerView=findViewById(R.id.recyView);
        mpullrefresh=findViewById(R.id.pullToRefresh);
        mLvList = findViewById(R.id.lv_list);

    }

    //初始化ToolBar
    private void initToolBar() {
        //toolbar.setNavigationIcon(R.drawable.view_48px);//设置导航的图标
        //toolbar.setLogo(R.drawable.alarm_48px_);//设置logo

        toolbar.setTitle("我的考勤表");//设置标题
        //toolbar.setSubtitle("subtitle");//设置子标题

        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));//设置标题的字体颜色
        toolbar.setSubtitleTextColor(getResources().getColor(android.R.color.white));//设置子标题的字体颜色

        setSupportActionBar(toolbar);

        //设置右上角的填充菜单
        //toolbar.inflateMenu(R.menu.mymenu);
        //设置导航图标的点击事件
        /*
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DianMing.this, "菜单", Toast.LENGTH_SHORT).show();
            }
        });
        */



    }

    /**
     * 初始化DrawerLayout
     */
    private void initDrawerLayout() {
        mDrawerToggle = new ActionBarDrawerToggle(this, drawable,toolbar, R.string.open, R.string.close);

        mDrawerToggle.syncState();//将ActionDrawerToggle与DrawerLayout的状态同步


        drawable.addDrawerListener(mDrawerToggle);

        //switchFragment(0);
    }

    //初始化SearchView
    private void initSearchView(){


        searchView.setSubmitButtonEnabled(true);//显示提交按钮
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //提交按钮的点击事件
                Toast.makeText(getApplicationContext(), query, Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //当输入框内容改变的时候回调
                Toast.makeText(getApplicationContext(), newText, Toast.LENGTH_SHORT).show();
                return true;
            }
        });

    }

    private void initRecyclerView(){
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

                FruitAdapter adapter=new FruitAdapter(studentList);
                recyclerView.setAdapter(adapter);

                //点击事件监听
               /* adapter.setOnItemClickListener(new MyItemClickListener() {
                    @Override
                    public void onItemClick(View view, int postion) {
                        Student course1=studentList.get(postion);

                        Intent intent=new Intent(getApplicationContext(),Class_Detail.class);
                        intent.putExtra("courseName",course1.getName_c());
                        intent.putExtra("teacherName",teacher1.getName_t());
                        intent.putExtra("teacherTel","Tel:"+teacher1.getPhonenumber_t());
                        intent.putExtra("teacherOffice","办公室:"+teacher1.getOffice_t());
                        intent.putExtra("teacherEmail","邮箱:"+teacher1.getEmail_t());
                        intent.putExtra("coursePlace",course1.getLocation());
                        intent.putExtra("courseProceeding",course1.getProceeding());
                        intent.putExtra("courseHomework",course1.getHomework());
                        startActivity(intent);
                    }
                });*/

    }

    private void getstudent(){
        SQLiteDatabase sqLiteDatabase =  databaseHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from attendance1", null);
        if (cursor.moveToFirst()) {
            do {
                studentList.add(new Student(
                        cursor.getString(cursor.getColumnIndex("id_s")),
                        cursor.getString(cursor.getColumnIndex("name")),
                        cursor.getString(cursor.getColumnIndex("password")),
                        cursor.getString(cursor.getColumnIndex("phone")),
                        cursor.getString(cursor.getColumnIndex("class")),
                        cursor.getString(cursor.getColumnIndex("nowclass"))
                        ));
            } while(cursor.moveToNext());
        }
        cursor.close();
    }

    /**
     * 设置左侧菜单条目的标题
     */
    private void initMenuTitles() {
        if (flag==0){  //老师
            mMenuTitles = getResources().getStringArray(R.array.menuTitles);
        }else {       //学生
            mMenuTitles = getResources().getStringArray(R.array.menuTitles1);
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mMenuTitles);
        mLvList.setAdapter(arrayAdapter);
        mLvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //switchFragment(position);//切换fragment
                Intent intent;
                if (flag==0) {
                    switch (position) {
                        case 0:
                            intent = new Intent(getApplicationContext(), DianMing.class);
                            startActivity(intent);
                            break;
                        case 1:
                            intent = new Intent(getApplicationContext(), YuYue.class);
                            startActivity(intent);
                            break;
                        case 2:
                            intent = new Intent(getApplicationContext(), myclass.MainActivity.class);
                            startActivity(intent);
                            break;
                        case 3:
                            intent = new Intent(getApplicationContext(), attendance.class);
                            startActivity(intent);
                            break;
                        case 4:
                            intent = new Intent(getApplicationContext(), Mysetting.class);
                            startActivity(intent);
                            break;
                    }
                }else{
                    switch (position){
                        case 0:
                            intent=new Intent(getApplicationContext(),QianDao.class);
                            startActivity(intent);
                            break;
                        case 1:
                            intent=new Intent(getApplicationContext(),myclass.MainActivity.class);
                            startActivity(intent);
                            break;
                        case 2:
                            intent=new Intent(getApplicationContext(),attendance.class);
                            startActivity(intent);
                            break;
                        case 3:
                            intent=new Intent(getApplicationContext(),Mysetting.class);
                            startActivity(intent);
                            break;
                    }
                }
                drawable.closeDrawers();//收起DrawerLayout

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu,menu);
        //找到searchView
        MenuItem searchItem = menu.findItem(R.id.action_search);
        //searchView=(SearchView) searchItem.getActionView();
        //searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        //initSearchView();

        return super.onCreateOptionsMenu(menu);
    }

    //设置右侧菜单项的点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        String tip = "";
        switch (id) {
            case R.id.item_size_10px:
                tip = "10px";
                SQLiteDatabase sqLiteDatabase =  databaseHelper.getWritableDatabase();
                sqLiteDatabase.execSQL("delete from attendance1 ");
                break;
            case R.id.item_size_12px:
                tip = "12px";
                break;
            case R.id.item_size_14px:
                tip = "14px";
                break;
            case R.id.item_color_red:
                tip = "红色";
                break;
            case R.id.item_color_bule:
                tip = "蓝色";
                break;
            case R.id.item_color_green:
                tip = "绿色";
                break;
        }
        Toast.makeText(getApplicationContext(), tip, Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }

    // 让菜单同时显示图标和文字
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equalsIgnoreCase("MenuBuilder")) {
                try {
                    Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    /**
     * 数据类
     */
    public class Fruit{
        private String name;
        private int imageId;

        public Fruit(String name,int imageId){
            this.name=name;
            this.imageId=imageId;
        }
        public String getName() {
            return name;
        }
        public int getImageId() {
            return imageId;
        }
    }

    // RecyclerView点击事件接口
    public interface MyItemClickListener {
        public void onItemClick(View view,int postion);
    }

    /**
     * Adapter for RecyclerView
     */
    public  class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder> {

        List<Student> mStudentList;

        private MyItemClickListener mOnItemClickListener;


        public void setOnItemClickListener(MyItemClickListener listener){
            this.mOnItemClickListener = listener;
        }

        class ViewHolder extends RecyclerView.ViewHolder{
            ImageView fruitImage;
            TextView courseName,courseinfo;
            Button btndianming;

            public ViewHolder(View view ){
                super(view);
                fruitImage=view .findViewById(R.id.item_img);
                courseName=view .findViewById(R.id.item_tv);
                courseinfo=view.findViewById(R.id.item_class);
                btndianming=view.findViewById(R.id.btn_dianming);
                btndianming.setText("删除");


                btndianming.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Student student1=mStudentList.get(getPosition());

                        SQLiteDatabase sqLiteDatabase =  databaseHelper.getWritableDatabase();
                        sqLiteDatabase.execSQL("delete from attendance1 where id_s = ?", new String[] {student1.getID_s()});
                        mStudentList.remove(getPosition());
                        notifyItemRemoved(getPosition());
                        notifyDataSetChanged();
                    }
                });

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemClickListener != null){
                            mOnItemClickListener.onItemClick(v,getPosition());
                        }
                    }
                });
            }

        }
        public FruitAdapter(List<Student> courseList){
            mStudentList=courseList;
        }

        /**创建条目布局*/
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.gridview_item,parent,false);
            ViewHolder holder=new ViewHolder(view);
            return holder;
        }

        /**绑定数据*/
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Student student=mStudentList.get(position);
            String s=student.getNowclass()+" 未到";
            //holder.fruitImage.setImageResource(course.get);
            holder.courseName.setText(student.getName_s());
            holder.courseinfo.setText(s);
        }



        @Override
        public int getItemCount() {
            return mStudentList.size();
        }


    }

}
