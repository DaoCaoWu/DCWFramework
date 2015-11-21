package com.dcw.app.di.component;

import com.dcw.app.biz.account.User;
import com.dcw.app.di.module.UserModule;

import javax.inject.Named;

import dagger.Component;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/11/21.
 */
@Component(modules = UserModule.class)
public interface UserComponent {

    User user();

    @Named("currentUser") User currentUser();
}
