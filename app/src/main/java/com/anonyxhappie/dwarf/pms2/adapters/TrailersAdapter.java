package com.anonyxhappie.dwarf.pms2.adapters;

/**
 * Created by dwarf on 9/29/2017.
 */

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
 * Created by dwarf on 9/28/2017.
 */

public class TrailersAdapter extends ArrayAdapter<String> {

    LayoutInflater mInflater;
    Context context;

    @BindView(R.id.trailer)
    TextView trailer;

    public TrailersAdapter(Context context, ArrayList<String> objects) {
        super(context, 0, objects);
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if (v == null) {
            v = mInflater.inflate(R.layout.video_item, parent, false);
        }

        ButterKnife.bind(this, v);
        trailer.setText("Trailer " + String.valueOf(position + 1));
        return v;
    }
}
