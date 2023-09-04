package com.example.animals_plate;


import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AnimalAdapter extends RecyclerView.Adapter<animalViewHolder>{
    Context context;
    List<ClipData.Item> items;
    public AnimalAdapter(Context context,List<ClipData.Item> items){
        this.context=context;
        this.items = items;
    }



    @NonNull
    @Override
    public animalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new animalViewHolder(LayoutInflater.from(context).inflate(R.layout.showrecycler_for_animal_sample,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull animalViewHolder holder, int position) {
       holder.animal_name.setText(items.get(position).toString());
       holder.animal_specis.setText(items.get(position).toString());
       holder.animal_img.setImageResource(R.drawable.baseline_person_24);
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
