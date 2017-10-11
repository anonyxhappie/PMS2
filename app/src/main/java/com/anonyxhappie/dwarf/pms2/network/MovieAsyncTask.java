package com.anonyxhappie.dwarf.pms2.network;

import android.content.Context;
import android.os.AsyncTask;

import com.anonyxhappie.dwarf.pms2.apis.MovieModel;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by dwarf on 9/28/2017.
 */

public class MovieAsyncTask extends AsyncTask<String, Void, ArrayList<MovieModel>> {

    Context context;
    AsyncTaskCompleteListener<ArrayList<MovieModel>> listener;

    public MovieAsyncTask(Context context, AsyncTaskCompleteListener<ArrayList<MovieModel>> listener
    ) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected ArrayList<MovieModel> doInBackground(String... params) {

        if (params.length < 1 || params[0] == null) {
            return null;
        }
        try {
            return Utils.extractDataFromJSON(Utils.makeHttpRequest(Utils.generateUrl(params[0])));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected void onPostExecute(ArrayList<MovieModel> movieList) {
        super.onPostExecute(movieList);
        listener.onTaskComplete(movieList);
    }

}
