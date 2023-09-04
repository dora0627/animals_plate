package com.example.animals_plate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivity5 extends AppCompatActivity {
    private BottomNavigationView bnv;
    private Toolbar tb;
    ImageView back,list;
    TextView tv;

    HomeFragment hf = new HomeFragment();
    MapFragment mf = new MapFragment();
    othersfunctionFragment off = new othersfunctionFragment();
    //SettingFragment sf = new SettingFragment();
    ChatFragment cf =new ChatFragment();
    MapanimalActivity ma = new MapanimalActivity();
    InfoFragment If = new InfoFragment();
    private void Checkpermission() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // 如果沒有定位權限，請請求權限
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        } else {

        }
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home5);
        tv = findViewById(R.id.title_toolbar);
        //action bar back click
        navbar_btnclicker();
        //navigation select
        navigation_bottom_selector();
        // 預設為 HomeFragment
        getSupportFragmentManager().beginTransaction().add(R.id.container, hf).commit();
        //隱藏初始 action bar
        getSupportActionBar().hide();

        Checkpermission();
    }
    public void navigation_bottom_selector(){
        bnv = (BottomNavigationView) findViewById(R.id.btn_nag);
        //bnv = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        Log.d("BottomNav", "Home clicked");
                        //getSupportFragmentManager().beginTransaction().replace(R.id.container, hf).commit();
                        //至動物瀏覽頁
                        Intent ita = new Intent(HomeActivity5.this,apiTestActivity.class);
                        startActivity(ita);
                        return true;
                    case R.id.map:
                        // getSupportFragmentManager().beginTransaction().replace(R.id.container, mf).commit();
                        Intent itma = new Intent(HomeActivity5.this,MapanimalActivity.class);
                        startActivity(itma);
                        return true;
                    case R.id.chat:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, cf).commit();
                        return true;
                    case R.id.others:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, off).commit();
                        return true;
                    case R.id.info:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,If).commit();
                        return true;
                    default:
                        return false;
                }
            }
        });
    }
    public void navbar_btnclicker(){
        back = findViewById(R.id.leftback);
        list = findViewById(R.id.right_list);
        back.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        back.setImageResource(R.drawable.baseline_keyboard_arrow_left_24_pressed);
                        break;
                    case MotionEvent.ACTION_UP:
                        back.setImageResource(R.drawable.baseline_keyboard_arrow_left_24);
                        break;
                }

                return false;
            }
        });

        list.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        list.setImageResource(R.drawable.baseline_format_list_bulleted_pressed_24);
                        break;
                    case MotionEvent.ACTION_UP:
                        list.setImageResource(R.drawable.baseline_format_list_bulleted_24);
                        break;
                }

                return false;
            }
        });
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ad = new AlertDialog.Builder(HomeActivity5.this);
                ad.setTitle("確定要離開嗎?");
                ad.setMessage("離開後將回首頁");
                ad.setIcon(R.drawable.baseline_warning_24);
                ad.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent it =new Intent(HomeActivity5.this,MainActivity.class);
                        startActivity(it);
                    }
                });
                ad.show();
            }
        });
    }
}