package com.dcw.app.rating.di.component;

import com.dcw.app.rating.app.App;
import com.dcw.app.rating.di.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/11/18.
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    App application();
}
