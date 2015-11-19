package com.dcw.app.rating.di.module;

import com.dcw.app.rating.di.scope.FragmentScope;
import com.dcw.app.ui.framework.BaseFragmentWrapper;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;

/**
 * @author JiaYing.Cheng
 * @version 1.0
 * @email adao12.vip@gmail.com
 * @create 15/11/19
 */
@Module
public class FragmentModule {

    BaseFragmentWrapper mFragment;

    @Inject
    public FragmentModule(BaseFragmentWrapper fragment) {
        mFragment = fragment;
    }

    @Provides
    @FragmentScope
    public BaseFragmentWrapper provideFragment() {
        return mFragment;
    }
}
