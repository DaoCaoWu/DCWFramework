package com.dcw.app.rating.di.module;

import com.dcw.app.rating.di.scope.ActivityScope;
import com.dcw.app.ui.framework.BaseActivityWrapper;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/11/19.
 */
@Module
public class ActivityModule {

    private BaseActivityWrapper mActivity;

    @Inject
    public ActivityModule(BaseActivityWrapper activity) {
        mActivity = activity;
    }

    @Provides
    @ActivityScope
    BaseActivityWrapper provideActivity() {
        return mActivity;
    }
}
