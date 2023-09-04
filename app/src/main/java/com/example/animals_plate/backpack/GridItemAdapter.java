package com.example.animals_plate.backpack;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.example.animals_plate.R;

import java.util.ArrayList;
import java.util.List;

public class GridItemAdapter extends ArrayAdapter<GridItem> {
    private Context context;
    private GridView gridView;
    private GridItemAdapter gridItemAdapter;
    private List<GridItem> gridItems;


    public GridItemAdapter(Context context, List<GridItem> gridItems) {
        super(context, 0, gridItems);
        this.context = context;
        this.gridItems = gridItems;
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_item_layout, parent, false);
        }

        GridItem currentItem = gridItems.get(position);
        gridView =  convertView.findViewById(R.id.grid_view_frame);

        ImageView imageView = convertView.findViewById(R.id.item_image);
        TextView textView = convertView.findViewById(R.id.item_name);

        imageView.setImageResource(currentItem.getImageResource());
        textView.setText(currentItem.getItemName());
        // 為每個項目添加點擊監聽器
        convertView.setOnClickListener(v -> showEquipDialog(currentItem.getItemName()));
        return convertView;
    }
    private void showEquipDialog(String itemName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("選擇動作");
        builder.setMessage("您想裝備 " + itemName + " 嗎？");

        builder.setPositiveButton("裝備", (dialog, which) -> {
            // 執行裝備動作
            // TODO: 實現裝備功能



        });

        builder.setNegativeButton("關閉", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}

