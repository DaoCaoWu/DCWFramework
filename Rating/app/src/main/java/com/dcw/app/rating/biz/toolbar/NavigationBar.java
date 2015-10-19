package com.dcw.app.rating.biz.toolbar;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;

import com.dcw.app.rating.ui.mvc.View;
import com.dcw.app.rating.ui.mvc.core.Observable;

public class NavigationBar extends Toolbar implements View<NavigationBar.IBackAction> {

    public IBackAction mListener;

    public NavigationBar(Context context) {
        super(context, null);
    }

    public NavigationBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NavigationBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
        }
    }

    public static interface IBackAction {

        void onNavigationOnClicked(android.view.View v);
    }
}
