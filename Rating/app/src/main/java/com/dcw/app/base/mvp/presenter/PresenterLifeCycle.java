package com.dcw.app.base.mvp.presenter;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * create by adao12.vip@gmail.com on 15/12/1
 *
 * @author JiaYing.Cheng
 * @version 1.0
 */
interface PresenterLifeCycle<V> {

    /**
     * This method is called after presenter construction.
     * <p/>
     * This method is intended for overriding.
     *
     * @param savedState If the presenter is being re-instantiated after a process restart then this Bundle
     *                   contains the data it supplied in {@link #onSave}.
     */
    void onCreate(@Nullable Bundle savedInstanceState);

    /**
     * This method is being called when a user leaves view.
     * <p/>
     * This method is intended for overriding.
     */
    void onDestroy();

    /**
     * Like {@link android.app.Fragment#onViewStateRestored(Bundle)}, but called only when {@link #getView()} is not
     * null, and debounced. That is, this method will be called exactly once for a given view
     * instance, at least until that view is {@link #dropView(Object) dropped}.
     * <p/>
     * See {@link #takeView} for details.
     */
    void onLoad(Bundle savedInstanceState);

    /**
     * A returned state is the state that will be passed to {@link #onCreate} for a new presenter instance after a process restart.
     * <p/>
     * This method is intended for overriding.
     *
     * @param state a non-null bundle which should be used to put presenter's state into.
     */
    void onSave(Bundle savedInstanceState);

    /**
     * This method is being called when a view gets attached to it.
     * Normally this happens during {@link Activity#onResume()}, {@link android.app.Fragment#onResume()}
     * and {@link android.view.View#onAttachedToWindow()}.
     * <p/>
     * This method is intended for overriding.
     *
     * @param view a view that should be taken
     */
    void onTakeView(V view);

    /**
     * This method is being called when a view gets detached from the presenter.
     * Normally this happens during {@link Activity#onPause()} ()}, {@link Fragment#onPause()} ()}
     * and {@link android.view.View#onDetachedFromWindow()}.
     * <p/>
     * This method is intended for overriding.
     */
    void onDropView();

}
