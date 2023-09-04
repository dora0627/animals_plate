package com.example.animals_plate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class MainActivity extends AppCompatActivity {
    Button login_t,sign_t;
    CheckBox cb;
    final LoadingDialog loadingDialog = new LoadingDialog(MainActivity.this);
    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //消除 ActionBar
        getSupportActionBar().hide();

        cb = (CheckBox)findViewById(R.id.cb_agree);
        //登入 註冊 點擊事件
        //login_t.setOnClickListener(btnclicker);
        //sign_t.setOnClickListener(btnclicker);
        btn_loginclicker();
        btn_signclicker();
        //loding頁面


        cb.setOnCheckedChangeListener(cc);
        login_t.setClickable(false);
        login_t.setEnabled(false);
        sign_t.setClickable(false);
        sign_t.setEnabled(false);
    }
    public void btn_loginclicker(){
        login_t = findViewById(R.id.btn_log);
        login_t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingDialog.startLoadingDialog();
                wait_secondl();

            }
        });
    }
    public void btn_signclicker(){
        sign_t  =(Button) findViewById(R.id.btn_sign);
        sign_t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingDialog.startLoadingDialog();
                wait_seconds();

            }
        });
    }
    public void wait_secondl(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingDialog.dismissDialog();
                Intent itl = new Intent(MainActivity.this,LoginActivity2.class);
                startActivity(itl);
            }
        },1000);

    }
    public void wait_seconds(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingDialog.dismissDialog();
                Intent its = new Intent(MainActivity.this,SignActivity3.class);
                startActivity(its);
            }
        },1000);

    }
    /*
    private View.OnClickListener btnclicker = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.btn_log:

                    Intent itl = new Intent(MainActivity.this,LoginActivity2.class);
                    startActivity(itl);
                    break;
                case R.id.btn_sign:
                    Intent its = new Intent(MainActivity.this,SignActivity3.class);
                    startActivity(its);
                    break;
            }

        }
    };*/
    private CompoundButton.OnCheckedChangeListener cc = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(buttonView.isChecked()){
                login_t.setClickable(true);
                login_t.setEnabled(true);
                sign_t.setClickable(true);
                sign_t.setEnabled(true);
            }else {
                login_t.setClickable(false);
                login_t.setEnabled(false);
                sign_t.setClickable(false);
                sign_t.setEnabled(false);
            }
        }
    };
}