package com.dcw.app.presentation.toolbar;

import android.content.res.Configuration;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import cn.ninegame.framework.adapter.BaseFragmentWrapper;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import cn.ninegame.library.component.mvc.Controller;

public class ToolbarController extends Controller<NavigationBar, ToolbarModel> implements NavigationBar.IBackAction, Toolbar.OnMenuItemClickListener {

    BaseFragmentWrapper mFragment;
    NavigationBar.IBackAction mOnNavigationOnClickListener;
    OnInitToolbarListener mOnInitToolbarListener;
    Toolbar.OnMenuItemClickListener mOnMenuItemClickListener;

    public ToolbarController(Object view, ToolbarModel model) {
        super((NavigationBar) view, model);
        getView().setViewListener(this);
        getModel().addObserver(getView());
    }

    public void setOnMenuItemClickListener(Toolbar.OnMenuItemClickListener onMenuItemClickListener) {
        mOnMenuItemClickListener = onMenuItemClickListener;
    }

    public void setOnNavigationOnClickListener(NavigationBar.IBackAction onNavigationOnClickListener) {
        mOnNavigationOnClickListener = onNavigationOnClickListener;
    }

    public void setOnInitToolbarListener(OnInitToolbarListener onInitToolbarListener) {
        mOnInitToolbarListener = onInitToolbarListener;
    }

    public void attachToFragment(BaseFragmentWrapper fragment) {
        mFragment = fragment;
        initToolbar();
    }

    protected void initToolbar() {
        AppCompatActivity activity = (AppCompatActivity) mFragment.getActivity();
        activity.setSupportActionBar(getView());
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            initSystemBar(activity);
            if (mOnInitToolbarListener == null) {
                actionBar.setDisplayHomeAsUpEnabled(getModel().isShowHomeAsUp());
            } else {
                mOnInitToolbarListener.onInitToolbar(getView(), actionBar);
            }
        }

    }

    // A method to find height of the status bar
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = mFragment.getActivity().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = mFragment.getActivity().getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private void initSystemBar(AppCompatActivity activity) {

        SystemBarTintManager tintManager = new SystemBarTintManager(activity);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(getView().getDrawingCacheBackgroundColor());
        SystemBarTintManager.SystemBarConfig config = tintManager.getConfig();

        // Set the padding to match the Status Bar height
        getView().setPadding(0, getStatusBarHeight(), 0, 0);
    }



    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

    public void onConfigurationChanged(Configuration newConfig) {
    }

    /**
     * 此方法用于初始化菜单，其中menu参数就是即将要显示的Menu实例。 返回true则显示该menu,false 则不显示;
     * (只会在第一次初始化菜单时调用) Inflate the menu; this adds items to the action bar
     * if it is present.
     */
    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {
        if (getModel().getMenuResId() > 0) {
            inflater.inflate(getModel().getMenuResId(), menu);
        }
        getModel().notifyObservers();
        getView().setOnMenuItemClickListener(ToolbarController.this);
        getView().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                onNavigationOnClicked(v);
            }
        });
    }

    /**
     * 在onCreateOptionsMenu执行后，菜单被显示前调用；如果菜单已经被创建，则在菜单显示前被调用。 同样的，
     * 返回true则显示该menu,false 则不显示; （可以通过此方法动态的改变菜单的状态，比如加载不同的菜单等）
     */
    public void onPrepareOptionsMenu(Menu menu) {

    }

    public void updateOptionMenu() {
        if (getView().getMenu() != null) {
            getView().getMenu().clear();
            if (getModel().getMenuResId() > 0) {
                mFragment.getActivity().getMenuInflater().inflate(getModel().getMenuResId(), getView().getMenu());
            }
            mFragment.getActivity().supportInvalidateOptionsMenu();
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (mOnMenuItemClickListener == null)
            return false;
        else
            return mOnMenuItemClickListener.onMenuItemClick(item);
    }

    @Override
    public void onNavigationOnClicked(View v) {
        if (mOnNavigationOnClickListener == null)
            mFragment.onBackPressed();
        else
            mOnNavigationOnClickListener.onNavigationOnClicked(v);
    }

    public interface OnInitToolbarListener {

        void onInitToolbar(Toolbar toolbar, ActionBar actionBar);
    }
}
