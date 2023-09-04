package com.example.animals_plate.spinner_animal_home;

import android.util.Log;

import com.example.animals_plate.R;

import java.util.ArrayList;
import java.util.List;

public class spinner_animals_data {
    public static List<Animals_use> getAnimalsList(){
        List<Animals_use> animals_useList = new ArrayList<>();

        Animals_use all = new Animals_use();
        all.setSpecie("全部All");
        all.setImage(R.drawable.ic_more_horizental);
        animals_useList.add(all);

        Animals_use bird = new Animals_use();
        bird.setSpecie("鳥類Bird");
        bird.setImage(R.drawable.ic_bird_svg);
        animals_useList.add(bird);

        Animals_use dog = new Animals_use();
        dog.setSpecie("狗Dog");
        dog.setImage(R.drawable.ic_dog_svg);
        animals_useList.add(dog);

        Animals_use cat = new Animals_use();
        cat.setSpecie("貓Cat");
        cat.setImage(R.drawable.ic_cat_svg);
        animals_useList.add(cat);
        Log.d("list", animals_useList.toString());
        return animals_useList;
    }
}
