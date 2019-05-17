package com.mahmoud.zaher.intcoretask_movieapp_.moviePresenter;

import com.mahmoud.zaher.intcoretask_movieapp_.movieModel.apiResponse.Result;

import java.util.List;

public interface MoviePresenterContract {

    interface IView {

        void showLoading();

        void hideLoading();

        void showErrorMsg(String msg);

        void onReceiveMovies(List<Result> list);

        void openDetailsActivity(String movieId);
    }

    interface IPresenter {

        void setView(IView IView);

        void loadData();

        void rxUnsubscribe();

        void getTopRatedMovies();

        void getPopularMovies();

        void getFavoriteMovies();
    }
}
