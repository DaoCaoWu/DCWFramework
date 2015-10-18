package com.dcw.app.rating.biz.toolbar;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.MenuItem;

import com.dcw.app.rating.biz.account.ToolbarModel;
import com.dcw.app.rating.ui.mvc.View;
import com.dcw.app.rating.ui.mvc.core.Observable;

public class Navigation extends Toolbar implements View<IBackAction> {

    public IBackAction mListener;

    public Navigation(Context context) {
        super(context, null);
    }

    public Navigation(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Navigation(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    public void setViewListener(IBackAction listener) {
        mListener = listener;
    }

    @Override
    public <T> void update(Observable observable, T data, Object... args) {
        if (data instanceof ToolbarModel) {
            ToolbarModel model = (ToolbarModel) data;
            setTitle(model.getTitle());
            updateBtnRight(model);
            addOrRemoveMenuItem(args);
        }
    }

    private MenuItem findMenuItem(String title) {
        int size = getMenu().size();
        for (int i = 0; i < size; i++) {
            MenuItem item = getMenu().getItem(i);
            if (item != null && item.getTitle().equals(title)) {
                return item;
            }
        }
        return null;
    }

    private MenuItem findMenuItem(int itemId) {
        int size = getMenu().size();
        for (int i = 0; i < size; i++) {
            MenuItem item = getMenu().getItem(i);
            if (item != null && item.getItemId() == itemId) {
                return item;
            }
        }
        return null;
    }

    private void updateBtnRight(ToolbarModel model) {
        ToolbarModel.MenuItem menuItem = model.getBtnRight();
        boolean isExist = false;
        int size = getMenu().size();
        MenuItem item = null;
        for (int i = 0; i < size; i++) {
            item = getMenu().getItem(i);
            if (item != null && item.getItemId() == menuItem.getItemId()) {
                isExist = true;
                break;
            }
        }
        if (menuItem.isShow()) {

            if (isExist) {
                if (item != null) {
                    item.setTitle(menuItem.getTitle());
                    item.setIcon(menuItem.getIcon());
                }
            } else {
                getMenu().add(0, menuItem.getItemId(), 0, menuItem.getTitle());
                MenuItem tempItem = findMenuItem(menuItem.getItemId());
                if (tempItem != null) {
                    tempItem.setIcon(menuItem.getIcon());
                }
            }
        } else {
            if (isExist) {
                getMenu().removeItem(menuItem.getItemId());
            }
        }
    }

    private void addOrRemoveMenuItem(Object... args) {
        if (args != null && args.length == 2 && args[0] != null && args[1] != null) { // add menu item
            if ((boolean) args[0]) {
                if (args[1] instanceof ToolbarModel.MenuItem) {
                    ToolbarModel.MenuItem menuItem = (ToolbarModel.MenuItem) args[1];
                    boolean isExist = false;
                    int size = getMenu().size();
                    for (int i = 0; i < size; i++) {
                        MenuItem item = getMenu().getItem(i);
                        if (item != null && item.getTitle().equals(menuItem.getTitle())) {
                            isExist = true;
                        }
                    }
                    if (!isExist) {
                        getMenu().add(0, menuItem.getItemId(), 0, menuItem.getTitle());
                        MenuItem item = findMenuItem(menuItem.getTitle());
                        if (item != null) {
                            item.setIcon(menuItem.getIcon());
                        }
                    } else {
                        MenuItem item = findMenuItem(menuItem.getTitle());
                        if (item != null) {
                            item.setTitle(menuItem.getTitle());
                            item.setIcon(menuItem.getIcon());
                        }
                    }
                }
            } else {
                getMenu().removeItem((int) args[1]);
            }

        }
    }
}
