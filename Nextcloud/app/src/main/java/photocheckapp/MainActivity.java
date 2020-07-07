package photocheckapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.myapplication.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import URLAndInterface.Interface;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    @ViewInject(R.id.photo2)
    private ImageView photo;
    @ViewInject(R.id.takePic2)
    private Button takePic;
    @ViewInject(R.id.takeGallery2)
    private Button takeGallery2;
    @ViewInject(R.id.next2)
    private Button next2;


    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    private static final int CAMERA_PERMISSIONS_REQUEST_CODE = 0x03;
    private static final int STORAGE_PERMISSIONS_REQUEST_CODE = 0x04;
    private File fileUri = new File(Environment.getExternalStorageDirectory().getPath() + "/_0.jpg");
    private File fileCropUri = new File(Environment.getExternalStorageDirectory().getPath() + "/_crop.jpg");
    private Uri imageUri;
    private Uri cropImageUri;

    Handler mHandler=new Handler();
    String name="";
    String firstPicture="";
    int flag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photocheck_test);
        ViewUtils.inject(this);
//        Bundle bundle2 = this.getIntent().getExtras();
//        //接收name值
       // final String name = "test";
       // Log.i("获取到的name值为",name);
        next2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd__HH_mm_ss");// HH:mm:ss
                Date date = new Date(System.currentTimeMillis());
                Toast.makeText(getApplicationContext(),simpleDateFormat.format(date),Toast.LENGTH_LONG).show();
                /*Intent i=new Intent(MainActivity.this,Phpto3Activity.class);
                Bundle bundle1=new Bundle();
                //传递name参数为tinyphp
                bundle1.putString("name", name);
                i.putExtras(bundle1);
                startActivity(i);*/
            }
        });

    }


    @OnClick({R.id.takePic2, R.id.takeGallery2})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.takePic2:
                autoObtainCameraPermission();
                break;
            case R.id.takeGallery2:
                autoObtainStoragePermission();
                break;
        }
    }

    /**
     * 自动获取相机权限
     */
    private void autoObtainCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                ToastUtils.showShort(this, "您已经拒绝过一次");
            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, CAMERA_PERMISSIONS_REQUEST_CODE);
        } else {//有权限直接调用系统相机拍照
            if (hasSdcard()) {
                imageUri = Uri.fromFile(fileUri);
                if (Build.VERSION.SDK_INT >= 24)
                    imageUri = FileProvider.getUriForFile(MainActivity.this, "com.rorsch.file", fileUri);//通过FileProvider创建一个content类型的Uri
                PhotoUtils.takePicture(this, imageUri, CODE_CAMERA_REQUEST);
                System.out.println("picture:                              "+imageUri);
            } else {
                ToastUtils.showShort(this, "设备没有SD卡！");
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_PERMISSIONS_REQUEST_CODE: {//调用系统相机申请拍照权限回调
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (hasSdcard()) {
                        imageUri = Uri.fromFile(fileUri);
                        if (Build.VERSION.SDK_INT >= 24)
                            imageUri = FileProvider.getUriForFile(MainActivity.this, "com.rarash.fileprovider", fileUri);//通过FileProvider创建一个content类型的Uri
                        PhotoUtils.takePicture(this, imageUri, CODE_CAMERA_REQUEST);
                    } else {
                        ToastUtils.showShort(this, "设备没有SD卡！");
                    }
                } else {

                    ToastUtils.showShort(this, "请允许打开相机！！");
                }
                break;


            }
            case STORAGE_PERMISSIONS_REQUEST_CODE://调用系统相册申请Sdcard权限回调
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    PhotoUtils.openPic(this, CODE_GALLERY_REQUEST);    //request for opening gallery
                } else {

                    ToastUtils.showShort(this, "请允许操作SDCard！！");
                }
                break;
        }
    }

    private static final int output_X = 480;
    private static final int output_Y = 480;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CODE_CAMERA_REQUEST://拍照完成回调
                    cropImageUri = Uri.fromFile(fileCropUri);
                    PhotoUtils.cropImageUri(this, imageUri, cropImageUri, 1, 1, output_X, output_Y, CODE_RESULT_REQUEST);
                    break;
                case CODE_GALLERY_REQUEST://访问相册完成回调
                    if (hasSdcard()) {
                        cropImageUri = Uri.fromFile(fileCropUri);
                        Uri newUri = Uri.parse(PhotoUtils.getPath(this, data.getData()));
                        if (Build.VERSION.SDK_INT >= 24)
                            newUri = FileProvider.getUriForFile(this, "com.rarash.file", new File(newUri.getPath()));
                        PhotoUtils.cropImageUri(this, newUri, cropImageUri, 1, 1, output_X, output_Y, CODE_RESULT_REQUEST);  //cut the pic
                    } else {
                        ToastUtils.showShort(this, "设备没有SD卡！");
                    }
                    break;
                case CODE_RESULT_REQUEST:
                    Bitmap bitmap = PhotoUtils.getBitmapFromUri(cropImageUri, this);
                    if (bitmap != null) {
                        showImages(bitmap);
                        new Thread(){
                            @Override
                            public void run()
                            {
                                String url="http://101.132.121.32:8080/ServletTest/UploadImage";
                                myupload(cropImageUri.getPath(),url);
                                // TODO: 2018/3/14 0014  把网络访问的代码放在这里

                            }
                        }.start();
                    }
                    break;
            }
        }
    }


    /**
     * 自动获取sdk权限
     */

    private void autoObtainStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSIONS_REQUEST_CODE);
        } else {
            PhotoUtils.openPic(this, CODE_GALLERY_REQUEST);
        }

    }

    private void showImages(Bitmap bitmap) {
        photo.setImageBitmap(bitmap);

    }

    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }
    private void myupload(String path,String url) {

        com.lidroid.xutils.http.RequestParams pare= new com.lidroid.xutils.http.RequestParams();
       // Bundle bundle1 = this.getIntent().getExtras();
        //获取当前时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd__HH_mm_ss");// HH:mm:ss
        Date date = new Date(System.currentTimeMillis());
        name = simpleDateFormat.format(date);      //用户ID
        pare.addBodyParameter("userId", name);
        try {
            //对中文参数进行URL编码，然后在服务器那边对参数进行URL解码
            pare.addBodyParameter("userName", URLEncoder.encode("张淏然", "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

//      传图片时，要写3个参数
//      imageFile：键名
//      new File(path)：要上传的图片，path图片路径
//      image/jpg：上传图片的扩展名
        pare.addBodyParameter("imageFile", new File(path), "image/jpg");
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset("utf-8");
        http.send(HttpRequest.HttpMethod.POST,
                url,
                pare, new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String resultStr = responseInfo.result;
                        Log.e("1", "上传成功：" + resultStr);
                        Toast.makeText(MainActivity.this, "上传成功!", Toast.LENGTH_SHORT).show();

                        if(flag==0){
                            firstPicture=name+"_crop";
                            Interface interface3=new Interface(getApplication());
                            interface3.transcoding(firstPicture, new Interface.VolleyCallback() {
                                @Override
                                public void onSuccess(String result) {
                                    Toast.makeText(MainActivity.this, "转码成功!", Toast.LENGTH_SHORT).show();
                                    flag=1;
                                    takePic.setText("开始检测");
                                }

                                @Override
                                public void onerror(String error) {
                                    Toast.makeText(MainActivity.this, "转码失败！", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else{
                            check(); //人脸检测
                        }


                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        Log.e("1", "上传失败：" + error.getExceptionCode() + ":" + msg);
                        Toast.makeText(MainActivity.this, "上传失败!", Toast.LENGTH_SHORT).show();
                    }
                });


    }

    private void check(){

        Interface interface1=new Interface(getApplicationContext());
        interface1.transcoding(name+"_crop", new Interface.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                Interface interface2=new Interface(getApplicationContext());
                interface2.facecheck(name+"_crop", firstPicture, new Interface.VolleyCallback() {
                    @Override
                    public void onSuccess(String result) {
                        Toast.makeText(getApplicationContext(),"相识度："+result,Toast.LENGTH_LONG).show();
                        next2.setText(result);
                    }

                    @Override
                    public void onerror(String error) {
                        Toast.makeText(getApplicationContext(),"人脸识别连接错误",Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onerror(String error) {
                Toast.makeText(getApplicationContext(),"转码连接错误",Toast.LENGTH_LONG).show();
            }
        });

    }
}
