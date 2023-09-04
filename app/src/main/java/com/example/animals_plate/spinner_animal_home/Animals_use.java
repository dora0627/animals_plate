package com.example.animals_plate.spinner_animal_home;

import java.io.Serializable;

public class Animals_use implements Serializable {
    private String species;
    private int image;
    public Animals_use(){

    }
    public String getSpecie() {
        return species;
    }

    public void setSpecie(String species){
        this.species=species;
    }
    public int getImage(){
        return image;
    }
    public void setImage(int image){
        this.image = image;
    }

}
