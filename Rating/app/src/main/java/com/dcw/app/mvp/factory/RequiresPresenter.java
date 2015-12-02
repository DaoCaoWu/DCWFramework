package com.dcw.app.mvp.factory;

import com.dcw.app.mvp.presenter.Presenter;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresPresenter {
    Class<? extends Presenter> value();
}
