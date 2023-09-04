package com.example.animals_plate;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class SignActivity3 extends AppCompatActivity {
    ImageView ab_back;
    Button btnsign,btnchk;
    EditText edacc,edpas,edcps,edmai;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 禁用返回鍵
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign3);
        setTitle("Sign/註冊");
        //銀藏預設action bar
        getSupportActionBar().hide();
        edacc = findViewById(R.id.editTextUsername_sign);
        edpas = findViewById(R.id.editTextPassword_sign);
        edcps = findViewById(R.id.epditTextUserphone_sign);
        edmai = findViewById(R.id.epditTextUsermail_sign);
        btnsign = findViewById(R.id.buttonRegister_sign);
        btnsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnclicker();
            }
        });
        //btn click

        //action bar back click
        navbar_btnclicker();



    }
    public void btnclicker(){
        String name = edacc.getText().toString();
        String password = edpas.getText().toString();
        String email = edmai.getText().toString();
        String phone = edcps.getText().toString();
        String type = "register";
       /* BottomSheetDialog bottomSheetDialog =new BottomSheetDialog(SignActivity3.this,R.style.BottomSheetDialogTheme);
        View bottomsheet = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_bottom_sheet_design,(LinearLayout)findViewById(R.id.bottomSheetContainer));
        bottomSheetDialog.setContentView(bottomsheet);
        bottomSheetDialog.show();*/
        BackgroundWorker backgroundWorker = new BackgroundWorker( SignActivity3.this);
        backgroundWorker.execute(type, name, password,email,phone);

        //btnsign = findViewById(R.id.btn_for_sign);
        //btnsign.setEnabled(false);
        //btnchk = findViewById(R.id.btn_for_chk);
        /*btnchk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判斷密碼與確認密碼是否相符
                String str1 = edpas.getText().toString();
                String str2 = edcps.getText().toString();
                if (str1.equals(str2)) {
                    // 內容相同，您可以在這裏添加相應的邏輯
                    Toast.makeText(SignActivity3.this, "內容相同", Toast.LENGTH_SHORT).show();

                } else {
                    // 內容不同，您可以在這裏添加相應的邏輯
                    Toast.makeText(SignActivity3.this, "內容不同", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //未註冊過
                AlertDialog.Builder ad = new AlertDialog.Builder(SignActivity3.this);
                ad.setTitle("註冊成功!!");
                ad.setIcon(getDrawable(R.drawable.baseline_info_success_24));
                ad.setMessage("點擊確定跳轉至登入頁面");
                ad.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent it = new Intent(SignActivity3.this,LoginActivity2.class);
                        startActivity(it);
                    }
                }).show();
            }
        });*/
    }
    @SuppressLint("WrongViewCast")
    public void navbar_btnclicker(){
        ab_back  =findViewById(R.id.img_backbtn);
        ab_back.setClickable(true);
        ab_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itb = new Intent(SignActivity3.this,MainActivity.class);
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
}