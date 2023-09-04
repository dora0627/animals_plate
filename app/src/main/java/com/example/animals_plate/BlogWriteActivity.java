package com.example.animals_plate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class BlogWriteActivity extends AppCompatActivity {
    private ImageView back_blog;
    private TextView blogWrite_toolbarTitle;
    private FloatingActionButton fab_post;
    private EditText blogWrite_title,blogWrite_content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_write);
        getSupportActionBar().hide();
        blogWrite_toolbarTitle = findViewById(R.id.title_toolbar);
        blogWrite_toolbarTitle.setText("發表文章");
        back_blog = findViewById(R.id.leftback);
        back_blog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it =  new Intent(BlogWriteActivity.this,BlogActivity.class);
                startActivity(it);
            }
        });
        blogWrite_title = findViewById(R.id.blogWrite_for_title);
        blogWrite_content = findViewById(R.id.blogWrite_for_content);


        fab_post = findViewById(R.id.blogWrite_for_post);
        fab_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //傳入資料庫
                String title =  blogWrite_title.getText().toString();
                String content =   blogWrite_content.getText().toString();
                //連線至資料庫
                String type = "posts";
                BackgroundWorker backgroundWorker = new BackgroundWorker( BlogWriteActivity.this);
                backgroundWorker.execute(type,UserData.username ,title, content);
                //跳至blogActivity
            }
        });
    }
}