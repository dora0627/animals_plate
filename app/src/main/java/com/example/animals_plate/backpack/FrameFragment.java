package com.example.animals_plate.backpack;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.animals_plate.R;

import java.util.ArrayList;
import java.util.List;


public class FrameFragment extends Fragment {

    private GridView gridView;
    private GridItemAdapter gridItemAdapter;
    // 假設您有一個道具列表
    private String[] frameItems = {"頭相框1", "頭相框2", "頭相框3", /* 添加更多頭相框 */};
    private int[] frameImages = {R.drawable.baseline_warning_24, R.drawable.baseline_house_48, R.drawable.baseline_email_24, /* 添加更多頭相框的圖片 */};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_frame, container, false);

        // 初始化 GridView 和 GridItemAdapter
        gridView = rootView.findViewById(R.id.grid_view_frame);
        gridItemAdapter = new GridItemAdapter(getActivity(), generateGridItems());

        // 設置適配器
        gridView.setAdapter(gridItemAdapter);

        return rootView;
    }
    private List<GridItem> generateGridItems() {
        // 在這裡生成 GridView 中的項目數據，例如從資源中獲取圖片和名稱
        List<GridItem> gridItems = new ArrayList<>();
        gridItems.add(new GridItem(R.drawable.baseline_warning_24, "項目1"));
        gridItems.add(new GridItem(R.drawable.baseline_house_48, "項目2"));
        // 添加更多的項目...

        return gridItems;
    }
}