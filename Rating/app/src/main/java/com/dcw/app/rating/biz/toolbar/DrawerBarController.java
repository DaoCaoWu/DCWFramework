package com.dcw.app.rating.biz.toolbar;

import android.content.res.Configuration;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;

import com.dcw.app.R;

/**
 * Created by adao12 on 2015/10/20.
 */
public class DrawerBarController extends ToolbarController implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    ActionBarDrawerToggle mDrawerToggle;
    NavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener;

    public DrawerBarController(Object view, ToolbarModel model) {
        super(view, model);
    }

    public void setNavigationView(NavigationView navigationView) {
        mNavigationView = navigationView;
    }

    public void setDrawerLayout(DrawerLayout drawerLayout) {
        mDrawerLayout = drawerLayout;
    }

    public void setOnNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener) {
        mOnNavigationItemSelectedListener = onNavigationItemSelectedListener;
    }

    public void initToolbar() {
        super.initToolbar();
        mNavigationView.setNavigationItemSelectedListener(this);
        mDrawerToggle = new ActionBarDrawerToggle(mFragment.getActivity(), mDrawerLayout, getView(), R.string.drawer_open, R.string.drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        if (mOnNavigationItemSelectedListener == null) {
            return false;
        } else {
            return mOnNavigationItemSelectedListener.onNavigationItemSelected(menuItem);
        }
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
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
}
