package com.mahmoud.zaher.intcoretask_movieapp_.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.mahmoud.zaher.intcoretask_movieapp_.utils.Constants.LOG_TAG;

public class Utils {

    public static final boolean DEBUGGABLE = true;

    /**
     * get the hash key
     */
    public static String getHashKey(Context context) {
        String hashKey = null;
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                hashKey = Base64.encodeToString(md.digest(), Base64.DEFAULT);
                Log.e("hash key", hashKey);
            }
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException ignored) {
            logE("Failed to get hash key");
        }

        return hashKey;
    }


    public static void logE(String msg) {
        if (DEBUGGABLE) Log.e(LOG_TAG, msg == null ? "null" : msg);
    }

    public static void showLongToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }


    /**
     * Checks the device has a connection (not necessarily connected to the internet)
     *
     * @param ctx
     * @return
     */
    public static boolean hasConnection(Context ctx) {
        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetwork != null && wifiNetwork.isConnected()) {
            return true;
        }
        NetworkInfo mobileNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobileNetwork != null && mobileNetwork.isConnected()) {
            return true;
        }
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

}
