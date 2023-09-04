package com.example.animals_plate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.animals_plate.recyclerset.recyclerBlog_adaper;
import com.example.animals_plate.recyclerset.recycler_adaper;
import com.example.animals_plate.toolbar_home.toolbar_homeActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BlogActivity extends AppCompatActivity {
    private TextView blogBigTitle;
    private FloatingActionButton blogPostFab;
    private RecyclerView blogRecycler;
    private ImageView blogBack;
    recyclerBlog_adaper adaper;
    SwipeRefreshLayout swipeRefreshLayout;
    List<Post_new> postlist = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog);
        getSupportActionBar().hide();
        swipeRefreshLayout = findViewById(R.id.swipeRefresh_blog);
        // 設置刷新操作的監聽器
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 在這裡執行下拉刷新的操作，例如重新加載數據
                // 完成後，記得調用 setRefreshing(false) 停止刷新動畫
                refreshData();
            }
        });
        blogBack = findViewById(R.id.leftback);
        blogBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //返回儀錶板
                Intent it = new Intent(BlogActivity.this, othersfunctionActivity.class);
                startActivity(it);
            }
        });
        blogBigTitle = findViewById(R.id.title_toolbar);
        blogBigTitle.setText("動物論壇");
        blogRecycler  =findViewById(R.id.blogRecycler);
        blogPostFab = findViewById(R.id.blogPostFab);
        blogPostFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //至撰寫頁撰寫
                Log.d("BlogActivity", "FloatingActionButton clicked");
                Intent it = new Intent(BlogActivity.this,BlogWriteActivity.class);
                startActivity(it);
            }
        });
        postlist = new ArrayList<>();
        setAdapters();
        // 執行網路請求
        new FetchAnimalsTask().execute();
    }
    public void setAdapters() {
        blogRecycler = findViewById(R.id.blogRecycler);
        adaper = new recyclerBlog_adaper(postlist);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        blogRecycler.setLayoutManager(layoutManager);
        blogRecycler.setItemAnimator(new DefaultItemAnimator());
        blogRecycler.setAdapter(adaper);
        Log.d("Adapter", adaper.toString());
        // adaper = new recycler_adaper(animals);
       // RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
       // recyclerView.setLayoutManager(layoutManager);
       // recyclerView.setItemAnimator(new DefaultItemAnimator());
      //  recyclerView.setAdapter(adaper);
      //  Log.d("Adapter", adaper.toString());
    }
    private class FetchAnimalsTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            String result = "";
            try {
                URL url = new URL("http://192.168.50.114/posts_get.php?");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    result += line;
                }
                reader.close();
                inputStream.close();
                connection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String jsonResult) {
            try {
                JSONArray jsonArray = new JSONArray(jsonResult);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String author_name = jsonObject.getString("author_name");
                    String title = jsonObject.getString("title");
                    String content =jsonObject.getString("content");
                    String posttime =jsonObject.getString("posttime");
                    //將新數據插入到列表的開頭 (最新的在上面)
                    postlist.add(0, new Post_new(author_name, title, content, posttime));
                }
                adaper.notifyDataSetChanged();
            }catch (JSONException e){
                e.printStackTrace();
            }
        }

        @Override
        protected void onPreExecute() {

        }
    }
    private void refreshData() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 如果沒有新文章，顯示 Toast 訊息
                if (postlist.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "目前沒有新文章!", Toast.LENGTH_SHORT).show();
                }else  {
                    // 清空舊數據
                    postlist.clear();
                    adaper.notifyDataSetChanged();
                    // 在這裡完成刷新操作，例如重新加載數據
                    BlogActivity.FetchAnimalsTask fetchAnimalsTask = new  BlogActivity.FetchAnimalsTask();
                    fetchAnimalsTask.execute();
                }

                adaper.notifyDataSetChanged();
                // 完成後，停止刷新動畫
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 2000); // 2秒延遲示例，實際中根據你的需求設定

    }

}