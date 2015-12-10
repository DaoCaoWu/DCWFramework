package com.dcw.app.base.mvp.view;

import com.dcw.app.base.mvp.presenter.Presenter;

/**
 * create by adao12.vip@gmail.com on 15/12/2
 *
 * @author JiaYing.Cheng
 * @version 1.0
 */
public interface ViewWithPresenter {

    Presenter<?> getPresenter();
}
