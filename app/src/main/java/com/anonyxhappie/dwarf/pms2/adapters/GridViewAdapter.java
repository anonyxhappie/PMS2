package com.anonyxhappie.dwarf.pms2.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.anonyxhappie.dwarf.pms2.DetailsActivity;
import com.anonyxhappie.dwarf.pms2.R;
import com.anonyxhappie.dwarf.pms2.apis.MovieModel;
import com.anonyxhappie.dwarf.pms2.network.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dwarf on 9/28/2017.
 */

public class GridViewAdapter extends RecyclerView.Adapter<GridViewAdapter.ViewHolder> {

    ArrayList<MovieModel> mMovies;
    LayoutInflater mInflater;
    Context context;
    MovieModel movie;
    Intent intent;

    public GridViewAdapter(Context context, ArrayList<MovieModel> movies) {
        mInflater = LayoutInflater.from(context);
        this.mMovies = movies;
        this.context = context;
        intent = new Intent(context, DetailsActivity.class);
    }

    public void setmMovies(ArrayList<MovieModel> mMovies) {
        this.mMovies = mMovies;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.grid_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        movie = mMovies.get(position);
        RequestOptions options = new RequestOptions()
                .centerCrop().placeholder(R.drawable.icon_play);
        Glide.with(context)
                .load(Utils.generateImageUrl(movie.getPoster_path()))
                .apply(options)
                .into(holder.imageView);
        holder.imageView.setTag(R.id.image, position);
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

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
            intent.putExtra("i_key", mMovies.get((Integer) v.getTag(R.id.image)));
            context.startActivity(intent);
        }

    }

}
