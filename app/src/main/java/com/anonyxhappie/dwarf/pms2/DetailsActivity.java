package com.anonyxhappie.dwarf.pms2;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anonyxhappie.dwarf.pms2.adapters.ReviewsAdapter;
import com.anonyxhappie.dwarf.pms2.adapters.TrailersAdapter;
import com.anonyxhappie.dwarf.pms2.apis.MovieModel;
import com.anonyxhappie.dwarf.pms2.database.MovieContract;
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
    CheckBox favourite;
    @BindView(R.id.runtime)
    TextView runtime;
    @BindView(R.id.trailers)
    RecyclerView trailerList;
    @BindView(R.id.reviews)
    RecyclerView reviewList;
    MovieModel movieModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);
        updateView();
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void updateView() {
        movieModel = getIntent().getParcelableExtra("i_key");

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


        favourite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //if (addToFavourites())
                    Toast.makeText(getBaseContext(), "Marked As Favourite.", Toast.LENGTH_SHORT).show();
                } else {
                    //if (removeFromFavourites())
                    Toast.makeText(getBaseContext(), "Removed From Favourites.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean addToFavourites() {

        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieContract.MovieEntry.COLUMN_ID, movieModel.getId());
        contentValues.put(MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE, movieModel.getOriginal_title());
        contentValues.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, movieModel.getRelease_date());
        contentValues.put(MovieContract.MovieEntry.COLUMN_RUNTIME, String.valueOf(movieModel.getRuntime()));
        contentValues.put(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE, String.valueOf(movieModel.getVote_average()));
        contentValues.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, movieModel.getOverview());
        contentValues.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH, movieModel.getPoster_path());

        Uri uri = getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, contentValues);
        if (uri == null) {
            return false;
        }
        finish();
        return true;
    }

    private boolean removeFromFavourites() {
        String id = String.valueOf(movieModel.getId());
        Uri uri = MovieContract.MovieEntry.CONTENT_URI;
        uri = uri.buildUpon().appendPath(id).build();
        int n = getContentResolver().delete(uri, null, null);
        return n != 0;
    }

}
