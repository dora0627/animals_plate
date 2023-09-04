package com.example.animals_plate.toolbar_home;
import com.example.animals_plate.AnimalData;
import com.example.animals_plate.Animalrecycler;
import com.example.animals_plate.recyclerset.recycler_adaper;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.animals_plate.Animal;
import com.example.animals_plate.ChatActivity;
import com.example.animals_plate.InfoActivity;
import com.example.animals_plate.MapanimalActivity;
import com.example.animals_plate.apiTestActivity;
import com.example.animals_plate.othersfunctionActivity;
import com.example.animals_plate.spinner_animal_home.spinner_animals_data;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.animals_plate.R;
import com.example.animals_plate.spinner_animal_home.animals_Adapater;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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
import java.util.Collection;
import java.util.List;

public class toolbar_homeActivity extends AppCompatActivity {
    private ImageView back_btn,search,exit;
    private Spinner select_spinner;
    private animals_Adapater adapater;
    private BottomNavigationView bottomNavigationView;
    private Switch aSwitch;
    // 初始化 SwipeRefreshLayout 和 RecyclerView
    SwipeRefreshLayout swipeRefreshLayout;

    //private ArrayList<Animalrecycler>animalList;
    List<Animal> animals = new ArrayList<>();
    private RecyclerView recyclerView;
    recycler_adaper adaper;
    String selectedAnimal = "all";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar_home);

        getSupportActionBar().hide();
        exit = findViewById(R.id.left_back_homes);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳出視窗詢問

                //離開
            }
        });
        exit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        exit.setImageResource(R.drawable.baseline_keyboard_arrow_left_24_pressed);
                        break;
                    case MotionEvent.ACTION_UP:
                        exit.setImageResource(R.drawable.baseline_keyboard_arrow_left_24);
                        break;
                }
                return false;
            }
        });

        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        // 設置刷新操作的監聽器
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 在這裡執行下拉刷新的操作，例如重新加載數據
                // 完成後，記得調用 setRefreshing(false) 停止刷新動畫
                refreshData();
            }
        });
        search  =findViewById(R.id.right_list);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 在點擊搜索按鈕時，獲取當前選單的選擇
                int selectedItemPosition = select_spinner.getSelectedItemPosition();

                // 根據選擇來更新 RecyclerView 的數據
                List<Animal> filteredAnimals = new ArrayList<>();
                switch (selectedItemPosition) {
                    case 0: // "all"
                        filteredAnimals.addAll(animals);

                        break;
                    case 1: // "bird"
                        for (Animal animal : animals) {
                            if ("bird".equals(animal.getSpecies())) {
                                filteredAnimals.add(animal);
                                //filteredAnimals.addAll((Collection<? extends Animal>) animal);
                            }
                        }
                        refreshData();
                        break;
                    case 2: // "dog"
                        for (Animal animal : animals) {
                            if ("dog".equals(animal.getSpecies())) {
                                filteredAnimals.add(animal);
                            }
                        }
                        refreshData();
                        break;
                    case 3: // "cat"
                        for (Animal animal : animals) {
                            if ("cat".equals(animal.getSpecies())) {
                                filteredAnimals.add(animal);
                            }
                        }
                        refreshData();
                        break;
                    // 添加其他選項的處理
                }

                // 更新 RecyclerView 的數據集，只顯示選擇的動物種類
                adaper.updateData(filteredAnimals);
                //adaper.notifyDataSetChanged();

            }
        });
        recyclerView = findViewById(R.id.toolbar_recycler);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    // 檢查是否已經滾動到底部
                    if (!recyclerView.canScrollVertically(1)) {
                        // 已經滾動到底部，執行您的操作，例如顯示提示框
                        showBottomReachedAlert();
                    }
                }

            }
        });
        animals = new ArrayList<>();


        setAdapters();
        // 執行網路請求
        new FetchAnimalsTask().execute();

        //犯歷城縣
        /*animals.add(new Animal("dog",121.0,56.0));
        animals.add(new Animal("cat",121.0,56.0));
        animals.add(new Animal("dog",121.0,56.0));
        animals.add(new Animal("cat",121.0,56.0));
        animals.add(new Animal("dog",121.0,56.0));
        animals.add(new Animal("cat",121.0,56.0));
        animals.add(new Animal("dog",121.0,56.0));
        animals.add(new Animal("cat",121.0,56.0));
        animals.add(new Animal("dog",121.0,56.0));
        animals.add(new Animal("cat",121.0,56.0));*/
        back_btn = findViewById(R.id.left_back_homes);
        //返回事件
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        select_spinner = findViewById(R.id.spinner_homes);
        adapater = new animals_Adapater(toolbar_homeActivity.this, spinner_animals_data.getAnimalsList());

        select_spinner.setAdapter(adapater);
        select_spinner.setSelection(0);
        /*select_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // 獲取選擇的動物種類
                switch (i) {

                    case 1:
                        selectedAnimal = "bird";
                        break;
                    case 2:
                        selectedAnimal = "dog";
                        break;
                    case 3:
                        selectedAnimal = "cat";
                        break;
                    case 0:
                        selectedAnimal="all";
                        break;
                    // 添加其他選項的處理

                }
                //String selectedAnimal = adapterView.getItemAtPosition(i).toString();

                // 過濾 animals 列表，只保留符合選擇的動物種類的項目
                List<Animal> filteredAnimals = new ArrayList<>();
                if(selectedAnimal=="all"){
                    filteredAnimals.addAll(animals);
                }
                else {
                    for (Animal animal : animals) {
                        if (animal.getSpecies().equals(selectedAnimal) ) {
                            filteredAnimals.add(animal);
                        }
                    }
                }

                // 更新 RecyclerView 的數據集，只顯示選擇的動物種類
                adaper.updateData(filteredAnimals);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                selectedAnimal="all";
            }
        });*/
        aSwitch = findViewById(R.id.aswitch);
        //開關
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    aSwitch.setThumbDrawable(getResources().getDrawable(R.drawable.thumbtrue));
                } else {
                    aSwitch.setThumbDrawable(getResources().getDrawable(R.drawable.thumbfalse));
                }
            }
        });
        //底部導覽列
        bottom_nav_event();

    }



    private void showBottomReachedAlert() {
        Toast.makeText(toolbar_homeActivity.this, "已經至最底部囉!", Toast.LENGTH_SHORT).show();
    }

    private class FetchAnimalsTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            String result = "";
            try {
                URL url = new URL("http://192.168.50.114/animaldata_get.php?");
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
            super.onPostExecute(jsonResult);
            try {
                JSONArray jsonArray = new JSONArray(jsonResult);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String name = jsonObject.getString("name");
                    String species = jsonObject.getString("species");
                    double longitude = jsonObject.getDouble("longitude");
                    double latitude = jsonObject.getDouble("latitude");
                    String uri = jsonObject.getString("image_url");
                    //將新數據插入到列表的開頭 (最新的在上面)
                    animals.add(0,new Animal(name, species, longitude, latitude,uri));
                }
                adaper.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void bottom_nav_event() {
        bottomNavigationView = findViewById(R.id.btn_nag_for_home);
        // 設定選擇的位置為 "home"
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:


                        return true;
                    case R.id.map:
                        Log.d("BottomNav", "map clicked");
                        // getSupportFragmentManager().beginTransaction().replace(R.id.container, mf).commit();
                        Intent itma = new Intent(toolbar_homeActivity.this, MapanimalActivity.class);

                        itma.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(itma);
                        return true;
                    case R.id.chat:
                        //getSupportFragmentManager().beginTransaction().replace(R.id.container, cf).commit();
                        Log.d("BottomNav", "map clicked");
                        // getSupportFragmentManager().beginTransaction().replace(R.id.container, mf).commit();
                        Intent itmc = new Intent(toolbar_homeActivity.this, ChatActivity.class);
                        itmc.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(itmc);

                        return true;
                    case R.id.others:
                        //getSupportFragmentManager().beginTransaction().replace(R.id.container,off).commit();
                        Intent ito = new Intent(toolbar_homeActivity.this, othersfunctionActivity.class);
                        ito.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(ito);
                        return true;
                    case R.id.info:
                        Intent iti = new Intent(toolbar_homeActivity.this, InfoActivity.class);
                        iti.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(iti);
                        //getSupportFragmentManager().beginTransaction().replace(R.id.container,If).commit();

                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    public void setAdapters() {
        adaper = new recycler_adaper(animals);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adaper);
        Log.d("Adapter", adaper.toString());
    }

    private void updateRecyclerView(String selectedAnimal) {
        List<Animal> filteredAnimals = new ArrayList<>();

        // 篩選出種類為 selectedAnimal 的動物
        for (Animal animal : animals) {
            if (animal.getSpecies().equalsIgnoreCase(selectedAnimal)) {
                filteredAnimals.add(animal);
            }
        }

        // 更新 RecyclerView
        adaper.setAnimalsList(filteredAnimals);
        adaper.notifyDataSetChanged();
    }

    // 創建一個方法用於下拉刷新操作
    private void refreshData() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 在這裡完成刷新操作，例如重新加載數據
                FetchAnimalsTask fetchAnimalsTask = new FetchAnimalsTask();
                fetchAnimalsTask.execute();

                adaper.notifyDataSetChanged();
                // 完成後，停止刷新動畫
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 2000); // 2秒延遲示例，實際中根據你的需求設定

    }
}