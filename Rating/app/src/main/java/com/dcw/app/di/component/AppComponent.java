package com.dcw.app.di.component;

import com.dcw.app.app.App;
import com.dcw.app.presentation.account.model.User;
import com.dcw.app.di.module.AppModule;
import com.dcw.app.di.module.UserModule;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/11/18.
 */
@Singleton
@Component(modules = {AppModule.class, UserModule.class})
public interface AppComponent {

    App application();

    @Named("currentUser") User currentUser();
}
