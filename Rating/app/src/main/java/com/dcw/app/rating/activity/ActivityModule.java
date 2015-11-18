package com.dcw.app.rating.activity;

import android.support.v7.app.AppCompatActivity;

import com.dcw.app.ui.framework.BaseActivityWrapper;

import javax.inject.Inject;
import javax.inject.Scope;
import javax.inject.Singleton;

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
    @Singleton
    BaseActivityWrapper provideActivity() {
        return mActivity;
    }
}
