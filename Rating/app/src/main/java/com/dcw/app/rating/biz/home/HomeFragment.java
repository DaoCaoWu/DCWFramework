package com.dcw.app.rating.biz.home;

import android.content.res.Configuration;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.dcw.app.rating.R;
import com.dcw.app.rating.biz.MainActivity;
import com.dcw.app.rating.biz.search.SearchFragment;
import com.dcw.app.rating.biz.test.AbsListFragment;
import com.dcw.app.rating.biz.test.RichTextFragment;
import com.dcw.app.rating.biz.test.StateViewFragment;
import com.dcw.app.rating.biz.toolbar.DrawerToolbar;
import com.dcw.app.rating.ui.adapter.BaseFragmentWrapper;
import com.dcw.framework.view.annotation.InjectLayout;

@InjectLayout(R.layout.fragment_home)
public class HomeFragment extends BaseFragmentWrapper<DrawerToolbar> {

    @Override
    public Class getHostActivity() {
        return MainActivity.class;
    }

    @Override
    public Class getToolbar() {
        return DrawerToolbar.class;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initUI() {
        setTitle("Home");
    }

    @Override
    public void initListeners() {

    }

    @Override
    protected void initToolbar() {
//        mToolBar = new DrawerToolbar((AppCompatActivity)getActivity());
//        mToolBar.setTitle(mTitle);
        super.initToolbar();
        mToolBar.getToolbar().setNavigationIcon(R.mipmap.ic_launcher);
        mToolBar.getToolbar().setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_search:
                        startFragment(SearchFragment.class);
                        break;
                }
                return false;
            }
        });
        ((DrawerToolbar) mToolBar).setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        ((DrawerToolbar) mToolBar).onConfigurationChanged(newConfig);

    }


    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the planet to show based on
        // position
        Class fragmentClass;
        switch (menuItem.getItemId()) {
            case R.id.nav_first_fragment:
                fragmentClass = RichTextFragment.class;
                break;
            case R.id.nav_second_fragment:
                fragmentClass = AbsListFragment.class;
                break;
            case R.id.nav_third_fragment:
                fragmentClass = StateViewFragment.class;
                break;
            default:
                fragmentClass = SearchFragment.class;
        }
        ((DrawerToolbar)mToolBar).toggle();
        // Insert the fragment by replacing any existing fragment
        startFragment(fragmentClass);
    }

    @Override
    public void onClick(View view) {
    }

}
