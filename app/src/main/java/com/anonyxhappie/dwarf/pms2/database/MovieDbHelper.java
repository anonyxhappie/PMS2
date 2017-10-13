package com.anonyxhappie.dwarf.pms2.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dwarf on 10/13/2017.
 */

public class MovieDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "movieDb.db";
    private static final int VERSION = 1;

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String CREATE_TABLE = "CREATE TABLE " + MovieContract.MovieEntry.TABLE_NAME + "(" +
                MovieContract.MovieEntry._ID + "INTEGER PRIMARY KEY, " +
                MovieContract.MovieEntry.COLUMN_ID + "TEXT NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE + "TEXT NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_RELEASE_DATE + "TEXT NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_RUNTIME + "TEXT NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE + "TEXT NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_OVERVIEW + "TEXT NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_POSTER_PATH + "TEXT NOT NULL);";

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieContract.MovieEntry.TABLE_NAME);
        onCreate(db);
    }

}
