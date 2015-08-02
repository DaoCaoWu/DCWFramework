package com.dcw.app.rating.biz.home;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.dcw.app.rating.R;
import com.dcw.app.rating.biz.MainActivity;
import com.dcw.app.rating.biz.test.AbsListFragment;
import com.dcw.app.rating.biz.test.RichTextFragment;
import com.dcw.app.rating.biz.test.StateViewFragment;
import com.dcw.app.rating.ui.adapter.BaseFragmentWrapper;
import com.dcw.framework.view.annotation.InjectLayout;
import com.dcw.framework.view.annotation.InjectView;

@InjectLayout(R.layout.fragment_home)
public class HomeFragment extends BaseFragmentWrapper {

    @InjectView(R.id.toolbar)
    private Toolbar mToolbar;

    @InjectView(R.id.drawer_layout)
    private DrawerLayout mDrawer;

    @InjectView(R.id.nvView)
    private NavigationView nvDrawer;

    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    public Class getHostActivity() {
        return MainActivity.class;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initUI() {
        initToolBar();
    }

    @Override
    public void initListeners() {

    }

    private void initToolBar() {
        // Inflate a menu to be displayed in the toolbar
        mToolbar.inflateMenu(R.menu.menu_second);
//        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setTitle("Home");
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_settings:
                        toggle();
                        break;
                }
                return false;
            }
        });
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDrawer.isDrawerVisible(GravityCompat.START)) {
                    mDrawer.closeDrawer(GravityCompat.START);
                } else {
                    onBackPressed();
                }
            }
        });

        setupDrawerContent(nvDrawer);
        mDrawerToggle = setupDrawerToggle();
        mDrawer.setDrawerListener(mDrawerToggle);
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(getActivity(), mDrawer, R.string.drawer_open, R.string.drawer_close);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        mDrawerToggle.onConfigurationChanged(newConfig);

    }

    private void toggle() {
        if (mDrawer.isDrawerVisible(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            mDrawer.openDrawer(GravityCompat.START);
        }
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the planet to show based on
        // position
        Class fragmentClass;
        switch(menuItem.getItemId()) {
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
                fragmentClass = AbsListFragment.class;
        }
        // Insert the fragment by replacing any existing fragment
        startFragment(fragmentClass);

        // Highlight the selected item, update the title, and close the drawer
        menuItem.setChecked(true);
        mToolbar.setTitle(menuItem.getTitle());
        mDrawer.closeDrawers();
    }

    @Override
    public void onClick(View view) {
    }

}
