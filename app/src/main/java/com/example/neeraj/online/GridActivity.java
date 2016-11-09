package com.example.neeraj.online;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.neeraj.online.Adapter.GridMovieAdapter;
import com.example.neeraj.online.Adapter.MoviesAdapter;
import com.example.neeraj.online.Clicks.ClickListener;
import com.example.neeraj.online.Clicks.RecyclerTouchListener;
import com.example.neeraj.online.Data.Movies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.neeraj.online.Extras.Keys.TMDBinfo.KEY_ID;
import static com.example.neeraj.online.Extras.Keys.TMDBinfo.KEY_MOVIES;
import static com.example.neeraj.online.Extras.Keys.TMDBinfo.KEY_POSTER_PATH;
import static com.example.neeraj.online.Extras.Keys.TMDBinfo.KEY_TITLE;
import static com.example.neeraj.online.Extras.Keys.TMDBinfo.KEY_VOTE_COUNT;

/**
 * Created by Kalka Repairs on 11/5/2016.
 */

public class GridActivity extends AppCompatActivity {

    ArrayList<Movies> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private GridMovieAdapter mAdapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview);
        recyclerView = (RecyclerView) findViewById(R.id.recyler_view);
        mAdapter = new GridMovieAdapter(getApplicationContext());
        // pbb = (ProgressBar) findViewById(R.id.pb);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(),3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.addItemDecoration((new DividerItemDecoration(this, LinearLayoutManager.VERTICAL)));
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                //Movies movies = movieList.get(position);
                Toast.makeText(getApplicationContext(), "clicked: "+position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        onj();

    }

    private void onj() {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, "https://api.themoviedb.org/3/movie/upcoming?api_key=fe0ed42c23aecfce4c955fe18ce9209a&language=en-US",
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        movieList = showJson(response);
                        mAdapter.setList(movieList);
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
                    String poster = jsonObject.getString(KEY_POSTER_PATH);
                    int id = jsonObject.getInt(KEY_ID);
                    sb.append(poster + "\n"+id + "\n");
                    Movies movies = new Movies();
                    //movies.setTitle(title);
                    movies.setImage_url("https://image.tmdb.org/t/p/w300"+poster);
                    //movies.setName(vote);
                    movies.setId(id);
                    moviesArrayList.add(movies);

                }
                mAdapter.notifyDataSetChanged();
                Log.d("ccheck: ", sb.toString());
                Toast.makeText(GridActivity.this, sb.toString(), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(GridActivity.this, "server error", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return moviesArrayList;
    }
}
