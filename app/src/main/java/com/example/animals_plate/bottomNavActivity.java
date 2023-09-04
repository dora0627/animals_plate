package com.example.animals_plate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class bottomNavActivity extends AppCompatActivity {
     BottomNavigationView bottomNavigationView;
    HomeFragment hf = new HomeFragment();
    MapFragment mf = new MapFragment();
    SettingFragment sf = new SettingFragment();
    ChatFragment cf =new ChatFragment();
    InfoFragment If = new InfoFragment();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_nav);
        bottomnavigationclicker();

    }
    public void bottomnavigationclicker(){
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()){
                    case R.id.nav_home:
                       // fragment = new hf()
                        //getSupportFragmentManager().beginTransaction().replace(R.id.container, hf).commit();
                        Intent it = new Intent(bottomNavActivity.this,apiTestActivity.class);
                        startActivity(it);
                        return true;
                    case R.id.nav_chat:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, cf).commit();
                        return true;

                    case R.id.nav_map:
                        Intent itma = new Intent(bottomNavActivity.this,MapanimalActivity.class);
                        startActivity(itma);
                        return true;
                    case R.id.nav_person:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, If).commit();
                        return true;
                    case R.id.nav_shop:

                        break;
                    default:
                        return false;
                }
                //頁面的使用方式
                getSupportFragmentManager().beginTransaction().replace(R.id.body_container,fragment).commit();
                return true;
            }
        });
    }
}