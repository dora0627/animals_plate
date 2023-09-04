package com.example.animals_plate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.api.IMapController;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class Mapshow_animal_Activity extends AppCompatActivity implements LocationListener {
    private Switch mapswitch;
    private MapView mapView;
    private int notificationId = 1; // 或者其他任何你想要的數值
    private Location lastLocation;
    private LocationManager locationManager;
    private GeoPoint currentLocation;
    private Marker currentLocationMarker; // 新增一個成員變量用於存儲目前位置的標記
    TextView bottomsheet_species, bottomsheet_location, bottomsheet_distance;
    private RoundedImageView bottomSheet_img;
    String result; // 儲存資料用的字串
    private IMapController mapController;
    boolean isMapA = true;
    int markerResId = R.drawable.baseline_star_24;

    public void switch_click() {
        mapswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {


                Intent it = new Intent(Mapshow_animal_Activity.this, MapanimalActivity.class);
                startActivity(it);
            }
        });
    }

    public void default_set() {
        double lat = 0;
        double lon = 0;
        //GeoPoint startpoint = new GeoPoint(lat,lon);
        //mapView.getController().setCenter(startpoint);
        mapView.getController().setZoom(18.0);
    }

    public void mapsetting() {
        //可點擊
        mapView.setClickable(true);
        //多點觸控
        mapView.setMultiTouchControls(true);
        //最小與最大範圍
        mapView.setMinZoomLevel(5.0);
        mapView.setMaxZoomLevel(23.0);
        GeoPoint startPoint = new GeoPoint(25.0339639, 121.5622831);
        //調用取得地圖控制器
        mapController = mapView.getController();
        //設置中心點
        mapController.setCenter(startPoint);
        //創建marker
        Marker marker = new Marker(mapView);
        //設置icon
        marker.setIcon(getResources().getDrawable(markerResId));
        //設置marker的點為指定的GeoPoint
        marker.setPosition(startPoint);
        //將marker添加到地圖上
        mapView.getOverlays().add(marker);
    }

    public void excuteThread() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                mutiThread();
            }
        });
        thread.start();
    }

    private void mutiThread() {
        try {
            //URL url = new URL("http://192.168.137.1/animaldata_get.php?" );
            URL url = new URL("http://192.168.50.114/animaldata_get.php?");

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
            if (responseCode ==
                    HttpURLConnection.HTTP_OK) {
                // 如果 HTTP 回傳狀態是 OK ，而不是 Error
                InputStream inputStream =
                        connection.getInputStream();
                // 取得輸入串流
                BufferedReader bufReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
                // 讀取輸入串流的資料
                String box = ""; // 宣告存放用字串
                String line = null; // 宣告讀取用的字串
                while ((line = bufReader.readLine()) != null) {
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


        } catch (Exception e) {
            result = e.toString(); // 如果出事，回傳錯誤訊息
        }
        // 當這個執行緒完全跑完後執行

    }

    private ArrayList<AnimalData> parseJsonData(String jsonData) {
        ArrayList<AnimalData> animalDataList = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                int id = jsonObject.getInt("id");
                Log.d("jsonArray.length", "jsonArray.length: " + jsonArray);
                String name = jsonObject.getString("name");
                String species = jsonObject.getString("species");
                String photo = jsonObject.getString("photo");
                String reportDate = jsonObject.getString("report_date");
                String longitude = jsonObject.getString("longitude");
                String latitude = jsonObject.getString("latitude");
                String reporterName = jsonObject.getString("reporter_name");
                String uri= jsonObject.getString("image_url");
                AnimalData animalData = new AnimalData(id, name, species, photo, reportDate, longitude, latitude, reporterName,uri);
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
            marker.setTitle(animalData.getName());
            marker.setSnippet("By. " + animalData.getReporterName() + "種類: " + animalData.getSpecies());
            //點擊事件
            marker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker, MapView mapView) {
                    BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(Mapshow_animal_Activity.this, R.style.BottomSheetDialogTheme);


                    View bottomsheet = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_bottom_sheet_design, (LinearLayout) findViewById(R.id.bottomSheetContainer));
                    bottomsheet_species = bottomsheet.findViewById(R.id.bottomSheet_species);
                    bottomsheet_species.setText("種類: " + animalData.getSpecies());
                    bottomsheet_location = bottomsheet.findViewById(R.id.bottomSheet_location);
                    bottomsheet_location.setText("經度: " + animalData.getLongitude() + "\n緯度: " + animalData.getLatitude());
                    bottomsheet_distance = bottomsheet.findViewById(R.id.bottomSheet_distance);
                    bottomsheet_distance.setText("距離");
                    bottomSheet_img = bottomsheet.findViewById(R.id.bottomSheet_animal_photo);
                    Picasso.get()
                            .load(animalData.getImage_url())
                            .into(bottomSheet_img);
                    bottomSheet_img.setImageURI(Uri.parse(animalData.getImage_url()));
                    Location animalLocation = new Location("");
                    animalLocation.setLatitude(latitude);
                    animalLocation.setLongitude(longitude);
                    Location myLocation = new Location("");
                    myLocation.setLatitude(lastLocation.getLatitude());
                    myLocation.setLongitude(lastLocation.getLongitude());

                    float distance = myLocation.distanceTo(animalLocation);

                    if (distance < 100) {
                        String animalInfo =
                                "種類：" + animalData.getSpecies() +
                                "\n名稱：" + animalData.getName() +
                                "\n經度：" + animalData.getLongitude() +
                                "\n緯度：" + animalData.getLatitude();

                        sendNotification("您附近有動物通報位置！", animalInfo);
                    }

                    if (distance / 1000.0 >= 1) {
                        bottomsheet_distance.setText("距離: " + Math.round(distance) / 1000 + " 公里");
                    } else {
                        bottomsheet_distance.setText("距離: " + Math.round(distance) + " 公尺");
                    }


                    bottomSheetDialog.setContentView(bottomsheet);
                    bottomSheetDialog.show();


                    return false;
                }
            });

            // 可以根據不同的物種設定不同的標記圖示
            if ("cat".equals(animalData.getSpecies()) || "貓".equals(animalData.getSpecies())) {
                marker.setIcon(getResources().getDrawable(R.drawable.ic_cat_svg));
            } else if ("dog".equals(animalData.getSpecies()) || "狗".equals(animalData.getSpecies())) {
                marker.setIcon(getResources().getDrawable(R.drawable.ic_dog_svg));
            } else if ("bird".equals(animalData.getSpecies()) || "鳥".equals(animalData.getSpecies())) {
                marker.setIcon(getResources().getDrawable(R.drawable.ic_bird_svg));
            }

            // marker.setIcon(yourAnimalIcon);
            mapView.getOverlayManager().add(marker);
            //mapView.getOverlays().add(marker);
        }
        // 更新地圖
        mapView.invalidate();
    }

    private void sendNotification(String message, String animalInfo) {
        String channelId = getPackageName() + ".channel-id";
        String channelName = getPackageName() + ".channel-name";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = new NotificationChannel(channelId, channelName, importance);
        }
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(Mapshow_animal_Activity.this, channelId)
                .setSmallIcon(R.drawable.baseline_person_24)
                .setContentTitle("附近動物資訊：")
                .setContentText(message)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(animalInfo))  // 加入動物資訊
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // 定義操作點擊後的Intent
        Intent intent = new Intent(getApplicationContext(), MapanimalActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(Mapshow_animal_Activity.this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        builder.setContentIntent(pendingIntent);

        // 發出通知

        notificationManager.notify(notificationId, builder.build());
        notificationId++;
    }
    private void showCurrentLocation(Location lastKnownLocation) {
        Drawable nowicon = getResources().getDrawable(R.drawable.baseline_person_pin_48);
        // 將當前位置轉換為 GeoPoint 對象
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
        //updateDistance(lastKnownLocation);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapshow_animal);
        getSupportActionBar().hide();


        //map set
        mapView = findViewById(R.id.mapviews);
        mapsetting();
        //default map position
        default_set();
        //switch event
        mapswitch = findViewById(R.id.map_toggles);

        mapswitch.setChecked(isMapA);
        switch_click();
        //資料庫導入收容所位置
        // 初始化 lastKnownLocation

        //資料庫導入通報位置
        //執行緒
        excuteThread();
        //讀取資料庫

        //連線至資料庫
        String type = "loading_animaldata";
        //BackgroundWorker_foranimal worker = new BackgroundWorker_foranimal(this);
        //worker.execute(type);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // 設置定位更新的時間間隔和距離閾值，這裡設置為每 5 秒或每 10 公尺更新一次
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 1, this);
        // 監聽 Marker 點擊事件
        //locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
                 lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
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

        @Override
        public void onLocationChanged(@NonNull Location location) {
            // 更新 lastLocation
            lastLocation = location;

            // 其他你原有的處理，比如顯示當前位置的標記等
            showCurrentLocation(location);
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

}



