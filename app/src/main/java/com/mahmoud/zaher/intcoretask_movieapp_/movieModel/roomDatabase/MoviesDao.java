package com.mahmoud.zaher.intcoretask_movieapp_.movieModel.roomDatabase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Maybe;

@Dao
public interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertNewMovie(MovieEntity movieEntity);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
        // void insertListOfMovies(List<MovieEntity> movieEntityList);
    void insertListOfMovies(MovieEntity movieEntityList);

    /**
     * Single is an Observable which only emits one item or throws an error.
     * Single emits only one value and applying some of the operator makes no sense.
     * Like we don’t want to take value and collect it to a list.
     **/
    @Query("SELECT * FROM MovieEntity")
    Maybe<List<MovieEntity>> fetchAllMovies();
    //Single<List<MovieEntity>> fetchAllMovies();

    @Query("UPDATE MovieEntity SET isFav = 1 where movieId =:movieId")
    void setFavorite(int movieId);

    /**
     * Maybe is similar to Single, only difference being that it allows for no emissions as well.
     **/
    @Query("SELECT * FROM MovieEntity where isFav== 1")
    Maybe<List<MovieEntity>> getFavorites();
}
