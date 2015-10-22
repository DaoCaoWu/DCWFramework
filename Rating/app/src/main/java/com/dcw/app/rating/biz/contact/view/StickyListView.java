package com.dcw.app.rating.biz.contact.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.dcw.app.rating.R;
import com.dcw.app.rating.ui.mvc.core.Observable;
import com.dcw.framework.view.DCWAnnotation;
import com.dcw.framework.view.annotation.InjectView;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/10/21.
 */
public class StickyListView extends LinearLayout implements com.dcw.app.rating.ui.mvc.View<StickyListView.ViewListener>, AbsListView.OnScrollListener {

    @InjectView(R.id.lv_list)
    ListView mListView;
    @InjectView(R.id.container)
    private LetterIndexView mIndexView;
    @InjectView(R.id.catalog_bar)
    View mLLCatalog;
    @InjectView(R.id.tv_content)
    TextView mCatalogView;
    private ViewListener mListener;
    private AbsListView.OnScrollListener mOnScrollListener;

    public StickyListView(Context context) {
        super(context);
    }

    public StickyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LetterIndexView getIndexView() {
        return mIndexView;
    }

    public ListView getListView() {
        return mListView;
    }

    @Override
    public void setViewListener(ViewListener listener) {
        mListener = listener;
    }

    public void setOnScrollListener(AbsListView.OnScrollListener onScrollListener) {
        mOnScrollListener = onScrollListener;
    }

    @Override
    public <T> void update(Observable observable, T data, Object... args) {
//        ((ContactAdapter)getListView().getAdapter()).notifyDataSetChanged();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        DCWAnnotation.inject(this);
        mIndexView.setListView(mListView);
        getListView().setOnScrollListener(this);
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {
        if (mOnScrollListener != null) {
            mOnScrollListener.onScrollStateChanged(absListView, i);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        View childView = view.getChildAt(0);
        if (childView != null) {
            int titleHeight = mLLCatalog.getHeight();
            int bottom = childView.getBottom();
            int top = childView.getTop();
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mLLCatalog.getLayoutParams();
            if (bottom < titleHeight) {
                LinearLayout subChildView = (LinearLayout) view.getChildAt(1);
                if (subChildView != null) {
                    if (subChildView.getChildCount() > 0) {
                        if (subChildView.getChildAt(0).getVisibility() != View.VISIBLE) {
                            return;
                        }
                    }
                }
                float pushedDistance = bottom - titleHeight;
                params.topMargin = (int) pushedDistance;
                mLLCatalog.setLayoutParams(params);
                mLLCatalog.setVisibility(View.VISIBLE);
            } else {
                if (params.topMargin != 0) {
                    params.topMargin = 0;
                    mLLCatalog.setLayoutParams(params);
                }
                if (firstVisibleItem == 0) {
                    if (top < 0) {
                        mLLCatalog.setVisibility(View.VISIBLE);
                    } else {
                        mLLCatalog.setVisibility(View.GONE);
                        return;
                    }
                }
            }
            if (view.getItemAtPosition(firstVisibleItem) != null) {
                mLLCatalog.setVisibility(View.VISIBLE);
                if (mListener != null) {
                    mListener.onCatalogViewShouldUpdate(mCatalogView, firstVisibleItem);
                }
            } else {
                mLLCatalog.setVisibility(View.GONE);
            }
        } else {
            mLLCatalog.setVisibility(View.VISIBLE);
        }
        if (mOnScrollListener != null) {
            mOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }
    }

    public interface ViewListener {

        void onCatalogViewShouldUpdate(TextView catalogView, int firstVisibleItem);
    }
}
