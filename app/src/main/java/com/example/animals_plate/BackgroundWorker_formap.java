package com.example.animals_plate;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class BackgroundWorker_formap extends AsyncTask<String,Void,String> {
    Context context;
    String result;
    BackgroundWorker_formap (Context ctx) {
        context = ctx;
    }
    @Override
    protected String doInBackground(String... params) {

        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        if (result != null && !result.isEmpty()) {
            if(result.equals("Message sent successfully!")){
                Log.d("send", "status: 成功" );
            }else{
                Log.d("send", "status: 失敗" );
            }

        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
    private ArrayList<AnimalData> parseJsonData(String jsonData) {
        ArrayList<AnimalData> animalDataList = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                int id = jsonObject.getInt("id");
                Log.d("jsonArray.length", "jsonArray.length: " +  jsonArray);
                String name = jsonObject.getString("name");
                String species = jsonObject.getString("species");
                String photo = jsonObject.getString("photo");
                String reportDate = jsonObject.getString("report_date");
                String longitude = jsonObject.getString("longitude");
                String latitude = jsonObject.getString("latitude");
                String reporterName = jsonObject.getString("reporter_name");
                String uri_s = jsonObject.getString("image_url");
                AnimalData animalData = new AnimalData(id, name, species, photo, reportDate, longitude, latitude, reporterName,uri_s);
                animalDataList.add(animalData);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("JSON Parse Error", "Error parsing JSON data: " + jsonData);
        }
        Log.d("showAnimalDataOnMap", "Animal Data List Size: " + animalDataList.size());
        return animalDataList;
    }
}
