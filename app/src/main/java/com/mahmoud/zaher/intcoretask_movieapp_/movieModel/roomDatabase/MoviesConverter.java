package com.mahmoud.zaher.intcoretask_movieapp_.movieModel.roomDatabase;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mahmoud.zaher.intcoretask_movieapp_.movieModel.apiResponse.Result;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class MoviesConverter {
    private static Gson gson = new Gson();

    @TypeConverter
    public static List<Result> stringToList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Result>>() {
        }.getType();
        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String ListToString(List<Result> someObjects) {
        return gson.toJson(someObjects);
    }
}