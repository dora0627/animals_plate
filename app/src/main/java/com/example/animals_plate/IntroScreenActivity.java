package com.example.animals_plate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

public class IntroScreenActivity extends AppCompatActivity {
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_screen);
        getSupportActionBar().hide();
        viewPager = findViewById(R.id.viewPager);

        IntroAdapter introAdapter = new IntroAdapter(getSupportFragmentManager());
        viewPager.setAdapter(introAdapter);
    }
}