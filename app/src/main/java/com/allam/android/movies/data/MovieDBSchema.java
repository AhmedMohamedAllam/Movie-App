package com.allam.android.movies.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Allam on 09/08/2016.
 */
public class MovieDBSchema {

    public final static String CONTENT_AUTHORITY = "com.allam.android.movies.app";


    public final static Uri CONTENT_AUTHORITY_BASE = Uri.parse("content://" + CONTENT_AUTHORITY);


    //Movie Table Contract
    public static class MovieTable {
        public final static String TABLE_NAME = "movieTable";
        public final static Uri CONTENT_URI = CONTENT_AUTHORITY_BASE.buildUpon().appendPath(TABLE_NAME).build();

        public final static String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;
        public final static String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;


        public static class Columns implements BaseColumns {
            public final static String MOVIE_ID = "movie_id";
            public final static String TITLE = "title";
            public final static String OVERVIEW = "overview";
            public final static String RATE = "avg_rating";
            public final static String DATE = "release_date";
            public final static String PATH = "image_path";
        }

        public static Uri BuildMovieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri BuildMovieWithId(int MovieId) {
            return CONTENT_URI.buildUpon().appendPath(Integer.toString(MovieId)).build();
        }

        public static int getMovieIdFromUri(Uri uri) {
            return Integer.parseInt(uri.getPathSegments().get(1));
        }

    }


    //Popular Table Contract
    public static class PopularTable {
        public final static String TABLE_NAME = "popularTable";
        public final static Uri CONTENT_URI = CONTENT_AUTHORITY_BASE.buildUpon().appendPath(TABLE_NAME).build();

        public final static String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;
        public final static String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;

        public static class Columns implements BaseColumns {
            public final static String MOVIE_ID = "movie_id";
        }
        public static Uri BuildMovieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }


    //Top Rated  Table Contract
    public static class TopRatedTable {
        public final static String TABLE_NAME = "topRatedTable";
        public final static Uri CONTENT_URI = CONTENT_AUTHORITY_BASE.buildUpon().appendPath(TABLE_NAME).build();

        public final static String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;
        public final static String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;

        public static class Columns implements BaseColumns {
            public final static String MOVIE_ID = "movie_id";
        }

        public static Uri BuildMovieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }

    //Favourite Table Contract
    public static class FavouriteTable {
        public final static String TABLE_NAME = "favTable";
        public final static Uri CONTENT_URI = CONTENT_AUTHORITY_BASE.buildUpon().appendPath(TABLE_NAME).build();

        public final static String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;
        public final static String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;

        public static class Columns implements BaseColumns {
            public final static String MOVIE_ID = "movie_id";
        }

        public static Uri BuildMovieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }


}
