package com.anonyxhappie.dwarf.pms2.adapters;

/**
 * Created by dwarf on 9/29/2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anonyxhappie.dwarf.pms2.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dwarf on 9/28/2017.
 */

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {

    ArrayList<String> mReviews;
    LayoutInflater mInflater;
    Context context;

    public ReviewsAdapter(Context context, ArrayList<String> reviews) {
        mInflater = LayoutInflater.from(context);
        mReviews = reviews;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.review_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String review = mReviews.get(position);
        if (getItemCount() == 0) {
            holder.textView.setText("No Reviews Found");
        } else {
            holder.textView.setText(review);
        }

    }

    @Override
    public int getItemCount() {
        return mReviews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.review)
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
