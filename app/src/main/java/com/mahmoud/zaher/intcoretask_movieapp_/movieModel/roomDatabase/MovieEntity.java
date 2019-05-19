package com.mahmoud.zaher.intcoretask_movieapp_.movieModel.roomDatabase;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.mahmoud.zaher.intcoretask_movieapp_.movieModel.apiResponse.Result;

import java.util.List;

@Entity
public class MovieEntity {

    @PrimaryKey(autoGenerate = true)
    private long movieId;

    @TypeConverters(MoviesConverter.class)
    public static List<Result> movieResult;

    private String movieTitle;
    private String movieOverView;
    private String moviePoster;
    private String movieRate;
    private String movieDate;
    private int isFav;

    public MovieEntity() {
    }

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getMovieOverView() {
        return movieOverView;
    }

    public void setMovieOverView(String movieOverView) {
        this.movieOverView = movieOverView;
    }

    public String getMoviePoster() {
        return moviePoster;
    }

    public void setMoviePoster(String moviePoster) {
        this.moviePoster = moviePoster;
    }

    public String getMovieRate() {
        return movieRate;
    }

    public void setMovieRate(String movieRate) {
        this.movieRate = movieRate;
    }

    public String getMovieDate() {
        return movieDate;
    }

    public void setMovieDate(String movieDate) {
        this.movieDate = movieDate;
    }

    public List<Result> getMovieResult() {
        return movieResult;
    }

    public void setMovieResult(List<Result> movieResult) {
        MovieEntity.movieResult = movieResult;
    }

    public int getIsFav() {
        return isFav;
    }

    public void setIsFav(int isFav) {
        this.isFav = isFav;
    }
}
