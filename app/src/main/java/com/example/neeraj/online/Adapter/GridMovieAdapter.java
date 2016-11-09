package com.example.neeraj.online.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.neeraj.online.Data.Movies;
import com.example.neeraj.online.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Kalka Repairs on 11/5/2016.
 */

public class GridMovieAdapter extends RecyclerView.Adapter<GridMovieAdapter.MyMovies> {

    private Context c;
    private ArrayList<Movies> arrayList = new ArrayList<>();

    public GridMovieAdapter(Context c) {
        this.c = c;
    }

    public void setList(ArrayList<Movies> moviesArrayList){
        arrayList = moviesArrayList;
    }

    @Override
    public MyMovies onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_grid,parent,false);
        return new MyMovies(view);
    }

    @Override
    public void onBindViewHolder(MyMovies holder, int position) {

        Movies movies = arrayList.get(position);
        holder.title.setText(movies.getTitle());
        //holder.genre.setText(movies.getName());
        // holder.year.setText(movies.getName());
        //Picasso.with(context).load(android_versions.get(i).getAndroid_image_url()).resize(120, 60).into(viewHolder.img_android);
        Picasso.with(c).load(movies.getImage_url()).
                resize(110,170).onlyScaleDown().error(R.drawable.error).placeholder(R.drawable.loading).
                into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        return arrayList==null?0:arrayList.size();
    }

    public class MyMovies extends RecyclerView.ViewHolder {

        TextView title;
        ImageView imageView;

        public MyMovies(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.img_grid);
            title = (TextView) itemView.findViewById(R.id.grid_image_title);

        }
    }
}
