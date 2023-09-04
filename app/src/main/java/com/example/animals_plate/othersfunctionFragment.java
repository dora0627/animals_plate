package com.example.animals_plate;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.facebook.shimmer.ShimmerFrameLayout;


public class othersfunctionFragment extends Fragment {
    CardView card_service,card_shop,card_community,card_version,card_aboutus,card_donate;
    ShimmerFrameLayout shimmerFrameLayout;
    LinearLayout dataLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_othersfunction, container, false);
        // Inflate the layout for this fragment
        // 先隐藏ActionBar

        shimmerFrameLayout = view.findViewById(R.id.shimmer_view2);
        dataLayout = view.findViewById(R.id.data_view2);
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

        card_service = view.findViewById(R.id.card_service);
        card_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it =new Intent(getActivity(),serviceActivity.class);
                startActivity(it);
            }
        });
        card_service.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    // 按下時改變透明度
                    int originalColor = card_service.getCardBackgroundColor().getDefaultColor();
                    int pressedColor = adjustAlpha(originalColor, 0.5f); // 調整透明度，這裡將透明度減少為原來的一半
                    card_service.setCardBackgroundColor(pressedColor);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP || motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {
                    // 釋放或取消觸摸時恢復原來的顏色
                    int originalColor = card_service.getCardBackgroundColor().getDefaultColor();
                    int pressedColor = adjustAlpha(originalColor, 1f);
                    card_service.setCardBackgroundColor(pressedColor);
                }
                return false;
            }
        });
        card_shop = view.findViewById(R.id.card_shop);
        card_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it =new Intent(getActivity(),shopanimalsActivity.class);
                startActivity(it);
            }
        });
        card_shop.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    // 按下時改變透明度
                    int originalColor = card_shop.getCardBackgroundColor().getDefaultColor();
                    int pressedColor = adjustAlpha(originalColor, 0.25f); // 調整透明度，這裡將透明度減少為原來的一半
                    card_shop.setCardBackgroundColor(pressedColor);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP || motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {
                    // 釋放或取消觸摸時恢復原來的顏色
                    int originalColor = card_shop.getCardBackgroundColor().getDefaultColor();
                    int pressedColor = adjustAlpha(originalColor, 1f);
                    card_shop.setCardBackgroundColor(pressedColor);
                }
                return false;
            }
        });
        card_community = view.findViewById(R.id.card_community);
        card_community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        card_community.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    // 按下時改變透明度
                    int originalColor = card_community.getCardBackgroundColor().getDefaultColor();
                    int pressedColor = adjustAlpha(originalColor, 0.25f); // 調整透明度，這裡將透明度減少為原來的一半
                    card_community.setCardBackgroundColor(pressedColor);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP || motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {
                    // 釋放或取消觸摸時恢復原來的顏色
                    int originalColor = card_community.getCardBackgroundColor().getDefaultColor();
                    int pressedColor = adjustAlpha(originalColor, 1f);
                    card_community.setCardBackgroundColor(pressedColor);
                }
                return false;
            }
        });
        card_version = view.findViewById(R.id.card_version);
        card_version.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        card_version.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    // 按下時改變透明度
                    int originalColor = card_version.getCardBackgroundColor().getDefaultColor();
                    int pressedColor = adjustAlpha(originalColor, 0.25f); // 調整透明度，這裡將透明度減少為原來的一半
                    card_version.setCardBackgroundColor(pressedColor);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP || motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {
                    // 釋放或取消觸摸時恢復原來的顏色
                    int originalColor = card_version.getCardBackgroundColor().getDefaultColor();
                    int pressedColor = adjustAlpha(originalColor, 1f);
                    card_version.setCardBackgroundColor(pressedColor);
                }
                return false;
            }
        });
        card_aboutus = view.findViewById(R.id.card_about);
        card_aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        card_aboutus.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    // 按下時改變透明度
                    int originalColor = card_aboutus.getCardBackgroundColor().getDefaultColor();
                    int pressedColor = adjustAlpha(originalColor, 0.25f); // 調整透明度，這裡將透明度減少為原來的一半
                    card_aboutus.setCardBackgroundColor(pressedColor);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP || motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {
                    // 釋放或取消觸摸時恢復原來的顏色
                    int originalColor = card_aboutus.getCardBackgroundColor().getDefaultColor();
                    int pressedColor = adjustAlpha(originalColor, 1f);
                    card_aboutus.setCardBackgroundColor(pressedColor);
                }
                return false;
            }
        });
        card_donate = view.findViewById(R.id.card_donate);
        card_donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        card_donate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    // 按下時改變透明度
                    int originalColor = card_donate.getCardBackgroundColor().getDefaultColor();
                    int pressedColor = adjustAlpha(originalColor, 0.25f); // 調整透明度，這裡將透明度減少為原來的一半
                    card_donate.setCardBackgroundColor(pressedColor);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP || motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {
                    // 釋放或取消觸摸時恢復原來的顏色
                    int originalColor = card_donate.getCardBackgroundColor().getDefaultColor();
                    int pressedColor = adjustAlpha(originalColor, 1f);
                    card_donate.setCardBackgroundColor(pressedColor);
                }
                return false;
            }
        });
        return view;
    }
    // 調整透明度的方法
    private int adjustAlpha(int color, float factor) {
        int alpha = Math.round(Color.alpha(color) * factor);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
    }

}


