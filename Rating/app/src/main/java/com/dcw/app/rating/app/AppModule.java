package com.dcw.app.rating.app;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/11/18.
 */
@Module
public class AppModule {

    private App mApp;

    @Inject public AppModule(App app) {
        mApp = app;
    }

    @Provides
    @Singleton
    App provideApplication() {
        return mApp;
    }

}
