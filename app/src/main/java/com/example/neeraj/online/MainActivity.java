package com.example.neeraj.online;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.neeraj.online.Adapter.MoviesAdapter;
import com.example.neeraj.online.Clicks.ClickListener;
import com.example.neeraj.online.Clicks.RecyclerTouchListener;
import com.example.neeraj.online.Data.Movies;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import static com.example.neeraj.online.Extras.Keys.TMDBinfo.*;
import static com.example.neeraj.online.Extras.UrlEndpoints.URL_API_KEY;
import static com.example.neeraj.online.Extras.UrlEndpoints.URL_CHAR_AMEPERSAND;
import static com.example.neeraj.online.Extras.UrlEndpoints.URL_CHAR_QUESTION;
import static com.example.neeraj.online.Extras.UrlEndpoints.URL_LANGUAGE;
import static com.example.neeraj.online.Extras.UrlEndpoints.URL_NOW_PLAYING;
import static com.example.neeraj.online.Extras.UrlEndpoints.URL_UPCOMING;

public class MainActivity extends AppCompatActivity {

    ArrayList<Movies> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MoviesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview);
        recyclerView = (RecyclerView) findViewById(R.id.recyler_view);
        mAdapter = new MoviesAdapter(getApplicationContext());
       // pbb = (ProgressBar) findViewById(R.id.pb);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration((new DividerItemDecoration(this, LinearLayoutManager.VERTICAL)));
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Movies movies = movieList.get(position);
                Toast.makeText(getApplicationContext(), movies.getTitle(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        onj();

    }

    private void onj() {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET,
                URL_NOW_PLAYING+URL_CHAR_QUESTION+URL_API_KEY+URL_CHAR_AMEPERSAND+URL_LANGUAGE,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        movieList = showJson(response);
                        mAdapter.setMovieList(movieList);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(req);
    }

    private ArrayList<Movies> showJson(JSONObject response){
        ArrayList<Movies> moviesArrayList = new ArrayList<>();
        if (response == null || response.length() == 0) {
            return null;
        }
        try {
            if (response.has("results")) {
                StringBuilder sb = new StringBuilder();
                JSONArray jsonArray = response.getJSONArray(KEY_MOVIES);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String title = jsonObject.getString(KEY_TITLE);
                    String poster = jsonObject.getString(KEY_POSTER_PATH);
                    String vote = jsonObject.getString(KEY_VOTE_COUNT);
                    int id = jsonObject.getInt(KEY_ID);
                    int rate = jsonObject.getInt(KEY_VOTE_AVG);
                    sb.append(rate + "\n"+id + "\n");
                    Movies movies = new Movies();
                    movies.setTitle(title);
                    movies.setRating((float) rate/2);
                    movies.setImage_url("https://image.tmdb.org/t/p/w300"+poster);
                    movies.setName(vote);
                    movies.setId(id);
                    moviesArrayList.add(movies);

                }
                mAdapter.notifyDataSetChanged();
                Log.d("ccheck: ", sb.toString());
                Toast.makeText(MainActivity.this, sb.toString(), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(MainActivity.this, "server error", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return moviesArrayList;
    }

    private void prepareMovieData() {

        Movies movies = new Movies("Fury Road", "hello", "https://api.learn2crack.com/android/images/donut.png");
        movieList.add(movies);

        movies = new Movies("Inside Out", "neeraj", "https://api.learn2crack.com/android/images/eclair.png");
        movieList.add(movies);

        movies = new Movies("Star Wars", "neeraj", "https://api.learn2crack.com/android/images/froyo.png");
        movieList.add(movies);
        movies = new Movies("Inside Out", "neeraj", "https://api.learn2crack.com/android/images/eclair.png");
        movieList.add(movies);

        movies = new Movies("Star Wars", "neeraj", "https://api.learn2crack.com/android/images/froyo.png");
        movieList.add(movies);
        movies = new Movies("Inside Out", "neeraj", "https://api.learn2crack.com/android/images/eclair.png");
        movieList.add(movies);

        movies = new Movies("Star Wars", "Action", "https://api.learn2crack.com/android/images/froyo.png");
        movieList.add(movies);
        movies = new Movies("Star Wars", "Action", "https://api.learn2crack.com/android/images/froyo.png");
        movieList.add(movies);
        movies = new Movies("Inside Out", "Animation", "https://api.learn2crack.com/android/images/eclair.png");
        movieList.add(movies);

        movies = new Movies("Star Wars", "Action", "https://api.learn2crack.com/android/images/froyo.png");
        movieList.add(movies);
        movies = new Movies("Star Wars", "Action", "https://api.learn2crack.com/android/images/froyo.png");
        movieList.add(movies);
        movies = new Movies("Inside Out", "Animation", "https://api.learn2crack.com/android/images/eclair.png");
        movieList.add(movies);

        movies = new Movies("Star Wars", "Action", "https://api.learn2crack.com/android/images/froyo.png");
        movieList.add(movies);
        movies = new Movies("Star Wars", "Action", "https://api.learn2crack.com/android/images/froyo.png");
        movieList.add(movies);
        movies = new Movies("Inside Out", "Animation", "https://api.learn2crack.com/android/images/eclair.png");
        movieList.add(movies);

        movies = new Movies("Star Wars", "Action", "https://api.learn2crack.com/android/images/froyo.png");
        movieList.add(movies);

        mAdapter.notifyDataSetChanged();
        // pbb.setVisibility(View.INVISIBLE);
    }
}
