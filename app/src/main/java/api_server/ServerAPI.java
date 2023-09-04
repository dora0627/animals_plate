package api_server;

import android.content.Context;
import android.os.AsyncTask;

import com.example.animals_plate.R;
import com.example.animals_plate.UserData;
import com.example.animals_plate.otp_mail;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class ServerAPI {
    public interface CheckResultCallback {
        void onCheckComplete(boolean exists);
    }
    public static void insertOTPRecord(String account, String email, String selectedFunction, String otp, String executionTime, String otpStatus) {
        new InsertOTPRecordTask().execute(account, email, selectedFunction, otp, executionTime, otpStatus);
    }
    public static void checkIfAccountEmailExists(String account, String email, CheckResultCallback callback) {
        new CheckAccountEmailTask(callback).execute(account, email);
    }

    private static class InsertOTPRecordTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            String account = params[0];
            String email = params[1];
            String selectedFunction = params[2];
            String otp = params[3];
            String otpStatus = params[4];

            // 處理插入 OTP 記錄的程式碼

            // 建立 HttpURLConnection 或 HttpsURLConnection，發送 POST 請求，將參數送至 PHP 進行插入
            // 可以參考之前的程式碼，類似地建立連線並處理請求
            try {
                // 建立連線
                String urlStr = "http://192.168.50.1147/insert_otp_record.php";
                URL url = new URL(urlStr);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);

                // 建立 POST 數據
                String postData = "account=" + URLEncoder.encode(account, "UTF-8")
                        + "&email=" + URLEncoder.encode(email, "UTF-8")
                        + "&selected_function=" + URLEncoder.encode(selectedFunction, "UTF-8")
                        + "&otp=" + URLEncoder.encode(otp, "UTF-8")
                        + "&otp_status=" + URLEncoder.encode(otpStatus, "UTF-8");

                // 寫入 POST 資料
                OutputStream outputStream = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                writer.write(postData);
                writer.flush();
                writer.close();
                outputStream.close();

                // 取得回應
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // 成功處理回應
                    // 你可以在這裡處理伺服器的回應，或執行其他操作
                } else {
                    // 處理失敗情況
                }

                connection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    private static class CheckAccountEmailTask extends AsyncTask<String, Void, Boolean> {
        private CheckResultCallback callback;

        public CheckAccountEmailTask(CheckResultCallback callback) {
            this.callback = callback;
        }

        @Override
        protected Boolean doInBackground(String... params) {
            String name = params[0];
            String email = params[1];
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                String urlStr = "http://192.168.50.114/check_account_email.php";
                URL url = new URL(urlStr);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST"); // 使用 POST 方法
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); // 設定 Content-Type

//              建立 POST 資料
                String postData = "name=" + URLEncoder.encode(otp_mail.username, "UTF-8") + "&" +
                        "email=" + URLEncoder.encode(otp_mail.email, "UTF-8");

//              寫入 POST 資料
                OutputStream outputStream = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                writer.write(postData);
                writer.flush();
                writer.close();
                outputStream.close();
                connection.connect(); // 開始連線

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String response = reader.readLine();
                    return response.equals("true");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (callback != null) {
                callback.onCheckComplete(result);
            }
        }
    }
}
