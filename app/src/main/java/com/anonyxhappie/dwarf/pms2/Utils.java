package com.anonyxhappie.dwarf.pms2;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by dwarf on 9/23/2017.
 */

public class Utils {

    public static final String LOGTAG = Utils.class.getSimpleName();

    public Utils() {

    }

    public static String makeHttpRequest(URL url) throws IOException {

        String jsonResponse = "";
        if (url==null){
            return jsonResponse;
        }

        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;

        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            if (httpURLConnection.getResponseCode()==200){
                inputStream = httpURLConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }else {
                Log.e(LOGTAG, "Error Response Code : " + httpURLConnection.getResponseCode());
            }

        } catch (IOException e) {
            Log.e(LOGTAG, "Problem retrieving the JSON results.", e);
        } finally {
            if (httpURLConnection != null){
                httpURLConnection.disconnect();
            }
            if (inputStream != null){
                inputStream.close();
            }
        }

        return jsonResponse;
    }


    public static String readFromStream(InputStream inputStream) throws IOException{

        StringBuilder stringBuilder = new StringBuilder();

        if (inputStream!=null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line = bufferedReader.readLine();
            while (line!=null){
                stringBuilder.append(line);
                line = bufferedReader.readLine();
            }
        }
        return stringBuilder.toString();
    }


    public static ArrayList<MovieModel> extractDataFromJSON(String movieJSON){

        if (TextUtils.isEmpty(movieJSON)){
            return null;
        }

        ArrayList<MovieModel> movieList = new ArrayList<>();
        try {
            JSONObject root = new JSONObject(movieJSON);
            JSONArray results = root.getJSONArray("results");
            JSONObject obj;
            for (int i=0; i < results.length(); i++) {
                obj = results.getJSONObject(i);
                movieList.add(new MovieModel(0, obj.getDouble("vote_average"), obj.getString("release_date"), "",
                        "", obj.getString("original_language"), obj.getString("original_title"),
                        obj.getString("overview"), obj.getString("poster_path")));
            }
        } catch (JSONException e) {
            Log.e(LOGTAG, "Problem Parsing the JSON results.", e);
        }
        return movieList;
    }

    public static URL generateUrl(String string){

        URL url = null;
        try {
            url = new URL(string);
        } catch (MalformedURLException e) {
            Log.e(LOGTAG,"Error with creating url.", e);
        }

        return url;
    }

    public static String generateImageUrl(String string){
        return "http://image.tmdb.org/t/p/w185"+string;
    }
}
