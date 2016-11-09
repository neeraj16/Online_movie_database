package com.example.neeraj.online.Fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.neeraj.online.DividerItemDecoration;
import com.example.neeraj.online.GridActivity;
import com.example.neeraj.online.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.neeraj.online.Extras.Keys.TMDBinfo.KEY_ID;
import static com.example.neeraj.online.Extras.Keys.TMDBinfo.KEY_MOVIES;
import static com.example.neeraj.online.Extras.Keys.TMDBinfo.KEY_POSTER_PATH;
import static com.example.neeraj.online.Extras.Keys.TMDBinfo.KEY_TITLE;
import static com.example.neeraj.online.Extras.Keys.TMDBinfo.KEY_VOTE_AVG;
import static com.example.neeraj.online.Extras.Keys.TMDBinfo.KEY_VOTE_COUNT;
import static com.example.neeraj.online.Extras.UrlEndpoints.URL_API_KEY;
import static com.example.neeraj.online.Extras.UrlEndpoints.URL_CHAR_AMEPERSAND;
import static com.example.neeraj.online.Extras.UrlEndpoints.URL_CHAR_QUESTION;
import static com.example.neeraj.online.Extras.UrlEndpoints.URL_LANGUAGE;
import static com.example.neeraj.online.Extras.UrlEndpoints.URL_NOW_PLAYING;

/**
 * Created by Kalka Repairs on 11/7/2016.
 */

public class Bollywood extends Fragment {

    ArrayList<Movies> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private GridMovieAdapter mAdapter;
    private SwipeRefreshLayout swipeContainer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.bollywood,container,false);
        recyclerView = (RecyclerView) rootview.findViewById(R.id.recyler_view2);
        swipeContainer = (SwipeRefreshLayout) rootview.findViewById(R.id.swipeContainer1);

        mAdapter = new GridMovieAdapter(getActivity().getApplicationContext());
        // pbb = (ProgressBar) findViewById(R.id.pb);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(),3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.addItemDecoration((new DividerItemDecoration(this, LinearLayoutManager.VERTICAL)));
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                //Movies movies = movieList.get(position);
                Toast.makeText(getActivity().getApplicationContext(), "clicked: "+position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                //fetchTimelineAsync(0);
                onj();
            }
        });

        swipeContainer.post(new Runnable() {
                                @Override
                                public void run() {
                                    swipeContainer.setRefreshing(true);

                                    onj();
                                }
                            }
        );

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);



        //onj();
        return rootview;
    }

    private void onj() {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, "https://api.themoviedb.org/3/movie/upcoming?api_key=fe0ed42c23aecfce4c955fe18ce9209a&language=en-USzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz",
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
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
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
                    int id = jsonObject.getInt(KEY_ID);
                    sb.append(title + "\n"+id + "\n");
                    Movies movies = new Movies();
                    //movies.setTitle(title);
                    movies.setImage_url("https://image.tmdb.org/t/p/w300"+poster);
                    //movies.setName(vote);
                    movies.setTitle(title);
                    movies.setId(id);
                    moviesArrayList.add(movies);

                }
                mAdapter.notifyDataSetChanged();
                swipeContainer.setRefreshing(false);
                Log.d("ccheck: ", sb.toString());
                //Toast.makeText(getActivity().getApplicationContext(), sb.toString(), Toast.LENGTH_LONG).show();
            } else {
                swipeContainer.setRefreshing(false);
                Toast.makeText(getActivity().getApplicationContext(), "server error", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return moviesArrayList;
    }


}
