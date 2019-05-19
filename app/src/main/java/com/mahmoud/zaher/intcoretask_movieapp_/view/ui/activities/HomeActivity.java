package com.mahmoud.zaher.intcoretask_movieapp_.view.ui.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mahmoud.zaher.intcoretask_movieapp_.R;
import com.mahmoud.zaher.intcoretask_movieapp_.movieModel.apiResponse.Result;
import com.mahmoud.zaher.intcoretask_movieapp_.moviePresenter.MoviePresenterContract;
import com.mahmoud.zaher.intcoretask_movieapp_.moviePresenter.MoviePresenterImpl;
import com.mahmoud.zaher.intcoretask_movieapp_.utils.Utils;
import com.mahmoud.zaher.intcoretask_movieapp_.view.ui.adapters.MovieListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.mahmoud.zaher.intcoretask_movieapp_.utils.Constants.SELECTION_VALUE;

public class HomeActivity extends AppCompatActivity implements MoviePresenterContract.IView {

    @BindView(R.id.no_connection_layout)
    LinearLayout noConnectionLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_movies_list)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.retry)
    TextView retry;

    private SharedPreferences.Editor preferences;
    private List<Result> movieList;
    private MoviePresenterImpl presenterImpl;

    private MovieListAdapter movieListAdapter;
    private GridLayoutManager layoutManager;

    public static void start(Context context) {
        Intent starter = new Intent(context, HomeActivity.class);
        starter.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(starter);
    }

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setTitle("");
        }

        preferences = getSharedPreferences("home", MODE_PRIVATE).edit();
        presenterImpl = new MoviePresenterImpl(this);
        presenterImpl.setView(this);

        movieList = new ArrayList<>();

        if (Utils.hasConnection(this)) {
            presenterImpl.loadData();
        } else {
            noConnectionLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_listing, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (Utils.hasConnection(this)) {
            switch (item.getItemId()) {
                case R.id.action_popular:
                    preferences.putInt(SELECTION_VALUE, 1);
                    preferences.commit();
                    movieList.clear();
                    presenterImpl.loadData();
                    break;
                case R.id.action_rate:
                    preferences.putInt(SELECTION_VALUE, 2);
                    preferences.commit();
                    movieList.clear();
                    presenterImpl.loadData();
                    break;
            }
        } else {
            noConnectionLayout.setVisibility(View.VISIBLE);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showErrorMsg(String msg) {
        if (msg != null)
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onReceiveMovies(List<Result> list) {
        if (list == null || list.isEmpty()) {
            Utils.showLongToast(HomeActivity.this, "No Data Found !");
            return;
        }
        movieList.addAll(list);
        movieListAdapter = new MovieListAdapter(HomeActivity.this, movieList);
        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(movieListAdapter);
        movieListAdapter.notifyDataSetChanged();
    }

    @Override
    public void openDetailsActivity(String movieId, String movieTitle, String movieOverview, String moviePoster) {
        MovieDetailsActivity.start(this, movieId, movieTitle, movieOverview, moviePoster);
    }

    @OnClick(R.id.retry)
    public void onViewClicked() {
        if (Utils.hasConnection(this)) {
            presenterImpl.loadData();
            noConnectionLayout.setVisibility(View.GONE);
        }
    }
}
