package com.example.administrator.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/13 0013.
 */

public class refreshActivity extends Activity {
    GridView lvcity;
    String[] courses;   //数据源
    Button btnback,btnadd;
    EditText etadd;
    TextView tvcity;
    Spinner spner;
    List<String> datasource=new ArrayList<String>(); //动态字符串数组
    Myadapter myadapter =new Myadapter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_listview);


        lvcity=findViewById(R.id.gridView);
        courses=getResources().getStringArray(R.array.course);
        btnback=findViewById(R.id.btnTomain);
        btnadd=findViewById(R.id.btn_add);
        etadd=findViewById(R.id.et_add);
        tvcity=findViewById(R.id.tvcity);
        spner=findViewById(R.id.spner_col);
        //pullrefresh=findViewById(R.id.pullToRefresh);
        //设置监听
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),TouchMove.class));
            }
        });
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String addstring=etadd.getText()+"";

                datasource.add(addstring);
                myadapter.notifyDataSetChanged();

            }
        });

        spner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TextView tv=view.findViewById(android.R.id.text1);
                String s=tv.getText()+"";
                lvcity.setNumColumns(Integer.parseInt(s));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        ArrayAdapter<String> adapter=new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,   //项目布局（系统自带的）
                android.R.id.text1,                    //在上面的布局文件中的 一个ID 叫txt1
                courses);

        for (int i=0;i<courses.length;i++){            //给数据源添加数据
            datasource.add(courses[i]);
        }



        lvcity.setAdapter(myadapter);         //放置数据
        //设置Item监听
        lvcity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView tvtext=view.findViewById(R.id.item_tv);
                tvcity.setText(tvtext.getText());
                //tvcity.setText(datasource.get(i));
            }
        });

    }
    //设置适配器
    class Myadapter extends BaseAdapter {

        //循环放置的次数
        @Override
        public int getCount() {
            return datasource.size();
        }

        //返回项
        @Override
        public Object getItem(int i) {
            return i;
        }

        //返回ID
        @Override
        public long getItemId(int i) {
            return i;
        }

        //返回View
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View row=view;

            Wrapper wrapper=null;
            if(row==null)         //提高效率 ，之前解析过的Item 就不再次去解析！
            {
                //LayoutInflater inflater=getLayoutInflater();
                //解析成View,此步骤非常消耗资源。
                //row=inflater.inflate(R.layout.gridview_item,viewGroup,false);
                row= LayoutInflater.from(getApplicationContext()).inflate(R.layout.gridview_item,viewGroup,false);
                wrapper=new Wrapper(row);
                row.setTag(wrapper);
            }else{
                wrapper=(Wrapper) row.getTag();
            }

            TextView textView=wrapper.gettvname();
            ImageView img=wrapper.getImgname();
            textView.setText(datasource.get(i));


            return row;
        }
    }
    // ListView 提高效率的方法
    class Wrapper{
        TextView textView;
        ImageView img;

        View row;

        public Wrapper(View row){    //构造函数
            this.row=row;
        }

        public TextView gettvname(){
            if (textView==null){
                textView=row .findViewById(R.id.item_tv);
            }
            return textView;
        }

        public ImageView getImgname(){
            if (img==null){
                img=row .findViewById(R.id.item_img);
            }
            return img;
        }
    }
}
