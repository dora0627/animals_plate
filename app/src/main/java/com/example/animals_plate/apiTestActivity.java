package com.example.animals_plate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Network;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;


import com.example.animals_plate.spinner_animal_home.animals_Adapater;
import com.example.animals_plate.spinner_animal_home.spinner_animals_data;
import com.example.animals_plate.utils.NetworkUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class apiTestActivity extends AppCompatActivity {
    EditText showdata,skip,species;
    Button btnapidata;
    String cityName,topValue,skipValue,animalKind;
    TextView result;
    ImageView back_btn;
    private Spinner spinner;
    private com.example.animals_plate.spinner_animal_home.animals_Adapater animals_adapter;
    private BottomNavigationView bottomNavigationView;
    //othersfunctionFragment off = new othersfunctionFragment();

    //ChatFragment cf =new ChatFragment();

    //InfoFragment If = new InfoFragment();
    private static final String BASE_URL ="https://datausa.io/api/data?drilldowns=State&measures=Population&year=latest";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_test);
        //隱藏初始 action bar
        getSupportActionBar().hide();

        //bottom nav event
        bottom_nav_event();
        //spinner = findViewById(R.id.spinner_home);
        //animals_adapter =new animals_Adapater(apiTestActivity.this, spinner_animals_data.getAnimalsList());
        //spinner.setAdapter(animals_adapter);

        btnapidata = findViewById(R.id.getapi_btn);
        showdata  =findViewById(R.id.showapi_data);
        skip = findViewById(R.id.showapi_skipdata);
        species  =findViewById(R.id.showapi_speciesdata);
        result = findViewById(R.id.txt_result);
        btnapidata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // API url
                cityName = showdata.getText().toString();
                //topValue = showdata.getText().toString();
                //skipValue = skip.getText().toString();
                //animalKind =species.getText().toString();
                try {
                    getData(cityName);
                    //getData(topValue, skipValue,animalKind);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }


            }
        });
    }
    public void bottom_nav_event(){
        //選單事件
        bottomNavigationView = findViewById(R.id.bottom_navigation_api);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        Log.d("BottomNav", "Home clicked");
                        //getSupportFragmentManager().beginTransaction().replace(R.id.container, hf).commit();


                        return true;
                    case R.id.map:
                        Log.d("BottomNav", "map clicked");
                        // getSupportFragmentManager().beginTransaction().replace(R.id.container, mf).commit();
                        Intent itma = new Intent(apiTestActivity.this,MapanimalActivity.class);
                        itma.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(itma);
                        return true;
                    case R.id.chat:
                        //getSupportFragmentManager().beginTransaction().replace(R.id.container, cf).commit();
                        Intent itmc = new Intent(apiTestActivity.this,ChatActivity.class);
                        itmc.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(itmc);

                        return true;
                    case R.id.others:
                        //getSupportFragmentManager().beginTransaction().replace(R.id.container,off).commit();
                        Intent ito = new Intent(apiTestActivity.this,othersfunctionActivity.class);
                        ito.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(ito);
                        return true;
                    case R.id.info:
                        Intent iti = new Intent(apiTestActivity.this,InfoActivity.class);
                        iti.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(iti);

                        return true;
                    default:
                        return false;
                }
            }
        });

    }
    public void getData(String CityName ) throws MalformedURLException {
        Uri uri = Uri.parse("https://datausa.io/api/data?drilldowns=State&measures=Population&year=latest").buildUpon().build();
        URL url = new URL(uri.toString());
        new DoTask().execute(url);

    }

    class DoTask extends AsyncTask<URL,Void,String>{

        @Override
        protected String doInBackground(URL... urls) {
            URL url = urls[0];
            String data = null;
            try{
                data= NetworkUtils.makeHTTPRequest(url);
            }catch (IOException e){
                e.printStackTrace();
            }
            return  data;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                parseJson(s);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }

        public void parseJson(String data) throws JSONException {
            JSONObject jsonObject = null;
            try{
                jsonObject = new JSONObject(data);
            }catch (JSONException e){
                e.printStackTrace();
            }
            JSONArray cityArray = jsonObject.getJSONArray("data");

            for (int i=0;i<cityArray.length();i++){
                JSONObject cityo=cityArray.getJSONObject(i);
                //找到欄位為State 的資料
                String cityn = cityo.get("State").toString();
                //比較輸入的內容與欄位的內容是否相符
                if(cityn.equals(cityName)){
                    //找到欄位為Population 的資料
                    String population = cityo.get("Population").toString();
                    result.setText(population);
                    break;
                }
                else {
                    result.setText("Not found");
                }
            }
        }

    }
}