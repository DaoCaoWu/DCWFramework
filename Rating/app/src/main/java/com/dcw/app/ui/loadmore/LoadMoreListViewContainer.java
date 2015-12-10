package com.dcw.app.ui.loadmore;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

public class LoadMoreListViewContainer extends LoadMoreContainerBase<ListView> {

    private AbsListView.OnScrollListener mOnScrollListener;

    public LoadMoreListViewContainer(Context context) {
        super(context);
    }

    public LoadMoreListViewContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOnScrollListener(AbsListView.OnScrollListener onScrollListener) {
        mOnScrollListener = onScrollListener;
    }

    @Override
    public void setupReachBottomRule() {
        getTargetView().setOnScrollListener(new AbsListView.OnScrollListener() {

            private boolean mIsEnd = false;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (null != mOnScrollListener) {
                    mOnScrollListener.onScrollStateChanged(view, scrollState);
                }
                if (scrollState == SCROLL_STATE_IDLE) {
                    if (mIsEnd) {
                        onReachBottom();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (null != mOnScrollListener) {
                    mOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
                }
                if (firstVisibleItem + visibleItemCount >= totalItemCount - 1) {
                    mIsEnd = true;
                } else {
                    mIsEnd = false;
                }
            }
        });
    }

    @Override
    protected void addFooterView(View view) {
        getTargetView().addFooterView(view);
    }

    @Override
    protected void removeFooterView(View view) {
        getTargetView().removeFooterView(view);
    }

}
