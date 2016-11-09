package com.example.neeraj.online.Store;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.neeraj.online.Data.Movies;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.google.gson.Gson;

/**
 * Created by Kalka Repairs on 11/8/2016.
 */

public class SharedPreference {

    public static final String PREFS_NAME = "PRODUCT_APP";
    public static final String FAVORITES = "Product_Favorite";

    public SharedPreference() {
        super();
    }

    // This four methods are used for maintaining favorites.
    public void saveFavorites(Context context, List<Movies> favorites) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);

        editor.putString(FAVORITES, jsonFavorites);
       // Toast.makeText(context, settings.getString(FAVORITES,jsonFavorites), Toast.LENGTH_SHORT).show();
        editor.commit();
    }

    public void addFavorite(Context context, Movies product) {
        List<Movies> favorites = getFavorites(context);
        if (favorites == null)
            favorites = new ArrayList<Movies>();
        favorites.add(product);
        saveFavorites(context, favorites);
    }

    public void removeFavorite(Context context, Movies product) {
        ArrayList<Movies> favorites = getFavorites(context);
        if (favorites != null) {
            favorites.remove(product);
            saveFavorites(context, favorites);
        }
    }

    public ArrayList<Movies> getFavorites(Context context) {
        SharedPreferences settings;
        List<Movies> favorites;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(FAVORITES)) {
            String jsonFavorites = settings.getString(FAVORITES, null);
            Gson gson = new Gson();
            Movies[] favoriteItems = gson.fromJson(jsonFavorites,
                    Movies[].class);

            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<Movies>(favorites);
        } else
            return null;

        return (ArrayList<Movies>) favorites;
    }


}
