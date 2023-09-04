package com.example.animals_plate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class recordCheckActivity extends AppCompatActivity {
    private Button recordCheck_changePasswordButton,recordCheck_changeMailButton;
    private LinearLayout line1,line2;
    private ImageView leftBack;
    private TextView title_toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_check);
        getSupportActionBar().hide();
        initialSetting();
        selctButtonClicker();
    }
    public void initialSetting(){
        leftBack = findViewById(R.id.leftback);
        leftBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it= new Intent(recordCheckActivity.this, othersfunctionActivity.class);
                startActivity(it);
            }
        });
        title_toolbar = findViewById(R.id.title_toolbar);
        title_toolbar.setText("帳號變更");


    }
    public void selctButtonClicker(){
        recordCheck_changePasswordButton = findViewById(R.id.recordCheck_changePasswordButton);
        recordCheck_changeMailButton = findViewById(R.id.recordCheck_changeMailButton);
        line1 = findViewById(R.id.recordCheck_Linear1);
        line2 = findViewById(R.id.recordCheck_Linear2);
        //點擊選擇顯示
        recordCheck_changeMailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //顯示和隱藏
                line2.setVisibility(View.VISIBLE);
                line1.setVisibility(View.GONE);
            }
        });
        recordCheck_changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //顯示和隱藏
                line1.setVisibility(View.VISIBLE);
                line2.setVisibility(View.INVISIBLE);
            }
        });

    }
}