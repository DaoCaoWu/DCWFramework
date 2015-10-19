package com.dcw.app.rating.biz.search;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.dcw.app.rating.R;
import com.dcw.app.rating.biz.MainActivity;
import com.dcw.app.rating.biz.toolbar.ToolbarModel;
import com.dcw.app.rating.biz.toolbar.NavigationBar;
import com.dcw.app.rating.biz.toolbar.ToolbarController;
import com.dcw.app.rating.ui.adapter.BaseFragmentWrapper;
import com.dcw.app.rating.ui.adapter.ToastManager;
import com.dcw.framework.view.annotation.InjectLayout;

@InjectLayout(R.layout.fragment_search)
public class SearchFragment extends BaseFragmentWrapper implements SearchView.OnQueryTextListener {

    @Override
    public Class getHostActivity() {
        return MainActivity.class;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initUI() {
        mToolbarController = new ToolbarController((NavigationBar) findViewById(R.id.toolbar), new ToolbarModel(getString(R.string.search), R.menu.menu_search));
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        final MenuItem searchItem = menu.findItem(R.id.btn_right);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setIconifiedByDefault(false);
        MenuItemCompat.expandActionView(searchItem);
        searchView.setOnQueryTextListener(this);
        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                mToolbarController.onNavigationOnClicked(mToolbarController.getView());
                return true;
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        ToastManager.getInstance().showToast(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
