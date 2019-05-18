package com.mahmoud.zaher.intcoretask_movieapp_.movieModel.roomDatabase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertNewMovie(MovieEntity movieEntity);

    @Query("SELECT * FROM MovieEntity")
    List<MovieEntity> getAllMovies();

    @Query("UPDATE movieentity SET isFav = 1 where movieId =:movieId")
    void setFavorite(int movieId);

    @Query("SELECT * FROM movieentity where isFav== 1")
    List<MovieEntity> getFavorites();
}
