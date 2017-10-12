package com.anonyxhappie.dwarf.pms2;

import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.anonyxhappie.dwarf.pms2.adapters.RecyclerViewAdapter;
import com.anonyxhappie.dwarf.pms2.apis.MovieModel;
import com.anonyxhappie.dwarf.pms2.network.MovieAsyncTask;
import com.anonyxhappie.dwarf.pms2.network.Utils;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<MovieModel>> {

    public static final int SEARCH_LOADER = 0;
    public static final String BUNDLE_URL_KEY = "com.anonyxhappie.dwarf.pms2";
    public static String IMDBURL = "https://api.themoviedb.org/3/movie/";
    public static String API = "?api_key="+ BuildConfig.THEMOVIEDB_ORG_API_KEY +"&language=en-US&page=1";
    public static ArrayList<MovieModel> favouriteList = new ArrayList<>();
    MovieAsyncTask movieAsyncTask;
    RecyclerView view;
    RecyclerViewAdapter adapter;
    Bundle queryBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(isNetworkAvailable()){

            view = (RecyclerView) findViewById(R.id.grid_view);

            if (getBaseContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
                view.setLayoutManager(new GridLayoutManager(getBaseContext(), 2));
            else
                view.setLayoutManager(new GridLayoutManager(getBaseContext(), 4));

            adapter = new RecyclerViewAdapter(getBaseContext(), new ArrayList<MovieModel>());
            view.setAdapter(adapter);
            queryBundle = new Bundle();
            queryBundle.putString(BUNDLE_URL_KEY, IMDBURL + "popular" + API);

            getSupportLoaderManager().initLoader(SEARCH_LOADER, queryBundle, this);

//            movieAsyncTask = new MovieAsyncTask(this, new MovieAsyncTaskListener());
//            movieAsyncTask.execute();

        }else {
            Toast.makeText(this, "Please Connect to Internet.", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(SEARCH_LOADER, null, this);
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
                    queryBundle.putString(BUNDLE_URL_KEY, IMDBURL + "popular" + API);
                    getSupportLoaderManager().restartLoader(SEARCH_LOADER, queryBundle, this);
                    adapter.notifyDataSetChanged();
                    return true;
                case R.id.item2:
                    queryBundle.putString(BUNDLE_URL_KEY, IMDBURL + "top_rated" + API);
                    getSupportLoaderManager().restartLoader(SEARCH_LOADER, queryBundle, this);
                    adapter.notifyDataSetChanged();
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

    @Override
    public Loader<ArrayList<MovieModel>> onCreateLoader(int id, final Bundle args) {

        return new AsyncTaskLoader<ArrayList<MovieModel>>(this) {

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                if (args == null) {
                    return;
                }
                forceLoad();
            }

            @Override
            public ArrayList<MovieModel> loadInBackground() {
                String stringUrl = args.getString(BUNDLE_URL_KEY);
                Log.v(MainActivity.class.getSimpleName(), "bundle:::" + stringUrl);
                if (stringUrl == null | TextUtils.isEmpty(stringUrl)) {
                    return null;
                }

                try {
                    return Utils.extractDataFromJSON(Utils.makeHttpRequest(Utils.generateUrl(stringUrl)));
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onStopLoading() {
                super.onStopLoading();
                cancelLoad();
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<MovieModel>> loader, ArrayList<MovieModel> movieList) {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<MovieModel>> loader) {
    }

//
//    public class MovieAsyncTaskListener implements AsyncTaskCompleteListener<ArrayList<MovieModel>> {
//
//        public MovieAsyncTaskListener() {
//        }
//
//        @Override
//        public void onTaskComplete(ArrayList<MovieModel> movieList) {
//            adapter.setMovies(movieList);
//            view.setAdapter(adapter);
//            adapter.notifyDataSetChanged();
//        }
//
//    }
}
