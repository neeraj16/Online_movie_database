package com.example.neeraj.online.Data;

/**
 * Created by Kalka Repairs on 10/27/2016.
 */

public class Movies {
    private String title,name,genere,Image_url;
    private int id;
    private float rating;
    public Movies(){}


    public Movies(String t, String n, String g){
        this.title = t;
        this.name = n;
        this.Image_url = g;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getImage_url() {
        return Image_url;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImage_url(String image_url) {
        this.Image_url = image_url;
    }

    public String getTitle(){
        return title;
    }

    public String getName(){
        return name;
    }

    public  String getGenere(){
        return genere;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public  void setName(String name){
        this.name = name;
    }

    public void setGenere(String genere){
        this.genere = genere;
    }

}
