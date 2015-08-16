package com.dcw.app.rating.biz.toolbar;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.dcw.app.rating.R;


/**
 * Created by adao12 on 2015/8/16.
 */
public class BackToolbar extends AbsToolbar {

    public BackToolbar(AppCompatActivity activity) {
        super(activity);
    }

    @Override
    protected void initToolbar() {
        if (mActivity == null) {
            return;
        }
        mToolbar = (Toolbar) mActivity.findViewById(R.id.toolbar);
        if (mToolbar != null) {
            mActivity.setSupportActionBar(mToolbar);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mToolBarActionListener != null) {
                        ((IBackAction) mToolBarActionListener).onBack();
                    }
                }
            });
        }
        ActionBar actionBar = mActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }
}
