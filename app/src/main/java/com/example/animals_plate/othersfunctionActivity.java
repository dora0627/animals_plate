package com.example.animals_plate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.example.animals_plate.toolbar_home.toolbar_homeActivity;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class othersfunctionActivity extends AppCompatActivity {
    CardView card_service,card_shop,card_community,card_version,card_aboutus,card_donate,card_dataChange;
    ShimmerFrameLayout shimmerFrameLayout;
    private BottomNavigationView bottomNavigationView;
    LinearLayout dataLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_othersfunction);
        getSupportActionBar().hide();
        dataLayout = findViewById(R.id.data_view2);
        dataLayout.setVisibility(View.INVISIBLE);
        //骨架畫面
        skeleton_screen();
        //bottom nav event
        bottom_nav_event();
        //card event
        cardServiceEvent();

    }
    public void skeleton_screen(){
        shimmerFrameLayout = findViewById(R.id.shimmer_view2);
        shimmerFrameLayout.startShimmerAnimation();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dataLayout.setVisibility(View.VISIBLE);
                shimmerFrameLayout.stopShimmerAnimation();
                shimmerFrameLayout.setVisibility(View.INVISIBLE);
            }
        }, 1000);
    }
    public void bottom_nav_event(){
        bottomNavigationView = findViewById(R.id.btn_nag_others);
        // 設定選擇的位置為 "others"
        bottomNavigationView.setSelectedItemId(R.id.others);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        Log.d("BottomNav", "Home clicked");
                        //getSupportFragmentManager().beginTransaction().replace(R.id.container, hf).commit();
                        Intent itmh = new Intent(othersfunctionActivity.this, toolbar_homeActivity.class);
                        itmh.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(itmh);


                        return true;
                    case R.id.map:
                        Log.d("BottomNav", "map clicked");
                        // getSupportFragmentManager().beginTransaction().replace(R.id.container, mf).commit();
                        Intent itma = new Intent(othersfunctionActivity.this,MapanimalActivity.class);
                        itma.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(itma);
                        return true;
                    case R.id.chat:
                        //getSupportFragmentManager().beginTransaction().replace(R.id.container, cf).commit();
                        Intent itc = new Intent(othersfunctionActivity.this,ChatActivity.class);
                        itc.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(itc);

                        return true;
                    case R.id.others:
                        //getSupportFragmentManager().beginTransaction().replace(R.id.container,off).commit();

                        return true;
                    case R.id.info:
                        Intent iti = new Intent(othersfunctionActivity.this,InfoActivity.class);
                        iti.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(iti);
                        //getSupportFragmentManager().beginTransaction().replace(R.id.container,If).commit();

                        return true;
                    default:
                        return false;
                }
            }
        });
    }
    public void cardServiceEvent(){
        card_service = findViewById(R.id.card_service);
        card_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(othersfunctionActivity.this, serviceActivity.class);
                startActivity(it);
            }
        });
        card_service.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    card_service.setBackgroundColor(Color.argb(32,8,203,250));

                } else if (event.getAction() == MotionEvent.ACTION_UP ||event.getAction() == MotionEvent.ACTION_CANCEL) {
                    // 釋放或取消觸摸時恢復原來的顏色
                    card_service.setBackgroundColor(Color.argb(52,8,203,250));
                }
                return false;
            }
        });
        card_community = findViewById(R.id.card_community);
        card_community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(othersfunctionActivity.this, BlogActivity.class);
                startActivity(it);
            }
        });
        card_dataChange = findViewById(R.id.card_dataChange);
        card_dataChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(othersfunctionActivity.this,recordCheckActivity.class);
                startActivity(it);
            }
        });
    }
}