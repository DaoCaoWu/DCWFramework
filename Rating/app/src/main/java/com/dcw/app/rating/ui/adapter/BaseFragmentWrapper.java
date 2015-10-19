package com.dcw.app.rating.ui.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dcw.app.rating.R;
import com.dcw.app.rating.biz.toolbar.ToolbarController;
import com.dcw.app.rating.config.BundleConstant;
import com.dcw.app.rating.util.Util;
import com.dcw.framework.pac.ui.BaseFragment;
import com.dcw.framework.view.DCWAnnotation;


public abstract class BaseFragmentWrapper extends BaseFragment implements ICreateTemplate {

    protected View mRootView;
    protected ToolbarController mToolbarController;
    private boolean mIsDestroy;

    public BaseFragmentWrapper() {
        super();
        setUseAnim(false);
        setCustomAnimations(R.anim.open_slide_in, R.anim.open_slide_out, R.anim.close_slide_in, R.anim.close_slide_out);
    }

    public abstract Class getHostActivity();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIsDestroy = false;
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = DCWAnnotation.inject(this, inflater);
            initData();
            initUI();
            initListeners();
        }
        if (mRootView != null) {
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (parent != null) {
                parent.removeAllViewsInLayout();
            }
            return mRootView;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mIsDestroy = true;
    }

    public boolean isFragmentDestroy() {
        return mIsDestroy;
    }

    public int getFragmentType() {
        int fragmentType = 0;
        Bundle bundle = getBundleArguments();
        if (bundle != null) {
            fragmentType = bundle.getInt(BundleConstant.KEY_FRAGMENT_TYPE);
        }
        return fragmentType;
    }

    @Override
    public void onResult(Bundle bundle) {
        super.onResult(bundle);
    }

    protected <T> T findViewById(int id) {
        return mRootView == null ? null : (T) mRootView.findViewById(id);
    }

    protected void hideKeyboard() {
        Activity activity = getActivity();
        if (activity == null) return;
        try {
            View v = getActivity().getCurrentFocus();
            if (v != null) {
                Util.hideKeyboard(getActivity(), v.getWindowToken());
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mToolbarController != null) {
            mToolbarController.attachToFragment(this);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (mToolbarController != null) {
            mToolbarController.onCreateOptionsMenu(menu, inflater);
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (mToolbarController != null) {
            mToolbarController.onPrepareOptionsMenu(menu);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mToolbarController != null) {
            mToolbarController.updateOptionMenu();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            if (mToolbarController != null) {
                mToolbarController.updateOptionMenu();
            }
        }
    }
}
