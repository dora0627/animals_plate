package com.example.animals_plate;

import android.util.Log;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class MyWebSocketServer extends WebSocketServer {
    private ConcurrentHashMap<WebSocket, String> clients = new ConcurrentHashMap<>();
    public MyWebSocketServer(InetSocketAddress address){
        super(address);
    }
    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        System.out.println("New connection opened: " + conn.getRemoteSocketAddress());
        clients.put(conn, conn.getRemoteSocketAddress().toString());
        Log.d("WebSocket", "clients: " + clients);
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println("Connection closed: " + conn.getRemoteSocketAddress());
        clients.remove(conn);
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println("Received message: \n" + message + " from " + conn.getRemoteSocketAddress());
        // 廣播訊息給所有客戶端
        Broadcast(message);
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        ex.printStackTrace();
    }

    @Override
    public void onStart() {

    }

    // 廣播訊息給所有客戶端
    private void Broadcast(String message) {
        Collection<WebSocket> clientSockets = clients.keySet();
        synchronized (clientSockets) {
            for (WebSocket clientSocket : clientSockets) {
                clientSocket.send(message);
            }
        }
    }



}
