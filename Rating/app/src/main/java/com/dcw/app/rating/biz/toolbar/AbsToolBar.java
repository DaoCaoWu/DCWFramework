package com.dcw.app.rating.biz.toolbar;

import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.dcw.app.rating.R;
import com.nineoldandroids.animation.ValueAnimator;

/**
 * Created by adao12 on 2015/8/16.
 */
public abstract class AbsToolBar {

    protected Toolbar mToolbar;
    protected AppCompatActivity mActivity;
    protected IMenuAction mToolBarActionListener;

    public AbsToolBar(AppCompatActivity activity) {
        mActivity = activity;
        initToolbar();
    }

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
                        ((IBackAction) mToolBarActionListener).onNavigationOnClicked();
                    }
                }
            });
        }
        ActionBar actionBar = mActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(false);
        }
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }

    public void setTitle(CharSequence title) {
        if (mToolbar != null) {
            mToolbar.setTitle(title);
        }
    }

    public void setToolBarActionListener(IMenuAction toolBarActionListener) {
        this.mToolBarActionListener = toolBarActionListener;
    }

    public IMenuAction getToolBarActionListener() {
        return mToolBarActionListener;
    }

    private enum ActionDrawableState {
        BURGER, ARROW
    }

    private static void toggleActionBarIcon(ActionDrawableState state, final ActionBarDrawerToggle toggle, boolean animate) {
        if (animate) {
            float start = state == ActionDrawableState.BURGER ? 0f : 1.0f;
            float end = Math.abs(start - 1);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                ValueAnimator offsetAnimator = ValueAnimator.ofFloat(start, end);
                offsetAnimator.setDuration(300);
                offsetAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                offsetAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float offset = (Float) animation.getAnimatedValue();
                        toggle.onDrawerSlide(null, offset);
                    }
                });
                offsetAnimator.start();
            } else {
                //do the same with nine-old-androids lib :)
            }
        } else {
            if (state == ActionDrawableState.BURGER) {
                toggle.onDrawerClosed(null);
            } else {
                toggle.onDrawerOpened(null);
            }
        }
    }
}
