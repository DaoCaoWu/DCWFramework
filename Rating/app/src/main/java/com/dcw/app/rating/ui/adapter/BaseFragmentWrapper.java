package com.dcw.app.rating.ui.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dcw.app.rating.R;
import com.dcw.app.rating.biz.toolbar.AbsToolbar;
import com.dcw.app.rating.biz.toolbar.INavigationBarAction;
import com.dcw.app.rating.biz.toolbar.NavigationBar;
import com.dcw.app.rating.config.BundleConstant;
import com.dcw.app.rating.util.Util;
import com.dcw.framework.pac.ui.BaseFragment;
import com.dcw.framework.view.DCWAnnotation;

import java.lang.reflect.Constructor;


public abstract class BaseFragmentWrapper<TB extends AbsToolbar> extends BaseFragment implements ICreateTemplate {

    private boolean mIsDestroy;
    protected View mRootView;
    protected AbsToolbar mToolBar;
    protected CharSequence mTitle;

    public BaseFragmentWrapper() {
        super();
        setUseAnim(false);
        setCustomAnimations(R.anim.open_slide_in, R.anim.open_slide_out, R.anim.close_slide_in, R.anim.close_slide_out);
    }

    public abstract Class getHostActivity();

    public Class getToolbar() throws NoSuchMethodException {
        return NavigationBar.class;
    }

    public int getMenuResId() {
        return R.menu.menu_main;
    }

    public void setTitle(CharSequence title) {
        mTitle = title;
    }

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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mToolBar == null) {
            initToolbar();
        }
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

    protected void initToolbar() {
        if (mRootView == null) {
            throw new NullPointerException("mRootView is null, This is called after inflated view ");
        }
        try {
            Class<TB> toolbarClass = getToolbar();
            Constructor<TB> constructor = toolbarClass.getConstructor(AppCompatActivity.class);
            mToolBar = constructor.newInstance((AppCompatActivity) getActivity());
            mToolBar.setTitle(mTitle);
            mToolBar.setToolBarActionListener(new INavigationBarAction() {
                @Override
                public void onRightAction() {
                    onRightBtnClicked();
                }

                @Override
                public void onBack() {
                    onBackPressed();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(getMenuResId(), menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void updateOptionMenu() {
        if (mToolBar != null && mToolBar.getToolbar() != null && mToolBar.getToolbar().getMenu() != null) {
            mToolBar.getToolbar().getMenu().clear();
            getActivity().getMenuInflater().inflate(getMenuResId(), mToolBar.getToolbar().getMenu());
            getActivity().supportInvalidateOptionsMenu();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateOptionMenu();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            updateOptionMenu();
        }
    }

    @Override
    public void onResult(Bundle bundle) {
        super.onResult(bundle);
    }

    public void onRightBtnClicked() {

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
}
