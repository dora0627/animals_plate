package com.example.animals_plate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class shopanimalsActivity extends AppCompatActivity {
    ImageView shop_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopanimals);
        getSupportActionBar().hide();
        shop_back = findViewById(R.id.shop_back);
        shop_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(shopanimalsActivity.this,HomeActivity5.class);
                startActivity(it);
            }
        });
        shop_back.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                   shop_back.setImageResource(R.drawable.baseline_keyboard_arrow_left_24_pressed);

                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP || motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {
                   shop_back.setImageResource(R.drawable.baseline_keyboard_arrow_left_24);

                }
                return false;
            }
        });
    }
}