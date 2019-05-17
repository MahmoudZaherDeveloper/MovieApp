package com.mahmoud.zaher.intcoretask_movieapp_.apiNetworkHandler;

import com.mahmoud.zaher.intcoretask_movieapp_.movieModel.apiResponse.MovieListResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.mahmoud.zaher.intcoretask_movieapp_.utils.Constants.API_KEY_ATT;
import static com.mahmoud.zaher.intcoretask_movieapp_.utils.Constants.API_MOST_POPULAR;
import static com.mahmoud.zaher.intcoretask_movieapp_.utils.Constants.API_TOP_RATED;

public interface MovieApiInterface {

    @GET(API_MOST_POPULAR)
    Observable<MovieListResponse> getPopularMovies(@Query(API_KEY_ATT) String apiKey);

    @GET(API_TOP_RATED)
    Observable<MovieListResponse> getTopRatedMovies(@Query(API_KEY_ATT) String apiKey);
}
