package com.example.animals_plate;
import com.example.animals_plate.AnimalData;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.Manifest;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.animals_plate.toolbar_home.toolbar_homeActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.api.IGeoPoint;
import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapController;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.gestures.RotationGestureDetector;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MapanimalActivity extends AppCompatActivity implements LocationListener, NetworkCallback {
    private MapView mapView;
    private Uri selectedImageUri;
    // 伺服器上存儲圖片的基本 URL
    String newPhotoImg = "";
    String filePath="";
    String serverBaseUrl = "http://192.168.50.114/img_animal/";
    private Handler handler = new Handler();
    private Runnable updateTimeRunnable;
    private ActivityResultLauncher<Intent> resultLauncher;
    private SearchView searchView;
    private ItemizedIconOverlay<OverlayItem> iconoverlay;
    private SensorManager sensorManager;
    private Sensor orientationSensor;
    private CompassOverlay compassOverlay;
    private MyLocationNewOverlay myLocationOverlay;
    private RotationGestureDetector rotationGestureDetector;
    private float currentRotationAngle = 0.0f; // 當前地圖旋轉角度
    // 判斷是否觸碰到收容所的座標
    boolean isShelterLocation = false;
    private LocationManager locationManager;
    private MyOverlay overlay;
    private GeoPoint currentLocation;
    private boolean isFirstLocation = true;
    private boolean isExpanded = false; //是否展開
    String result; // 儲存資料用的字串
    private ArrayList<Marker> existingMarkers = new ArrayList<>(); //存储已经存在的标记的信息

    // 声明一个变量用于保存已存在的标记
    Marker existingMarker = null;
    boolean markerExists = false;// 检查是否已经存在该标记
    private float totalDistance = 0; //移動距離
    private Location previousLocation;//上一個位置
    private Location lastLocation;
    private IMapController mapController;
    LinearLayout dashboard_for_animal,dashborad_counts,dashborad_distance;
    Context context;
    boolean isMapA = true;
    Switch mapswitch;
    ImageButton fbtn_search,show_statue_btn;
    ImageView ab_back, list,compassImageView,ani_photo,sel_aniphoto,bottomsheet_image;
    TextView txt_ref, txt_now, txt_new, txt_she,dialog_nowtime,showtotaldistance,txt_show_name,txt_show_type,txt_show_lon,txt_show_lat,txt_report_time;
    TextView today_reporttime,today_move;
    FloatingActionButton fab_ref, fab_now, fab_new, fab_she;
    ExtendedFloatingActionButton efab_function,floatingButton;
    Button dialogbtn_select,dialogbtn_capture,dialogbtn_submit,bottomsheet_update,bottomsheet_delete,bottomsheet_cancel;
    Boolean isAllFabVisible;
    EditText an_name, an_class,bottomsheet_id,bottomsheet_name,bottomsheet_species,bottomsheet_newName,bottomsheet_newSpecies;
    // 創建 Marker 對象的列表
    List<Marker> markerList = new ArrayList<>();


    //權限檢查
    private void Checkpermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // 如果沒有定位權限，請請求權限
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        } else {

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationManager.removeUpdates(this);
    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        LocationListener.super.onStatusChanged(provider, status, extras);
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        Location lastKnownLocation = locationManager.getLastKnownLocation(provider);
        if (lastKnownLocation != null) {
            showCurrentLocation(lastKnownLocation);
        }
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        LocationListener.super.onProviderDisabled(provider);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();


    }



    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();


    }

    public void switch_click(){
        mapswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                   Intent it =new Intent(MapanimalActivity.this,Mapshow_animal_Activity.class);
                   startActivity(it);


            }
        });
    }

    @Override
    public void onNetworkComplete(String result) {
        JSONArray customJsonArray = convertToCustomJsonFormat(result);
        String reportCount = customJsonArray.optString(Integer.parseInt("report_count"));
        txt_report_time.setText(reportCount);
    }




    public void excuteThreads(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                mutiThreads();
            }
        });
        thread.start();
    }
    private void mutiThreads(){
        try {
            //URL url = new URL("http://192.168.137.1/animaldata_get.php?" );
            URL url = new URL("http://192.168.50.114/dailyreport_get.php?" );

            // 開始宣告 HTTP 連線需要的物件，這邊通常都是一綑的
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // 建立 Google 比較挺的 HttpURLConnection 物件
            connection.setRequestMethod("GET");
            // 設定連線方式為 POST
            connection.setDoOutput(true); // 允許輸出
            connection.setDoInput(true); // 允許讀入
            connection.setUseCaches(false); // 不使用快取


            // 建 POST 數據
            String postData = "username=" + URLEncoder.encode(UserData.username, "UTF-8");

            // 寫入 POST 資料
            OutputStream outputStream = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            writer.write(postData);
            writer.flush();
            writer.close();
            outputStream.close();
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
                // 解析 JSON 資料並設定到 animalDataList

                result = box; // 把存放用字串放到全域變數
                convertToCustomJsonFormat(result);
                //List<AnimalData> animalDataList = parseJsonData(result);
                //showAnimalDataOnMap(animalDataList);
                // 顯示動物資料在地圖上

            }
        }catch (Exception e){
            result = e.toString(); // 回傳錯誤訊息
        }
    }
    private JSONArray convertToCustomJsonFormat(String jsonData) {
        JSONArray customJsonArray = new JSONArray();

        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                // 从原始 JSON 中提取所需的字段

                String report_date = jsonObject.getString("report_date");
                String report_count= jsonObject.getString("report_count");
                txt_report_time.setText(report_count);
                // 构建新的 JSON 对象
               /* JSONObject customJsonObject = new JSONObject();
                customJsonObject.put("username", username);
                customJsonObject.put("report_count", 1); // 设置为 1，您可以根据需求进行调整
                customJsonObject.put("report_date", report_date);

                // 将新的 JSON 对象添加到结果数组
                customJsonArray.put(customJsonObject);*/
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("JSON Parse Error", "Error parsing JSON data: " + jsonData);
        }

        return customJsonArray;
    }
    public void excuteThread(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                mutiThread();
            }
        });
        thread.start();
    }
    private void mutiThread(){
        try {
            //URL url = new URL("http://192.168.137.1/animaldata_get.php?" );
            URL url = new URL("http://192.168.50.114/animaldata_getself.php?" );

            // 開始宣告 HTTP 連線需要的物件，這邊通常都是一綑的
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // 建立 Google 比較挺的 HttpURLConnection 物件
            connection.setRequestMethod("GET");
            // 設定連線方式為 POST
            connection.setDoOutput(true); // 允許輸出
            connection.setDoInput(true); // 允許讀入
            connection.setUseCaches(false); // 不使用快取


            // 建 POST 數據
            String postData = "reporter_name=" + URLEncoder.encode(UserData.username, "UTF-8");

            // 寫入 POST 資料
            OutputStream outputStream = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            writer.write(postData);
            writer.flush();
            writer.close();
            outputStream.close();
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
                // 解析 JSON 資料並設定到 animalDataList

                result = box; // 把存放用字串放到全域變數
                List<AnimalData> animalDataList = parseJsonData(result);
                showAnimalDataOnMap(animalDataList);
                // 顯示動物資料在地圖上

            }
        }catch (Exception e){
            result = e.toString(); // 回傳錯誤訊息
        }
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
    private void showAnimalDataOnMap(List<AnimalData> animalDataList) {

        for (AnimalData animalData : animalDataList) {
            double latitude = Double.parseDouble(animalData.getLatitude());
            double longitude = Double.parseDouble(animalData.getLongitude());

            Marker marker = new Marker(mapView);
            marker.setPosition(new GeoPoint(latitude, longitude));
            marker.setTitle(animalData.getName()+" 編號: "+animalData.getId());
            marker.setSnippet("通報者: 你 \n"+"種類: "+animalData.getSpecies());


            // 可以根據不同的物種設定不同的標記圖示
            if ("cat".equals(animalData.getSpecies()) || "貓".equals(animalData.getSpecies())) {
                marker.setIcon(getResources().getDrawable(R.drawable.ic_cat_svg));
            } else if ("dog".equals(animalData.getSpecies()) || "狗".equals(animalData.getSpecies())) {
                marker.setIcon(getResources().getDrawable(R.drawable.ic_dog_svg));
            }else if ("bird".equals(animalData.getSpecies()) || "鳥".equals(animalData.getSpecies())) {
                marker.setIcon(getResources().getDrawable(R.drawable.ic_bird_svg));
            }


            mapView.getOverlayManager().add(marker);
            //mapView.getOverlays().add(marker);
        }
        // 更新地圖

        mapView.invalidate();

    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // 將觸摸摸事件傳遞给 RotationGestureDetector 處理旋轉手势
        rotationGestureDetector.onTouch(ev);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapanimal);
        getSupportActionBar().hide();

        //action bar back click
        navbar_btnclicker();
        //switch event
        mapswitch = findViewById(R.id.map_toggle);
        switch_click();
        an_name = findViewById(R.id.animal_name);
        an_class = findViewById(R.id.animal_type);
        dashborad_counts = findViewById(R.id.dashborad_counts);
        dashborad_distance = findViewById(R.id.dashborad_distance);
        dashborad_counts.setVisibility(View.INVISIBLE);
        dashborad_distance.setVisibility(View.INVISIBLE);
        txt_report_time = findViewById(R.id.today_report_time);
        functionbtnclicker();
        //展開按鈕狀態
        show_statue_clicker();
        ExtendedFloatingActionButton floatingButton = findViewById(R.id.fbtn_search);
        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.setVisibility(View.VISIBLE);
                floatingButton.setVisibility(View.GONE);
            }
        });
        list.setVisibility(View.INVISIBLE);

        //地圖設置
        mapsetting();
        //座標點還原
        excuteThread();
        //連線至資料庫

        //今日的更新
        //excuteThreads();
       // NetworkManager networkManager = new NetworkManager(this, UserData.username); // 傳遞 userData
        //networkManager.execute();
        //檢查權限
        Checkpermission();
        //地圖座標設置
        mappost();
        //指北針事件
        compassclicker();
        //search事件
        searchinputer();

        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            Intent data = result.getData();
                            if (data != null) {
                               selectedImageUri = data.getData();
                                try {
                                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                                    bottomsheet_image.setImageURI(selectedImageUri);



                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                });

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // 設置定位更新的時間間隔和距離閾值，這裡設置為每 5 秒或每 10 公尺更新一次
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 1, this);
        // 監聽 Marker 點擊事件
        //locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
                Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (lastLocation != null) {
                    currentLocation = new GeoPoint(lastLocation.getLatitude(), lastLocation.getLongitude());
                    mapView.getController().setCenter(currentLocation);
                }
            } else {
                // 提示用戶啟用位置服務
                AlertDialog.Builder builder = new AlertDialog.Builder(this,android.R.style.Theme_DeviceDefault_Light_Dialog);
                builder.setMessage("請啟用位置服務以繼續使用應用程式")
                        .setCancelable(false)
                        .setPositiveButton("啟用位置服務", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        }

        // 初始化定位
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // 設置定位更新的時間和距離
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 3, this);
        // 獲取最後一次已知位置
        Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (lastKnownLocation != null) {
            // 在地圖上標記當前位置
            showCurrentLocation(lastKnownLocation);
        }
    }
    private void updateMapLocation(double latitude, double longitude) {
        // 在這裡使用 OpenStreetMap 或其他地圖庫來更新地圖位置
        // 例如，如果使用 OpenStreetMap，您可以透過設置地圖中心點來顯示最新位置
        GeoPoint currentLocation = new GeoPoint(latitude, longitude);
        mapView.getController().setCenter(currentLocation);
        mapView.invalidate();
    }

    private void calculateDistance(double latitude, double longitude) {
        // 在這裡執行其他與位置相關的計算或邏輯處理
        // 例如，您可以計算目前位置與特定地點之間的距離
        //double distance = calculateDistanceToTarget(latitude, longitude, targetLatitude, targetLongitude);
        //Log.d(TAG, "距離目標地點的距離為：" + distance + " 公尺");
    }

    //下方儀表板事件
    public void show_statue_clicker(){
        show_statue_btn = findViewById(R.id.show_statue_btn);
        show_statue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isExpanded){
                    dashborad_counts.setVisibility(View.INVISIBLE);
                    dashborad_distance.setVisibility(View.INVISIBLE);
                    show_statue_btn.setImageResource(R.drawable.show_arrow_right);
                }else{
                    //按鈕形式變換
                    show_statue_btn.setImageResource(R.drawable.show_arrow_left);
                    //展開dashboard
                    dashborad_counts.setVisibility(View.VISIBLE);
                    dashborad_distance.setVisibility(View.VISIBLE);
                }
                isExpanded = !isExpanded;
            }
        });

    }
    //導覽列功能事件
    public void navbar_btnclicker(){
        //上方bar按鈕
        ab_back = findViewById(R.id.leftback);
        list = findViewById(R.id.right_list);
        ab_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent itma = new Intent(MapanimalActivity.this, toolbar_homeActivity.class);
                startActivity(itma);
            }
        });
        ab_back.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ab_back.setImageResource(R.drawable.baseline_keyboard_arrow_left_24_pressed);
                        break;
                    case MotionEvent.ACTION_UP:
                        ab_back.setImageResource(R.drawable.baseline_keyboard_arrow_left_24);
                        break;
                }
                return false;
            }
        });
        list.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        list.setImageResource(R.drawable.baseline_format_list_bulleted_pressed_24);
                        break;
                    case MotionEvent.ACTION_UP:
                        list.setImageResource(R.drawable.baseline_format_list_bulleted_24);
                        break;
                }
                return false;
            }
        });
    }

    //收尋輸入事件
    public void searchinputer(){
        searchView = findViewById(R.id.searchView);
        // 设置搜索功能
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchLocation(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                searchView.setVisibility(View.GONE); // 隐藏SearchView
                ExtendedFloatingActionButton floatingButton = findViewById(R.id.fbtn_search);
                floatingButton.setVisibility(View.VISIBLE);
                //floatingButton.setVisibility(View.VISIBLE); // 显示FloatingActionButton
                return false;
            }
        });
    }
    //收巡事件
    private void searchLocation(String query) {
        for (Marker marker : markerList) {
            if (marker.getTitle().equalsIgnoreCase(query)) {

                // 移動至該位置：
                mapView.getController().setCenter(marker.getPosition());

                // 清除SearchView中的文本
                //searchView.setQuery("", false); // 第二个参数表示是否提交查询，设置为false以避免再次触发搜索操作
                break; // 找到匹配位置后，可以选择停止搜索
            }
        }
    }






    //地圖初始化設置
    public void mapsetting(){
        mapView = findViewById(R.id.mapview);
        Configuration.getInstance().setUserAgentValue(getPackageName());
        //mapView.setBuiltInZoomControls(true);
        mapView.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.ALWAYS);
        mapView.setClickable(true);
        mapView.getController().setZoom(15.0); // 設置縮放級別
        mapView.setMultiTouchControls(true);

        Marker markers = new Marker(mapView);
        // 定位到指定座標點
        mapController = mapView.getController();
        //設定縮放調整大小
        mapController.setZoom(20.0);
        //在座標點化紅點
        GeoPoint startPoint = new GeoPoint(25.0339639, 121.5622831);
        mapController.setCenter(startPoint);

        // 創建標記覆疊層
        List<OverlayItem> items = new ArrayList<>();
        Drawable marker = ContextCompat.getDrawable(this, R.drawable.icon_dessx);
        OverlayItem overlayItem = new OverlayItem("Taipei 101", "Taipei 101 description", startPoint);
        overlayItem.setMarker(marker);
        items.add(overlayItem);

        overlay = new MyOverlay(items);
        overlay.addItem(overlayItem);
        mapView.getOverlayManager().add(overlay);

        //mapView.getOverlays().add(overlay);
    }
    public void addMarkerWithClickEvent(double latitude, double longitude, String title, String snippet){
        Drawable doticon = getResources().getDrawable(R.drawable.baseline_house_48);
        Marker marker = new Marker(mapView);
        marker.setPosition(new GeoPoint(latitude, longitude));
        marker.setTitle(title);
        marker.setSnippet(snippet);
        marker.setIcon(doticon);
        // 設置點擊事件
        marker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker, MapView mapView) {
                AlertDialog.Builder ad1 = new AlertDialog.Builder(MapanimalActivity.this);
                ad1.setTitle(marker.getTitle());
                ad1.setMessage(marker.getSnippet());

                ad1.setPositiveButton("確認", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 點擊確認按鈕時的處理邏輯
                    }
                });

                ad1.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 點擊取消按鈕時的處理邏輯
                    }
                });

                ad1.setNeutralButton("導航", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 呼叫導航方法並傳入地址
                        navigateToAddress(marker.getSnippet());
                    }
                });

                ad1.show();
                return false;
            }
        });

        // 將 Marker 添加到地圖上
        mapView.getOverlayManager().add(marker);
        markerList.add(marker);
    }
    private void navigateToAddress(String address) {
        // 將地址轉換為Uri編碼格式
        String encodedAddress = Uri.encode(address);
        // 構建導航的URI
        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + encodedAddress);

        // 創建導航意圖
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");

        // 檢查設備是否安裝了Google地圖app
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            // 啟動導航
            startActivity(mapIntent);
        } else {
            // 提示用户安裝Google地圖應用或其他導航應用
            Toast.makeText(MapanimalActivity.this, "您的裝置未安裝Google地圖或其他導航應用", Toast.LENGTH_SHORT).show();
        }
    }
    public void map_point(){
        //臺北市動物保護處 110台北市信義區吳興街600巷109號 25.01870925392157, 121.5726948034499
        addMarkerWithClickEvent(25.020256, 121.572378, "台北市動物保護處", "110台北市信義區吳興街600巷109號");
        //台北市動物之家 114台北市內湖區安美街191號 25.060490862387983, 121.60332172322383
        addMarkerWithClickEvent(25.062538, 121.602403, "台北市動物之家", "114台北市內湖區安美街191號");
        //新北市八里動物之家 25.087705340290547, 121.3982159450998
        addMarkerWithClickEvent(25.087705340290547, 121.3982159450998, "新北市八里動物之家", "110台北市信義區吳興街600巷109號");
        //五股區公立動物之家 248新北市五股區外寮路9-9號 25.07762671348991, 121.41579858255048
        addMarkerWithClickEvent(25.07762671348991, 121.41579858255048, "五股區公立動物之家", "248新北市五股區外寮路9-9號");
        //板橋動物之家 220新北市板橋區板城路28-1號 24.995165227344994, 121.44819521715382
        addMarkerWithClickEvent(24.995165227344994, 121.44819521715382, "板橋動物之家", "220新北市板橋區板城路28-1號");
        //新北市政府動物保護防疫處 220新北市板橋區四川路一段157巷2號 25.004278098695146, 121.46032619419894
        addMarkerWithClickEvent(25.004278098695146, 121.46032619419894, "新北市政府動物保護防疫處", "220新北市板橋區四川路一段157巷2號");
        //新北市中和公立動物之家 235新北市中和區興南路三段100號 24.975619582178552, 121.48875087907295
        addMarkerWithClickEvent(24.975619582178552, 121.48875087907295, "新北市中和公立動物之家", "235新北市中和區興南路三段100號");
        //Help Save A Pet Fund Taiwan 23578新北市中和區景平路71之7號20樓 24.991458457237545, 121.51895921718928
        addMarkerWithClickEvent(24.991458457237545, 121.51895921718928, "Help Save A Pet Fund Taiwan", "23578新北市中和區景平路71之7號20樓");
        //拼圖喵中途之家 234新北市永和區秀朗路一段237號 25.003142842676695, 121.52040428965479
        addMarkerWithClickEvent(25.003142842676695, 121.52040428965479, "拼圖喵中途之家", "234新北市永和區秀朗路一段237號");
        //社團法人好好善待動物協會 234新北市永和區自由街55號9樓 25.00545417410029, 121.51381192009514
        addMarkerWithClickEvent(25.00545417410029, 121.51381192009514, "社團法人好好善待動物協會", "234新北市永和區自由街55號9樓");
        //新店動物之家 號, No. 235安泰路新店區新北市231 24.927162609497735, 121.49080937756486
        addMarkerWithClickEvent(24.927162609497735, 121.49080937756486, "新店動物之家", "號, No. 235安泰路新店區新北市231");
        //基隆市寵物銀行 206基隆市七堵區大華三路45-12號 25.12727817964342, 121.67502897522779
        addMarkerWithClickEvent(25.12727817964342, 121.67502897522779, "基隆市寵物銀行", "206基隆市七堵區大華三路45-12號");
        //基隆市動物保護防疫所 201基隆市信義區信二路241號 25.13006529640703, 121.74806266488405
        addMarkerWithClickEvent(25.13006529640703, 121.74806266488405, "基隆市動物保護防疫所", "201基隆市信義區信二路241號");
        //瑞芳動物之家 224新北市瑞芳區靜安路四段 25.07594760674416, 121.79977659034935
        addMarkerWithClickEvent(25.07594760674416, 121.79977659034935, "瑞芳動物之家", "224新北市瑞芳區靜安路四段");
        //桃園市動物保護教育園區 327桃園市新屋區藻礁路1668號 25.008492930110584, 121.02777126394642
        addMarkerWithClickEvent(25.008492930110584, 121.02777126394642, "桃園市動物保護教育園區", "327桃園市新屋區藻礁路1668號");
        //新竹市動物保護教育園區 300新竹市北區海濱路250號 24.83268996486745, 120.919623780019
        addMarkerWithClickEvent(24.83268996486745, 120.919623780019, "新竹市動物保護教育園區", "300新竹市北區海濱路250號");
        //新竹縣動物保護教育園區 302新竹縣竹北市縣政五街192號 24.82845653027636, 121.0150620602744
        addMarkerWithClickEvent(24.82845653027636, 121.0150620602744, "新竹縣動物保護教育園區", "302新竹縣竹北市縣政五街192號");
        //新竹市紅項圈流浪動物協會 300新竹市東區大學路1001號 24.786085214213486, 120.99990993442853
        addMarkerWithClickEvent(24.786085214213486, 120.99990993442853, "新竹市紅項圈流浪動物協會", "300新竹市東區大學路1001號");
        //苗栗縣生態保育教育中心 366苗栗縣銅鑼鄉朝陽村6鄰朝北55-1號 24.499630467534022, 120.7939974958818
        addMarkerWithClickEvent(24.499630467534022, 120.7939974958818, "苗栗縣生態保育教育中心", "366苗栗縣銅鑼鄉朝陽村6鄰朝北55-1號");
        //苗栗縣動物保護防疫所 360苗栗縣苗栗市玉清路382-1號 24.561003735319343, 120.83633111691667
        addMarkerWithClickEvent(24.561003735319343, 120.83633111691667, "苗栗縣動物保護防疫所", "360苗栗縣苗栗市玉清路382-1號");
        //苗栗市公所苗栗縣中區流浪動物收容中心 360苗栗縣苗栗市南勢坑段小段726-17地號 24.5455865557485, 120.80351913892405
        addMarkerWithClickEvent(24.5455865557485, 120.80351913892405, "苗栗市公所苗栗縣中區流浪動物收容中心", "360苗栗縣苗栗市南勢坑段小段726-17地號");
        //飛翔蜜境-台灣莉丰慧民V關懷動物協會 269宜蘭縣冬山鄉三堵二路151號 24.654246625119793, 121.80031622144489
        addMarkerWithClickEvent(24.654246625119793, 121.80031622144489, "飛翔蜜境-台灣莉丰慧民V關懷動物協會", "269宜蘭縣冬山鄉三堵二路151號");
        //宜蘭縣流浪動物中途之家 268宜蘭縣五結鄉利寶路60號 24.6666293658304, 121.8308445061333
        addMarkerWithClickEvent(24.6666293658304, 121.8308445061333, "宜蘭縣流浪動物中途之家", "268宜蘭縣五結鄉利寶路60號");
        //花蓮縣狗貓躍動園區 975花蓮縣鳳林鎮永豐路255號 23.805570852231163, 121.49826137471165
        addMarkerWithClickEvent(23.805570852231163, 121.49826137471165, "花蓮縣狗貓躍動園區", "975花蓮縣鳳林鎮永豐路255號");
        //臺東縣流浪動物收容中心 950台東縣台東市中華路四段999巷600號之1號 22.71959549199086, 121.10096970902326
        addMarkerWithClickEvent(22.71959549199086, 121.10096970902326, "臺東縣流浪動物收容中心", "950台東縣台東市中華路四段999巷600號之1號");
        //臺中市動物之家南屯園區 408台中市南屯區中台路601號 24.147693920060107, 120.57523421147856
        addMarkerWithClickEvent(24.147693920060107, 120.57523421147856, "臺中市動物之家南屯園區", "408台中市南屯區中台路601號");
        //臺中市動物之家后里園區 421台中市后里區堤防路370號 24.28635796831823, 120.7095706431338
        addMarkerWithClickEvent(24.28635796831823, 120.7095706431338, "臺中市動物之家后里園區", "421台中市后里區堤防路370號");
        //社團法人浪浪的後盾協會 404台中市北區進化北路430巷17弄3號17弄5號2樓 24.16350594916432, 120.67483158723222
        addMarkerWithClickEvent(24.16350594916432, 120.67483158723222, "社團法人浪浪的後盾協會", "404台中市北區進化北路430巷17弄3號17弄5號2樓");
        //臺中市動物保護防疫處 408台中市南屯區萬和路一段28-18號 24.13340431222425, 120.63733385229959
        addMarkerWithClickEvent(24.13340431222425, 120.63733385229959, "臺中市動物保護防疫處", "408台中市南屯區萬和路一段28-18號");
        //臺中市日光動物協會TSAA 411台中市太平區長龍路三段276號 24.104126648333164, 120.77494808524082
        addMarkerWithClickEvent(24.104126648333164, 120.77494808524082, "臺中市日光動物協會TSAA", "411台中市太平區長龍路三段276號");
        //台中市世界聯合保護動物協會 436031台中市清水區海風二街578號 24.298358055394356, 120.60860243551933
        addMarkerWithClickEvent(24.298358055394356, 120.608602435519, "台中市世界聯合保護動物協會", "436031台中市清水區海風二街578號");
        //彰化縣流浪狗中途之家(導航請走芬園鄉大彰路一段875巷直走到底） 510彰化縣員林市阿寶巷426號芬園鄉大彰路一段875巷導航請走 23.969223771279452, 120.61972036519245
        addMarkerWithClickEvent(23.969223771279452, 120.61972036519245, "彰化縣流浪狗中途之家(導航請走芬園鄉大彰路一段875巷直走到底）", "510彰化縣員林市阿寶巷426號芬園鄉大彰路一段875巷導航請走");
        //彰化縣動物防疫所 500彰化縣彰化市中央路2號 24.06402209017635, 120.53624486087011
        addMarkerWithClickEvent(24.06402209017635, 120.53624486087011, "彰化縣動物防疫所", "500彰化縣彰化市中央路2號");
        //南投縣公立動物收容所 540南投縣南投市嶺興路36-1號 23.90588646661529, 120.66982656083522
        addMarkerWithClickEvent(23.90588646661529, 120.66982656083522, "南投縣公立動物收容所", "540南投縣南投市嶺興路36-1號");
        //南投動物收容所 540南投縣南投市嶺興路36-1號 23.906415983210923, 120.66981139836315
        addMarkerWithClickEvent(23.906415983210923, 120.66981139836315, "南投動物收容所", "540南投縣南投市嶺興路36-1號");
        //雲林縣流浪動物收容所 640雲林縣斗六市雲林路二段517號 23.698298925352454, 120.52603814915577
        addMarkerWithClickEvent(23.698298925352454, 120.52603814915577, "雲林縣流浪動物收容所", "640雲林縣斗六市雲林路二段517號");
        //雲林縣動植物防疫所 640雲林縣斗六市雲林路二段517號 23.69831191507048, 120.52593128614396
        addMarkerWithClickEvent(23.69831191507048, 120.52593128614396, "雲林縣動植物防疫所", "640雲林縣斗六市雲林路二段517號");
        //嘉義市動物保護教育園區 600嘉義市東區彌陀路31號600 23.464336687519005, 120.46877589356323
        addMarkerWithClickEvent(23.464336687519005, 120.46877589356323, "嘉義市動物保護教育園區", "600嘉義市東區彌陀路31號600");
        //社團法人嘉義市正德護生慈善會 600嘉義市西區世賢路一段839號 23.48587397597046, 120.42469114657708
        addMarkerWithClickEvent(23.48587397597046, 120.42469114657708, "社團法人嘉義市正德護生慈善會", "600嘉義市西區世賢路一段839號");
        //嘉義縣動物保護教育園區(嘉義縣收容所) 621嘉義縣民雄鄉 23.547647563999842, 120.50547444724377
        addMarkerWithClickEvent(23.547647563999842, 120.50547444724377, "嘉義縣動物保護教育園區(嘉義縣收容所)", "621嘉義縣民雄鄉");
        //流浪犬中途之家(民間收容所) 622嘉義縣大林鎮中興26號 23.57313704162052, 120.50048477752036
        addMarkerWithClickEvent(23.57313704162052, 120.50048477752036, "流浪犬中途之家(民間收容所)", "622嘉義縣大林鎮中興26號");
        //臺南市動物之家灣裡站 702台南市南區萬年路580巷92號 22.936792474923056, 120.19440881612
        addMarkerWithClickEvent(22.936792474923056, 120.19440881612, "臺南市動物之家灣裡站", "702台南市南區萬年路580巷92號");
        //臺南市動物之家善化站 741台南市善化區1-19號 23.14882133511597, 120.33163059630526
        addMarkerWithClickEvent(23.14882133511597, 120.33163059630526, "臺南市動物之家善化站", "741台南市善化區1-19號");
        //徐文良(徐園長護生園) 72543台南市將軍區西湖112號 23.1943276961791, 120.14045110669639
        addMarkerWithClickEvent(23.1943276961791, 120.14045110669639, "徐文良(徐園長護生園)", "72543台南市將軍區西湖112號");
        //高雄市動物保護教育園區 804高雄市鼓山區萬壽路350號 22.63695075070839, 120.27799283757787
        addMarkerWithClickEvent(22.63695075070839, 120.27799283757787, "高雄市動物保護教育園區", "804高雄市鼓山區萬壽路350號");
        //社團法人高雄市關懷流浪動物協會 804高雄市鼓山區中華一路369號 22.65835680118762, 120.2926670580472
        addMarkerWithClickEvent(22.65835680118762, 120.2926670580472, "社團法人高雄市關懷流浪動物協會", "804高雄市鼓山區中華一路369號");
        //艾瑪護生園〈快樂寶貝窩〉831高雄市大寮區文化路66號之15號 22.623269042561883, 120.38422813686229
        addMarkerWithClickEvent(22.623269042561883, 120.38422813686229, "艾瑪護生園〈快樂寶貝窩〉", "831高雄市大寮區文化路66號之15號");
        //高雄市燕巢動物收容所 824高雄市燕巢區師大路98號 22.792648993673826, 120.404661244803
        addMarkerWithClickEvent(22.792648993673826, 120.404661244803, "高雄市燕巢動物收容所", "824高雄市燕巢區師大路98號");
        //岡山陳老師護生園 820高雄市岡山區潭底路31號 22.83615448429506, 120.30433497096107
        addMarkerWithClickEvent(22.83615448429506, 120.30433497096107, "岡山陳老師護生園", "820高雄市岡山區潭底路31號");
        //高雄市動物保護處 830高雄市鳳山區忠義街166號 22.62794511597649, 120.35077397636744
        addMarkerWithClickEvent(22.62794511597649, 120.35077397636744, "高雄市動物保護處", "830高雄市鳳山區忠義街166號");
    }

    public void mappost(){
        map_point();
        mapView.getOverlayManager().add(new Overlay() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e, MapView mapView) {
                IGeoPoint point = mapView.getProjection().fromPixels((int) e.getX(), (int) e.getY());
                double lat = point.getLatitude();
                double lon = point.getLongitude();
                double epsilon = 0.65; // 設定一個小的誤差範圍
                // 計算點擊位置與收容所的距離

                Log.d("markerList", "markerList: " +  markerList.size());
                for (Marker shelterMarker : markerList) {
                    double shelterLat = shelterMarker.getPosition().getLatitude();
                    double shelterLon = shelterMarker.getPosition().getLongitude();


                    Log.d("Math.abs(shelterLat - lat)", "Math.abs(shelterLat - lat)" +  Math.abs(shelterLat - lat));
                    Log.d("Math.abs(shelterLon - lon)", "Math.abs(shelterLon - lon)" +  Math.abs(shelterLon - lon));
                    Log.d("lat", "lat: " +  lat);
                    Log.d("lon", "lon: " +  lon);
                    Log.d("getLat", "getLa: " +  shelterMarker.getPosition().getLatitude());
                    Log.d("getLon", "getLon: " +  shelterMarker.getPosition().getLongitude());

                    if ( Math.abs(shelterLat - lat) <= 0.0013   && Math.abs(shelterLon - lon)  <= 0.11) {
                        isShelterLocation = true;

                        break;
                    }
                    else {
                        isShelterLocation = false;
                    }
                }

                if(!isShelterLocation){
                    AlertDialog.Builder builder = new AlertDialog.Builder(MapanimalActivity.this);
                    // 添加通報點
                    //AlertDialog.Builder builder = new AlertDialog.Builder(MapanimalActivity.this);

                    builder.setTitle("新增地點");
                    builder.setIcon(R.drawable.baseline_add_location_24);

                    // Inflate the custom layout for the dialog
                    LayoutInflater inflater = getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.dialog_add_animal, null);
                    builder.setView(dialogView);

                    // Get references to the EditText fields in the custom layout
                    EditText editName = dialogView.findViewById(R.id.animal_name);
                    EditText editSpecies = dialogView.findViewById(R.id.animal_type);
                    //image
                    ImageView sel_aniphoto = dialogView.findViewById(R.id.ani_photo);
                    sel_aniphoto.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //開啟視窗
                            //選擇圖片
                            selectImage();
                        }
                    });



                    builder.setTitle("發布位置")
                            .setMessage("是否要在此位置發布？")
                            .setPositiveButton("確認", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    txt_show_name = findViewById(R.id.txt_animal_name);
                                    txt_show_type = findViewById(R.id.txt_animal_type);
                                    txt_show_lon = findViewById(R.id.txt_animal_lon);
                                    txt_show_lat = findViewById(R.id.txt_animal_lat);
                                    // 創建一個新的標記
                                    Marker marker = new Marker(mapView);
                                    marker.setPosition(new GeoPoint(lat, lon));
                                    existingMarkers.add(marker); // 将标记添加到列表中
                                    // 獲取EditText中的輸入内容 1.2
                                    String animalName = editName.getText().toString();
                                    String animalSpecies = editSpecies.getText().toString();
                                    // 將動物名稱和種類設置為標記的標題和內容
                                    marker.setTitle("動物性名: " + animalName);
                                    marker.setSnippet("動物種類: " + animalSpecies);

                                    timeclicker();
                                    // 目前時間日期
                                    String nowDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                                    //經緯度5. 6
                                    String longitude = String.valueOf(lon);
                                    String latitude = String.valueOf(lat);
                                    //使用者姓名 7.
                                    String username = UserData.username;
                                    //將詳細資料傳入資料庫
                                    //連線至資料庫
                                    String type = "add_point";
                                    BackgroundWorker backgroundWorker = new BackgroundWorker( MapanimalActivity.this);
                                    backgroundWorker.execute(type, animalName, animalSpecies,"img的檔案",nowDate,longitude,latitude,username);
                                    marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);



                                    mapView.getOverlayManager().add(marker);
                                    mapView.invalidate(); // 刷新地圖
                                    isShelterLocation = false; // 重置為false，準備處理下一次點擊事件
                                    // 顯示經緯度的提示訊息
                                    Toast.makeText(MapanimalActivity.this, "緯度: " + lat + ", 經度: " + lon, Toast.LENGTH_SHORT).show();


                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    isShelterLocation = false; // 重置為false，準備處理下一次點擊事件
                                }
                            })
                            .show();
                    Toast.makeText(MapanimalActivity.this, "緯度: " + lat + ", 經度: " + lon, Toast.LENGTH_SHORT).show();
                }else{
                        isShelterLocation = false; // 重置為false，準備處理下一次點擊事件
                }

                return super.onSingleTapConfirmed(e, mapView);
            }


        });
    }
    public void timeclicker(){
        // 创建用于更新时间的Runnable
        updateTimeRunnable = new Runnable() {
            @Override
            public void run() {
                // 獲取當前時間
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
                String currentTime = dateFormat.format(new Date());

                // 設置當前時間到TextView
                dialog_nowtime.setText("目前時間: " + currentTime);

                // 延遲1秒後再次執行該Runnable
                handler.postDelayed(this, 1000);
            }
        };
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        String currentTime = dateFormat.format(new Date());

        // 设置当前时间到TextView
        //dialog_nowtime.setText("目前時間: " + currentTime);
        // 使用Handler启动定期更新时间
        //handler.postDelayed(updateTimeRunnable, 1000);
    }

    public void functionbtnclicker(){
        //浮動按鈕功能列
        txt_ref = findViewById(R.id.txt_ref);
        txt_new = findViewById(R.id.txt_new);
        txt_now = findViewById(R.id.txt_now);
        txt_she = findViewById(R.id.txt_she);
        efab_function = findViewById(R.id.btn_fab_function);
        fab_ref = findViewById(R.id.btn_fab_reflash);
        fab_new = findViewById(R.id.btn_fab_newpost);
        fab_now = findViewById(R.id.btn_fab_nowlocation);
        fab_she = findViewById(R.id.btn_fab_findshe);

        txt_ref.setVisibility(View.GONE);
        txt_new.setVisibility(View.GONE);
        txt_now.setVisibility(View.GONE);
        txt_she.setVisibility(View.GONE);
        fab_ref.setVisibility(View.GONE);
        fab_new.setVisibility(View.GONE);
        fab_now.setVisibility(View.GONE);
        fab_she.setVisibility(View.GONE);

        isAllFabVisible = false;
        efab_function.shrink();
        efab_function.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isAllFabVisible){
                    fab_ref.show();
                    fab_new.show();
                    fab_now.show();
                    fab_she.show();
                    txt_ref.setVisibility(View.VISIBLE);
                    txt_new.setVisibility(View.VISIBLE);
                    txt_now.setVisibility(View.VISIBLE);
                    txt_she.setVisibility(View.VISIBLE);

                    efab_function.extend();
                    isAllFabVisible =true;
                }
                else {
                    fab_ref.hide();
                    fab_new.hide();
                    fab_now.hide();
                    fab_she.hide();
                    txt_ref.setVisibility(View.GONE);
                    txt_new.setVisibility(View.GONE);
                    txt_now.setVisibility(View.GONE);
                    txt_she.setVisibility(View.GONE);
                    efab_function.shrink();
                    isAllFabVisible = false;
                }

            }
        });
        fab_ref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isLocationEnabled()) {
                    Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (lastKnownLocation != null) {
                        showCurrentLocation(lastKnownLocation);
                    } else {
                        Toast.makeText(MapanimalActivity.this, "無法獲取最新位置", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //showLocationEnableDialog();
                }
            }
        });
        fab_new.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingInflatedId")
            @Override
            public void onClick(View view) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MapanimalActivity.this, R.style.BottomSheetDialogTheme);


                View bottomsheet = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_bottom_sheet_design_firstmap, (LinearLayout) findViewById(R.id.bottomSheetContainer1));
                bottomsheet_id = bottomsheet.findViewById(R.id.bottomSheet_id1);
                bottomsheet_name = bottomsheet.findViewById(R.id.bottomSheet_name1);
                bottomsheet_species = bottomsheet.findViewById(R.id.bottomSheet_species1);
                bottomsheet_newName = bottomsheet.findViewById(R.id.bottomSheet_name2);
                bottomsheet_newSpecies = bottomsheet.findViewById(R.id.bottomSheet_species2);
                bottomsheet_image  =bottomsheet.findViewById(R.id.bottomSheet_animal_photo2);
                bottomsheet_update = bottomsheet.findViewById(R.id.bottomSheet_update1);
                bottomsheet_delete = bottomsheet.findViewById(R.id.bottomSheet_delete1);
                bottomsheet_cancel = bottomsheet.findViewById(R.id.bottomSheet_cancel1);
                //選圖片
                bottomsheet_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      selectImage();


                    }
                });

                //更新
                bottomsheet_update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //連線至資料庫
                        String type = "update_animal";
                        String id = bottomsheet_id.getText().toString();
                        String name = bottomsheet_name.getText().toString();
                        String species = bottomsheet_species.getText().toString();
                        String newName = bottomsheet_newName.getText().toString();
                        String newSpecies = bottomsheet_newSpecies.getText().toString();

                        if (selectedImageUri != null) {
                            // 從 Uri 中獲取實際的文件路徑
                            String[] projection = {MediaStore.Images.Media.DATA};
                            Cursor cursor = getContentResolver().query(selectedImageUri, projection, null, null, null);
                            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                            cursor.moveToFirst();
                            filePath = cursor.getString(column_index);
                            cursor.close();

                            // 构建完整的 URL
                            String imageUrl = serverBaseUrl + new File(filePath).getName();
                            newPhotoImg = imageUrl; // 將 URI 轉換為文件路徑

                            new Fileload().saveFileToServer(filePath, serverBaseUrl, new File(filePath).getName());
                        }

                        BackgroundWorker backgroundWorker = new BackgroundWorker( MapanimalActivity.this);
                        backgroundWorker.execute(type, id, name,species,UserData.username,newName,newSpecies,newPhotoImg);
                    }
                });
                //刪除
                bottomsheet_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //連線至資料庫
                        String type = "delete_animal";
                        String id = bottomsheet_id.getText().toString();
                        String name = bottomsheet_name.getText().toString();
                        String species = bottomsheet_species.getText().toString();
                        BackgroundWorker backgroundWorker = new BackgroundWorker( MapanimalActivity.this);
                        backgroundWorker.execute(type, id, name,species,UserData.username);
                    }
                });
                //取消
                bottomsheet_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();
                    }
                });

                bottomSheetDialog.setContentView(bottomsheet);
                bottomSheetDialog.show();
            }
        });
        fab_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 定位到目前的位置
                MapController mapController = (MapController) mapView.getController();
                mapController.setZoom(20); // 設置縮放級別
                GeoPoint myLocation = new GeoPoint(currentLocation); // 創建一個GeoPoint對象，用於定位到當前位置
                mapController.animateTo(myLocation); // 用animateTo()方法來實現平滑移動到當前位置
            }
        });
        fab_she.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (lastLocation != null) {
                    GeoPoint currentLocation = new GeoPoint(lastLocation.getLatitude(), lastLocation.getLongitude());
                    double minDistance = Double.MAX_VALUE;
                    Marker nearestMarker = null;
                    for (Marker marker : markerList) {
                        GeoPoint markerLocation = marker.getPosition();
                        double distance = currentLocation.distanceToAsDouble(markerLocation);
                        //找最小距離
                        if (distance < minDistance) {
                            minDistance = distance;
                            nearestMarker = marker;
                        }
                    }
                    // 先移除之前的線
                    List<Overlay> overlays = mapView.getOverlayManager().overlays();
                    for (int i = overlays.size() - 1; i >= 0; i--) {
                        Overlay overlay = overlays.get(i);
                        if (overlay instanceof Polyline) {
                            mapView.getOverlayManager().remove(overlay);
                        }
                    }

                    Polyline polyline = new Polyline();
                    polyline.setColor(Color.RED); // 設置線的顏色為紅色
                    mapView.getOverlayManager().add(polyline); // 將 Polyline 物件添加到地圖上
                    polyline.getPoints().clear();
                    polyline.addPoint(currentLocation);
                    polyline.addPoint(nearestMarker.getPosition());
                    mapView.invalidate();
                    mapView.getController().setCenter(nearestMarker.getPosition());
                    nearestMarker.showInfoWindow();


                }
            }
        });
        fab_she.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                // 移除線段物件
                for (Overlay overlay : mapView.getOverlayManager().overlays()) {
                    if (overlay instanceof Polyline) {
                        mapView.getOverlayManager().remove(overlay);
                    }
                }
                mapView.invalidate(); // 刷新地圖
                return true;
            }
        });

    }
    // 上傳圖片到伺服器的方法
    private void uploadImageToServer(String imagePath, OnImageUploadListener listener) {
        OkHttpClient client = new OkHttpClient();

        // 構建 MultipartBody，用於上傳圖片
        MediaType mediaType = MediaType.parse("image/jpeg"); // 請根據你的圖片類型調整
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", new File(imagePath).getName(), RequestBody.create(mediaType, new File(imagePath)))
                .build();

        Request request = new Request.Builder()
                .url(newPhotoImg) // 請替換為你的伺服器上傳圖片的 API URL
                .post(requestBody)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                // 上傳成功
                listener.onUploadComplete();
            } else {
                // 上傳失敗
                listener.onUploadFailed();
            }
        } catch (IOException e) {
            e.printStackTrace();
            // 上傳失敗
            listener.onUploadFailed();
        }
    }

    // 定義一個圖片上傳完成的回調接口
    interface OnImageUploadListener {
        void onUploadComplete();

        void onUploadFailed();
    }
    public class Fileload {

        public void saveFileToServer(String filePath, String serverPath, String fileName) {
            File targetFile = new File(serverPath, fileName); // 指定要保存的文件名

            try {
                InputStream fileInputStream = new FileInputStream(filePath);

                OutputStream outputStream = new FileOutputStream(targetFile);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = fileInputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
                outputStream.close();
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void selectImage(){

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        resultLauncher.launch(intent);
    }
    //指北針事件
    public void compassclicker(){
        compassImageView = findViewById(R.id.compassImageView);
        compassImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentRotationAngle = 0;
                mapView.setMapOrientation(currentRotationAngle);
                compassImageView.setRotation(currentRotationAngle);
            }
        });

        rotationGestureDetector = new RotationGestureDetector(new RotationGestureDetector.RotationListener() {
            @Override
            public void onRotate(float deltaAngle) {
                // 調整地圖的旋轉角度
                currentRotationAngle += (deltaAngle/3.0);
                mapView.setMapOrientation(currentRotationAngle);
                compassImageView.setRotation(currentRotationAngle);
            }
        });
    }
    //儀錶板展開事件
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean isGpsEnabled = false;
        boolean isNetworkEnabled = false;

        try {
            isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isGpsEnabled || isNetworkEnabled;
    }
    private List<Overlay> markerOverlays = new ArrayList<>();
    private Marker currentLocationMarker; // 新增一個成員變量用於存儲目前位置的標記
    private void showCurrentLocation(Location lastKnownLocation) {
        Drawable nowicon = getResources().getDrawable(R.drawable.baseline_person_pin_48);
        // 将当前位置转换为 GeoPoint 对象
        GeoPoint geoPoint = new GeoPoint(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
        // 先移除之前的標記 *會把地圖原有marker的隱藏
       // mapView.getOverlays().clear();
        // 移除舊的目前位置標記
        if (currentLocationMarker != null) {
            mapView.getOverlayManager().remove(currentLocationMarker);
        }
        //mapView.getOverlays().removeAll(markerOverlays);
        //將地圖視角移動到目前位置
        mapView.getController().animateTo(geoPoint);
        // 創建一個新的標記並將其添加到地圖上
        currentLocationMarker = new Marker(mapView);
        currentLocationMarker.setPosition(geoPoint);
        currentLocationMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        currentLocationMarker.setTitle("您的目前位置");
        currentLocationMarker.setIcon(nowicon);

        mapView.getOverlays().add(currentLocationMarker);
        updateDistance(lastKnownLocation);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

        showCurrentLocation(location);

        if (isFirstLocation) {
            currentLocation = new GeoPoint(location.getLatitude(), location.getLongitude());
            mapView.getController().setCenter(currentLocation);
            isFirstLocation = false;
        }
        updateDistance(location);
    }
    //更新距離
    private void updateDistance(Location currentLocation) {
        if (previousLocation != null) {
            float distance = previousLocation.distanceTo(currentLocation);
            totalDistance += distance;
            // 在此处使用计步器进行相关操作，例如更新UI上的移动距离显示等
        }
        previousLocation = currentLocation;
        showtotaldistance = findViewById(R.id.show_totaldistance);
        showtotaldistance.setText(Math.round(totalDistance)+" m");
    }
}