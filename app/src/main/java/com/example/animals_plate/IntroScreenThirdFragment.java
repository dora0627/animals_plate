package com.example.animals_plate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.animals_plate.toolbar_home.toolbar_homeActivity;


public class IntroScreenThirdFragment extends Fragment {

    ViewPager viewPager;
    TextView back,done,skip_bottoms;

    public IntroScreenThirdFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_intro_screen_third, container, false);
        viewPager = getActivity().findViewById(R.id.viewPager);
        back = view.findViewById(R.id.slideThirdBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(1);
            }
        });
        done = view.findViewById(R.id.slideThirdDone);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getActivity(),toolbar_homeActivity.class);
                startActivity(it);
            }
        });
        skip_bottoms = view.findViewById(R.id.skip_bottom3);
        String link = skip_bottoms.getText().toString();
        SpannableString spannableString = new SpannableString(link);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                //至忘記密碼頁面
                //Intent intent = new Intent(LoginActivity2.this, SignActivity3.class);
                // startActivity(intent);
            }
        };
        spannableString.setSpan(clickableSpan, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        skip_bottoms.setText(spannableString);
        skip_bottoms.setMovementMethod(LinkMovementMethod.getInstance());

        skip_bottoms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent it = new Intent(getActivity(), toolbar_homeActivity.class);
                startActivity(it);
            }
        });
        return view;
    }
}