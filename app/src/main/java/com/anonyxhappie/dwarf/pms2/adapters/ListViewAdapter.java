package com.anonyxhappie.dwarf.pms2.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anonyxhappie.dwarf.pms2.R;
import com.anonyxhappie.dwarf.pms2.network.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dwarf on 10/15/2017.
 */

public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.ViewHolder> {

    Context mContext;
    LayoutInflater mLayoutInflator;
    ArrayList<String> mArrayList;
    char mT; /* T - for Trailers, R - for Reviews*/
    Intent intent;
    String mItem;
    Uri uri;

    public ListViewAdapter(Context context, ArrayList<String> arrayList, char T) {
        mContext = context;
        mLayoutInflator = LayoutInflater.from(context);
        mArrayList = arrayList;
        mT = T;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflator.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        mItem = mArrayList.get(position);
        if (mT == 'T') {
            holder.textView.setText("Trailer " + String.valueOf(position + 1));
            uri = Uri.parse(Utils.generateVideoUrl(mItem));
        } else {
            holder.textView.setText(mItem);
            holder.imageView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        @BindView(R.id.content)
        TextView textView;
        @BindView(R.id.playIcon)
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mT == 'T') {
                intent = new Intent(Intent.ACTION_VIEW, uri);
                mContext.startActivity(intent);
            }
        }
    }
}
