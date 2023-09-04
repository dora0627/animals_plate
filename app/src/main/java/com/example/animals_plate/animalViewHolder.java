package com.example.animals_plate;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class animalViewHolder extends RecyclerView.ViewHolder {
    ImageView animal_img;
    TextView animal_name,animal_specis,animal_date;
    public animalViewHolder(@NonNull View itemView) {
        super(itemView);
        animal_img = itemView.findViewById(R.id.load_animal_photo);
        animal_name = itemView.findViewById(R.id.load_animal_name);
        animal_specis = itemView.findViewById(R.id.load_animal_spices);
        animal_date = itemView.findViewById(R.id.load_animal_date);
    }
}
