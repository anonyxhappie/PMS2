package com.anonyxhappie.dwarf.pms2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dwarf on 9/28/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    ArrayList<MovieModel> mMovies;
    LayoutInflater mInflater;
    Context context;
//    Random random = new Random();

    public RecyclerViewAdapter(Context context, ArrayList<MovieModel> movies) {
        mInflater = LayoutInflater.from(context);
        mMovies = movies;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.grid_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MovieModel movie = mMovies.get(position);

//        holder.imageView.getLayoutParams().height = getRandomIntRange(300, 500);

        RequestOptions options = new RequestOptions()
                .centerCrop().placeholder(R.drawable.loading_spinner);
        Glide.with(context)
                .load(Utils.generateImageUrl(movie.getPoster_path()))
                .apply(options)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

//    public int getRandomIntRange(int min, int max){
//        return random.nextInt((max-min)+min)+max;
//    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.image)
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemClick(v, getAdapterPosition());
        }

        MovieModel getItem(int id) {
            return mMovies.get(id);
        }

        public void onItemClick(View view, int position) {
            Intent intent = new Intent(context, DetailsActivity.class);
            MovieModel movieModel = getItem(position);
            intent.putExtra("i_key", movieModel);
            context.startActivity(intent);
        }
    }

}
