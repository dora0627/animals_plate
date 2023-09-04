package com.example.animals_plate;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import adapter.functionListAdapter;
import models.functionListModel;


public class SettingFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        functionListModel[] functionListModels = new functionListModel[]{
            //圖片
                new functionListModel("hi",R.drawable.baseline_house_48),
                new functionListModel("hi",R.drawable.baseline_house_48),
                new functionListModel("hi",R.drawable.baseline_house_48),
                new functionListModel("關於我們",R.drawable.baseline_supervised_user_circle_24),
                new functionListModel("目前版本號",R.drawable.baseline_build_24),
                new functionListModel("問題回報",R.drawable.baseline_engineering_24),
                new functionListModel("使用說明",R.drawable.baseline_library_books_24),
                new functionListModel("常見問題",R.drawable.baseline_help_24),
                new functionListModel("隱私政策",R.drawable.baseline_privacy_tip_24),
                new functionListModel("服務條款",R.drawable.baseline_playlist_add_check_24),
                new functionListModel("版權聲明",R.drawable.baseline_house_48)
        };
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_list);
        functionListAdapter adapter = new functionListAdapter(functionListModels,getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        return view;

    }
}