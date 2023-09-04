package com.example.animals_plate;

public class Animal {
    public int id;
    public String name;
    public String species;
    public double latitude;
    public double longitude;
    public String uri;
    public Animal(String name,String species,double latitude,double longitude,String uri){
        this.name = name;
        this.species = species;
        this.latitude = latitude;
        this.longitude = longitude;
        this.uri = uri;
    }

    public int getId() {
        return id;
    }
    public String getUri(){return uri;}


    public String getName(){return name;}
    public String getSpecies(){return species;}
    public double getLatitude(){return latitude;}
    public double getLongitude(){return longitude;}
}
