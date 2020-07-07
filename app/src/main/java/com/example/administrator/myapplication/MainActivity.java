package com.example.administrator.myapplication;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView tvInfo,tvpos;
    EditText tvUserN,tvPsd,edContent;
    Button btn,btnBack;
    CheckBox ckbB,ckbI;
    SeekBar sBar;
    int style;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tvUserN=(EditText) findViewById(R.id.edUsername);
        tvPsd=(EditText) findViewById(R.id.edPassword);
        btn=(Button) findViewById(R.id.btRegister);
        btnBack=findViewById(R.id.btnBack);
        tvInfo=(TextView) findViewById(R.id.textView3);
        tvpos=findViewById(R.id.tvpos);
        edContent=findViewById(R.id.edContent);
        ckbB=findViewById(R.id.ckbB);
        ckbI=findViewById(R.id.ckbI);
        sBar=findViewById(R.id.sBar_sound);




        // appkey和appSecret在mob申请的应用中获取

        // 初始化SDK,单例，可以多次调用；任何方法调用前，必须先初始化

        /*SMSSDK.initSDK(this, "您的appkey", "您的appsecret");

        // 其中参数第一个代表的是国家代号，86为中国

        SMSSDK.getVerificationCode("86","手机号码");
        */

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),TouchMove.class));
            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {          //btn按键监听

                String result;
                StringBuilder sb=new StringBuilder();
                result="用户名为："+tvUserN.getText()+" ,密码为："+tvPsd.getText() ;
                sb.append("用户名为："+tvUserN.getText()+" ,密码为："+tvPsd.getText());
                tvInfo.setText(result+" ");
            }
        });

        sBar.setMax(255);  //设置最大值
        sBar.setProgress(getSystemBrightness());//设置当前进度
        sBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                changeAppBrightness(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        CheckedChange listener=new CheckedChange();
        ckbB.setOnCheckedChangeListener(listener);
        ckbI.setOnCheckedChangeListener(listener);

    }
    class CheckedChange implements CompoundButton.OnCheckedChangeListener{    //选项控制字体粗、斜

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            switch (compoundButton.getId()){           //获取选择的ckb控件ID
                case R.id.ckbB:
                    if (b)
                        style += Typeface.BOLD;
                    else
                        style -= Typeface.BOLD;
                    break;
                case R.id.ckbI:
                if(b)
                    style+=Typeface.ITALIC;
                else style-=Typeface.ITALIC;
                    break;
            }
            edContent.setTypeface(Typeface.DEFAULT,style);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {                     //触摸监控
        if (event.getAction()==MotionEvent.ACTION_DOWN){
           String pos="";
            float x=event.getX();
            float y=event.getY();
            pos="x="+x+",y="+y;
            tvpos.setText(pos+"");
        }
       // else if (event.getAction()==MotionEvent.ACTION_UP){}


        return super.onTouchEvent(event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            //字号
            case R.id.item_size_10px:
                if(item.isChecked()) {
                    item.setChecked(false);
                    edContent.setTextSize(10*2);
                }
                else {
                    item.setChecked(true);
                    edContent.setTextSize(8*2);
                }
                break;
            case R.id.item_size_12px:
                if(item.isChecked()) {
                    item.setChecked(false);
                    edContent.setTextSize(8*2);
                }
                else {
                    item.setChecked(true);
                    edContent.setTextSize(12*2);
                }
                break;
            case R.id.item_size_14px:
                if(item.isChecked()) {
                    item.setChecked(false);
                    edContent.setTextSize(8*2);
                }
                else {
                    item.setChecked(true);
                    edContent.setTextSize(14*2);
                }
                break;
             //颜色
            case R.id.item_color_red:
                item.setChecked(true);
                edContent.setTextColor(Color.RED);
                break;
            case R.id.item_color_bule:
                item.setChecked(true);
                edContent.setTextColor(Color.BLUE);
                break;
            case R.id.item_color_green:
                item.setChecked(true);
                edContent.setTextColor(Color.GREEN);
                break;
        }


        return super.onOptionsItemSelected(item);
    }
/*********************************在这里的是对屏幕亮度的调节***************************************/
     //获取当前窗口亮度
    private int getSystemBrightness() {
        int systemBrightness = 0;
        try {
            systemBrightness = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return systemBrightness;
    }
    //设置窗口亮度
    public void changeAppBrightness(int brightness) {
        Window window = this.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        if (brightness == -1) {
            lp.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE;
        } else {
            lp.screenBrightness = (brightness <= 0 ? 1 : brightness) / 255f;
        }
        window.setAttributes(lp);
    }
    /*
    最近开发一个项目，遇到一个问题，在手机开启热点的情况下，想要获取是哪个设备已经连接上了Android手机开启的热点。

    经过google，baidu ，最终没有找到答案。

    最后想起在国外论坛下载了一个AP Demo，看了看源码，最终找到了可解决问题的方法。

    如下：此方法肯定是熟知linux开发者想到的办法，用re文件管理器去"/proc/net/arp"，进去一看，发现连接上热点的设备信息都在这里了，包括mac ip等

    private ArrayList<String> getConnectedIP() {
    ArrayList<String> connectedIP = new ArrayList<String>();
    try {
    BufferedReader br = new BufferedReader(new FileReader(
    "/proc/net/arp"));
    String line;
    while ((line = br.readLine()) != null) {
    String[] splitted = line.split(" +");
    if (splitted != null && splitted.length >= 4) {
    String ip = splitted[0];
    connectedIP.add(ip);
    }
    }
    } catch (Exception e) {
    e.printStackTrace();
    }
    return connectedIP;
    }



    调用方法：

    ArrayList<String> connectedIP = getConnectedIP();
    resultList = new StringBuilder();
    for (String ip : connectedIP) {
    resultList.append(ip);
    resultList.append("\n");
    }
    System.out.print(resultList);

    over~~~~
     */

}
