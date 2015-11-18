package com.dcw.app.rating.app;

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
