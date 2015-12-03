package com.dcw.app.mvp.factory;

import com.dcw.app.mvp.presenter.Presenter;

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
