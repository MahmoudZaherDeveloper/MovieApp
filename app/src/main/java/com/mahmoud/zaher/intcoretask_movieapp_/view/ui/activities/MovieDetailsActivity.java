package com.mahmoud.zaher.intcoretask_movieapp_.view.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mahmoud.zaher.intcoretask_movieapp_.R;
import com.mahmoud.zaher.intcoretask_movieapp_.movieModel.roomDatabase.ModelForCachedMovies;
import com.mahmoud.zaher.intcoretask_movieapp_.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.mahmoud.zaher.intcoretask_movieapp_.utils.Constants.IMAGE_URL;
import static com.mahmoud.zaher.intcoretask_movieapp_.utils.Constants.MOVIE_ID;
import static com.mahmoud.zaher.intcoretask_movieapp_.utils.Constants.MOVIE_OVERVIEW;
import static com.mahmoud.zaher.intcoretask_movieapp_.utils.Constants.MOVIE_POSTER;
import static com.mahmoud.zaher.intcoretask_movieapp_.utils.Constants.MOVIE_TITLE;

public class MovieDetailsActivity extends AppCompatActivity {

    @BindView(R.id.tvMovieTitle)
    TextView tvMovieTitle;
    @BindView(R.id.ivFav)
    ImageView ivFav;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvMovieDetails)
    TextView tvMovieDetails;
    @BindView(R.id.ivMoviePoster)
    ImageView ivMoviePoster;
    private String movieId;

    public static void start(Context context, String movieId, String movieTitle, String movieOverview, String moviePoster) {
        Intent starter = new Intent(context, MovieDetailsActivity.class);
        starter.putExtra(MOVIE_ID, movieId);
        starter.putExtra(MOVIE_TITLE, movieTitle);
        starter.putExtra(MOVIE_OVERVIEW, movieOverview);
        starter.putExtra(MOVIE_POSTER, moviePoster);
        starter.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            HomeActivity.start(this);
            return;
        }
        tvMovieTitle.setText(bundle.getString(MOVIE_TITLE));
        tvMovieTitle.setText(bundle.getString(MOVIE_TITLE));
        tvMovieDetails.setText(bundle.getString(MOVIE_OVERVIEW));
        movieId = bundle.getString(MOVIE_ID);
        try {
            Glide.with(this)
                    .load(IMAGE_URL + bundle.getString(MOVIE_POSTER))
                    .into(ivMoviePoster);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.ivFav, R.id.ivBack})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivFav:
                setFavBtn();
                int i = Integer.parseInt(movieId);
                new ModelForCachedMovies(this, null).setFavoriteMovie(Integer.parseInt(movieId));
                Utils.showLongToast(this, "Added to favourite .");
                break;
            case R.id.ivBack:
                finish();
                break;
        }
    }

    private void setFavBtn() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ivFav.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_black_24dp, getApplicationContext().getTheme()));
        } else {
            ivFav.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_black_24dp));
        }
    }
}
