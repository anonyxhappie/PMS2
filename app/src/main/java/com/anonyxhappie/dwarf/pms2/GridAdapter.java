package com.anonyxhappie.dwarf.pms2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

/**
 * Created by dwarf on 9/23/2017.
 */

public class GridAdapter extends ArrayAdapter<MovieModel> {

    public GridAdapter(Context context, ArrayList<MovieModel> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if (v == null){
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            v = layoutInflater.inflate(R.layout.grid_item, parent, false);
        }

        MovieModel movieModel = getItem(position);

        ImageView imageView = (ImageView) v.findViewById(R.id.image);

        RequestOptions options = new RequestOptions()
                .centerCrop().placeholder(R.drawable.loading_spinner);
        Glide.with(getContext())
                .load(Utils.generateImageUrl(movieModel.getPoster_path()))
                .apply(options)
                .into(imageView);

        return v;
    }
}
