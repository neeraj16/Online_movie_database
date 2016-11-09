package com.example.neeraj.online.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.neeraj.online.Data.Movies;
import com.example.neeraj.online.R;
import com.example.neeraj.online.Store.SharedPreference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kalka Repairs on 10/27/2016.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {


    private ArrayList<Movies> moviesList;
    private Context c;
    SharedPreference sharedPreference;
    //ProgressBar pbb;

    public MoviesAdapter(Context c) {
        //this.moviesList = moviesList;
        this.c = c;
    }
    public void setMovieList(ArrayList<Movies> moviesList){
        this.moviesList = moviesList;
        sharedPreference = new SharedPreference();
        notifyDataSetChanged();
    }
    public MoviesAdapter(Context c,ArrayList<Movies> moviesList){
        this.c = c;
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_list_row, parent, false);
         //pbb = (ProgressBar) itemView.findViewById(R.id.pb);
        return new MyViewHolder(itemView);
    }

    public void delete(int position){
        moviesList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final Movies movies = moviesList.get(position);
        holder.title.setText(movies.getTitle());
        holder.genre.setText(movies.getName());
       // holder.year.setText(movies.getName());
        //Picasso.with(context).load(android_versions.get(i).getAndroid_image_url()).resize(120, 60).into(viewHolder.img_android);
        Picasso.with(c).load(movies.getImage_url()).
                resize(92,140).onlyScaleDown().error(R.drawable.error).placeholder(R.drawable.loading).
                into(holder.year);
        holder.rating.setRating(movies.getRating());
       // holder.rating.setAlpha(1.0F);
        if (checkFavoriteItem(movies)) {
            holder.imageButton.setImageResource(R.drawable.bm_h_f);
            holder.imageButton.setTag("red");
            //addItem(movies);
        } else {
            holder.imageButton.setImageResource(R.drawable.bm_h_e);
            holder.imageButton.setTag("grey");
            //addItem(movies);
        }
       // holder.imageButton.setImageResource(R.drawable.bm_h_e);
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tag = holder.imageButton.getTag().toString();
                if(tag.equalsIgnoreCase("grey")){
                    sharedPreference.addFavorite(c, movies);
                    Toast.makeText(c, "Added to list", Toast.LENGTH_SHORT).show();
                    holder.imageButton.setTag("red");
                    holder.imageButton.setImageResource(R.drawable.bm_h_f);
                }
                else{
                    sharedPreference.removeFavorite(c, moviesList.get(position));
                    holder.imageButton.setTag("grey");
                    holder.imageButton.setImageResource(R.drawable.bm_h_e);
                    Toast.makeText(c,
                            "removed from list",
                            Toast.LENGTH_SHORT).show();

                }
                //holder.imageButton.setImageResource(R.drawable.bm_h_f);
            }
        });
        holder.moreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(c, "coming soon", Toast.LENGTH_SHORT).show();
            }
        });
       // pbb.setVisibility(View.INVISIBLE);
    }

    public void addItem(Movies lat){
        moviesList.add(lat);
        notifyDataSetChanged();
    }

    public boolean checkFavoriteItem(Movies checkProduct) {
        boolean check = false;
        List<Movies> favorites = sharedPreference.getFavorites(c);
        if (favorites != null) {
            for (Movies product : favorites) {
                if (product.equals(checkProduct)) {
                    check = true;
                    break;
                }
            }
        }
        return check;
    }


    @Override
    public int getItemCount() {
        return moviesList==null?0:moviesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, genre;
        public ImageView year;
        public ImageButton imageButton;
        public RatingBar rating;
        Button moreInfo;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.tv);
            genre = (TextView) view.findViewById(R.id.tv1);
            year = (ImageView) view.findViewById(R.id.image_view);
            imageButton = (ImageButton) view.findViewById(R.id.bookmark);
            rating = (RatingBar) view.findViewById(R.id.ratings);
            moreInfo = (Button) view.findViewById(R.id.more_info);
        }

    }
}
