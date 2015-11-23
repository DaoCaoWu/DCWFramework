package com.dcw.app.di.module;

import com.dcw.app.di.scope.FragmentScope;
import cn.ninegame.framework.ToastManager;

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
