package com.anonyxhappie.dwarf.pms2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.anonyxhappie.dwarf.pms2.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dwarf on 9/29/2017.
 */

public class ReviewsAdapter extends ArrayAdapter<String> {

    LayoutInflater mInflater;
    Context context;

    @BindView(R.id.review)
    TextView review;

    public ReviewsAdapter(Context context, ArrayList<String> objects) {
        super(context, 0, objects);
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if (v == null) {
            v = mInflater.inflate(R.layout.review_item, parent, false);
        }

        ButterKnife.bind(this, v);
        review.setText(getItem(position));
        return v;
    }
}