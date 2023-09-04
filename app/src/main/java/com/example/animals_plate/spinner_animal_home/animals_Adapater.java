package com.example.animals_plate.spinner_animal_home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.animals_plate.R;
import com.example.animals_plate.apiTestActivity;

import java.util.List;

public class animals_Adapater extends BaseAdapter {
    private Context context;
    private List<Animals_use>animals_useList;

    public animals_Adapater(Context context, List<Animals_use> animals_useList) {
        this.context = context;
        this.animals_useList = animals_useList;
    }

    @Override
    public int getCount() {
        return animals_useList!=null ? animals_useList.size():0;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rootview = LayoutInflater.from(context).inflate(R.layout.animals_item,viewGroup,false);
        TextView txtspecies=rootview.findViewById(R.id.item_species);
        ImageView image=rootview.findViewById(R.id.item_image);

        txtspecies.setText(animals_useList.get(i).getSpecie());
        image.setImageResource(animals_useList.get(i).getImage());

        return rootview;
    }
}
