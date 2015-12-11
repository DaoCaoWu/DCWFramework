package com.dcw.app.base.mvp.view;

import android.os.Bundle;
import android.support.annotation.CallSuper;

import com.fragmentmaster.app.MasterFragment;

import com.dcw.app.base.mvp.factory.PresenterFactory;
import com.dcw.app.base.mvp.presenter.Presenter;

/**
 * create by adao12.vip@gmail.com on 15/12/2
 *
 * @author JiaYing.Cheng
 * @version 1.0
 */
public class PresenterFragment extends MasterFragment implements ViewWithPresenter {

    private PresenterLifeCycleDelegate mDelegate = new PresenterLifeCycleDelegate(PresenterFactory.create(getClass()));

    @Override
    public Presenter<?> getPresenter() {
        return mDelegate.getPresenter();
    }

    @Override
    @CallSuper
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mDelegate.onRestoreInstanceState(savedInstanceState);
        }
    }

    @Override
    @CallSuper
    public void onResume() {
        super.onResume();
        mDelegate.onResume(this);
    }

    @Override
    @CallSuper
    public void onPause() {
        super.onPause();
        mDelegate.onPause(getActivity().isFinishing());
    }

    @Override
    @CallSuper
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(mDelegate.onSaveInstanceState(outState));
    }
}
