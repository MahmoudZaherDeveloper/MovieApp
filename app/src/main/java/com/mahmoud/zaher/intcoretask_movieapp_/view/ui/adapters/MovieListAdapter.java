package com.mahmoud.zaher.intcoretask_movieapp_.view.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mahmoud.zaher.intcoretask_movieapp_.R;
import com.mahmoud.zaher.intcoretask_movieapp_.movieModel.ViewModel;
import com.mahmoud.zaher.intcoretask_movieapp_.movieModel.apiResponse.Result;
import com.mahmoud.zaher.intcoretask_movieapp_.moviePresenter.MoviePresenterContract;
import com.mahmoud.zaher.intcoretask_movieapp_.view.ui.activities.HomeActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.mahmoud.zaher.intcoretask_movieapp_.utils.Constants.IMAGE_URL;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MyViewHolder> implements View.OnClickListener {

    private Context context;
    private List<Result> movieList;
    private MoviePresenterContract.IView activityIView;
    private ViewModel viewModel;
    private GridLayoutManager gridLayoutManager = null;
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

        holder.cardViewLayout.setVisibility(View.VISIBLE);
        holder.tvMovieName.setText(movieResult.getTitle());
        holder.tvMovieDate.setText(movieResult.getReleaseDate());
        holder.tvMovieRate.setText(movieResult.getVoteAverage() + "/10");

        try {
            Glide.with(context)
                    .load(IMAGE_URL + movieResult.getPosterPath())
                    .into(holder.ivPoster);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.itemView.setTag(String.valueOf(movieResult.getId()));
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    @Override
    public void onClick(View v) {
        String tagId = (String) v.getTag();
        activityIView.openDetailsActivity(tagId);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
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

        MyViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            v.setOnClickListener(MovieListAdapter.this);
        }
    }
}
