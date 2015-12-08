package com.dcw.app.ui.loadmore;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * create by adao12.vip@gmail.com on 15/12/8
 *
 * @author JiaYing.Cheng
 * @version 1.0
 */
public class LoadMoreRecyclerViewContainer extends LoadMoreContainerBase<RecyclerView> {

    RecyclerView.OnScrollListener mOnScrollListener;

    public void setOnScrollListener(RecyclerView.OnScrollListener onScrollListener) {
        mOnScrollListener = onScrollListener;
    }

    public LoadMoreRecyclerViewContainer(RecyclerView targetView) {
        super(targetView);
    }

    @Override
    public void setupReachBottomRule() {

    }

    @Override
    protected void addFooterView(View view) {
    }

    @Override
    protected void removeFooterView(View view) {

    }
}
