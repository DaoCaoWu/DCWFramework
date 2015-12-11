package com.dcw.app.di.module;

import com.dcw.app.presentation.account.model.User;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/11/21.
 */
@Module
public class UserModule {

    @Provides
    User provideUser() {
        return new User();
    }

    @Provides @Named("currentUser")
    User provideCurrentUser() {
        return new User();
    }
}
