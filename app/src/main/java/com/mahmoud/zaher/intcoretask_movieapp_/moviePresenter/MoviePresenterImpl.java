package com.mahmoud.zaher.intcoretask_movieapp_.moviePresenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.mahmoud.zaher.intcoretask_movieapp_.R;
import com.mahmoud.zaher.intcoretask_movieapp_.apiNetworkHandler.MovieApiInterface;
import com.mahmoud.zaher.intcoretask_movieapp_.apiNetworkHandler.NetworkClientDispatcher;
import com.mahmoud.zaher.intcoretask_movieapp_.movieModel.apiResponse.MovieListResponse;
import com.mahmoud.zaher.intcoretask_movieapp_.movieModel.roomDatabase.ModelForCachedMovies;
import com.mahmoud.zaher.intcoretask_movieapp_.utils.Constants;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import rx.Subscription;

import static com.mahmoud.zaher.intcoretask_movieapp_.utils.Constants.SELECTION_VALUE;

public class MoviePresenterImpl implements Observer<MovieListResponse>, MoviePresenterContract.IPresenter {

    private MoviePresenterContract.IView view;
    private Subscription subscription = null;
    private SharedPreferences preferences;
    private Context context;
    private ActionBar actionBar;

    public MoviePresenterImpl(Context context) {
        this.context = context;
        this.preferences = context.getSharedPreferences("home", Context.MODE_PRIVATE);
        this.actionBar = ((AppCompatActivity) context).getSupportActionBar();
    }


    /**
     * implement IPresenter methods
     **/

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
    public void getTopRatedMovies() {
        actionBar.setTitle(R.string.menu_rate_title);
        view.showLoading();
        MovieApiInterface topRatingMovieApi = NetworkClientDispatcher.getClient().create(MovieApiInterface.class);
        Observable<MovieListResponse> toRatedCall = topRatingMovieApi.getTopRatedMovies(Constants.API_KEY);
        getMoviesData(toRatedCall);
    }

    @Override
    public void getPopularMovies() {
        actionBar.setTitle(R.string.menu_popular_title);
        view.showLoading();
        MovieApiInterface popularMovieApi = NetworkClientDispatcher.getClient().create(MovieApiInterface.class);
        Observable<MovieListResponse> popularCall = popularMovieApi.getPopularMovies(Constants.API_KEY);
        getMoviesData(popularCall);
    }

    @Override
    public void getFavoriteMovies() {

    }

    @Override
    public void rxUnsubscribe() {
        if (subscription != null) {
            if (!subscription.isUnsubscribed()) {
                subscription.unsubscribe();
            }
        }
    }

    private void getMoviesData(Observable<MovieListResponse> call) {
        call.subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(this);
    }


    /**
     * implement RX methods
     **/
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(MovieListResponse movieListResponse) {

        ModelForCachedMovies cachedMovie = new ModelForCachedMovies(context, view);
        cachedMovie.cacheNewMovie(movieListResponse.getResults());

        view.hideLoading();
        view.onReceiveMovies(movieListResponse.getResults());
    }

    @Override
    public void onError(Throwable e) {
        view.showErrorMsg(e.getMessage());
        // in case error this mean there is no internet connection , should use cashes
        getCashedMovies();
    }

    private void getCashedMovies() {
        ModelForCachedMovies cachedMovies = new ModelForCachedMovies(context, view);
        cachedMovies.getPopularMovies();
    }

    @Override
    public void onComplete() {

    }

}
