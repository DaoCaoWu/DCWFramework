package com.dcw.app.di.component;

import com.dcw.app.app.App;
import com.dcw.app.biz.account.LoginFragment;
import com.dcw.app.di.module.UIModule;
import com.dcw.app.di.scope.FragmentScope;

import cn.ninegame.library.ui.framework.ToastManager;

import dagger.Component;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/11/19.
 */
@FragmentScope
@Component(dependencies = {AppComponent.class}, modules = {UIModule.class})
public interface UIComponent {

    void inject(LoginFragment fragment);

    App application();

    ToastManager toastManager();
}
