package com.example.animals_plate;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BackgroundWorker_foranimal extends AsyncTask<String,Void, List<Animal>> {
    Context context;
    List<Animal> animals = new ArrayList<>();
    BackgroundWorker_foranimal (Context ctx) {
        context = ctx;
    }
    @Override
    protected List<Animal> doInBackground(String... params) {
        // 發送GET請求,解析JSON

        String type = params[0];
        try {
            if(type.equals("load_animals")){
                animals = loadAnimalsFromApi();

            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return animals;
    }

    @Override
    protected void onPostExecute(List<Animal> animals) {
        //super.onPostExecute(animals);

    }
    private List<Animal> loadAnimalsFromApi() throws JSONException {
        List<Animal> animals = new ArrayList<>();

        // 发起网络请求获取JSON字符串
        String json = sendApiRequest();

        // 解析JSON
        JSONArray array = new JSONArray(json);
        for(int i = 0; i < array.length(); i++) {

            JSONObject obj = array.getJSONObject(i);
            String name = obj.getString("name");
            String species = obj.getString("species");
            double lat = obj.getDouble("latitude");
            double lon = obj.getDouble("longitude");
            String uri = obj.getString("uri");
            Animal animal = new Animal(name,species,lat,lon,uri);
            animal.id = obj.getInt("id");
            animal.name = obj.getString("name");
            animal.species = obj.getString("species");
            animal.latitude = obj.getDouble("latitude");
            animal.longitude = obj.getDouble("longitude");
            animal.uri=obj.getString("image_url");
            //...解析其他属性

            animals.add(animal);
        }

        return animals;
    }
    private String sendApiRequest() {
        HttpURLConnection conn = null;
        try {
            //URL url = new URL("http://192.168.137.1/show_animaldata.php");
            URL url = new URL("http://192.168.50.114/show_animaldata.php");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // 處理響應碼等

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null) {
                sb.append(line);
            }

            return sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(conn != null) {
                conn.disconnect();
            }
        }
        return "";
    }

}

