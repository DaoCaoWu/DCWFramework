package com.dcw.app.rating.biz.search;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.dcw.app.rating.R;
import com.dcw.app.rating.biz.MainActivity;
import com.dcw.app.rating.ui.adapter.BaseFragmentWrapper;
import com.dcw.framework.view.annotation.InjectLayout;

/**
 * Created by adao12 on 2015/8/16.
 */
@InjectLayout(R.layout.fragment_search)
public class SearchFragment extends BaseFragmentWrapper {

    @Override
    public Class getHostActivity() {
        return MainActivity.class;
    }

//    @Override
//    public Class getToolbar() {
//        return SearchToolbar.class;
//    }
//
//    @Override
//    public int getMenuResId() {
//        return R.menu.menu_search;
//    }

    @Override
    public void initData() {

    }

    @Override
    public void initUI() {

    }

    @Override
    public void initListeners() {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        mSearchView.setIconifiedByDefault(false);
        MenuItemCompat.expandActionView(searchItem);
        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
//                if (mToolBar.getToolBarActionListener() instanceof INavigationBarAction) {
//                    ((INavigationBarAction) mToolBar.getToolBarActionListener()).onNavigationOnClicked();
//                }
                return true;
            }
        });
    }
}
