package com.anonyxhappie.dwarf.pms2;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static String IMDBURL = "https://api.themoviedb.org/3/movie/";

    public static String API = "?api_key="+ BuildConfig.THEMOVIEDB_ORG_API_KEY +"&language=en-US&page=1";

    public static ArrayList<MovieModel> favouriteList = new ArrayList<>();

    MovieAsyncTask movieAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(isNetworkAvailable()){
            movieAsyncTask = new MovieAsyncTask(this);
            movieAsyncTask.execute(IMDBURL+"popular"+API);
        }else {
            Toast.makeText(this, "Please Connect to Internet.", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(isNetworkAvailable()){
            switch (item.getItemId()){
                case R.id.item1:
                    movieAsyncTask = new MovieAsyncTask(this);
                    movieAsyncTask.execute(IMDBURL+"popular"+API);
                    return true;
                case R.id.item2:
                    movieAsyncTask = new MovieAsyncTask(this);
                    movieAsyncTask.execute(IMDBURL+"top_rated"+API);
                    return true;
//            case R.id.item3:
//                movieAsyncTask.updateUi(favouriteList);
//                return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }else {
            Toast.makeText(this, "Please Connect to Internet.", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Function to check network state
     * @return network state
     */
    private boolean isNetworkAvailable(){
        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }

    private class MovieAsyncTask extends AsyncTask<String, Void, ArrayList<MovieModel>> {

        Context context;
        GridView view;

        private MovieAsyncTask(Context context) {
            this.context = context;
        }

        @Override
        protected ArrayList<MovieModel> doInBackground(String... params) {

            if (params.length < 1 || params[0]==null){
                return null;
            }
            try {
                 return  Utils.extractDataFromJSON(Utils.makeHttpRequest(Utils.generateUrl(params[0])));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<MovieModel> movieList) {
            super.onPostExecute(movieList);
            updateUi(movieList);
        }

        private void updateUi(final ArrayList<MovieModel> movieList){
            view = (GridView) findViewById(R.id.grid_view);

            GridAdapter adapter = new GridAdapter(context, movieList);
            adapter.notifyDataSetChanged();
            view.setAdapter(adapter);

            view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(context, DetailsActivity.class);
                    MovieModel movieModel = (MovieModel) parent.getAdapter().getItem(position);
                    intent.putExtra("i_key", movieModel);
                    startActivity(intent);
                }
            });
        }

    }
}
