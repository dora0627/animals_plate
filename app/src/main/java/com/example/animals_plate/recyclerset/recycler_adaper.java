package com.example.animals_plate.recyclerset;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.animals_plate.Animal;
import com.example.animals_plate.AnimalData;
import com.example.animals_plate.Animalrecycler;
import com.example.animals_plate.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class recycler_adaper extends RecyclerView.Adapter<recycler_adaper.myViewHolder> {

    private List<Animal> animalList;
    public void updateData(List<Animal> filteredAnimals) {
        this.animalList = filteredAnimals;
        notifyDataSetChanged();
    }
    public recycler_adaper(List<Animal> animalList){
        this.animalList = animalList;
    }
    public void setAnimalsList(List<Animal> filteredAnimals) {
        animalList = filteredAnimals;
    }
    public class myViewHolder extends RecyclerView.ViewHolder{
        private TextView nametxt,speciestxt,locationtxt;
        private ImageView img;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            nametxt = itemView.findViewById(R.id.recycler_name);
            speciestxt = itemView.findViewById(R.id.recycler_species);
            locationtxt = itemView.findViewById(R.id.recycler_location);
            img  =itemView.findViewById(R.id.recycler_image);
        }

    }

    @NonNull
    @Override
    public recycler_adaper.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item,parent,false);
       return new myViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull recycler_adaper.myViewHolder holder, int position) {
        String species = animalList.get(position).getSpecies();
        String name = animalList.get(position).getName();
        double lat  = animalList.get(position).getLatitude();
        double lon = (animalList.get(position).getLongitude());
        String url =animalList.get(position).getUri();
        holder.nametxt.setText(name);
        holder.speciestxt.setText("種類: "+species);
        holder.locationtxt.setText("經度: "+ lat +" 緯度:"+ lon);
        //holder.img.setImageURI(uri);
        Picasso.get()
                .load(url)
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        return animalList.size();
    }
}

