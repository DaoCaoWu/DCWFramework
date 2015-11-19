package com.dcw.app.rating.di.component;

import com.dcw.app.rating.di.module.ActivityModule;
import com.dcw.app.rating.di.scope.ActivityScope;
import com.dcw.app.ui.framework.BaseActivityWrapper;

import dagger.Component;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/11/19.
 */
@ActivityScope
@Component(dependencies = AppComponent.class,
        modules = ActivityModule.class)
public interface ActivityComponent {

    BaseActivityWrapper activity();
}
