package com.example.animals_plate;

import androidx.appcompat.app.AppCompatActivity;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class testsqlActivity extends AppCompatActivity {
    EditText usernames,passwords;
    TextView sql_message;
    Button btn_ok;
    private static pgConnection con=new pgConnection();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testsql);
        usernames = findViewById(R.id.etusername);
        passwords= findViewById(R.id.etuserpassword);
        sql_message = findViewById(R.id.sql_message);
        btn_ok = findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = usernames.getText().toString();
                String password = passwords.getText().toString();
                String type = "login";
                BackgroundWorker backgroundWorker = new BackgroundWorker( testsqlActivity.this);
                backgroundWorker.execute(type, username, password);

            }
        });


    }


}