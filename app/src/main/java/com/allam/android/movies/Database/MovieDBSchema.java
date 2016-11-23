package com.allam.android.movies.Database;

import android.provider.BaseColumns;

/**
 * Created by Allam on 09/08/2016.
 */
public class MovieDBSchema  {
    public  final static String DATABASE_NAME = "myMovies.db";

    public static class MovieTable{
        public  final static String NAME = "movieTable";
        public static class Columns{
            public  final static String _ID = "_id";
            public  final static String TITLE = "title";
            public  final static String OVERVIEW = "overview";
            public  final static String RATE = "avg_rating";
            public  final static String DATE = "release_date";
            public  final static String PATH = "image_path";
        }
    }
}
