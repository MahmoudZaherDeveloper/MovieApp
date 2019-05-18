package com.mahmoud.zaher.intcoretask_movieapp_.movieModel.roomDatabase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {MovieEntity.class}, version = 1, exportSchema = false)
public abstract class MoviesDataBase extends RoomDatabase {
    public abstract MoviesDao moviesDao();
}
