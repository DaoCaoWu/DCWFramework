package com.dcw.app.base.mvp.factory;

import com.dcw.app.base.mvp.presenter.Presenter;

/**
 * create by adao12.vip@gmail.com on 15/12/1
 *
 * @author JiaYing.Cheng
 * @version 1.0
 */
public interface IPresenterRepository {

    boolean isExist(String id);

    Presenter<?> get(String id);

    void add(Presenter<?> presenter);

    void remove(String id);
}
