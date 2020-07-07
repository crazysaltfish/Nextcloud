package com.example.administrator.myapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cleveroad.pulltorefresh.firework.FireworkyPullToRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/13 0013.
 */

public class RecyclerViewdemo extends Activity {
    List<String> datasource=new ArrayList<String>(); //动态字符串数组
    RecyclerView recyclerView;
    List<Fruit> fruitList=new ArrayList<>();
    FireworkyPullToRefreshLayout mpullrefresh;
    Handler mHandler=new Handler();


    /*
    @SuppressLint("HandlerLeak")
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x001:
                    mpullrefresh.setRefreshing(false);
                    break;
            }
        }
    };
                    new Thread(new Runnable(){

                        public void run(){
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            handler.sendEmptyMessage(0x001); //告诉主线程执行任务
                        }


                    }).start();*/


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pulltorefresh);
        initfruit();
        recyclerView=findViewById(R.id.recyView);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        FruitAdapter adapter=new FruitAdapter(fruitList);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this,3
                ,getResources().getColor(android.R.color.white)));
        mpullrefresh=findViewById(R.id.pullToRefresh);
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

                //mpullrefresh.setRefreshing(false);
            }
        });

        adapter.setOnItemClickListener(new FruitAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                Toast.makeText(RecyclerViewdemo.this, fruitList.get(postion).name, Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.mymenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void initfruit(){
        for(int i=0;i<3;i++){
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





    public static class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder> {

        List<Fruit> mFruitList;

        public interface MyItemClickListener {
            public void onItemClick(View view,int postion);
        }

        private MyItemClickListener mOnItemClickListener;

        public void setOnItemClickListener(MyItemClickListener listener){
            this.mOnItemClickListener = listener;
        }

        class ViewHolder extends RecyclerView.ViewHolder{
            ImageView fruitImage;
            TextView fruitName;

            public ViewHolder(View view ){
                super(view);
                fruitImage=view .findViewById(R.id.item_img);
                fruitName=view .findViewById(R.id.item_tv);
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
        public FruitAdapter(List<Fruit> fruitList){
            mFruitList=fruitList;
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
            Fruit fruit=mFruitList.get(position);
            holder.fruitImage.setImageResource(fruit.getImageId());
            holder.fruitName.setText(fruit.getName());
        }

        @Override
        public int getItemCount() {
            return mFruitList.size();
        }


    }

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
