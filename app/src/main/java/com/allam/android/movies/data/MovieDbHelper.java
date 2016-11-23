package com.allam.android.movies.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.allam.android.movies.data.MovieDBSchema.FavouriteTable;
import com.allam.android.movies.data.MovieDBSchema.MovieTable;
import com.allam.android.movies.data.MovieDBSchema.PopularTable;
import com.allam.android.movies.data.MovieDBSchema.TopRatedTable;
import com.allam.android.movies.Model.Movie.ResultsBean;

import java.util.ArrayList;
import java.util.List;

import static android.provider.BaseColumns._ID;
import static com.allam.android.movies.data.MovieDBSchema.MovieTable.Columns.DATE;
import static com.allam.android.movies.data.MovieDBSchema.MovieTable.Columns.MOVIE_ID;
import static com.allam.android.movies.data.MovieDBSchema.MovieTable.Columns.OVERVIEW;
import static com.allam.android.movies.data.MovieDBSchema.MovieTable.Columns.PATH;
import static com.allam.android.movies.data.MovieDBSchema.MovieTable.Columns.RATE;
import static com.allam.android.movies.data.MovieDBSchema.MovieTable.Columns.TITLE;
import static com.allam.android.movies.data.MovieDBSchema.MovieTable.TABLE_NAME;

/**
 * Created by Allam on 09/08/2016.
 */
public class MovieDbHelper extends SQLiteOpenHelper {
    private static final int VERSION = 2;
    public final static String DATABASE_NAME = "myMovies.db";
    private SQLiteDatabase mDatabase;

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " + MovieTable.TABLE_NAME + "( " +
                _ID + " INTEGER PRIMARY KEY," +
                MOVIE_ID + " INTEGER NOT NULL UNIQUE ON CONFLICT REPLACE," +
                TITLE + " TEXT NOT NULL," +
                OVERVIEW + " TEXT NOT NULL," +
                RATE + " REAL NOT NULL," +
                DATE + " TEXT NOT NULL," +
                PATH + " TEXT NOT NULL);";


        String SQL_CREATE_POPULAR_TABLE = "CREATE TABLE " + PopularTable.TABLE_NAME + "( " +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MOVIE_ID + " INTEGER UNIQUE NOT NULL," +
                " FOREIGN KEY (" + PopularTable.Columns.MOVIE_ID + ") REFERENCES " +
                MovieTable.TABLE_NAME + " (" + MovieTable.Columns.MOVIE_ID + "), " +
                " UNIQUE (" + PopularTable.Columns.MOVIE_ID + ") ON CONFLICT REPLACE);";

        String SQL_CREATE_TOPRATED_TABLE = "CREATE TABLE " + TopRatedTable.TABLE_NAME + "( " +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MOVIE_ID + " INTEGER UNIQUE NOT NULL," +
                " FOREIGN KEY (" + TopRatedTable.Columns.MOVIE_ID + ") REFERENCES " +
                MovieTable.TABLE_NAME + " (" + MovieTable.Columns.MOVIE_ID + "), " +
                " UNIQUE (" + TopRatedTable.Columns.MOVIE_ID + ") ON CONFLICT REPLACE);";



        String SQL_CREATE_FAVOURITE_TABLE = "CREATE TABLE " + FavouriteTable.TABLE_NAME + "( " +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MOVIE_ID + " INTEGER UNIQUE NOT NULL," +
                " FOREIGN KEY (" + FavouriteTable.Columns.MOVIE_ID + ") REFERENCES " +
                MovieTable.TABLE_NAME + " (" + MovieTable.Columns.MOVIE_ID + "), " +
                " UNIQUE (" + FavouriteTable.Columns.MOVIE_ID + ") ON CONFLICT REPLACE);";


        db.execSQL(SQL_CREATE_MOVIE_TABLE);
        db.execSQL(SQL_CREATE_POPULAR_TABLE);
        db.execSQL(SQL_CREATE_TOPRATED_TABLE);
        db.execSQL(SQL_CREATE_FAVOURITE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PopularTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TopRatedTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + FavouriteTable.TABLE_NAME);
        onCreate(db);
    }


    private ContentValues getContentValues(ResultsBean item) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TITLE, item.getOriginal_title());
        contentValues.put(OVERVIEW, item.getOverview());
        contentValues.put(RATE, item.getVote_average());
        contentValues.put(DATE, item.getRelease_date());
        contentValues.put(PATH, item.getPoster_path());
        return contentValues;
    }

    public boolean insert(ResultsBean item) {
        mDatabase = this.getWritableDatabase();
        ContentValues contentValues = getContentValues(item);
        try {
            mDatabase.insertWithOnConflict(TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }


    public ResultsBean getMovieData(String title) {
        mDatabase = this.getWritableDatabase();
        ResultsBean resultsBean = new ResultsBean();
        Cursor cursor = mDatabase.query(TABLE_NAME, null, TITLE + " = ? ", new String[]{title}, null, null, null);

        try {


            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String Title = cursor.getString(cursor.getColumnIndex(TITLE));
                String Overview = cursor.getString(cursor.getColumnIndex(OVERVIEW));
                Double Rate = cursor.getDouble(cursor.getColumnIndex(RATE));
                String Date = cursor.getString(cursor.getColumnIndex(DATE));
                String Path = cursor.getString(cursor.getColumnIndex(PATH));

                resultsBean.setOriginal_title(Title);
                resultsBean.setOverview(Overview);
                resultsBean.setVote_average(Rate);
                resultsBean.setRelease_date(Date);
                resultsBean.setPoster_path(Path);
                cursor.moveToNext();

            }
            return resultsBean;
        } finally {
            cursor.close();
            this.close();
            mDatabase.close();
        }
    }

    // get This data if network is off
    public List<ResultsBean> getAllMoviesData() {
        List<ResultsBean> mList = new ArrayList<>();
        mDatabase = this.getWritableDatabase();
        ResultsBean resultsBean = new ResultsBean();
        Cursor cursor = mDatabase.query(TABLE_NAME, null, null, null, null, null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String Title = cursor.getString(cursor.getColumnIndex(TITLE));
                String Overview = cursor.getString(cursor.getColumnIndex(OVERVIEW));
                Double Rate = cursor.getDouble(cursor.getColumnIndex(RATE));
                String Date = cursor.getString(cursor.getColumnIndex(DATE));
                String Path = cursor.getString(cursor.getColumnIndex(PATH));

                resultsBean.setOriginal_title(Title);
                resultsBean.setOverview(Overview);
                resultsBean.setVote_average(Rate);
                resultsBean.setRelease_date(Date);
                resultsBean.setPoster_path(Path);
                mList.add(resultsBean);
                cursor.moveToNext();

            }
            return mList;
        } finally {
            cursor.close();
            this.close();
            mDatabase.close();
        }
    }


}
