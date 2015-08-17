package com.dcw.app.rating.biz.toolbar;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.dcw.app.rating.R;


/**
 * Created by adao12 on 2015/8/16.
 */
public class BackTooBar implements IToolBar {

    private Toolbar mToolbar;

    public BackTooBar(AppCompatActivity activity) {
        this.mToolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(R.drawable.nav_icon_back);
        activity.setSupportActionBar(mToolbar);
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }
}
