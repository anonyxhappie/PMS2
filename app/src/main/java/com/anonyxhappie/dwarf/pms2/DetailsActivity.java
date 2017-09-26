package com.anonyxhappie.dwarf.pms2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        final MovieModel movieModel = getIntent().getParcelableExtra("i_key");

        TextView title = (TextView) findViewById(R.id.movie_title);
        title.setText(movieModel.getOriginal_title());

        ImageView poster = (ImageView) findViewById(R.id.poster);
        Glide.with(this)
                .load(Utils.generateImageUrl(movieModel.getPoster_path()))
                .into(poster);
        TextView overview = (TextView) findViewById(R.id.overview);
        overview.setText(movieModel.getOverview());
        TextView ratings = (TextView) findViewById(R.id.rating);
        ratings.setText(String.valueOf(movieModel.getVote_average() ));
        TextView date = (TextView) findViewById(R.id.year);
        date.setText(movieModel.getRelease_date());

        final Button favourite = (Button) findViewById(R.id.favourite);
        if (movieModel.isFavourite()){
            favourite.setText("Remove From Favourites");
        }else{
            favourite.setText("Mark As Favourite");
        }

        favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (movieModel.isFavourite()==false){
                    movieModel.setFavourite(true);
                    MainActivity.favouriteList.add(movieModel);
                    favourite.setText("Remove From Favourites");
                }else{
                    movieModel.setFavourite(false);
                    MainActivity.favouriteList.remove(movieModel);
                    favourite.setText("Mark As Favourite");
                }
            }
        });
    }

}
