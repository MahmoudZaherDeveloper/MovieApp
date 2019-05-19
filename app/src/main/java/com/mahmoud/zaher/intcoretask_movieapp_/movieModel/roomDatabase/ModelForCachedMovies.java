package com.mahmoud.zaher.intcoretask_movieapp_.movieModel.roomDatabase;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.mahmoud.zaher.intcoretask_movieapp_.R;
import com.mahmoud.zaher.intcoretask_movieapp_.movieModel.apiResponse.Result;
import com.mahmoud.zaher.intcoretask_movieapp_.moviePresenter.MoviePresenterContract;
import com.mahmoud.zaher.intcoretask_movieapp_.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import rx.Subscription;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.mahmoud.zaher.intcoretask_movieapp_.utils.Constants.SELECTION_VALUE;

public class ModelForCachedMovies implements MaybeObserver<List<MovieEntity>>, MoviePresenterContract.IPresenter {

    private MoviePresenterContract.IView view;
    private Subscription subscription = null;
    private SharedPreferences preferences;
    private Context context;
    private ActionBar actionBar;

    public ModelForCachedMovies(Context context, MoviePresenterContract.IView IView) {
        this.context = context;
        this.preferences = context.getSharedPreferences("home", Context.MODE_PRIVATE);
        this.actionBar = ((AppCompatActivity) context).getSupportActionBar();
        this.view = IView;
    }

    public ModelForCachedMovies(Context context) {
        this.context = context;
    }

    @Override
    public void setView(MoviePresenterContract.IView IView) {
        this.view = IView;
    }

    @Override
    public void loadData() {
        view.showLoading();
        switch (preferences.getInt(SELECTION_VALUE, 0)) {
            case 1:
                getPopularMovies();
                break;
            case 2:
                getTopRatedMovies();
                break;
            default:
                getPopularMovies();

        }
    }


    @Override
    public void rxUnsubscribe() {
        if (subscription != null) {
            if (!subscription.isUnsubscribed()) {
                subscription.unsubscribe();
            }
        }
    }

    @Override
    public void getTopRatedMovies() {
        actionBar.setTitle(R.string.menu_rate_title);
        view.showLoading();
        Maybe<List<MovieEntity>> moviesList = getDbInstance().moviesDao().fetchAllMovies();
        getMoviesData(moviesList);
    }

    @Override
    public void getPopularMovies() {
        actionBar.setTitle(R.string.menu_popular_title);
        view.showLoading();
        Maybe<List<MovieEntity>> moviesList = getDbInstance().moviesDao().fetchAllMovies();
        getMoviesData(moviesList);
    }

    private MoviesDataBase getDbInstance() {
        return Room.databaseBuilder(getApplicationContext(),
                MoviesDataBase.class, Constants.DATABASE_NAME)
                .build();
    }

    @Override
    public void getFavoriteMovies() {
        Maybe<List<MovieEntity>> favorites = getDbInstance().moviesDao().getFavorites();
        getMoviesData(favorites);
    }

    public void setFavoriteMovie(final int movieId) {
        Completable.fromRunnable(new Runnable() {
            @Override
            public void run() {
                getDbInstance().moviesDao().setFavorite(movieId);

            }
        }).subscribeOn(Schedulers.io())
                .subscribe();
    }

    private void getMoviesData(Maybe<List<MovieEntity>> call) {
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this);
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onSuccess(List<MovieEntity> movieEntityList) {
        view.hideLoading();
        // view.onReceiveMovies(movieListResponse.getResults());
        ArrayList<Result> movieList = new ArrayList<>();
        for (MovieEntity entity : movieEntityList) {
            Result movieResult = new Result();
            movieResult.setId(entity.getMovieId());
            movieResult.setTitle(entity.getMovieTitle());
            movieResult.setOverview(entity.getMovieOverView());
            movieResult.setReleaseDate(entity.getMovieDate());
            movieResult.setPosterPath(entity.getMoviePoster());
            movieList.add(movieResult);
        }
        view.onReceiveMovies(movieList);
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }


    public void cacheNewMovie(final List<Result> list) {
        Completable.fromRunnable(new Runnable() {
            @Override
            public void run() {
                MovieEntity movieEntity = new MovieEntity();
                for (Result movie : list) {
                    movieEntity.setMovieId(movie.getId());
                    movieEntity.setMovieTitle(movie.getTitle());
                    movieEntity.setMovieOverView(movie.getOverview());
                    movieEntity.setMovieDate(movie.getReleaseDate());
                    movieEntity.setMoviePoster(movie.getPosterPath());
                    movieEntity.setMovieRate(movie.getVoteAverage() + "/10");
                    //Now access all the methods defined in DaoAccess with sampleDatabase object
                    getDbInstance().moviesDao().insertNewMovie(movieEntity);
                }


            }
        }).subscribeOn(Schedulers.io())
                .subscribe();
    }
}
