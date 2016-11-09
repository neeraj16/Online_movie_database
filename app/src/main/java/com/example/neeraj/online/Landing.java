package com.example.neeraj.online;

import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;


import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.example.neeraj.online.Fragments.Bollywood;
import com.example.neeraj.online.Fragments.Favorite;
import com.example.neeraj.online.Fragments.Hollywood;

/**
 * Created by Kalka Repairs on 11/7/2016.
 */

public class Landing extends AppCompatActivity implements AHBottomNavigation.OnTabSelectedListener {


    AHBottomNavigation bottomNavigation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_frags);
        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bootom_bar);
        bottomNavigation.setBehaviorTranslationEnabled(true);
        bottomNavigation.setOnTabSelectedListener(this);
        this.NavItems();
        bottomNavigation.setOnNavigationPositionListener(new AHBottomNavigation.OnNavigationPositionListener() {
            @Override
            public void onPositionChange(int position) {
                if(position==0){
                    Hollywood hollywood = new Hollywood();
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_id,hollywood).commit();
                }else if(position==1){
                    Bollywood bollywood = new Bollywood();
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_id,bollywood).commit();
                }
                else if(position==2){
                    Bollywood bollywood1 = new Bollywood();
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_id,bollywood1).commit();
                }
            }
        });

    }



    private void NavItems(){
        AHBottomNavigationItem holly = new AHBottomNavigationItem("Hollywood",R.drawable.ic_h1);
        AHBottomNavigationItem bolly = new AHBottomNavigationItem("Bollywood",R.drawable.ic_b1);
        AHBottomNavigationItem other = new AHBottomNavigationItem("Other",R.drawable.ic_b1);

        bottomNavigation.addItem(holly);
        bottomNavigation.addItem(bolly);
        bottomNavigation.addItem(other);

        //bottomNavigation.setAccentColor(Color.parseColor("#F63D2B"));
        //bottomNavigation.setInactiveColor(Color.parseColor("#747474"));
       // bottomNavigation.setForceTint(true);
        //bottomNavigation.setColored(true);


        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#4DB6AC"));

        bottomNavigation.setCurrentItem(1);
    }

    @Override
    public boolean onTabSelected(int position, boolean wasSelected) {
        if(position==0){
            Hollywood hollywood = new Hollywood();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_id,hollywood).commit();
        }else if(position==1){
            Bollywood bollywood = new Bollywood();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_id,bollywood).commit();
        }
        else if(position==2){
            Favorite f1 = new Favorite();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_id,f1).commit();
        }
        return true;
    }

}
