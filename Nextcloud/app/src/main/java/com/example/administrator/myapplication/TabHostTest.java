package com.example.administrator.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;

/**
 * Created by Administrator on 2018/1/3 0003.
 */

public class TabHostTest extends Activity {
    Button btnback;
    TabHost tbhost;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabhost);

        tbhost=findViewById(R.id.TabHost1);
        tbhost.setup();

        btnback=findViewById(R.id.btn_tab_back);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),TouchMove.class));
            }
        });



        //setContent()有3种参数  int型 指定包含的控件、intent型 在Activity之间跳转、 TabContentFactory 把xml解析成View

        //使用包含控件的方法 兼容性会好很多！！
        //使用xml解析是按原来大小转换，显示不全。
        tbhost.addTab(
                tbhost.newTabSpec("TEST1")
                        .setIndicator("",getResources().getDrawable(R.mipmap
                                .ic_launcher))
                        .setContent(R.id.tab1)
        );
        tbhost.addTab(
                tbhost.newTabSpec("TEST2")
                        .setIndicator("预约")
                        .setContent(R.id.tab2)
        );
        tbhost.addTab(
                tbhost.newTabSpec("TEST3")
                        .setIndicator("课表")
                        .setContent(R.id.tab3)
        );

    }

    //创建菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
    //在工厂里吧xml解析成Viwe  然后拿给TabHost去 setcontent

    private TabHost.TabContentFactory factory1 = new TabHost.TabContentFactory() {

        @Override
        public View createTabContent(String tag) {
            return LayoutInflater.from(TabHostTest.this).inflate(R.layout.tab1_layout, null);
        }
    };

    private TabHost.TabContentFactory factory2 = new TabHost.TabContentFactory() {

        @Override
        public View createTabContent(String tag) {
            return LayoutInflater.from(TabHostTest.this).inflate(R.layout.tab2_layout, null);
        }
    };
    private TabHost.TabContentFactory factory3 = new TabHost.TabContentFactory() {

        @Override
        public View createTabContent(String tag) {
            return LayoutInflater.from(TabHostTest.this).inflate(R.layout.tab3_layout, null);
        }
    };
}
