package com.mahmoud.zaher.intcoretask_movieapp_.view.ui.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mahmoud.zaher.intcoretask_movieapp_.R;
import com.mahmoud.zaher.intcoretask_movieapp_.movieModel.apiResponse.Result;
import com.mahmoud.zaher.intcoretask_movieapp_.moviePresenter.MoviePresenterContract;
import com.mahmoud.zaher.intcoretask_movieapp_.view.ui.activities.HomeActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.mahmoud.zaher.intcoretask_movieapp_.utils.Constants.IMAGE_URL;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MyViewHolder> {

    private Context context;
    private List<Result> movieList;
    private MoviePresenterContract.IView activityIView;
    private Result movieResult;

    public MovieListAdapter(Context context, List<Result> movieList) {
        this.context = context;
        this.activityIView = (HomeActivity) context;
        this.movieList = movieList;

    }

    @NonNull
    @Override
    public MovieListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_movie, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieListAdapter.MyViewHolder holder, int position) {
        movieResult = movieList.get(position);
        bindDataToHolder(movieResult, holder);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    // bind data to view holder
    @SuppressLint("SetTextI18n")
    private void bindDataToHolder(Result movieResult, MovieListAdapter.MyViewHolder holder) {
        final String movieId = String.valueOf(movieResult.getId());
        final String movieResultTitle = movieResult.getTitle();
        final String movieResultOverview = movieResult.getOverview();
        String movieResultReleaseDate = movieResult.getReleaseDate();
        String voteAverage = movieResult.getVoteAverage();
        final String moviePosterPath = movieResult.getPosterPath();

        holder.cardViewLayout.setVisibility(View.VISIBLE);
        holder.tvMovieName.setText(movieResultTitle);
        holder.tvMovieDate.setText(movieResultReleaseDate);
        holder.tvMovieRate.setText(voteAverage + "/10");

        try {
            Glide.with(context)
                    .load(IMAGE_URL + moviePosterPath)
                    .into(holder.ivPoster);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityIView.openDetailsActivity(movieId, movieResultTitle, movieResultOverview, moviePosterPath);
            }
        });
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private final View view;
        @BindView(R.id.cardViewLayout)
        CardView cardViewLayout;
        @BindView(R.id.tvMovieName)
        TextView tvMovieName;
        @BindView(R.id.tvMovieDate)
        TextView tvMovieDate;
        @BindView(R.id.tvMovieRate)
        TextView tvMovieRate;
        @BindView(R.id.ivPoster)
        ImageView ivPoster;

        public MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);

        }
    }

}
