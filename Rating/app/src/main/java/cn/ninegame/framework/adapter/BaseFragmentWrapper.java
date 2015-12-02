package cn.ninegame.framework.adapter;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.dcw.app.biz.toolbar.ToolbarController;
import com.dcw.app.di.HasComponent;
import com.dcw.app.di.component.ActivityComponent;
import com.dcw.app.di.component.DaggerFragmentComponent;
import com.dcw.app.di.component.FragmentComponent;
import com.dcw.app.di.module.FragmentModule;
import com.dcw.app.mvp.view.PresenterFragment;
import com.dcw.app.util.Util;
import com.dcw.framework.view.annotation.InjectLayout;
import com.fragmentmaster.app.MasterFragment;

import butterknife.ButterKnife;
import cn.ninegame.framework.ICreateTemplate;


public abstract class BaseFragmentWrapper extends PresenterFragment implements ICreateTemplate, HasComponent<FragmentComponent> {

    protected View mRootView;
    protected ToolbarController mToolbarController;
    private FragmentComponent mFragmentComponent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    protected ActivityComponent getActivityComponent() {
        return ((BaseActivityWrapper) getActivity()).getActivityComponent();
    }

    @Override
    public FragmentComponent getComponent() {
        return mFragmentComponent;
    }

    protected FragmentComponent getFragmentComponent() {
        return mFragmentComponent;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
            Class<? extends BaseFragmentWrapper> targetClass = getClass();
            if (targetClass.isAnnotationPresent(InjectLayout.class)) {
                mRootView = inflater.inflate((targetClass.getAnnotation(InjectLayout.class)).value(), container, false);
                ButterKnife.bind(this, mRootView);
                initData();
                initUI();
                initListeners();
            }
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
    public void initData() {

    }

    @Override
    public void initUI() {

    }

    @Override
    public void initListeners() {

    }

    protected <T> T findViewById(int id) {
        return mRootView == null ? null : (T) mRootView.findViewById(id);
    }

    public void hideKeyboard() {
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
        mFragmentComponent = DaggerFragmentComponent.builder()
                .activityComponent(getActivityComponent())
                .fragmentModule(new FragmentModule(this))
                .build();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToolbarController != null) {
            return mToolbarController.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (mToolbarController != null) {
            mToolbarController.onConfigurationChanged(newConfig);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
