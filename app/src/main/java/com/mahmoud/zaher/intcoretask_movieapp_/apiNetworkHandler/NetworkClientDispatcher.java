package com.mahmoud.zaher.intcoretask_movieapp_.apiNetworkHandler;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.mahmoud.zaher.intcoretask_movieapp_.utils.Constants.BASE_URL;
import static com.mahmoud.zaher.intcoretask_movieapp_.utils.Constants.REQUEST_TIMEOUT;

public class NetworkClientDispatcher {
    public static Context mcontext;
    private static OkHttpClient okHttpClient;
    private static Retrofit retrofit = null;
    private static HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);

    public NetworkClientDispatcher(Context context) {
        mcontext = context.getApplicationContext();
    }

    public static Retrofit getClient() {

        if (okHttpClient == null)
            initOkHttp();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }

    private static void initOkHttp() {
        OkHttpClient.Builder httpClient = new OkHttpClient().newBuilder()
                .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(interceptor);
        okHttpClient = httpClient.build();
    }
}
