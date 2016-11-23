package com.allam.android.movies.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.allam.android.movies.data.MovieDBSchema.FavouriteTable;
import com.allam.android.movies.data.MovieDBSchema.MovieTable;
import com.allam.android.movies.data.MovieDBSchema.PopularTable;
import com.allam.android.movies.data.MovieDBSchema.TopRatedTable;

/**
 * Created by Allam on 09/09/2016.
 */
public class MovieProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = BuildUriMatcher();

    private MovieDbHelper mOpenHelper;

    static final int movieTable = 1;
    static final int popularTable = 2;
    static final int topTable = 3;
    static final int favouriteTable = 4;
    static final int movieTableWithId = 10;


    private static final SQLiteQueryBuilder sFavoriteMovieQueryBuilder = getMovieQueryBuilder(FavouriteTable.TABLE_NAME);
    private static final SQLiteQueryBuilder sPopularMovieQueryBuilder = getMovieQueryBuilder(PopularTable.TABLE_NAME);
    private static final SQLiteQueryBuilder sTopRatedMovieQueryBuilder = getMovieQueryBuilder(TopRatedTable.TABLE_NAME);


    private static SQLiteQueryBuilder getMovieQueryBuilder(String table) {
        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();
        sqLiteQueryBuilder.setTables(
                table + " INNER JOIN " +
                        MovieTable.TABLE_NAME +
                        " ON " + table +
                        "." + FavouriteTable.Columns.MOVIE_ID +
                        " = " + MovieTable.TABLE_NAME +
                        "." + MovieTable.Columns.MOVIE_ID);
        return sqLiteQueryBuilder;
    }


    public static UriMatcher BuildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        String Content_Authority = MovieDBSchema.CONTENT_AUTHORITY;
        uriMatcher.addURI(Content_Authority, MovieTable.TABLE_NAME, movieTable);
        uriMatcher.addURI(Content_Authority, PopularTable.TABLE_NAME, popularTable);
        uriMatcher.addURI(Content_Authority, TopRatedTable.TABLE_NAME, topTable);
        uriMatcher.addURI(Content_Authority, FavouriteTable.TABLE_NAME, favouriteTable);
        uriMatcher.addURI(Content_Authority, MovieTable.TABLE_NAME + "/#", movieTableWithId);
        return uriMatcher;
    }


    @Override
    public boolean onCreate() {
        mOpenHelper = new MovieDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        int match = sUriMatcher.match(uri);
        Cursor retCursor;
        switch (match) {
            case popularTable:
                retCursor =
                sPopularMovieQueryBuilder.query(
                        mOpenHelper.getReadableDatabase(),
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case topTable:
                retCursor = sTopRatedMovieQueryBuilder.query(
                        mOpenHelper.getReadableDatabase(),
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;

            case favouriteTable:
                retCursor = sFavoriteMovieQueryBuilder.query(
                        mOpenHelper.getReadableDatabase(),
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);

                break;
            case movieTable:

                retCursor = mOpenHelper.getReadableDatabase().query(MovieTable.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case movieTableWithId:

                long id = ContentUris.parseId(uri);
                retCursor = mOpenHelper.getReadableDatabase().
                        query(MovieTable.TABLE_NAME,
                                projection,
                                MovieTable.Columns._ID + " = ? ",
                                new String[]{id + ""},
                                null,
                                null,
                                sortOrder);

                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        int match = sUriMatcher.match(uri);
        switch (match) {
            case movieTable:
                return MovieTable.CONTENT_TYPE;
            case popularTable:
                return PopularTable.CONTENT_TYPE;
            case topTable:
                return TopRatedTable.CONTENT_TYPE;
            case favouriteTable:
                return FavouriteTable.CONTENT_TYPE;
            case movieTableWithId:
                return MovieTable.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case movieTable: {
                long _id = db.insert(MovieTable.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = MovieTable.BuildMovieUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case popularTable: {
                long _id = db.insert(PopularTable.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = PopularTable.BuildMovieUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case topTable: {
                long _id = db.insert(TopRatedTable.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = TopRatedTable.BuildMovieUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case favouriteTable: {
                long _id = db.insert(FavouriteTable.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = FavouriteTable.BuildMovieUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }


    private int insertTableInBulk(String tableName, ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        db.beginTransaction();
        int returnCount = 0;
        try {
            for (ContentValues value : values) {
                long _id = db.insert(tableName, null, value);
                if (_id != -1) {
                    returnCount++;
                }
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return returnCount;
    }


    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int returnCount = 0;
        switch (match) {
            case movieTable:
                returnCount = insertTableInBulk(MovieTable.TABLE_NAME, values);
                break;
            case popularTable:
                returnCount = insertTableInBulk(PopularTable.TABLE_NAME, values);
                break;
            case topTable:
                returnCount = insertTableInBulk(TopRatedTable.TABLE_NAME, values);
                break;
            case favouriteTable:
                returnCount = insertTableInBulk(FavouriteTable.TABLE_NAME, values);
                break;
            default:
                return super.bulkInsert(uri, values);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnCount;
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int deleted = 0;
        if (selection == null) selection = "1";
        switch (match) {
            case movieTable:
                deleted = db.delete(MovieTable.TABLE_NAME, selection, selectionArgs);
                break;
            case popularTable:
                deleted = db.delete(PopularTable.TABLE_NAME, selection, selectionArgs);
                break;
            case topTable:
                deleted = db.delete(TopRatedTable.TABLE_NAME, selection, selectionArgs);
                break;
            case favouriteTable:
                deleted = db.delete(FavouriteTable.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (deleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return deleted;
    }


    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        if (selection == null) selection = "1";
        int updated = 0;

        switch (match) {
            case movieTable:
                updated = db.delete(MovieTable.TABLE_NAME, selection, selectionArgs);
                break;
            case popularTable:
                updated = db.delete(PopularTable.TABLE_NAME, selection, selectionArgs);
                break;
            case topTable:
                updated = db.delete(TopRatedTable.TABLE_NAME, selection, selectionArgs);
                break;
            case favouriteTable:
                updated = db.delete(FavouriteTable.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (updated > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return updated;
    }


}
