package com.anonyxhappie.dwarf.pms2;

import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.anonyxhappie.dwarf.pms2.adapters.RecyclerViewAdapter;
import com.anonyxhappie.dwarf.pms2.apis.MovieModel;
import com.anonyxhappie.dwarf.pms2.network.AsyncTaskCompleteListener;
import com.anonyxhappie.dwarf.pms2.network.MovieAsyncTask;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static String IMDBURL = "https://api.themoviedb.org/3/movie/";

    public static String API = "?api_key="+ BuildConfig.THEMOVIEDB_ORG_API_KEY +"&language=en-US&page=1";

    public static ArrayList<MovieModel> favouriteList = new ArrayList<>();

    MovieAsyncTask movieAsyncTask;
    RecyclerView view;
    RecyclerViewAdapter adapter;
    ArrayList<MovieModel> mMovieList;

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


            movieAsyncTask = new MovieAsyncTask(this, new MovieAsyncTaskListener());
            movieAsyncTask.execute(IMDBURL+"popular"+API);
            adapter = new RecyclerViewAdapter(getBaseContext());

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
                    movieAsyncTask = new MovieAsyncTask(this, new MovieAsyncTaskListener());
                    movieAsyncTask.execute(IMDBURL+"popular"+API);
                    adapter.notifyDataSetChanged();
                    return true;
                case R.id.item2:
                    movieAsyncTask = new MovieAsyncTask(this, new MovieAsyncTaskListener());
                    movieAsyncTask.execute(IMDBURL+"top_rated"+API);
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

    public class MovieAsyncTaskListener implements AsyncTaskCompleteListener<ArrayList<MovieModel>> {

        public MovieAsyncTaskListener() {
        }

        @Override
        public void onTaskComplete(ArrayList<MovieModel> movieList) {
            adapter.setMovies(movieList);
            view.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }

    }
}
