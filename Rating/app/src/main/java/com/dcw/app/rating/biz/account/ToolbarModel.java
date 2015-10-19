package com.dcw.app.rating.biz.account;

import android.support.annotation.IdRes;
import android.support.annotation.MenuRes;

import com.dcw.app.rating.R;
import com.dcw.app.rating.app.RatingApplication;
import com.dcw.app.rating.ui.mvc.core.Observable;

public class ToolbarModel extends Observable {

    private String mTitle;

    private int mMenuResId;

    private boolean mShowHomeAsUp;

    private MenuItem mBtnRight;

    public ToolbarModel(String title) {
        this(title, 0, true, false);
    }

    public ToolbarModel(String title, boolean showHomeAsUp) {
        this(title, 0, showHomeAsUp, false);
    }

    public ToolbarModel(String title, @MenuRes int menuResId) {
        this(title, menuResId, true);
    }

    public ToolbarModel(String title, int menuResId, boolean showHomeAsUp) {
        this(title, menuResId, showHomeAsUp, true);
    }

    public ToolbarModel(String title, int menuResId, boolean showHomeAsUp, boolean showBtnRight) {
        mTitle = title;
        mMenuResId = menuResId;
        mShowHomeAsUp = showHomeAsUp;
        mBtnRight = new MenuItem(showBtnRight, R.id.btn_right, RatingApplication.getInstance().getString(R.string.more));
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

    public boolean isShowRightBtn() {
        return mBtnRight.isShow();
    }

    public void setShowRightBtn(boolean showRightBtn) {
        mBtnRight.setIsShow(showRightBtn);
        notifyObservers(this, mBtnRight);
    }

    public MenuItem getBtnRight() {
        return mBtnRight;
    }

    public void setBtnRight(MenuItem btnRight) {
        mBtnRight = btnRight;
        notifyObservers(this, mBtnRight);
    }

    public void addMenuItem(String title) {
        notifyObservers(this, true, new MenuItem(title));
    }

    public void addMenuItem(String title, int icon) {
        MenuItem menuItem = new MenuItem(true, 0, title, icon);
        notifyObservers(this, true, menuItem);
    }

    public void addMenuItem(int itemId, String title, int icon) {
        MenuItem menuItem = new MenuItem(true, itemId, title, icon);
        notifyObservers(this, true, menuItem);
    }

    public void removeMenuItemId(@IdRes int itemId) {
        notifyObservers(this, false, itemId);
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

    public class MenuItem {
        boolean mIsShow;
        boolean mEnable;
        int mItemId;
        String mTitle;
        int mIcon;

        public MenuItem(String title) {
            this(true, 0, title);
        }

//        public MenuItem(String title, int icon) {
//            this(true, title, icon);
//        }
//
//        public MenuItem(boolean isShow, int itemId, String title) {
//            this(isShow, true, itemId, title, 0);
//        }
//
//        public MenuItem(boolean isShow, String title, int icon) {
//            this(isShow, true, 0, title, icon);
//        }

        public MenuItem(boolean isShow, int itemId, String title) {
            this(isShow, itemId, title, 0);
        }

        public MenuItem(boolean isShow, int itemId, String title, int icon) {
            this(isShow, itemId, title, icon, true);
        }

        public MenuItem(boolean isShow, int itemId, String title, int icon, boolean enable) {
            mIsShow = isShow;
            mEnable = enable;
            mItemId = itemId;
            mTitle = title;
            mIcon = icon;
        }

        public boolean isShow() {
            return mIsShow;
        }

        public void setIsShow(boolean isShow) {
            mIsShow = isShow;
        }

        public boolean isEnable() {
            return mEnable;
        }

        public void setEnable(boolean enable) {
            mEnable = enable;
        }

        public int getItemId() {
            return mItemId;
        }

        public void setItemId(int itemId) {
            mItemId = itemId;
        }

        public String getTitle() {
            return mTitle;
        }

        public void setTitle(String title) {
            mTitle = title;
        }

        public int getIcon() {
            return mIcon;
        }

        public void setIcon(int icon) {
            mIcon = icon;
        }
    }
}
