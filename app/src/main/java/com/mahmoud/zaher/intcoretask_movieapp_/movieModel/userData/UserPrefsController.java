package com.mahmoud.zaher.intcoretask_movieapp_.movieModel.userData;

import android.content.Context;

import com.mahmoud.zaher.intcoretask_movieapp_.utils.SharedPrefs;

import static com.mahmoud.zaher.intcoretask_movieapp_.utils.Constants.USER;

public class UserPrefsController {
    private static User instance;
    private Context context;
    private SharedPrefs<User> prefs;

    public UserPrefsController(Context context) {
        this.context = context;
        prefs = new SharedPrefs(context, User.class);
    }

    public void save() {
        prefs.save(instance, USER);
    }

    public User getUser() {
        if (instance == null) {
            instance = prefs.load(USER);
        }

        return instance;
    }

    public void setUser(User user) {
        instance = user;
    }

    public boolean hasLoggedInUser() {
        return getUser() != null;
    }

    public void logout() {
        prefs.remove(USER);
        setUser(null);
    }

    public User clone() {
        return prefs.load(USER);
    }

}
