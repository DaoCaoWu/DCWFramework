package com.dcw.app.mvp.view;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.dcw.app.mvp.factory.PresenterFactory;
import com.dcw.app.mvp.factory.PresenterRepository;
import com.dcw.app.mvp.presenter.Presenter;

/**
 * create by adao12.vip@gmail.com on 15/12/1
 *
 * @author JiaYing.Cheng
 * @version 1.0
 */
public class PresenterLifeCycleDelegate {

    private static final String PRESENTER = "presenter";
    private static final String PRESENTER_ID = "presenter_id";

    @Nullable PresenterFactory mFactory;
    @Nullable Bundle mPresenterState;
    @Nullable Presenter mPresenter;

    public PresenterLifeCycleDelegate(PresenterFactory factory) {
        mFactory = factory;
    }

    public PresenterFactory getFactory() {
        return mFactory;
    }

    public void setFactory(@NonNull PresenterFactory factory) {
        mFactory = factory;
    }

    public Presenter<?> getPresenter() {
        return mPresenter;
    }

    /**
     * {@link android.app.Activity#onSaveInstanceState(Bundle)}, {@link android.app.Fragment#onSaveInstanceState(Bundle)}, {@link android.view.View#onSaveInstanceState()}.
     */
    public Bundle onSaveInstanceState(Bundle outState) {
        restorePresenter();
        if (mPresenter != null) {
            mPresenter.save(outState);
            if (outState == null) {
                outState = new Bundle();
            }
            outState.putString(PRESENTER_ID, mPresenter.getId());
        }
        return outState;
    }

    /**
     * {@link android.app.Activity#onCreate(Bundle)}, {@link android.app.Fragment#onCreate(Bundle)}, {@link android.view.View#onRestoreInstanceState(Parcelable)}.
     */
    public void onRestoreInstanceState(Bundle presenterState) {
        if (mPresenter != null)
            throw new IllegalArgumentException("onRestoreInstanceState() should be called before onResume()");
        mPresenterState = presenterState;
        mPresenterState = ParcelFn.unMarshall(ParcelFn.marshall(presenterState));
    }

    private void restorePresenter() {
        if (mFactory != null) {
            if (mPresenter == null && mPresenterState != null) {
                mPresenter = PresenterRepository.getInstance().get(mPresenterState.getString(PRESENTER_ID));
            }
            if (mPresenter == null) {
                mPresenter = mFactory.getPresenter();
                if (mPresenter != null) {
                    PresenterRepository.getInstance().add(mPresenter);
                    mPresenter.create(mPresenterState == null ? null : mPresenterState.getBundle(PRESENTER));
                }
            }
        }
    }

    /**
     * {@link android.app.Activity#onResume()}, {@link android.app.Fragment#onResume()}, {@link android.view.View#onAttachedToWindow()}
     */
    public void onResume(Object view) {
        restorePresenter();
        if (mPresenter != null)
            //noinspection unchecked
            mPresenter.takeView(view);
    }

    /**
     * {@link android.app.Activity#onPause()}, {@link android.app.Fragment#onPause()}, {@link android.view.View#onDetachedFromWindow()}
     */
    public void onPause(boolean destroy) {
        if (mPresenter != null) {
            mPresenter.dropView();
            if (destroy) {
                mPresenter.destroy();
                mPresenter = null;
            }
        }
    }


}
