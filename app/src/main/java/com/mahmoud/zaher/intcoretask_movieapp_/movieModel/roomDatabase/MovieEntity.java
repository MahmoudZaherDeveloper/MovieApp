package com.mahmoud.zaher.intcoretask_movieapp_.movieModel.roomDatabase;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.mahmoud.zaher.intcoretask_movieapp_.movieModel.apiResponse.Result;

import java.util.List;

@Entity(tableName = "MovieEntity")
public class MovieEntity {

    @ColumnInfo(name = "listOfMovies")
    @TypeConverters(MoviesConverter.class)
    public static List<Result> movieResult;
    @PrimaryKey(autoGenerate = true)
    private int movieId;
    @ColumnInfo(name = "movieTitle")
    private String movieTitle;
    @ColumnInfo(name = "movieOverView")
    private String movieOverView;
    @ColumnInfo(name = "moviePoster")
    private String moviePoster;
    @ColumnInfo(name = "movieRate")
    private String movieRate;
    @ColumnInfo(name = "movieDate")
    private String movieDate;
    @ColumnInfo(name = "isFav")
    private int isFav;

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
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
