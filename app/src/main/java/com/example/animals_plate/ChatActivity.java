package com.example.animals_plate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.animals_plate.toolbar_home.toolbar_homeActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class ChatActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    private LinearLayout messageContainer;
    private  WebSocket webSocket;
    private MessageAdapter adapter;
    private ListView messageList;
    private EditText inputEditText;
    private Button sendButton;
    private ScrollView scrollView;
    private static final String WS_URL = "ws://192.168.50.114:80"; // 替換為你的 WebSocket 伺服器的 URL

    private WebSocketClient webSocketClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getSupportActionBar().hide();
        // 創建並啟動 MyWebSocketServer
        //int port = 8888;
        //MyWebSocketServer server = new MyWebSocketServer(new InetSocketAddress(port));
        //server.start();

        //initWebSocket();
        //bottom nav event
        bottom_nav_event();
        messageList = findViewById(R.id.messagelist);
        //scrollView  =findViewById(R.id.scrollview_chatroom);
        //scrollView  =findViewById(R.id.chat_scroll);

        //messageContainer = findViewById(R.id.messageContainer);
        inputEditText = findViewById(R.id.edit_message);

        instantiateWebSocket();
        adapter = new MessageAdapter();
        messageList.setAdapter(adapter);

        sendButton  =findViewById(R.id.btn_send);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 獲取輸入的訊息並發送
                String message = inputEditText.getText().toString().trim();
                if (!TextUtils.isEmpty(message)) {
                    webSocket.send(message);
                    inputEditText.setText(""); // 清空輸入框
                    //sendMessage(message);
                    JSONObject jsonObject = new JSONObject();
                    try{
                        jsonObject.put("message",message);
                        jsonObject.put("byServer",false);
                        adapter.addItem(jsonObject);

                    }catch (JSONException e){
                        e.printStackTrace();
                    }

                }
            }
        });

    }
    private void instantiateWebSocket(){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("ws://192.168.50.114:8080").build();
        SocketListener socketListener = new SocketListener(this);
        webSocket = client.newWebSocket(request,socketListener);
    }
    public class SocketListener extends WebSocketListener{
        public ChatActivity chatActivity;
        public SocketListener(ChatActivity chatActivity){
            this.chatActivity = chatActivity;
        }

        @Override
        public void onOpen(@NonNull WebSocket webSocket, @NonNull Response response) {
            super.onOpen(webSocket, response);
            chatActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(chatActivity,"連線建立",Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onMessage(@NonNull WebSocket webSocket, @NonNull String text) {
            super.onMessage(webSocket, text);
            chatActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject jsonObject = new JSONObject();
                    try{
                        jsonObject.put("message",text);
                        jsonObject.put("byServer",true);
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            });
        }


        @Override
        public void onClosing(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
            super.onClosing(webSocket, code, reason);
        }

        @Override
        public void onClosed(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
            super.onClosed(webSocket, code, reason);
        }

        @Override
        public void onFailure(@NonNull WebSocket webSocket, @NonNull Throwable t, @NonNull Response response) {
            super.onFailure(webSocket, t, response);
        }
    }
    public class MessageAdapter extends BaseAdapter{
        List<JSONObject> messageList = new ArrayList<>();
        @Override
        public int getCount() {
            return messageList.size();
        }

        @Override
        public Object getItem(int i) {
            return messageList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            if(view==null)
                view=getLayoutInflater().inflate(R.layout.message_list_item,viewGroup,false);
            TextView sendMessage = view.findViewById(R.id.messageTextView_self);
            TextView receiveMessage = view.findViewById(R.id.messageTextView);
            TextView user_self = view.findViewById(R.id.usernameTextView_self);
            TextView current_self = view.findViewById(R.id.timeTextView_self);
            TextView currenttime = view.findViewById(R.id.timeTextView);
            ScrollView chat_scroll = view.findViewById(R.id.chat_scroll);
            chat_scroll.fullScroll(View.FOCUS_DOWN);

            user_self.setText(UserData.username);
            current_self.setText(getCurrentTime());

            JSONObject item = messageList.get(i);

            try{
                if(item.getBoolean("byServer")){
                    receiveMessage.setVisibility(View.VISIBLE);
                    receiveMessage.setText(item.getString("message"));
                    sendMessage.setVisibility(View.INVISIBLE);
                }
                else {
                    sendMessage.setVisibility(View.VISIBLE);
                    sendMessage.setText(item.getString("message"));
                    receiveMessage.setVisibility(View.INVISIBLE);
                }
            }catch (JSONException e){
                e.printStackTrace();
            }

            return view;
        }
        void addItem(JSONObject item)
        {
            messageList.add(item);
            notifyDataSetChanged();
        }

    }
    private void initWebSocket() {
       WebSocketListener listener = new WebSocketListener() {
           @Override
           public void onOpen(@NonNull WebSocket webSocket,@NonNull okhttp3.Response response) {
               super.onOpen(webSocket, response);
               Log.d("WebSocket", "Connection opened");
               boolean success = webSocket.send("Hello from Android client!");
               if (success) {
                   Log.d("WebSocket", "Message sent successfully");
               } else {
                   Log.d("WebSocket", "Failed to send message");
               }
               // 連接成功
               //webSocket.send("Hello from Android client!");
           }

           @Override
           public void onMessage(@NonNull WebSocket webSocket, @NonNull String text) {
               super.onMessage(webSocket, text);
               runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       displayReceivedMessage(text); // 更新 UI 元素
                   }
               });
           }

           @Override
           public void onClosed(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
               super.onClosed(webSocket, code, reason);
               // 連接關閉
           }
       };
        webSocketClient = new WebSocketClient(WS_URL, listener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webSocketClient != null) {
            webSocketClient.closeConnection();
        }
    }

    public void bottom_nav_event(){
        bottomNavigationView = findViewById(R.id.btn_nag_chat);
        // 設定選擇的位置為 "Chat"
        bottomNavigationView.setSelectedItemId(R.id.chat);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        Log.d("BottomNav", "Home clicked");
                        //getSupportFragmentManager().beginTransaction().replace(R.id.container, hf).commit();
                        Intent itmh = new Intent(ChatActivity.this, toolbar_homeActivity.class);
                        itmh.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(itmh);


                        return true;
                    case R.id.map:
                        Log.d("BottomNav", "map clicked");
                        // getSupportFragmentManager().beginTransaction().replace(R.id.container, mf).commit();
                        Intent itma = new Intent(ChatActivity.this,MapanimalActivity.class);
                        itma.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(itma);
                        return true;
                    case R.id.chat:
                        //getSupportFragmentManager().beginTransaction().replace(R.id.container, cf).commit();


                        return true;
                    case R.id.others:
                        //getSupportFragmentManager().beginTransaction().replace(R.id.container,off).commit();
                        Intent ito = new Intent(ChatActivity.this,othersfunctionActivity.class);
                        ito.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(ito);
                        return true;
                    case R.id.info:
                        Intent iti = new Intent(ChatActivity.this,InfoActivity.class);
                        iti.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(iti);
                        //getSupportFragmentManager().beginTransaction().replace(R.id.container,If).commit();

                        return true;
                    default:
                        return false;
                }
            }
        });
    }
    private void sendMessage(String message) {
        String username = UserData.username;
        String currentTime = getCurrentTime();
        // 合併發送者名稱和訊息成一個格式
        String combinedMessage = "發送者: "+username + ": \n" + message+"\n"+currentTime;
        // 將LinearLayout靠右對齊

        // 創建包裝聊天方塊的LinearLayout
        LinearLayout chatBox = new LinearLayout(this);
        chatBox.setOrientation(LinearLayout.VERTICAL);

        chatBox.setBackgroundResource(R.drawable.chat_box_background); // chat_box_background 是自訂的 Drawable 資源檔案
        chatBox.setPadding(16, 8, 16, 8);

        // 創建顯示username的TextView
        TextView usernameTextView = new TextView(this);
        usernameTextView.setTextSize(18);
        usernameTextView.setText(username);
        usernameTextView.setGravity(Gravity.END);
        // 設定username文字顏色
        usernameTextView.setTextColor(Color.RED);

        // 創建顯示message的TextView
        TextView messageTextView = new TextView(this);
        messageTextView.setTextSize(22);
        messageTextView.setText(message);
        messageTextView.setTypeface(null, Typeface.BOLD); // 設定文字粗體
        messageTextView.setGravity(Gravity.END);
        messageTextView.setTextColor(Color.BLACK);
        // 創建顯示currentTime的TextView
        TextView timeTextView = new TextView(this);
        timeTextView.setTextSize(14);
        timeTextView.setText(currentTime);
        timeTextView.setGravity(Gravity.END);
        timeTextView.setTextColor(Color.BLACK);
        // 將usernameTextView添加到chatBox中
        chatBox.addView(usernameTextView);

        // 將messageTextView添加到chatBox中
        chatBox.addView(messageTextView);

        // 將timeTextView添加到chatBox中
        chatBox.addView(timeTextView);

        // 將LinearLayout添加到messageContainer中
        messageContainer.addView(chatBox);

        // 創建間隔View
        View spacer = new View(this);
        int spacing = 20; // 在dimens.xml中定義間隔大小
        spacer.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, spacing));

        // 將間隔View添加到messageContainer中
        messageContainer.addView(spacer);
        // 滾動到最新訊息處
        messageContainer.post(new Runnable() {
            @Override
            public void run() {
                // 使用smoothScrollTo()可實現平滑滾動
                // 如果要直接滾動到最下方，可以使用scrollTo()
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });
        // 使用 WebSocketClient 發送訊息
        webSocketClient.sendMessage(combinedMessage);
        //連線至資料庫
        String type = "send";
        BackgroundWorker_forchat backgroundWorker_forchat = new BackgroundWorker_forchat(ChatActivity.this);
        backgroundWorker_forchat.execute(type, UserData.username, message);
    }
    private void displayReceivedMessage(String message) {
        // 創建包裝聊天方塊的LinearLayout
        LinearLayout chatBox = new LinearLayout(this);
        chatBox.setOrientation(LinearLayout.VERTICAL);

        chatBox.setBackgroundResource(R.drawable.chat_box_background); // chat_box_background 是自訂的 Drawable 資源檔案
        chatBox.setPadding(16, 8, 16, 8);

        // 創建顯示message的TextView
        TextView messageTextView = new TextView(this);
        messageTextView.setTextSize(22);
        messageTextView.setText(message);
        messageTextView.setTypeface(null, Typeface.BOLD); // 設定文字粗體
        messageTextView.setGravity(Gravity.START); // 這裡修改成靠左對齊
        messageTextView.setTextColor(Color.BLACK);

        // 將messageTextView添加到chatBox中
        chatBox.addView(messageTextView);

        // 將LinearLayout添加到messageContainer中
        messageContainer.addView(chatBox);

        // 創建間隔View
        View spacer = new View(this);
        int spacing = 20; // 在dimens.xml中定義間隔大小
        spacer.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, spacing));

        // 將間隔View添加到messageContainer中
        messageContainer.addView(spacer);

        // 滾動到最新訊息處
        messageContainer.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });

    }
    // 獲取當前時間的方法
    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.TAIWAN);
        return sdf.format(new Date());
    }
}