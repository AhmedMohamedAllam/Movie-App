package com.allam.android.movies.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.allam.android.movies.Model.Movie;
import com.allam.android.movies.Model.Movie.ResultsBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.provider.BaseColumns._ID;
import static com.allam.android.movies.Database.MovieDBSchema.DATABASE_NAME;
import static com.allam.android.movies.Database.MovieDBSchema.MovieTable.Columns.DATE;
import static com.allam.android.movies.Database.MovieDBSchema.MovieTable.Columns.OVERVIEW;
import static com.allam.android.movies.Database.MovieDBSchema.MovieTable.Columns.PATH;
import static com.allam.android.movies.Database.MovieDBSchema.MovieTable.Columns.RATE;
import static com.allam.android.movies.Database.MovieDBSchema.MovieTable.Columns.TITLE;
import static com.allam.android.movies.Database.MovieDBSchema.MovieTable.NAME;

/**
 * Created by Allam on 09/08/2016.
 */
public class MovieDbHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private SQLiteDatabase mDatabase;

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {



        String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " + NAME + "( " +
                _ID + " INTEGER PRIMARY KEY, " +
                TITLE + " TEXT UNIQUE, " +
                OVERVIEW + " TEXT, " +
                RATE + " REAL, " +
                DATE + " TEXT, " +
                PATH + " TEXT );";

        db.execSQL(SQL_CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + NAME);
        onCreate(db);
    }


    private ContentValues getContentValues(ResultsBean item){
        ContentValues contentValues = new ContentValues();

        contentValues.put(TITLE, item.getOriginal_title());
        contentValues.put(OVERVIEW, item.getOverview());
        contentValues.put(RATE, item.getVote_average());
        contentValues.put(DATE, item.getRelease_date());
        contentValues.put(PATH, item.getPoster_path());
        return contentValues;
    }

    public  boolean insert(ResultsBean item ){
        mDatabase = this.getWritableDatabase();
        ContentValues contentValues = getContentValues(item);
        try {
            mDatabase.insertWithOnConflict(NAME, null, contentValues,SQLiteDatabase.CONFLICT_IGNORE);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }


    public ResultsBean getMovieData(String title){
        mDatabase = this.getWritableDatabase();
        ResultsBean resultsBean = new ResultsBean();
        Cursor cursor = mDatabase.query(NAME, null, TITLE + " = ? ", new String[]{title}, null, null, null);

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
        }finally {
            cursor.close();
            this.close();
            mDatabase.close();
        }
    }

    // get This data if network is off
    public List<ResultsBean> getAllMoviesData(){
        List<ResultsBean> mList = new ArrayList<>();
        mDatabase = this.getWritableDatabase();
        ResultsBean resultsBean = new ResultsBean();
        Cursor cursor = mDatabase.query(NAME, null,null, null, null, null, null);

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
        }finally {
            cursor.close();
            this.close();
            mDatabase.close();
        }
    }


}
