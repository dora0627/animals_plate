package com.example.animals_plate;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.example.animals_plate.MapanimalActivity;
//import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


public class MapFragment extends Fragment {
Button btnf,btnl;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this
        //創建這個 Fragment 的畫面，並將畫面存入變量 view 中
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        btnf = view.findViewById(R.id.map_btn_for_f);
        btnl = view.findViewById(R.id.map_btn_for_l);
        btnf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //至外來種地圖

            }
        });
        btnl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //至流浪動物地圖
                Intent ita = new Intent(getActivity(),MapanimalActivity.class);
                startActivity(ita);
            }
        });
        return inflater.inflate(R.layout.fragment_map, container, false);
    }
}