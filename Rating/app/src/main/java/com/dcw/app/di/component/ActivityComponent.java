package com.dcw.app.di.component;

import com.dcw.app.di.module.ActivityModule;
import com.dcw.app.di.scope.ActivityScope;
import cn.ninegame.library.ui.framework.BaseActivityWrapper;

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
