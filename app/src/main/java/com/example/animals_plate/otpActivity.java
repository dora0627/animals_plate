package com.example.animals_plate;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import api_server.ServerAPI;

public class otpActivity extends AppCompatActivity {
    ImageView otp_imgback;
    Button otp_btn_next;
    EditText edt_mail,edt_account;
    String enter_mail,enter_name;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        //銀藏預設action bar
        getSupportActionBar().hide();
        otp_imgback = findViewById(R.id.otp_img_back);
        otp_imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(otpActivity.this,LoginActivity2.class));
            }
        });
        edt_mail = findViewById(R.id.otp_edtmail);
        edt_account = findViewById(R.id.otp_edtaccount);
        otp_btn_next = findViewById(R.id.otp_btn_next);
        //初始預設
        otp_btn_next.setClickable(false);
        otp_btn_next.setEnabled(false);
        //edit輸入事件
        edt_account.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkFieldsForEmptyValues();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edt_mail.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkFieldsForEmptyValues();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        otp_btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enter_mail = edt_mail.getText().toString();
                enter_name = edt_account.getText().toString();
                otp_mail.email = enter_mail; // 將輸入的 email 存入 otp_mail 類別中
                otp_mail.username =enter_name;
                //先讀資料庫是否有這個mail
                // 檢查帳號和 email 是否存在
                ServerAPI.checkIfAccountEmailExists(enter_name, enter_mail, new ServerAPI.CheckResultCallback() {
                    @Override
                    public void onCheckComplete(boolean exists) {
                        if (exists) {
                            //資料庫新增
                            //ServerAPI.insertOTPRecord();
                            // 帳號和 email 存在，進行後續動作
                            startActivity(new Intent(otpActivity.this, otpActivity2.class));
                        } else {
                            // 帳號和 email 不存在，顯示提示視窗
                            AlertDialog.Builder builder = new AlertDialog.Builder(otpActivity.this);
                            builder.setTitle("提示");
                            builder.setMessage("帳號和 email 不存在。");
                            builder.setPositiveButton("確定", null);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    }
                });
               //startActivity(new Intent(otpActivity.this,otpActivity2.class));
            }
        });

    }
    // 檢查輸入框的內容，根據情況設定按鈕的狀態
    private void checkFieldsForEmptyValues() {
        String enteredMail = edt_mail.getText().toString();
        String enteredAccount = edt_account.getText().toString();

        if (!enteredMail.isEmpty() && !enteredAccount.isEmpty()) {
            otp_btn_next.setEnabled(true);
        } else {
            otp_btn_next.setEnabled(false);
        }
    }
}