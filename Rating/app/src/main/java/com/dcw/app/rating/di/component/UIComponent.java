package com.dcw.app.rating.di.component;

import com.dcw.app.rating.app.App;
import com.dcw.app.rating.biz.account.LoginFragment;
import com.dcw.app.rating.di.module.ActivityModule;
import com.dcw.app.rating.di.module.FragmentModule;
import com.dcw.app.rating.di.module.UIModule;
import com.dcw.app.rating.di.scope.ActivityScope;
import com.dcw.app.rating.di.scope.FragmentScope;
import com.dcw.app.ui.framework.BaseActivityWrapper;
import com.dcw.app.ui.framework.BaseFragmentWrapper;
import com.dcw.app.ui.framework.ToastManager;

import dagger.Component;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/11/19.
 */
@FragmentScope
@Component(dependencies = AppComponent.class, modules = {UIModule.class})
public interface UIComponent {

    void inject(LoginFragment fragment);

    App application();

    ToastManager toastManager();
}
