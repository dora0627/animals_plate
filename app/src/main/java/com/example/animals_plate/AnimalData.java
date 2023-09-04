package com.example.animals_plate;

import android.net.Uri;

public class AnimalData {
    private int id;
    private String name;
    private String species;
    private String photo;
    private String reportDate;
    private String longitude;
    private String latitude;
    private String reporterName;
    private String image_uri;

    // 建構子
    public AnimalData(int id, String name, String species, String photo, String reportDate, String longitude, String latitude, String reporterName,String uri) {
        this.id = id;
        this.name = name;
        this.species = species;
        this.photo = photo;
        this.reportDate = reportDate;
        this.longitude = longitude;
        this.latitude = latitude;
        this.reporterName = reporterName;
        this.image_uri = uri;
    }




    // Getter 方法
    public int getId() {
        return id;
    }
    public String getImage_url(){return image_uri;}

    public String getName() {
        return name;
    }

    public  String getSpecies() {
        return species;
    }

    public String getPhoto() {
        return photo;
    }

    public String getReportDate() {
        return reportDate;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getReporterName() {
        return reporterName;
    }

    // Setter 方法 (如果需要的話)
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public void setImage_uri(String uri){this.image_uri=uri;}
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setReporterName(String reporterName) {
        this.reporterName = reporterName;
    }
}
