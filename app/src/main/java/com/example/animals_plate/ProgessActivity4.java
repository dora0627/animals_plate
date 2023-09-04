package com.example.animals_plate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class ProgessActivity4 extends AppCompatActivity {
    ProgressBar pb;
    TextView ts;
    int cnt=0;

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
        setContentView(R.layout.activity_progess4);
        //消除 ActionBar
        getSupportActionBar().hide();
        prog();
    }
    private void prog(){
        pb = (ProgressBar) findViewById(R.id.pb);
        ts = (TextView) findViewById(R.id.txtfont);
        Timer t =new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                cnt++;
                pb.setProgress(cnt);

                if(cnt==100){
                    t.cancel();
                    Intent it =new Intent(ProgessActivity4.this,HomeActivity5.class);
                     startActivity(it);
                }
            }
        };
        t.schedule(tt,0,100);

    }
}