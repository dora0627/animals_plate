package com.example.animals_plate;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.io.IOException;


public class InfoFragment extends Fragment {
    private static final int PICK_IMAGE = 1;
    private boolean isExpanded = false; //是否展開
    ImageView user_photo,info_more,info_edit,info_share;
    TextView userinfoname,userinfolv,userinfoxp,userinfophoto,userinfopoint,userinforeportcount,userinfocompleted,userinfoquantity;
    CardView info_shopcard;
    LinearLayout dataLayout;
    ShimmerFrameLayout shimmerFrameLayout;
    //EditText edtname,edtmail,edtpwd,edtnpwd;
    //Button btnupdate;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        //使用者資料
        userinfoname = view.findViewById(R.id.userinfo_name);
        userinfolv = view.findViewById(R.id.userinfo_level);
        user_photo = view.findViewById(R.id.userinfophoto);
        userinfopoint = view.findViewById(R.id.credit);
        userinforeportcount = view.findViewById(R.id.userinfo_report_count);
        userinfocompleted = view.findViewById(R.id.userinfo_tasks_completed);
        userinfoquantity = view.findViewById(R.id.userinfo_redemption_quantity);
        // 從 UserData 類中讀取使用者名稱
        String username = UserData.username;
        String level = UserData.level;
        String photo = UserData.photo;
        String experience = UserData.experience;
        int points = UserData.points;
        int reportCount = UserData.reportCount;
        int tasksCompleted = UserData.tasksCompleted;
        int redemptionQuantity = UserData.redemptionQuantity;
        // 使用使用者名稱顯示相應的內容
        userinfoname.setText(username);
        userinfolv.setText("LV"+level);
        userinfopoint.setText(String.valueOf(points));
        userinforeportcount.setText(String.valueOf(reportCount));
        userinfocompleted.setText(String.valueOf(tasksCompleted));
        userinfoquantity.setText(String.valueOf(redemptionQuantity));
        //骨架視窗
        shimmerFrameLayout = view.findViewById(R.id.shimmer_view);
        dataLayout = view.findViewById(R.id.data_view);
        dataLayout.setVisibility(View.INVISIBLE);
        shimmerFrameLayout.startShimmerAnimation();


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dataLayout.setVisibility(View.VISIBLE);
                shimmerFrameLayout.stopShimmerAnimation();
                shimmerFrameLayout.setVisibility(View.INVISIBLE);
            }
        },2000);

        info_more = view.findViewById(R.id.info_more);
        info_edit = view.findViewById(R.id.info_edit);
        info_share = view.findViewById(R.id.info_share);

        info_share.setVisibility(View.INVISIBLE);
        info_edit.setVisibility(View.INVISIBLE);
        info_shopcard = view.findViewById(R.id.info_card_shop);
        info_shopcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getActivity(),shopanimalsActivity.class);
                startActivity(it);
            }
        });
        info_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isExpanded){
                    info_share.setVisibility(View.INVISIBLE);
                    info_edit.setVisibility(View.INVISIBLE);
                }else{
                    info_share.setVisibility(View.VISIBLE);
                    info_edit.setVisibility(View.VISIBLE);
                }
                isExpanded = !isExpanded;

            }
        });

        return view;
    }
    ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            Uri uri = data.getData();
                            try {
                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                                // 指定縮放後的寬度和高度
                                int newWidth = 100;
                                int newHeight = 100;

                                // 計算縮放比例
                                float scaleWidth = ((float) newWidth) / bitmap.getWidth();
                                float scaleHeight = ((float) newHeight) / bitmap.getHeight();

                                // 建立縮放後的影像
                                Matrix matrix = new Matrix();
                                matrix.postScale(scaleWidth, scaleHeight);
                                Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

                                //將圖片轉成圓形
                                Bitmap output = Bitmap.createBitmap(newBitmap.getWidth(), newBitmap.getHeight(), Bitmap.Config.ARGB_8888);
                                Canvas canvas = new Canvas(output);
                                Paint paint = new Paint();
                                paint.setColor(Color.RED);
                                paint.setStyle(Paint.Style.FILL);
                                int centerX = newBitmap.getWidth() / 2;
                                int centerY = newBitmap.getHeight() / 2;
                                int radius = Math.min(centerX, centerY);
                                canvas.drawCircle(centerX, centerY, radius, paint);
                                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                                canvas.drawBitmap(newBitmap, 0, 0, paint);
                                user_photo.setImageBitmap(output);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });


    private void selectImage(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        resultLauncher.launch(intent);
    }

}