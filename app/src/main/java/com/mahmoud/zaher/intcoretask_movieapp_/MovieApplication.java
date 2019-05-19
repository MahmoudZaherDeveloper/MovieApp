package com.mahmoud.zaher.intcoretask_movieapp_;

import android.app.Application;

public class MovieApplication extends Application {


    private static MovieApplication movieApplication;


    public static MovieApplication getInstance() {
        return movieApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        movieApplication = this;
    }
}
