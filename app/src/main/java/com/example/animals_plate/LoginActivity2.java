package com.example.animals_plate;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.animals_plate.toolbar_home.toolbar_homeActivity;

import java.util.Objects;

public class LoginActivity2 extends AppCompatActivity {
    EditText edtacc,edtpas;

    TextView forget_p,message,sig,login_title;
    Button login,register;
    ImageView ab_back,btnfblog;
    Toolbar tb;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        login_title = findViewById(R.id.login_title);
        btnfblog = findViewById(R.id.btn_fblog);
        btnfblog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(LoginActivity2.this, toolbar_homeActivity.class);
                startActivity(it);
            }
        });
        /*new Thread(new Runnable(){
            @Override
            public void run(){
               MySQLConnector con = new MySQLConnector();
                con.run();
                final String data = con.getData();
                Log.v("OK",data);


            }
        }).start();*/
        //銀藏預設action bar
        getSupportActionBar().hide();
        setTitle("登入Login");
        edtacc = findViewById(R.id.editTextUsername);
        edtpas = findViewById(R.id.editTextPassword);

        //action bar back click
        navbar_btnclicker();
        //登入按鈕事件
        btnLoginclicker();
        //註冊按鈕事件
        btnRigsterclicker();
        //文字超連結
        textlinker();
        //文字典籍事件
        forgetclicker();

        //edtacc = (EditText) findViewById(R.id.edtacc); // find the EditText
        //edtpas = (EditText) findViewById(R.id.edtpas);
       // forget_p = (TextView) findViewById(R.id.textView);
       // sig = (TextView) findViewById(R.id.sign_link);
        //message = (TextView) findViewById(R.id.txtshow);
       // login = (Button) findViewById(R.id.btn_log);
        //tb = (Toolbar) findViewById(R.id.tbl);

        //setSupportActionBar(tb);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);




    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 禁用返回鍵
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
    public void navbar_btnclicker(){
        ab_back = (ImageView) findViewById(R.id.img_login_back);
        ab_back.setClickable(true);
        ab_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itb = new Intent(LoginActivity2.this,MainActivity.class);
                startActivity(itb);
            }
        });
        ab_back.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ab_back.setImageResource(R.drawable.baseline_keyboard_arrow_left_24_pressed);
                        break;
                    case MotionEvent.ACTION_UP:
                        ab_back.setImageResource(R.drawable.baseline_keyboard_arrow_left_24);
                        break;
                }
                return false;
            }
        });
    }
    public void forgetclicker(){
        forget_p = findViewById(R.id.textViewForgotPassword);
        forget_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(LoginActivity2.this,otpActivity.class);
                startActivity(it);
            }
        });
    }
    public void btnRigsterclicker(){
        register = findViewById(R.id.buttonRegister);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(LoginActivity2.this,SignActivity3.class));
            }
        });
    }
    //文字超連結
    public void textlinker(){
        sig = (TextView) findViewById(R.id.textViewForgotPassword);
        String link = sig.getText().toString();
        SpannableString spannableString = new SpannableString(link);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                //至忘記密碼頁面
                //Intent intent = new Intent(LoginActivity2.this, SignActivity3.class);
               // startActivity(intent);
            }
        };
        spannableString.setSpan(clickableSpan, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sig.setText(spannableString);
        sig.setMovementMethod(LinkMovementMethod.getInstance());
    }
    public void btnLoginclicker() {



        login = (Button) findViewById(R.id.buttonLogin);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.buttonLogin:
                        String usernames =  edtacc.getText().toString();
                        String passwords =  edtpas.getText().toString();
                        //連線至資料庫
                        String type = "login";
                        //Intent it = new Intent(LoginActivity2.this, apiTestActivity.class);
                        //startActivity(it);
                        BackgroundWorker backgroundWorker = new BackgroundWorker( LoginActivity2.this);
                        backgroundWorker.execute(type, usernames, passwords);
                        //select account,password from register;

                        //如果成功

                        //登入成功通知
                        //AlertDialog.Builder window = new AlertDialog.Builder(LoginActivity2.this);
                        // window.setIcon(R.drawable.success);
                        //window.setTitle("登入成功");
                        //window.setIcon(R.drawable.baseline_info_success_24);
                        //window.setMessage(" 按下確定將進行畫面跳轉");
                        /*window.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent it = new Intent(LoginActivity2.this, IntroScreenActivity.class);
                                startActivity(it);
                            }
                        }).show();*/
                        break;

                }
            }
        });
    }
}