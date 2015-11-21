package com.dcw.app.di.component;

import android.content.SharedPreferences;

import com.dcw.app.di.module.AppModule;
import com.dcw.app.di.module.NetworkModule;
import com.squareup.okhttp.OkHttpClient;

import javax.inject.Singleton;

import dagger.Component;
import retrofit.Retrofit;

/**
 * @author JiaYing.Cheng
 * @version 1.0
 * @email adao12.vip@gmail.com
 * @create 15/11/19
 */
@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})
public interface NetworkComponent {

    // downstream components need these exposed
    Retrofit retrofit();

    OkHttpClient okHttpClient();

    SharedPreferences sharedPreferences();
}
