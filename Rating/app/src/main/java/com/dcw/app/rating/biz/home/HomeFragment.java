package com.dcw.app.rating.biz.home;

import android.app.Activity;
import android.content.res.Configuration;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.dcw.app.rating.R;
import com.dcw.app.rating.biz.MainActivity;
import com.dcw.app.rating.biz.account.ToolbarModel;
import com.dcw.app.rating.biz.search.SearchFragment;
import com.dcw.app.rating.biz.test.AbsListFragment;
import com.dcw.app.rating.biz.test.RichTextFragment;
import com.dcw.app.rating.biz.test.StateViewFragment;
import com.dcw.app.rating.biz.toolbar.IBackAction;
import com.dcw.app.rating.biz.toolbar.NavigationBar;
import com.dcw.app.rating.biz.toolbar.ToolbarController;
import com.dcw.app.rating.ui.adapter.BaseFragmentWrapper;
import com.dcw.framework.view.annotation.InjectLayout;
import com.dcw.framework.view.annotation.InjectView;

@InjectLayout(R.layout.fragment_home)
public class HomeFragment extends BaseFragmentWrapper implements ToolbarController.OnInitToolbarListener
        , Toolbar.OnMenuItemClickListener, IBackAction, NavigationView.OnNavigationItemSelectedListener {
    @InjectView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        getActivity().supportInvalidateOptionsMenu();
    }

    @Override
    public Class getHostActivity() {
        return MainActivity.class;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initUI() {
        ToolbarModel model = new ToolbarModel(getString(R.string.home), R.menu.menu_home, true, true);
        mToolbarController = new ToolbarController((NavigationBar) findViewById(R.id.toolbar), model);
        mToolbarController.setOnInitToolbarListener(this);
        mToolbarController.setOnMenuItemClickListener(this);
        mToolbarController.setOnNavigationOnClickListener(this);
    }

    @Override
    public void initListeners() {
    }

    @Override
    public void onInitToolbar(Toolbar toolbar, ActionBar actionBar) {
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), mDrawerLayout, R.string.drawer_open, R.string.drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    public void toggle() {
        if (mDrawerLayout.isDrawerVisible(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
    }

    @Override
    public void onNavigationOnClicked(View v) {
        toggle();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                startFragment(SearchFragment.class);
                break;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
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
        toggle();
        // Insert the fragment by replacing any existing fragment
        startFragment(fragmentClass);
        return true;
    }

    @Override
    public void onClick(View view) {
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
}
