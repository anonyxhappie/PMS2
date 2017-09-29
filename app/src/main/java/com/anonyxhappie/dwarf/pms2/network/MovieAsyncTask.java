package com.anonyxhappie.dwarf.pms2.network;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.anonyxhappie.dwarf.pms2.apis.MovieModel;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by dwarf on 9/28/2017.
 */

public class MovieAsyncTask extends AsyncTask<String, Void, ArrayList<MovieModel>> {

    Context context;
    ProgressDialog dialog;
    AsyncTaskCompleteListener<ArrayList<MovieModel>> listener;

    public MovieAsyncTask(Context context, AsyncTaskCompleteListener<ArrayList<MovieModel>> listener
    ) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = ProgressDialog.show(context, "Hey, Welcome", "Getting Movies, Please Wait...", false, false);
    }

    @Override
    protected ArrayList<MovieModel> doInBackground(String... params) {

        if (params.length < 1 || params[0] == null) {
            return null;
        }
        try {
            return doInBackgroundHelper(params);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
        Toast.makeText(context, "Please Wait", Toast.LENGTH_LONG).show();
    }

    private ArrayList<MovieModel> doInBackgroundHelper(String... params) throws IOException {
        return Utils.extractDataFromJSON(Utils.makeHttpRequest(Utils.generateUrl(params[0])));
    }

    @Override
    protected void onPostExecute(ArrayList<MovieModel> movieList) {
        super.onPostExecute(movieList);
        dialog.dismiss();
        listener.onTaskComplete(movieList);
    }

}
