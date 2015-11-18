package com.dcw.app.rating.activity;

import com.dcw.app.rating.app.AppComponent;
import com.dcw.app.ui.framework.BaseActivityWrapper;

import dagger.Component;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/11/19.
 */
@PreActivity
@Component(dependencies = AppComponent.class,
        modules = ActivityModule.class)
public interface ActivityComponent {

    BaseActivityWrapper activity();
}
