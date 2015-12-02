package com.dcw.app.mvp.view;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.dcw.app.mvp.factory.PresenterFactory;
import com.dcw.app.mvp.presenter.Presenter;

/**
 * create by adao12.vip@gmail.com on 15/12/2
 *
 * @author JiaYing.Cheng
 * @version 1.0
 */
public class PresenterLayout extends FrameLayout implements ViewWithPresenter {

    private static final String PARENT_STATE = "parent_state";
    private static final String PRESENTER_STATE = "presenter_state";

    private PresenterLifeCycleDelegate mDelegate = new PresenterLifeCycleDelegate(PresenterFactory.create(getClass()));

    public PresenterLayout(Context context) {
        super(context);
    }

    public PresenterLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PresenterLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public Presenter<?> getPresenter() {
        return mDelegate.getPresenter();
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putBundle(PRESENTER_STATE, mDelegate.onSaveInstanceState(bundle));
        bundle.putParcelable(PARENT_STATE, super.onSaveInstanceState());
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        Bundle bundle = (Bundle)state;
        super.onRestoreInstanceState(bundle.getParcelable(PARENT_STATE));
        mDelegate.onRestoreInstanceState(bundle.getBundle(PRESENTER_STATE));
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!isInEditMode()) {
            mDelegate.onResume(this);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mDelegate.onPause(getActivity().isFinishing());
    }

    /**
     * Returns the unwrapped activity of the view or throws an exception.
     *
     * @return an unwrapped activity
     */
    public Activity getActivity() {
        Context context = getContext();
        while (!(context instanceof Activity) && context instanceof ContextWrapper)
            context = ((ContextWrapper)context).getBaseContext();
        if (!(context instanceof Activity))
            throw new IllegalStateException("Expected an activity context, got " + context.getClass().getSimpleName());
        return (Activity)context;
    }
}
