package com.dcw.app.ui.loadmore;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.dcw.app.R;

public abstract class LoadMoreContainerBase<V extends ViewGroup> extends LinearLayout implements LoadMoreContainer {

    private int mLayoutId;
    private V mTargetView;
    private LoadMoreUIHandler mLoadMoreUIHandler;
    private LoadMoreHandler mLoadMoreHandler;
    private boolean mIsLoading;
    private boolean mHasMore = false;
    private boolean mAutoLoadMore = true;
    private boolean mLoadError = false;
    private boolean mListEmpty = true;
    private boolean mShowLoadingForFirstPage = false;
    private View mFooterView;


    public LoadMoreContainerBase(Context context) {
        super(context);
    }

    public LoadMoreContainerBase(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (attrs != null) {
            parseAttrs(context, attrs);
        }
    }

    /**
     * Parses the incoming attributes from XML inflation
     */
    private void parseAttrs(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.LoadMoreContainerBase, 0, 0);
        try {
            mLayoutId = a.getResourceId(R.styleable.LoadMoreContainerBase_targetId, R.id.listView);
        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTargetView = (V)findViewById(mLayoutId);
        init();
        postDelayed(new Runnable() {
            @Override
            public void run() {
                useDefaultFooter();
            }
        }, 0);
    }

    //    public LoadMoreContainerBase(V targetView) {
//        mTargetView = targetView;
//        init();
//    }

    public V getTargetView() {
        return mTargetView;
    }

    public void useDefaultFooter() {
        LoadMoreDefaultFooterView footerView = new LoadMoreDefaultFooterView(getTargetView().getContext());
        footerView.setVisibility(View.VISIBLE);
        setLoadMoreView(footerView);
        setLoadMoreUIHandler(footerView);
    }

    private void init() {
        if (mFooterView != null) {
            addFooterView(mFooterView);
        }
        setupReachBottomRule();

    }

    private void tryToPerformLoadMore() {
        if (mIsLoading) {
            return;
        }
        // no more content and also not load for first page
        if (!mHasMore && !(mListEmpty && mShowLoadingForFirstPage)) {
            return;
        }
        mIsLoading = true;
        if (mLoadMoreUIHandler != null) {
            mLoadMoreUIHandler.onLoading(this);
        }
        if (null != mLoadMoreHandler) {
            mLoadMoreHandler.onLoadMore(this);
        }
    }

    protected void onReachBottom() {
        // if has error, just leave what it should be
        if (mLoadError) {
            return;
        }
        if (mAutoLoadMore) {
            tryToPerformLoadMore();
        } else {
            if (mHasMore) {
                mLoadMoreUIHandler.onWaitToLoadMore(this);
            }
        }
    }

    @Override
    public void setShowLoadingForFirstPage(boolean showLoading) {
        mShowLoadingForFirstPage = showLoading;
    }

    @Override
    public void setAutoLoadMore(boolean autoLoadMore) {
        mAutoLoadMore = autoLoadMore;
    }

    @Override
    public void setLoadMoreView(View view) {
        // has not been initialized
        if (mTargetView == null) {
            mFooterView = view;
            return;
        }
        // remove previous
        if (mFooterView != null && mFooterView != view) {
            removeFooterView(view);
        }
        // add current
        mFooterView = view;
        mFooterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tryToPerformLoadMore();
            }
        });
        addFooterView(view);
    }

    @Override
    public void setLoadMoreUIHandler(LoadMoreUIHandler handler) {
        mLoadMoreUIHandler = handler;
    }

    @Override
    public void setLoadMoreHandler(LoadMoreHandler handler) {
        mLoadMoreHandler = handler;
    }

    /**
     * page has loaded
     */
    @Override
    public void loadMoreFinish(boolean emptyResult, boolean hasMore) {
        mLoadError = false;
        mListEmpty = emptyResult;
        mIsLoading = false;
        mHasMore = hasMore;

        if (mLoadMoreUIHandler != null) {
            mLoadMoreUIHandler.onLoadFinish(this, emptyResult, hasMore);
        }
    }

    @Override
    public void loadMoreError(int errorCode, String errorMessage) {
        mIsLoading = false;
        mLoadError = true;
        if (mLoadMoreUIHandler != null) {
            mLoadMoreUIHandler.onLoadError(this, errorCode, errorMessage);
        }
    }

    protected abstract void addFooterView(View view);

    protected abstract void removeFooterView(View view);
}