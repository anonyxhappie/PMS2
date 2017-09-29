package com.anonyxhappie.dwarf.pms2.apis;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by dwarf on 9/23/2017.
 */

public class MovieModel implements Parcelable{

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
    int id;
    boolean favourite = false;
    String release_date;
    int runtime;
    double vote_average;
    String original_title;
    String overview;
    String poster_path;
    ArrayList<String> videos;
    ArrayList<String> reviews;

    public MovieModel(int id, String release_date, int runtime,
                      double vote_average, String original_title, String overview,
                      String poster_path, ArrayList<String> videos, ArrayList<String> reviews) {
        this.id = id;
        this.release_date = release_date;
        this.runtime = runtime;
        this.vote_average = vote_average;
        this.original_title = original_title;
        this.overview = overview;
        this.poster_path = poster_path;
        this.videos = videos;
        this.reviews = reviews;
    }

    protected MovieModel(Parcel in) {
        id = in.readInt();
        favourite = in.readByte() != 0;
        release_date = in.readString();
        runtime = in.readInt();
        vote_average = in.readDouble();
        original_title = in.readString();
        overview = in.readString();
        poster_path = in.readString();
        videos = in.createStringArrayList();
        reviews = in.createStringArrayList();
    }

    public int getId() {
        return id;
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

    public String getOriginal_title() {
        return original_title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public ArrayList<String> getVideos() {
        return videos;
    }

    public ArrayList<String> getReviews() {
        return reviews;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeByte((byte) (favourite ? 1 : 0));
        dest.writeString(release_date);
        dest.writeInt(runtime);
        dest.writeDouble(vote_average);
        dest.writeString(original_title);
        dest.writeString(overview);
        dest.writeString(poster_path);
        dest.writeStringList(videos);
        dest.writeStringList(reviews);
    }
}
