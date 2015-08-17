package com.dcw.app.rating.biz.toolbar;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.dcw.app.rating.R;

/**
 * Created by adao12 on 2015/8/17.
 */
public class SearchToolbar extends AbsToolbar {

    private SearchView mSearchView;

    public SearchToolbar(AppCompatActivity activity) {
        super(activity);
    }

    @Override
    protected void initToolbar() {
        if (mActivity == null) {
            return;
        }
        mToolbar = (Toolbar) mActivity.findViewById(R.id.toolbar);
        mToolbar.inflateMenu(R.menu.menu_search);
        MenuItem searchItem = mToolbar.getMenu().findItem(R.id.action_search);
        mSearchView = (SearchView)MenuItemCompat.getActionView(searchItem);
        mSearchView.setIconifiedByDefault(false);
        MenuItemCompat.expandActionView(searchItem);
        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                if (mToolBarActionListener != null) {
                    ((IBackAction) mToolBarActionListener).onBack();
                }
                return true;
            }
        });
    }

    public SearchView getSearchView() {
        return mSearchView;
    }
}
