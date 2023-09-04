package com.example.animals_plate;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class NetworkManager extends AsyncTask<Void,Void,String> {
    UserData userData;
    Context context;
    String username = userData.username;
    private NetworkCallback callback;
    NetworkManager(Context  ctx) {
        context = ctx;
    }
    public NetworkManager(NetworkCallback callback, String username) {
        this.callback = callback;
        this.username = username;
    }



    @Override
    protected String doInBackground(Void... voids) {
        String result = "";
        try {
            URL url = new URL("http://192.168.50.114/dailyreport_get.php");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);

            String postData = "username=" + URLEncoder.encode(UserData.username, "UTF-8");

            OutputStream outputStream = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            writer.write(postData);
            writer.flush();
            writer.close();
            outputStream.close();
            connection.connect();

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                BufferedReader bufReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
                String line = null;
                while ((line = bufReader.readLine()) != null) {
                    result += line + "\n";
                }
                inputStream.close();
            }
        } catch (Exception e) {
            result = e.toString();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        if (callback != null) {
            callback.onNetworkComplete(result);
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
}
