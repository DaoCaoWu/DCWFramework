package com.dcw.app.di.module;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.dcw.app.app.App;
import com.dcw.app.network.GsonConverterFactory;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * @author JiaYing.Cheng
 * @version 1.0
 * @email adao12.vip@gmail.com
 * @create 15/11/19
 */
@Module
public class NetworkModule {

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(App application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }


    @Provides
    @Singleton
    Cache provideOkHttpCache(App application) {
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(application.getCacheDir(), cacheSize);
        return cache;
    }


    @Provides  // Dagger will only look for methods annotated with @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return gsonBuilder.create();
    }


    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Cache cache) {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setCache(cache);
        return okHttpClient;
    }


    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
//                .addConverterFactory(RatingConvertFactory.create())
                .baseUrl("http://sns.mock.uctest.local:8024")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
//                .addConverterFactory(SecurityConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
//        retrofit.client().interceptors().add(new RatingInterceptor());
        return retrofit;
    }
}
