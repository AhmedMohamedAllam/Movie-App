package com.allam.android.movies.Fragment;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.allam.android.movies.Model.Movie.ResultsBean;
import com.allam.android.movies.Model.Review;
import com.allam.android.movies.Model.Trailer;
import com.allam.android.movies.Network.NetworkTasks;
import com.allam.android.movies.R;
import com.allam.android.movies.data.MovieDBSchema.FavouriteTable;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

/**
 * Created by Allam on 07/08/2016.
 */
public class MovieDetailFragment extends Fragment {
    private ImageView mImageView;
    private TextView mTitleTextView, mOverviewTextView, mRatingTextView, mReleaseDateTextView;
    private String mImageUrl;
    private ResultsBean mResultsBean;
    private RecyclerView mTrailerRecyclerView, mReviewRecyclerView;
    private Trailer mTrailer;
    private Review mReview;
    private String mFirstTrailer;
    private ImageButton mFavButton;
    private boolean isFavourite;
    private ShareActionProvider mShareActionProvider;
    public static final String EXTRA_MOVIE = "MovieDetailFragment.movie";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public static MovieDetailFragment newInstance(ResultsBean movie) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_MOVIE, movie);
        MovieDetailFragment mdf = new MovieDetailFragment();
        mdf.setArguments(bundle);
        return mdf;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movie_detail_fragment, container, false);

        mImageView = (ImageView) view.findViewById(R.id.detail_imageview);
        mTitleTextView = (TextView) view.findViewById(R.id.film_title);
        mOverviewTextView = (TextView) view.findViewById(R.id.overview);
        mRatingTextView = (TextView) view.findViewById(R.id.rating_text);
        mReleaseDateTextView = (TextView) view.findViewById(R.id.release_date);
        mTrailerRecyclerView = (RecyclerView) view.findViewById(R.id.trailer_recyclerview);
        mReviewRecyclerView = (RecyclerView) view.findViewById(R.id.reviews_recyclerview);
        mFavButton = (ImageButton) view.findViewById(R.id.fav_button);

        if (getArguments() == null) return view;
        Object obj = getArguments().getSerializable(EXTRA_MOVIE);
        if (obj != null)
            mResultsBean = (ResultsBean) obj;

        if (MainPostersFragment.isNetworkAvailable(getActivity())) {
            new FetchTrailerTask().execute(1 + "", mResultsBean.getId() + "");
            new FetchReviewTask().execute(1 + "", mResultsBean.getId() + "");
        } else {
            Toast.makeText(getActivity(), "Error, Check network connection!", Toast.LENGTH_SHORT).show();
        }


        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mTrailerRecyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mReviewRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        //Build Image Url and display it via Picasso
        mImageUrl = MainPostersFragment.buildPosterUrl(mResultsBean.getPoster_path());
        Picasso.with(getActivity()).load(mImageUrl).into(mImageView);

        mTitleTextView.setText(mResultsBean.getOriginal_title());
        mOverviewTextView.setText(mResultsBean.getOverview());
        mRatingTextView.setText(mResultsBean.getVote_average() + "");
        mReleaseDateTextView.setText(mResultsBean.getRelease_date());

        mFavButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isFavourite) {
                    getActivity().getContentResolver().delete(FavouriteTable.CONTENT_URI, FavouriteTable.Columns.MOVIE_ID + " = ? ",
                            new String[]{mResultsBean.getId() + ""});

                    mFavButton.setImageResource(R.drawable.favourite);
                    Toast.makeText(getContext(),
                            mResultsBean.getOriginal_title() + " is removed from your favorite successfully", Toast.LENGTH_SHORT).show();
                } else {
                    ContentValues cv = new ContentValues();
                    cv.put(FavouriteTable.Columns.MOVIE_ID, mResultsBean.getId());
                    getActivity().getContentResolver().insert(FavouriteTable.CONTENT_URI, cv);
                    mFavButton.setImageResource(R.drawable.heart_fav);
                    Toast.makeText(getContext(), mResultsBean.getOriginal_title() + " is added to your favorite successfully", Toast.LENGTH_SHORT).show();
                }
                isFavourite = !isFavourite;
            }
        });


        return view;
    }

    private void checkFavoriteStar() {
        Cursor c = getActivity().getContentResolver().query(FavouriteTable.CONTENT_URI,
                null,
                FavouriteTable.TABLE_NAME + "." + FavouriteTable.Columns.MOVIE_ID + " = ? ",
                new String[]{mResultsBean.getId() + ""}, null);

        isFavourite = c.moveToFirst();
        c.close();
        if (isFavourite) mFavButton.setImageResource(R.drawable.heart_fav);
        else mFavButton.setImageResource(R.drawable.favourite);
    }

    @Override
    public void onStart() {
        super.onStart();

        if (mResultsBean != null) {
            checkFavoriteStar();
        }

    }


    //Trailer RecyclerView adapter and holder
    private class TrailerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mTrailerIcon;
        private TextView mName, mQuality;
        private Trailer.ResultsBean mBean;

        public TrailerHolder(View view) {
            super(view);
            mTrailerIcon = (ImageView) view.findViewById(R.id.trailer_play_icon);
            mName = (TextView) view.findViewById(R.id.trailer_name);
            mQuality = (TextView) view.findViewById(R.id.trailer_quality);
            mTrailerIcon.setOnClickListener(this);
        }

        public void onBindView(Trailer.ResultsBean trailer) {
            mBean = trailer;
            Uri mTrailerUrl = Uri.parse("http://img.youtube.com/vi").buildUpon().appendPath(mBean.getKey()).appendPath("default.jpg").build();
            Picasso.with(getActivity()).load(mTrailerUrl).into(mTrailerIcon);
            mName.setText(trailer.getName());
            mQuality.setText("Quality " + trailer.getSize() + "p");
        }

        @Override
        public void onClick(View v) {
            Uri uri = Uri.parse("https://www.youtube.com/watch").buildUpon()
                    .appendQueryParameter("v", mBean.getKey()).build();
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(getActivity(), "Couldn't play video, no receiving apps installed!", Toast.LENGTH_SHORT).show();
            }
            startActivity(intent);
        }
    }

    private class TrailerAdapter extends RecyclerView.Adapter<TrailerHolder> {
        private List<Trailer.ResultsBean> mTrailerResultsBeans;

        public TrailerAdapter(Trailer trailer) {
            mTrailerResultsBeans = trailer.getResults();
        }

        @Override
        public TrailerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.trailer_recyclerview_items, parent, false);
            return new TrailerHolder(view);
        }

        @Override
        public void onBindViewHolder(TrailerHolder holder, int position) {
            holder.onBindView(mTrailerResultsBeans.get(position));
        }

        @Override
        public int getItemCount() {
            return mTrailerResultsBeans.size();
        }
    }


    //Review RecyclerView adapter and holder
    private class ReviewHolder extends RecyclerView.ViewHolder {
        private TextView mAuthor, mContent;

        public ReviewHolder(View itemView) {
            super(itemView);
            mAuthor = (TextView) itemView.findViewById(R.id.review_author);
            mContent = (TextView) itemView.findViewById(R.id.review_content);
        }

        public void onBindView(Review.ResultsBean review) {
            mAuthor.setText(review.getAuthor());
            mContent.setText(review.getContent());
        }

    }

    private class ReviewAdapter extends RecyclerView.Adapter<ReviewHolder> {
        private List<Review.ResultsBean> mReviewResultsBeans;

        public ReviewAdapter(Review review) {
            mReviewResultsBeans = review.getResults();
        }

        @Override
        public ReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.review_recyclerview_items, parent, false);
            return new ReviewHolder(view);
        }

        @Override
        public void onBindViewHolder(ReviewHolder holder, int position) {
            holder.onBindView(mReviewResultsBeans.get(position));
        }

        @Override
        public int getItemCount() {
            return mReviewResultsBeans.size();
        }
    }


    // background task that fetches the movie Trailer
    private class FetchTrailerTask extends AsyncTask<String, Void, Trailer> {

        @Override
        protected Trailer doInBackground(String... params) {
            try {
                return new NetworkTasks(getActivity()).fetchTrailers(params[0], Integer.parseInt(params[1]));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Trailer trailer) {
            super.onPostExecute(trailer);
            if (trailer != null) {
                mTrailer = trailer;
                mFirstTrailer = "http://www.youtube.com/watch?v=" + trailer.getResults().get(0).getKey();
                if (mShareActionProvider != null) mShareActionProvider.setShareIntent(createShareTrailerIntent());
                mTrailerRecyclerView.setAdapter(new TrailerAdapter(mTrailer));
            }

        }
    }

    // background task that fetches Reviews about a movie

    private class FetchReviewTask extends AsyncTask<String, Void, Review> {
        @Override
        protected Review doInBackground(String... params) {
            try {
                return new NetworkTasks(getActivity()).fetchreviews(params[0], Integer.parseInt(params[1]));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Review review) {
            super.onPostExecute(review);
            if (review != null) {
                mReview = review;
                mReviewRecyclerView.setAdapter(new ReviewAdapter(mReview));
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.fragment_detail, menu);

        // Retrieve the share menu item
        MenuItem menuItem = menu.findItem(R.id.action_share);

        // Get the provider and hold onto it to set/change the share intent.
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);

        // If onLoadFinished happens before this, we can go ahead and set the share intent now.
        if (mFirstTrailer != null) {
            mShareActionProvider.setShareIntent(createShareTrailerIntent());
        }
    }

    private Intent createShareTrailerIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, mFirstTrailer);
        return shareIntent;
    }



}
