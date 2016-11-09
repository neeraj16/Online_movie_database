package com.example.neeraj.online.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.support.v4.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.neeraj.online.Adapter.MoviesAdapter;
import com.example.neeraj.online.Clicks.ClickListener;
import com.example.neeraj.online.Clicks.RecyclerTouchListener;
import com.example.neeraj.online.Data.Movies;
import com.example.neeraj.online.R;
import com.example.neeraj.online.Store.SharedPreference;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kalka Repairs on 11/8/2016.
 */

public class Favorite extends Fragment {

    public static final String ARG_ITEM_ID = "favorite_list";

    private RecyclerView recyclerView;
    SharedPreference sharedPreference;
    ArrayList<Movies> favorites;

    //Activity activity;
    MoviesAdapter productListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.bollywood,container,false);
        recyclerView = (RecyclerView) rootview.findViewById(R.id.recyler_view2);
        sharedPreference = new SharedPreference();
        favorites = sharedPreference.getFavorites(getActivity().getApplicationContext());
        //Log.d("bbbbbbb",favorites.)
        if(favorites==null){
            //showAlert("Error",
              //      "no items");
        }
        else{
            if (favorites.size()==0){
              //  showAlert("Error",
                //        "no items");
            }
            if(favorites!=null){
                productListAdapter = new MoviesAdapter(getActivity().getApplicationContext(),favorites);
                recyclerView.setAdapter(productListAdapter);
                recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
                    @Override
                    public void onClick(View view, int position) {

                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }
                }));
            }
        }
        return rootview;
    }
    public void showAlert(String title, String message) {
        if (getActivity().getApplicationContext() != null && !getActivity().isFinishing()) {
            AlertDialog alertDialog = new AlertDialog.Builder(getActivity().getApplicationContext())
                    .create();
            alertDialog.setTitle(title);
            alertDialog.setMessage(message);
            alertDialog.setCancelable(false);

            // setting OK Button
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            // activity.finish();
                            getFragmentManager().popBackStackImmediate();
                        }
                    });
            alertDialog.show();
        }
    }
}
