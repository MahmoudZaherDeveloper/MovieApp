package com.mahmoud.zaher.intcoretask_movieapp_.view.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mahmoud.zaher.intcoretask_movieapp_.R;
import com.mahmoud.zaher.intcoretask_movieapp_.utils.Utils;

import static com.mahmoud.zaher.intcoretask_movieapp_.utils.Constants.MOVIE_ID;

public class MovieDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            HomeActivity.start(this);
            return;
        }

        String movieId = bundle.getString(MOVIE_ID);
        Utils.showLongToast(this, movieId);
    }
}
