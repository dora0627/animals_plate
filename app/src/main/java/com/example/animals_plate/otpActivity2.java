package com.example.animals_plate;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class otpActivity2 extends AppCompatActivity {
    ImageView otp2_img_back;
    TextView otp_mail,otp_account;
    EditText edt_1,edt_2,edt_3,edt_4;
    Button otp_check;
    private EditText[] editTexts;
    private int currentEditTextIndex = 0;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp2);
        getSupportActionBar().hide();

        otp_mail = findViewById(R.id.otp_showMail);
        otp_mail.setText(com.example.animals_plate.otp_mail.email);
        otp_account = findViewById(R.id.otp_showAccount);
        otp_account.setText(com.example.animals_plate.otp_mail.username);

        //otp default_set
        edt_1 = findViewById(R.id.edt1);
        edt_2 = findViewById(R.id.edt2);
        edt_3 = findViewById(R.id.edt3);
        edt_4 = findViewById(R.id.edt4);
        // Create an array of EditTexts
        editTexts = new EditText[]{edt_1, edt_2, edt_3, edt_4};

        setupEditTexts();
        //讀資料庫

        //有於註冊找到 =>新增

        //驗證送出

        //典籍按鈕 =>更新
        otp_check = findViewById(R.id.btn_otp_check);
        otp_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String your_otp = edt_1.getText().toString() + edt_2.getText().toString()+edt_3.getText().toString()+edt_4.getText().toString();
                //判斷資料庫 的otp 與輸入是否相符
            }
        });
        otp2_img_back = findViewById(R.id.otp2_img_back);
        otp2_img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(otpActivity2.this,otpActivity.class));
            }
        });
    }
    private void setupEditTexts() {
        for (int i = 0; i < editTexts.length; i++) {
            final EditText editText = editTexts[i];

            // Set input type to number and limit length to 1
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
            editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});

            // Add TextWatcher to each EditText
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if (editable.length() == 1) {
                        moveToNextEditText();
                    } else if (editable.length() == 0 && currentEditTextIndex > 0) {
                        // Delete character and move to previous EditText
                        currentEditTextIndex--;
                        editTexts[currentEditTextIndex].setText("");
                        editTexts[currentEditTextIndex].requestFocus();
                    }
                }
            });
        }
    }

    private void moveToNextEditText() {
        if (currentEditTextIndex < editTexts.length - 1) {
            currentEditTextIndex++;
            editTexts[currentEditTextIndex].requestFocus();
        }
    }
}