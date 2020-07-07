package com.example.administrator.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Info.Course;
import Info.Teacher;
import URLAndInterface.Interface;

/**
 * Created by Administrator on 2018/1/29 0029.
 */

public class QianDao extends AppCompatActivity {

    Toolbar toolbar ;
    DrawerLayout drawable;
    ActionBarDrawerToggle mDrawerToggle;
    SearchView searchView;

    private List<ContentFragment> mFragments = new ArrayList<>();
    private ListView mLvList;
    String[] mMenuTitles;   //菜单栏


    List<String> datasource=new ArrayList<String>(); //动态字符串数组
    RecyclerView recyclerView;
    List<Fruit> fruitList=new ArrayList<>();

    String[] course;   //课程ID
    List<Course> courseList=new ArrayList<>();     //所有课程
    Teacher teacher;   //老师信息
    String[] teacherid; //老师ID
    Map<String, Teacher> map = new HashMap<String, Teacher>();
    List<Teacher> teacherList=new ArrayList<>();   //所有老师
    int i=0;

    FireworkyPullToRefreshLayout mpullrefresh;
    Handler mHandler=new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);  //隐藏掉系统原先的导航栏
        setContentView(R.layout.dianming);

        initView();
        getTeacherClassId(); //获取老师所有课程ID 和课程信息

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

        toolbar.setTitle("课程签到");//设置标题
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
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                FruitAdapter adapter=new FruitAdapter(courseList);
                recyclerView.setAdapter(adapter);
       /* recyclerView.addItemDecoration(new MyDividerItemDecoration(this,3
                ,getResources().getColor(android.R.color.darker_gray)));*/

                //点击时间监听
                adapter.setOnItemClickListener(new MyItemClickListener() {
                    @Override
                    public void onItemClick(View view, int postion) {
                        Course course1=courseList.get(postion);
                        //Teacher teacher1=teacherList.get(postion);
                        for (String in : map.keySet()) {
                            if( in.equals(course1.getID_c()) ){
                                teacher=map.get(in);
                                break;
                            }
                        }
                        String s=course1.getStartweek()+"-"+course1.getEndweek()+"周  周"+course1.getDate()
                                +"  "+course1.getStarttime()+"-"+course1.getEndtime()+"节";
                        String s1=course1.getCharacter_c()+" "+course1.getCredit_c()+"学分 "+course1.getTextmethed_c();
                        Intent intent=new Intent(getApplicationContext(),Class_Detail.class);
                        intent.putExtra("courseName",course1.getName_c());
                        intent.putExtra("courseCharacter",s1);
                        intent.putExtra("teacherName",teacher.getName_t());
                        intent.putExtra("teacherTel","Tel:"+teacher.getPhonenumber_t());
                        intent.putExtra("teacherOffice","办公室:"+teacher.getOffice_t());
                        intent.putExtra("teacherEmail","邮箱:"+teacher.getEmail_t());
                        intent.putExtra("coursePlace",course1.getLocation());
                        intent.putExtra("courseTime",s);
                        intent.putExtra("courseProceeding",course1.getProceeding());
                        intent.putExtra("courseHomework",course1.getHomework());
                        startActivity(intent);
                    }
                });
            }
        },1000);


    }

    /**
     * 设置左侧菜单条目的标题
     */
    private void initMenuTitles() {
        mMenuTitles = getResources().getStringArray(R.array.menuTitles1);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mMenuTitles);
        mLvList.setAdapter(arrayAdapter);
        mLvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //switchFragment(position);//切换fragment
                Intent intent;
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
                drawable.closeDrawers();//收起DrawerLayout

            }
        });
    }

    private void initFragments() {
        ContentFragment fragment1 = new ContentFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putString(ContentFragment.TEXT,getString(R.string.menu_dianming));
        fragment1.setArguments(bundle1);


        ContentFragment fragment2 = new ContentFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putString(ContentFragment.TEXT,getString(R.string.menu_yuyue));
        fragment2.setArguments(bundle2);

        mFragments.add(fragment1);
        mFragments.add(fragment2);
    }

    /**
     * 切换fragment
     * @param index 下标
     */
    private void switchFragment(int index) {
        ContentFragment contentFragment = mFragments.get(index);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.cs_content,contentFragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();
    }

    /**
     * 获取老师所有课程ID
     */
    private void getTeacherClassId(){
        SharedPreferences pref=getSharedPreferences("teacher",MODE_PRIVATE);
        String student_id=pref.getString("t_id","");         //获取老师ID

        //返回这个老师教的所有课程的id
        Interface interface1=new Interface(getApplicationContext());
        interface1.getAllSelectCourse(student_id, new Interface.VolleyCallback_Stringshuzu() {
            @Override
            public void onSuccess(String[] result) {
                course=result;
                for (String aCourse : course) {
                    //获取课程信息
                    Interface interface2 = new Interface(getApplicationContext());
                    interface2.getCourseInfoByID(aCourse, new Interface.VolleyCallback_course() {
                        @Override
                        public void onSuccess(Course course) {
                            courseList.add(course);
                        }

                        @Override
                        public void onerror(String error) {
                            Toast.makeText(getApplicationContext(), "获取课程信息失败", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }

            @Override
            public void onerror(String error) {
                Toast.makeText(getApplicationContext(),"获取课表失败"
                        ,Toast.LENGTH_LONG).show();
            }
        });

        //获取老师信息
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (final String aCourse : course){
                //获取老师ID
                Interface interface0=new Interface(getApplicationContext());
                interface0.getTeacherIdByCourseId(aCourse, new Interface.VolleyCallback() {
                    @Override
                    public void onSuccess(String result) {

                        //获取老师信息
                        Interface interface3=new Interface(getApplicationContext());
                        interface3.getTeacherInfoById(result, new Interface.VolleyCallback_Teacher() {
                            @Override
                            public void onSuccess(Teacher teacher) {
                               // teacherList.add(teacher);
                                map.put(aCourse,teacher);
                            }

                            @Override
                            public void onerror(String error) {

                            }
                        });
                    }

                    @Override
                    public void onerror(String error) {

                    }
                });
                }
            }
        },500);


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

    /**
     * 填充数据
     */
    private void initfruit(){
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<course.length;i++){
                    Fruit apple =new Fruit("apple",R.mipmap.ic_launcher);
                    fruitList.add(apple);
                    Fruit banana =new Fruit("banana",R.mipmap.ic_launcher);
                    fruitList.add(banana);
                    Fruit orange =new Fruit("orange",R.mipmap.ic_launcher);
                    fruitList.add(orange);
                    Fruit watermelon =new Fruit("watermelon",R.mipmap.ic_launcher);
                    fruitList.add(watermelon);
                    Fruit pear =new Fruit("pear",R.mipmap.ic_launcher);
                    fruitList.add(pear);
                }
            }
        },300);
    }

    // RecyclerView点击事件接口
    public interface MyItemClickListener {
        public void onItemClick(View view,int postion);
    }

    /**
     * Adapter for RecyclerView
     */
    public  class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder> {

        List<Course> mCourseList;

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
                btndianming.setText("签到");

                btndianming.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Course course1=mCourseList.get(getPosition());
                        for (String in : map.keySet()) {
                            if( in.equals(course1.getID_c()) ){
                                teacher=map.get(in);
                                break;
                            }
                        }

                        String s=course1.getStartweek()+"-"+course1.getEndweek()+"周  周"+course1.getDate()
                                +"  "+course1.getStarttime()+"-"+course1.getEndtime()+"节";
                        Intent intent=new Intent(getApplicationContext(),Dianming_1.class);
                        intent.putExtra("courseid",course1.getID_c());
                        intent.putExtra("coursename",course1.getName_c());
                        intent.putExtra("teachername",teacher.getName_t());
                        intent.putExtra("courselocation",course1.getLocation());//获取上课教室
                        intent.putExtra("coursetime",s);
                        startActivity(intent);
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
        public FruitAdapter(List<Course> courseList){
            mCourseList=courseList;
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
            Course course=mCourseList.get(position);
            String s=course.getStartweek()+"-"+course.getEndweek()+"(周)  "+course.getLocation();
            //holder.fruitImage.setImageResource(course.get);
            holder.courseName.setText(course.getName_c());
            holder.courseinfo.setText(s);
        }

        @Override
        public int getItemCount() {
            return mCourseList.size();
        }


    }

    //分割线绘制
    public class MyDividerItemDecoration extends RecyclerView.ItemDecoration{
        private Paint mPaint;
        private int mDividerHeight;

        public MyDividerItemDecoration(Context context, int dividerHeight, int dividerColor){
            mDividerHeight = dividerHeight;
            mPaint = new Paint();
            mPaint.setColor(dividerColor);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.bottom = mDividerHeight;//矩形的底部赋值分割线的高度
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            int childCount = parent.getChildCount();//获取到子View的个数
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();

            for (int i = 0; i < childCount - 1; i++) {
                View view = parent.getChildAt(i);
                float top = view.getBottom();
                float bottom = view.getBottom() + mDividerHeight;//子View底部添加分割线的高度
                c.drawRect(left, top, right, bottom, mPaint);//绘制
            }
        }
    }
}
