package com.dcw.app.mvp.factory;

import android.support.annotation.NonNull;

import com.dcw.app.mvp.presenter.Presenter;

/**
 * create by adao12.vip@gmail.com on 15/12/1
 *
 * @author JiaYing.Cheng
 * @version 1.0
 */
public class PresenterFactory implements Presenter.Factory {

    private final Class<?> mViewClass;

    public static PresenterFactory create(@NonNull Class<?> viewClass) {
        return new PresenterFactory(viewClass);
    }

    private PresenterFactory(Class<?> viewClass) {
        mViewClass = viewClass;
    }

    @Override
    public Presenter<?> getPresenter() {
        RequiresPresenter annotation = mViewClass.getAnnotation(RequiresPresenter.class);
        //noinspection unchecked
        Class<? extends Presenter> presenterClass = annotation == null ? null : (Class<? extends Presenter>) annotation.value();
        try {
            return (Presenter<?>)(presenterClass == null ? null : presenterClass.newInstance());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
