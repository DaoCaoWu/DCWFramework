package com.dcw.app.di.component;

import com.dcw.app.app.App;
import com.dcw.app.presentation.account.view.fragment.LoginFragment;
import com.dcw.app.di.module.UIModule;
import com.dcw.app.di.scope.FragmentScope;

import cn.ninegame.framework.ToastManager;

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
