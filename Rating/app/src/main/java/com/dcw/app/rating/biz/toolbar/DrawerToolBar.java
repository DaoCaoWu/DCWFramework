package com.dcw.app.rating.biz.toolbar;

import android.content.res.Configuration;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.dcw.app.rating.R;

/**
 * Created by adao12 on 2015/8/16.
 */
public class DrawerToolbar extends AbsToolbar {

    private DrawerLayout mDrawer;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle mDrawerToggle;

    public DrawerToolbar(AppCompatActivity activity) {
        super(activity);
    }

    @Override
    protected void initToolbar() {
        if (mActivity == null) {
            return;
        }
        mToolbar = (Toolbar) mActivity.findViewById(R.id.toolbar);
        mToolbar.inflateMenu(R.menu.menu_home);
        mDrawer = (DrawerLayout) mActivity.findViewById(R.id.drawer_layout);
        nvDrawer = (NavigationView) mActivity.findViewById(R.id.navigation_view);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
            }
        });
        mDrawerToggle = setupDrawerToggle();
        mDrawer.setDrawerListener(mDrawerToggle);
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(mActivity, mDrawer, R.string.drawer_open, R.string.drawer_close);
    }

    public void toggle() {
        if (mDrawer.isDrawerVisible(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            mDrawer.openDrawer(GravityCompat.START);
        }
    }

    public void setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener listener) {
        nvDrawer.setNavigationItemSelectedListener(listener);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        // Pass any configuration change to the drawer toggles
        mDrawerToggle.onConfigurationChanged(newConfig);

    }

    public DrawerLayout getDrawer() {
        return mDrawer;
    }

    public NavigationView getNvDrawer() {
        return nvDrawer;
    }

    public ActionBarDrawerToggle getDrawerToggle() {
        return mDrawerToggle;
    }
}
