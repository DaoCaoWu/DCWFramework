package com.dcw.app.rating.biz.toolbar;

import android.support.annotation.IdRes;
import android.support.annotation.MenuRes;

import com.dcw.app.rating.R;
import com.dcw.app.rating.app.RatingApplication;
import com.dcw.app.rating.ui.mvc.core.Observable;

public class ToolbarModel extends Observable {

    private String mTitle;

    private int mMenuResId;

    private boolean mShowHomeAsUp;

    public ToolbarModel(String title) {
        this(title, 0, true);
    }

    public ToolbarModel(String title, boolean showHomeAsUp) {
        this(title, 0, showHomeAsUp);
    }

    public ToolbarModel(String title, @MenuRes int menuResId) {
        this(title, menuResId, true);
    }

    public ToolbarModel(String title, int menuResId, boolean showHomeAsUp) {
        mTitle = title;
        mMenuResId = menuResId;
        mShowHomeAsUp = showHomeAsUp;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
        setChanged();
        notifyObservers(this);
    }

    public int getMenuResId() {
        return mMenuResId;
    }

    public void setMenuResId(int menuResId) {
        mMenuResId = menuResId;
    }

    public boolean isShowHomeAsUp() {
        return mShowHomeAsUp;
    }

    public void setShowHomeAsUp(boolean showHomeAsUp) {
        mShowHomeAsUp = showHomeAsUp;
    }

    @Override
    public void notifyObservers() {
        setChanged();
        super.notifyObservers(this);
    }

    @Override
    public <T> void notifyObservers(T data, Object... args) {
        setChanged();
        super.notifyObservers(data, args);
    }
}
