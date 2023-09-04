package com.example.animals_plate;

import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class WebSocketClient {
    private final WebSocket webSocket;

    public WebSocketClient(String url, WebSocketListener listener) {
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().url(url).build();
        webSocket = client.newWebSocket(request,listener);

    }
    public void sendMessage(String message) {
        webSocket.send(message);
        Log.d("WebSocket", "Received message: " + message);
    }

    public void closeConnection() {
        webSocket.close(1000, "Connection closed");
    }
}
