package com.dcw.app.rating.di.module;

import com.dcw.app.rating.di.scope.ActivityScope;
import com.dcw.app.rating.di.scope.FragmentScope;
import com.dcw.app.ui.framework.ToastManager;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/11/19.
 */
@Module
public class UIModule {

//    ToastManager mToastManager;
//
//    @Inject public UIModule(ToastManager toastManager) {
//        mToastManager = toastManager;
//    }


    public UIModule() {
    }

    @Provides @FragmentScope
    ToastManager provideToastManager() {
        return ToastManager.getInstance();
    }
}
