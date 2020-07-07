package photocheckapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.SignupActivity;

import URLAndInterface.Interface;

/**
 * Created by Administrator on 2018/3/15 0015.
 */

public class Login extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    EditText _emailText;
    EditText _passwordText;
    Button _loginButton;
    TextView _signupLink;
    RadioGroup radgroup;

    String mac;
    String t_id="";       //ID
    int flag=0;
    String res="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);  //隐藏掉系统原先的导航栏
        setContentView(R.layout.material_design_login);
        initview(); //初始化控件
        radgroup.setVisibility(View.INVISIBLE);


        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                for (int i = 0; i < radgroup.getChildCount(); i++) {
                    RadioButton rd = (RadioButton) radgroup.getChildAt(i);
                    if (rd.isChecked()) {
                        //flag=i;
                    }
                }
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }

    private void initview(){
        _emailText=findViewById(R.id.input_email);
        _passwordText=findViewById(R.id.input_password);
        _loginButton=findViewById(R.id.btn_login);
        _signupLink=findViewById(R.id.link_signup);
        radgroup=findViewById(R.id.radioGroup);
    }

    public void login() {
        Log.d(TAG, "Login");

       /* if (!validate()) {
            onLoginFailed();
            return;
        }
        */

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(photocheckapp.Login.this,
                R.style.Theme_AppCompat_DayNight_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        t_id = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        //login验证
        Interface interface1=new Interface(getApplicationContext());
        interface1.SignIn(t_id, password, new Interface.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                res=result;
            }

            @Override
            public void onerror(String error) {
                Toast.makeText(getApplicationContext(),"服务器连接错误",Toast.LENGTH_LONG).show();
            }
        });


        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        if(res.equals("200")){
                            //Toast.makeText(getApplicationContext(),"登录成功",Toast.LENGTH_LONG).show();
                            onLoginSuccess();
                        } else{
                            onLoginFailed();
                        }
                        progressDialog.dismiss();
                    }
                }, 2000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);

        Intent intent;
        intent=new Intent(getApplicationContext(),photocheckapp.MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void onLoginFailed() {
        if(res.equals("100")){
            Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        }
        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}
