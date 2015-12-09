package com.dcw.app.ui.loadmore;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.View;

import cn.ninegame.library.component.adapter.RecyclerViewAdapter;

/**
 * create by adao12.vip@gmail.com on 15/12/8
 *
 * @author JiaYing.Cheng
 * @version 1.0
 */
public class LoadMoreRecyclerViewContainer extends LoadMoreContainerBase<RecyclerView> {

    RecyclerView.OnScrollListener mOnScrollListener;

    public LoadMoreRecyclerViewContainer(Context context) {
        super(context);
    }

    public LoadMoreRecyclerViewContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOnScrollListener(RecyclerView.OnScrollListener onScrollListener) {
        mOnScrollListener = onScrollListener;
    }

//    public LoadMoreRecyclerViewContainer(RecyclerView targetView) {
//        super(targetView);
//    }

    @Override
    public void setupReachBottomRule() {
        getTargetView().addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                onReachBottom();
            }
        });
    }

    @Override
    protected void addFooterView(View view) {
        if (getTargetView().getAdapter() instanceof RecyclerViewAdapter) {
            RecyclerViewAdapter adapter = (RecyclerViewAdapter) getTargetView().getAdapter();
            adapter.addFooterView(view);
        }
    }

    @Override
    protected void removeFooterView(View view) {
        if (getTargetView().getAdapter() instanceof RecyclerViewAdapter) {
            RecyclerViewAdapter adapter = (RecyclerViewAdapter) getTargetView().getAdapter();
            adapter.removeFooterView(view);
        }
    }

    public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {

        int mPreviousTotal = 0; // The total number of items in the dataset after the last load
        boolean mIsLoadingMore = true; // True if we are still waiting for the last set of data to load.
        static final int VISIBLE_THRESHOLD = 1; // The minimum amount of items to have below your current scroll position before loading more.
        int mFirstVisibleItem, mVisibleItemCount, mTotalItemCount, mLastVisibleItemPosition;
        int[] mLastPositions;
        int layoutManagerType;
        private boolean mIsEnd = false;

        public EndlessRecyclerOnScrollListener() {
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE || newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                if (mIsEnd) {
                    // End has been reached
                    onLoadMore();
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);


            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

            if (layoutManagerType == 0) {
                if (layoutManager instanceof GridLayoutManager) {
                    layoutManagerType = 1;
                } else if (layoutManager instanceof LinearLayoutManager) {
                    layoutManagerType = 2;
                } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                    layoutManagerType = 3;
                } else {
                    throw new RuntimeException("Unsupported LayoutManager used. Valid ones are LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager");
                }
            }

            switch (layoutManagerType) {
                case 2:
                    mVisibleItemCount = layoutManager.getChildCount();
                    mTotalItemCount = layoutManager.getItemCount();
                case 1:
                    mLastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                    mFirstVisibleItem = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
                    break;
                case 3:
                    StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                    if (mLastPositions == null)
                        mLastPositions = new int[staggeredGridLayoutManager.getSpanCount()];

                    staggeredGridLayoutManager.findLastVisibleItemPositions(mLastPositions);
                    mLastVisibleItemPosition = findMax(mLastPositions);

                    staggeredGridLayoutManager.findFirstVisibleItemPositions(mLastPositions);
                    mFirstVisibleItem = findMin(mLastPositions);
                    break;
            }

//            if (mIsLoadingMore) {
//                if (mTotalItemCount > mPreviousTotal) {
//                    mIsLoadingMore = false;
//                }
//                mPreviousTotal = mTotalItemCount;
//            }

            if (mFirstVisibleItem + mVisibleItemCount >= mTotalItemCount - 1) {

                mIsLoadingMore = true;
                mIsEnd = true;
//                mPreviousTotal = mTotalItemCount;
            } else {
                mIsEnd = false;
            }
        }


        public abstract void onLoadMore();

        private int findMax(int[] lastPositions) {
            int max = Integer.MIN_VALUE;
            for (int value : lastPositions) {
                if (value > max)
                    max = value;
            }
            return max;
        }

        private int findMin(int[] lastPositions) {
            int min = Integer.MAX_VALUE;
            for (int value : lastPositions) {
                if (value != RecyclerView.NO_POSITION && value < min)
                    min = value;
            }
            return min;
        }
    }
}
