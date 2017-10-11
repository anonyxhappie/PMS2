package com.anonyxhappie.dwarf.pms2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.anonyxhappie.dwarf.pms2.adapters.ReviewsAdapter;
import com.anonyxhappie.dwarf.pms2.adapters.TrailersAdapter;
import com.anonyxhappie.dwarf.pms2.apis.MovieModel;
import com.anonyxhappie.dwarf.pms2.network.Utils;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {


    public static final String LOGTAG = DetailsActivity.class.getSimpleName();
    @BindView(R.id.movie_title)
    TextView title;
    @BindView(R.id.poster)
    ImageView poster;
    @BindView(R.id.overview)
    TextView overview;
    @BindView(R.id.rating)
    TextView ratings;
    @BindView(R.id.year)
    TextView date;
    @BindView(R.id.favourite)
    Button favourite;
    @BindView(R.id.runtime)
    TextView runtime;
    @BindView(R.id.trailers)
    RecyclerView trailerList;
    @BindView(R.id.reviews)
    RecyclerView reviewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        updateView();
    }

    private void updateView() {
        final MovieModel movieModel = getIntent().getParcelableExtra("i_key");

        title.setText(movieModel.getOriginal_title());
        Glide.with(this)
                .load(Utils.generateImageUrl(movieModel.getPoster_path()))
                .into(poster);
        runtime.setText(String.valueOf(movieModel.getRuntime()) + getString(R.string.unit));
        overview.setText(movieModel.getOverview());
        ratings.setText(String.valueOf(movieModel.getVote_average()));
        date.setText(movieModel.getRelease_date());

        // Log.i(LOGTAG, "::::"+ movieModel.getVideos().toString());

        ReviewsAdapter reviewsAdapter = new ReviewsAdapter(this, movieModel.getReviews());
        reviewList.setLayoutManager(new LinearLayoutManager(this));
        reviewList.setAdapter(reviewsAdapter);

        TrailersAdapter trailersAdapter = new TrailersAdapter(this, movieModel.getVideos());
        trailerList.setLayoutManager(new LinearLayoutManager(this));
        trailerList.setAdapter(trailersAdapter);

//
//        if (movieModel.isFavourite()) {
//            favourite.setText("Remove From Favourites");
//        } else {
//            favourite.setText("Mark As Favourite");
//        }
//
//        favourite.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (movieModel.isFavourite() == false) {
//                    movieModel.setFavourite(true);
//                    MainActivity.favouriteList.add(movieModel);
//                    favourite.setText("Remove From Favourites");
//                } else {
//                    movieModel.setFavourite(false);
//                    MainActivity.favouriteList.remove(movieModel);
//                    favourite.setText("Mark As Favourite");
//                }
//            }
//        });
    }

}
