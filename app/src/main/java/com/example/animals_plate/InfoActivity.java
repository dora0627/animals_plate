package com.example.animals_plate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.animals_plate.backpack.BackpackActivity;
import com.example.animals_plate.toolbar_home.toolbar_homeActivity;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class InfoActivity extends AppCompatActivity {
    private static final int PICK_IMAGE = 1;
    private boolean isExpanded = false; //是否展開
    ImageView user_photos,info_mores,info_edits,info_shares;
    TextView txttest,userinfoname,userinfolv,userinfoxp,userinfophoto,userinfopoint,userinforeportcount,userinfocompleted,userinfoquantity;
    CardView info_shopcards;
    LinearLayout dataLayout;
    String result; // 儲存資料用的字串
    int points,report_count,tasks_completed,redemption_quantity;
    ShimmerFrameLayout shimmerFrameLayout;
    BottomNavigationView bottomNavigationView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        getSupportActionBar().hide();
        txttest = findViewById(R.id.txttest);
        // 從 UserData 類中讀取使用者名稱
        readUserData();
        //執行緒
        excuteThread(UserData.username);
        //bottom nav event
        bottom_nav_event();
        //骨架畫面
        skeleton_screen();
        //使用者資料




        dataLayout = findViewById(R.id.data_view);
        dataLayout.setVisibility(View.INVISIBLE);

        //功能列
        useFunction();

    }
    public void excuteThread(String name){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                mutiThread(name);
            }
        });
        thread.start();
    }
    private void mutiThread(String name){
        try {
            String query = "name=" + URLEncoder.encode(name, "UTF-8");
            Log.d("query name", "Name: " + name);
            Log.d("query name", "query: " + query);
            //URL url = new URL("http://192.168.137.1/user_info.php?" + query);
            URL url = new URL("http://192.168.50.114/user_info.php?" + query);

            // 開始宣告 HTTP 連線需要的物件，這邊通常都是一綑的
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // 建立 Google 比較挺的 HttpURLConnection 物件
            connection.setRequestMethod("GET");
            // 設定連線方式為 POST
            connection.setDoOutput(true); // 允許輸出
            connection.setDoInput(true); // 允許讀入
            connection.setUseCaches(false); // 不使用快取
            connection.connect(); // 開始連線

            int responseCode =
                    connection.getResponseCode();
            // 建立取得回應的物件
            if(responseCode ==
                    HttpURLConnection.HTTP_OK){
                // 如果 HTTP 回傳狀態是 OK ，而不是 Error
                InputStream inputStream =
                        connection.getInputStream();
                // 取得輸入串流
                BufferedReader bufReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
                // 讀取輸入串流的資料
                String box = ""; // 宣告存放用字串
                String line = null; // 宣告讀取用的字串
                while((line = bufReader.readLine()) != null) {
                    box += line + "\n";
                    // 每當讀取出一列，就加到存放字串後面
                }
                inputStream.close(); // 關閉輸入串流
                result = box; // 把存放用字串放到全域變數
            }
            JSONObject jsonObject = new JSONObject(result);
            //String id = jsonObject.getString("id");
            // String userName = jsonObject.getString("name");
            //String photo = jsonObject.getString("photo");
            points = Integer.parseInt(jsonObject.getString("points"));
            report_count = Integer.parseInt(jsonObject.getString("report_count"));
            tasks_completed = Integer.parseInt(jsonObject.getString("tasks_completed"));
            redemption_quantity= Integer.parseInt(jsonObject.getString("redemption_quantity"));
            //String report_count = jsonObject.getString("report_count");
            // String tasks_completed = jsonObject.getString("tasks_completed");
            //tring redemption_quantity = jsonObject.getString("redemption_quantity");



            // 更新 UserData 的 points 變數
            UserData.points = points;
            UserData.reportCount= report_count;
            UserData.tasksCompleted = tasks_completed;
            UserData.redemptionQuantity =redemption_quantity;
            Log.d("user data", "userinfopoint " +  points);
            // 讀取輸入串流並存到字串的部分
            // 取得資料後想用不同的格式
            // 例如 Json 等等，都是在這一段做處理

        }catch (Exception e){
            result = e.toString(); // 如果出事，回傳錯誤訊息
        }
        // 當這個執行緒完全跑完後執行
        runOnUiThread(new Runnable() {
            public void run() {

                userinfopoint.setText(String.valueOf(UserData.points));
                userinforeportcount.setText(String.valueOf(UserData.reportCount));
                userinfocompleted.setText(String.valueOf(UserData.tasksCompleted));
                userinfoquantity.setText(String.valueOf(UserData.redemptionQuantity));
                //txttest.setText(result);
                //textView.setText(result); // 更改顯示文字
            }
        });
    }

    public void bottom_nav_event(){
        bottomNavigationView = findViewById(R.id.btn_nag_info);
        // 設定選擇的位置為 "info"
        bottomNavigationView.setSelectedItemId(R.id.info);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        Log.d("BottomNav", "Home clicked");
                        //getSupportFragmentManager().beginTransaction().replace(R.id.container, hf).commit();
                        Intent itmh = new Intent(InfoActivity.this, toolbar_homeActivity.class);
                        itmh.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(itmh);


                        return true;
                    case R.id.map:
                        Log.d("BottomNav", "map clicked");
                        // getSupportFragmentManager().beginTransaction().replace(R.id.container, mf).commit();
                        Intent itma = new Intent(InfoActivity.this,MapanimalActivity.class);
                        itma.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(itma);
                        return true;
                    case R.id.chat:
                        //getSupportFragmentManager().beginTransaction().replace(R.id.container, cf).commit();
                        Intent itc = new Intent(InfoActivity.this,ChatActivity.class);
                        itc.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(itc);

                        return true;
                    case R.id.others:
                        //getSupportFragmentManager().beginTransaction().replace(R.id.container,off).commit();
                        Intent iti = new Intent(InfoActivity.this,othersfunctionActivity.class);
                        iti.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(iti);
                        return true;
                    case R.id.info:

                        //getSupportFragmentManager().beginTransaction().replace(R.id.container,If).commit();

                        return true;
                    default:
                        return false;
                }
            }
        });
    }
    public void skeleton_screen(){
        //骨架視窗
        shimmerFrameLayout = findViewById(R.id.shimmer_view);
        shimmerFrameLayout.startShimmerAnimation();


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dataLayout.setVisibility(View.VISIBLE);
                shimmerFrameLayout.stopShimmerAnimation();
                shimmerFrameLayout.setVisibility(View.INVISIBLE);
            }
        },1000);
    }
    public void readUserData(){
        userinfoname = findViewById(R.id.userinfo_name);
        userinfolv = findViewById(R.id.userinfo_level);
        user_photos = findViewById(R.id.userinfophoto);
        userinfopoint = findViewById(R.id.credit);
        userinforeportcount = findViewById(R.id.userinfo_report_count);
        userinfocompleted = findViewById(R.id.userinfo_tasks_completed);
        userinfoquantity = findViewById(R.id.userinfo_redemption_quantity);

        String username = UserData.username;
        String level = UserData.level;
        String photo = UserData.photo;
        String experience = UserData.experience;
        int points = UserData.points;
        int reportCount = UserData.reportCount;
        int tasksCompleted = UserData.tasksCompleted;
        int redemptionQuantity = UserData.redemptionQuantity;
        // 使用使用者名稱顯示相應的內容
        userinfoname.setText(username);

        //userinfolv.setText("LV"+level);
        //userinfopoint.setText(String.valueOf(points));
        //userinforeportcount.setText(String.valueOf(reportCount));
        //userinfocompleted.setText(String.valueOf(tasksCompleted));
        //userinfoquantity.setText(String.valueOf(redemptionQuantity));
    }
    public void useFunction(){
        info_mores = findViewById(R.id.info_more);
        info_edits = findViewById(R.id.info_edit_activity);
        info_shares = findViewById(R.id.info_share_activity);

        info_shares.setVisibility(View.INVISIBLE);
        info_edits.setVisibility(View.INVISIBLE);
        info_shopcards = findViewById(R.id.info_card_shop);
        info_shopcards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(InfoActivity.this,shopanimalsActivity.class);
                startActivity(it);
            }
        });
        info_mores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isExpanded){
                    info_shares.setVisibility(View.INVISIBLE);
                    info_edits.setVisibility(View.INVISIBLE);
                }else{
                    info_shares.setVisibility(View.VISIBLE);
                    info_edits.setVisibility(View.VISIBLE);
                }
                isExpanded = !isExpanded;

            }
        });
        info_edits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //開啟背包
                Intent it = new Intent(InfoActivity.this, BackpackActivity.class);
                startActivity(it);
            }
        });
        //編輯紐事件
        info_edits.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (view.getId()){
                    case MotionEvent.ACTION_DOWN:
                        info_edits.setBackgroundResource(R.drawable.info_circlebg_click);
                        break;
                    case MotionEvent.ACTION_UP:
                        info_edits.setBackgroundResource(R.drawable.info_circlebg);
                        break;
                }

                return false;
            }
        });
    }
}