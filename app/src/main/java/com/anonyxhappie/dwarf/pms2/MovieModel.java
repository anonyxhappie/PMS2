package com.anonyxhappie.dwarf.pms2;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dwarf on 9/23/2017.
 */

public class MovieModel implements Parcelable{

    boolean favourite = false;
    String release_date;
    int runtime;
    double vote_average;
    String homepage;
    String imdb_id;
    String original_language;
    String original_title;
    String overview;
    String poster_path;

    public MovieModel(){

    }

    public MovieModel(int runtime, double vote_average, String release_date, String homepage,
                      String imdb_id, String original_language, String original_title,
                      String overview, String poster_path) {
        this.runtime = runtime;
        this.vote_average = vote_average;
        this.release_date = release_date;
        this.homepage = homepage;
        this.imdb_id = imdb_id;
        this.original_language = original_language;
        this.original_title = original_title;
        this.overview = overview;
        this.poster_path = poster_path;
    }

    protected MovieModel(Parcel in) {
        favourite = in.readByte() != 0;
        release_date = in.readString();
        runtime = in.readInt();
        vote_average = in.readDouble();
        homepage = in.readString();
        imdb_id = in.readString();
        original_language = in.readString();
        original_title = in.readString();
        overview = in.readString();
        poster_path = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (favourite ? 1 : 0));
        dest.writeString(release_date);
        dest.writeInt(runtime);
        dest.writeDouble(vote_average);
        dest.writeString(homepage);
        dest.writeString(imdb_id);
        dest.writeString(original_language);
        dest.writeString(original_title);
        dest.writeString(overview);
        dest.writeString(poster_path);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MovieModel> CREATOR = new Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel in) {
            return new MovieModel(in);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public String getRelease_date() {
        return release_date;
    }

    public int getRuntime() {
        return runtime;
    }

    public double getVote_average() {
        return vote_average;
    }

    public String getHomepage() {
        return homepage;
    }

    public String getImdb_id() {
        return imdb_id;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPoster_path() {
        return poster_path;
    }
}
