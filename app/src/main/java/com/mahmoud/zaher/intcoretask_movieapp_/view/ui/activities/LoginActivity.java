package com.mahmoud.zaher.intcoretask_movieapp_.view.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.mahmoud.zaher.intcoretask_movieapp_.R;
import com.mahmoud.zaher.intcoretask_movieapp_.movieModel.userData.User;
import com.mahmoud.zaher.intcoretask_movieapp_.movieModel.userData.UserPrefsController;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.mahmoud.zaher.intcoretask_movieapp_.utils.Utils.showLongToast;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.login_button)
    LoginButton loginButton;
    @BindView(R.id.visitorbtn)
    Button visitorbtn;
    UserPrefsController userPrefsController;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        userPrefsController = new UserPrefsController(this);
        // we use it in https://developers.facebook.com/docs/facebook-login/android   -=> in Key Hashes
        // String hashKey = Utils.getHashKey(this);
        loginWithFB();
    }

    private void loginWithFB() {
        callbackManager = CallbackManager.Factory.create();

        final String EMAIL = "email";
        final String PROFILE = "public_profile";
        loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList(EMAIL, PROFILE));

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                try {
                    // App code
                    Profile profile = Profile.getCurrentProfile();
                    User user = new User(profile.getId(), profile.getName(), "not_found", profile.getProfilePictureUri(100, 100).toString());
                    userPrefsController.setUser(user);
                    HomeActivity.start(LoginActivity.this);
                } catch (Exception e) {
                    showLongToast(LoginActivity.this, "Check connection and try again!");
                }
            }

            @Override
            public void onCancel() {
                // App code
                showLongToast(LoginActivity.this, "you have canceled !");

            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                showLongToast(LoginActivity.this, " " + exception.getMessage());
            }
        });
    }

    @OnClick(R.id.visitorbtn)
    public void onViewClicked() {
        HomeActivity.start(LoginActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
