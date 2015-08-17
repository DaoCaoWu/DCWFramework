package com.dcw.app.rating.biz.toolbar;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.dcw.app.rating.R;

/**
 * Created by adao12 on 2015/8/17.
 */
public class NavigationBar extends BackToolbar {

    public NavigationBar(AppCompatActivity activity) {
        super(activity);
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        if (mToolbar != null) {
            mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.btn_right:
                            if (mToolBarActionListener != null) {
                                ((INavigationBarAction) mToolBarActionListener).onRightAction();
                            }
                            break;

                    }
                    return false;
                }
            });
        }
    }
}
