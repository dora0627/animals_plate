package com.example.animals_plate;





import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

public class BackgroundWorker extends AsyncTask<String,Void,String> {
    Context context;
    AlertDialog alertDialog;
    private String username;
    String userinfo;
    int i=0;
    BackgroundWorker (Context ctx) {
        context = ctx;
    }
private  String readStream(InputStream in) throws IOException{
    StringBuilder sb = new StringBuilder();
    BufferedReader reader = new BufferedReader(new InputStreamReader(in));

    String line;
    while ((line = reader.readLine()) != null) {
        sb.append(line);
    }

    return sb.toString();
}
    private void getTodayData(String username){

        // 開始宣告 HTTP 連線需要的物件，這邊通常都是一綑的
        try{
            URL url = new URL("http://192.168.50.114/dailyreport_get.php?" );

            // 開始宣告 HTTP 連線需要的物件，這邊通常都是一綑的
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // 建立 Google 比較挺的 HttpURLConnection 物件
            connection.setRequestMethod("GET");
            // 設定連線方式為 POST
            connection.setDoOutput(true); // 允許輸出
            connection.setDoInput(true); // 允許讀入
            connection.setUseCaches(false); // 不使用快取
            // 寫入 POST 資料
            String postData = "username=" + URLEncoder.encode(UserData.username, "UTF-8");
            OutputStream outputStream = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            writer.write(postData);
            writer.flush();
            writer.close();
            outputStream.close();
            connection.connect();




        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void getuserinfo(String usernames){
        String userinfo_url = "http://192.168.50.114/userinfo.php";
        //String userinfo_url = "http://192.168.137.1//userinfo.php";
        HttpURLConnection connection = null;
        try{
            URL apiUrl = new URL(userinfo_url);
            connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            // 傳遞參數
            OutputStream outputStream = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
            writer.write(post_data);
            writer.flush();
            writer.close();
            outputStream.close();

            // 讀取回應
            InputStream inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String result = "";
            String line;
            while ((line = reader.readLine()) != null) {
                result += line;
            }
            reader.close();
            inputStream.close();

            // 解析回應，獲取使用者資料
            JSONObject jsonObject = new JSONObject(result);
            String level = jsonObject.getString("level");
            String photo = jsonObject.getString("photo");
            String experience = jsonObject.getString("experience");
            int points = jsonObject.getInt("points");
            int reportCount = jsonObject.getInt("report_count");
            int tasksCompleted = jsonObject.getInt("tasks_completed");
            int redemptionQuantity = jsonObject.getInt("redemption_quantity");

            // 在這裡使用獲取到的使用者資料進行相應的操作或顯示
            // 例如更新界面上的資料
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        //手機 192.168.72.189
        //String login_url_p = "http://192.168.72.189/login.php";
        //String register_url_p="http://192.168.72.189/register.php";
        //String add_point_url_p="http://192.168.72.189/add_point.php";
        //學校
        String login_url_s = "http://192.168.137.1/login.php";
        String register_url_s="http://192.168.137.1/register.php";
        String add_point_url_s="http://192.168.137.1/add_point.php";
        String send_url_s = "http://192.168.137.1/sendermessage.php";
        //String show_animaldata_url = "http://192.168.137.1/show_animaldata.php";
        String login_url_h = "http://192.168.50.114/login.php";
        String register_url_h="http://192.168.50.114/register.php";
        String add_point_url_h="http://192.168.50.114/add_point.php";
        String update_animal_url_h="http://192.168.50.114/update_animal.php";
        String delete_animal_url_h="http://192.168.50.114/delete_animal.php";
        String post_url_h="http://192.168.50.114/posts.php";
        if(type.equals("login")) {

            try {
                String user_name = params[1];
                String password = params[2];
                this.username = user_name;
                URL url = new URL(login_url_h);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("user_name","UTF-8")+"="+URLEncoder.encode(user_name,"UTF-8")+"&"
                        +URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (type.equals("add_point")) {
            try {
                String name = params[1]; // 動物名稱
                String species = params[2]; // 動物種類
                String photo = params[3]; // 替換為圖片的URL或文件名
                String report_date = params[4]; // 替換為通報日期
                double longitude = Double.parseDouble(params[5]); // 替換為通報點的經度
                double latitude = Double.parseDouble(params[6]); // 替換為通報點的緯度
                String reporter_name = params[7]; // 替換為通報者名稱

                // 做一些前置检查，确保数据合法性和完整性
                Log.d("add_point", "Name: " + name);
                Log.d("add_point", "species: " + species);
                Log.d("add_point", "photo: " + photo);
                Log.d("add_point", "report_date: " + report_date);
                Log.d("add_point", "longitude: " + longitude);
                Log.d("add_point", "latitude: " + latitude);
                Log.d("add_point", "reporter_name: " + reporter_name);
                // 創建URL對象
                URL url = new URL(add_point_url_h); // 将URL_OF_YOUR_PHP_FILE替换为你的PHP文件的URL

                // 打開連接
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();

                // 發送POST请求
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8")
                        + "&" + URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8")
                        + "&" + URLEncoder.encode("species", "UTF-8") + "=" + URLEncoder.encode(species, "UTF-8")
                        + "&" + URLEncoder.encode("photo", "UTF-8") + "=" + URLEncoder.encode(photo, "UTF-8")
                        + "&" + URLEncoder.encode("report_date", "UTF-8") + "=" + URLEncoder.encode(report_date, "UTF-8")
                        + "&" + URLEncoder.encode("longitude", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(longitude), "UTF-8")
                        + "&" + URLEncoder.encode("latitude", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(latitude), "UTF-8")
                        + "&" + URLEncoder.encode("reporter_name", "UTF-8") + "=" + URLEncoder.encode(reporter_name, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                // 處理服物器響應
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;

            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (type.equals("getTodayData")) {
            try {
                URL url = new URL("http://192.168.50.114/dailyreport_get.php?" );
                // 開始宣告 HTTP 連線需要的物件，這邊通常都是一綑的


            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(type.equals("update_animal")){
            try{
                String id = params[1];
                String name = params[2];
                String species = params[3];
                String reporter_name =params[4];
                String newName = params[5];
                String newSpecies = params[6];
                String image_url = params[7];
                // 添加日志信息
                Log.d("MyApp", "ID: " + id);
                Log.d("MyApp", "Name: " + name);
                Log.d("MyApp", "Species: " + species);
                Log.d("MyApp", "Reporter Name: " + reporter_name);
                Log.d("MyApp", "New Name: " + newName);
                Log.d("MyApp", "New Species: " + newSpecies);
                Log.d("MyApp", "Image URL: " + image_url);

                URL url = new URL(update_animal_url_h);
                // 打開連接
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();

                // 發送POST请求
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8")
                        + "&" + URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8") // 传递动物的 ID
                        + "&" + URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") // 传递旧的动物名称
                        + "&" + URLEncoder.encode("species", "UTF-8") + "=" + URLEncoder.encode(species, "UTF-8")
                        + "&" +  URLEncoder.encode("reporter_name", "UTF-8") + "=" + URLEncoder.encode(reporter_name, "UTF-8")
                        + "&" + URLEncoder.encode("newName", "UTF-8") + "=" + URLEncoder.encode(newName, "UTF-8") // 传递新的动物名称
                        + "&" + URLEncoder.encode("newSpecies", "UTF-8") + "=" + URLEncoder.encode(newSpecies, "UTF-8") // 传递新的物种
                        + "&" + URLEncoder.encode("image_url", "UTF-8") + "=" + URLEncoder.encode(image_url, "UTF-8"); // 传递新的照片 URL
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                // 處理服物器響應
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        else if(type.equals("delete_animal")){
            try{
                String id = params[1];
                String name = params[2];
                String species = params[3];
                String reporter_name =params[4];

                // 添加日志信息
                Log.d("MyApp", "ID: " + id);
                Log.d("MyApp", "Name: " + name);
                Log.d("MyApp", "Species: " + species);
                Log.d("MyApp", "Reporter Name: " + reporter_name);


                URL url = new URL(delete_animal_url_h);
                // 打開連接
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();

                // 發送POST请求
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8")
                        + "&" + URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8") // 传递动物的 ID
                        + "&" + URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") // 传递旧的动物名称
                        + "&" + URLEncoder.encode("species", "UTF-8") + "=" + URLEncoder.encode(species, "UTF-8")
                        + "&" +  URLEncoder.encode("reporter_name", "UTF-8") + "=" + URLEncoder.encode(reporter_name, "UTF-8");

                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                // 處理服物器響應
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        else if (type.equals("posts")){
            try{
                String author_name = params[1];
                String title = params[2];
                String content = params[3];
                URL url = new URL(post_url_h);

                // 打開連接
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();

                // 發送POST请求
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8")
                        + "&" + URLEncoder.encode("author_name", "UTF-8") + "=" + URLEncoder.encode(author_name, "UTF-8")
                        + "&" + URLEncoder.encode("title", "UTF-8") + "=" + URLEncoder.encode(title, "UTF-8")
                        + "&" + URLEncoder.encode("content", "UTF-8") + "=" + URLEncoder.encode(content, "UTF-8");

                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                // 處理服物器響應
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        else if (type.equals("register")) {
            try {
                String name = params[1];
                String password = params[2];
                String email = params[3];
                String phone = params[4];

                Log.d("Register", "Name: " + name);
                Log.d("Register", "Password: " + password);
                Log.d("Register", "Email: " + email);
                Log.d("Register", "Phone: " + phone);
                URL url = new URL(register_url_h);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8")
                        + "&" + URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8")
                        + "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8")
                        + "&" + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8")
                        + "&" + URLEncoder.encode("phone", "UTF-8") + "=" + URLEncoder.encode(phone, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                // 處理注冊響應
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {

            alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle("Status");


    }

    @Override
    protected void onPostExecute(String result) {

        //sql_message.setText(result);
        alertDialog.setMessage(result);
        alertDialog.show();

        if (result.equals("login success !!!!! Welcome user")){
            // 登入成功後獲取到的使用者名稱
            String username = this.username;
            //
            getuserinfo(username);
            //目前下方只是測試資料
            String level = "3";
            String photo = "http://example.com/photo.jpg";
            String experience = "500";
            //int points = 100;
            //int reportCount = 5;
            //int tasksCompleted = 10;
            //int redemptionQuantity = 3;
            // 保存使用者名稱到 UserData 類中的 username 字段
            UserData.username = username;
            UserData.level = level;
            UserData.photo = photo;
            UserData.experience = experience;
            //UserData.points = points;
            //UserData.reportCount = reportCount;
            //UserData.tasksCompleted = tasksCompleted;
            //UserData.redemptionQuantity = redemptionQuantity;
            alertDialog.setIcon(R.drawable.baseline_info_success_24);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {


                    Intent intent2 = new Intent(context, IntroScreenActivity.class);
                    context.startActivity(intent2);
                }
            },1000);

        } else if (result.equals("register success")) {
            alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle("Status");

            alertDialog.setIcon(R.drawable.baseline_info_success_24);
            alertDialog.show();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent intent = new Intent(context, LoginActivity2.class);
                    context.startActivity(intent);
                }
            },1000);
        }else if (result.equals("更新成功")) {
            alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle("Status");
            alertDialog.setMessage("更新成功");
            alertDialog.setIcon(R.drawable.baseline_info_success_24);
            alertDialog.show();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(context, MapanimalActivity.class);
                    context.startActivity(intent);

                }
            },1000);
        }
        else if (result.equals("刪除成功")) {
            alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle("Status");
            alertDialog.setMessage("刪除成功");
            alertDialog.setIcon(R.drawable.baseline_info_success_24);
            alertDialog.show();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(context, MapanimalActivity.class);
                    context.startActivity(intent);

                }
            },1500);
        }
        else if(result.equals("成功新增通報點")) {
            alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle("Status");
            alertDialog.setMessage("成功新增通報點");
            alertDialog.setIcon(R.drawable.baseline_info_success_24);
            alertDialog.show();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(context, MapanimalActivity.class);
                    context.startActivity(intent);

                }
            },1500);
        }
        else if(result.equals("成功新增文章")) {
            alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle("通知");
            alertDialog.setMessage("成功新增文章");
            alertDialog.setIcon(R.drawable.baseline_info_success_24);
            alertDialog.show();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(context, BlogActivity.class);
                    context.startActivity(intent);

                }
            },1500);
        }
        else if (result.equals("找無此資料")) {
            alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle("Status");
            alertDialog.setMessage("找無此資料");
            alertDialog.setIcon(R.drawable.baseline_info_error_24);
            alertDialog.show();

        }

        else if (result != null && !result.isEmpty()) {
            if(result.equals("Message sent successfully!")){
                Log.d("send", "status: 成功" );
            }else{
                Log.d("send", "status: 失敗" );
            }

        }




        else{
            alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle("Status");

            alertDialog.setIcon(R.drawable.baseline_info_error_24);
            alertDialog.show();
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }




}

