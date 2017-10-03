package com.anonyxhappie.dwarf.pms2.adapters;

/**
 * Created by dwarf on 9/29/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anonyxhappie.dwarf.pms2.R;
import com.anonyxhappie.dwarf.pms2.network.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dwarf on 9/28/2017.
 */
public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.ViewHolder> {

    ArrayList<String> mTrailers;
    LayoutInflater mInflater;
    Context context;
    String trailer;

    public TrailersAdapter(Context context, ArrayList<String> trailers) {
        mInflater = LayoutInflater.from(context);
        mTrailers = trailers;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.video_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        trailer = mTrailers.get(position);
        holder.textView.setText("Trailer " + String.valueOf(position + 1));
    }

    @Override
    public int getItemCount() {
        return mTrailers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.trailer)
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(Utils.generateVideoUrl(trailer)));
            context.startActivity(intent);
        }
    }

}

