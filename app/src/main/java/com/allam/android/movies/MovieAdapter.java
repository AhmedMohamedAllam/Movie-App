package com.allam.android.movies;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;

import com.allam.android.movies.Fragment.MainPostersFragment;
import com.squareup.picasso.Picasso;

/**
 * Created by Allam on 10/09/2016.
 */
public class MovieAdapter extends CursorAdapter {

    private Context mContext;

    public MovieAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        mContext = context;
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.recycler_view_items, parent, false);
        MovieHolder m = new MovieHolder(v);
        v.setTag(m);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        MovieHolder movieHolder = (MovieHolder) view.getTag();
        movieHolder.bindMoviePoster(cursor.getString(MainPostersFragment.POSTER_PATH));
    }


    private class MovieHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;

        public MovieHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.recyclerview_imageview);
        }

        public void bindMoviePoster(String poster_path) {
            Picasso.with(mContext)
                    .load("http://image.tmdb.org/t/p/w185/" + poster_path)
                    .placeholder(R.drawable.loading)
                    .into(mImageView);
        }

    }
}
