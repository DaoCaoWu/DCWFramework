package com.dcw.app.biz.contact.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.dcw.app.R;
import com.dcw.app.biz.contact.model.ContactModel;
import com.dcw.app.biz.select.CatalogHelper;
import cn.ninegame.library.component.mvc.BaseView;
import cn.ninegame.library.component.mvc.core.Observable;
import com.dcw.framework.view.DCWAnnotation;
import com.dcw.framework.view.annotation.InjectView;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/10/21.
 */
public class StickyListView extends LinearLayout implements BaseView<StickyListView.ViewListener>, AbsListView.OnScrollListener {

    @InjectView(R.id.lv_list)
    ListView mListView;
    @InjectView(R.id.catalog_bar)
    View mLLCatalog;
    @InjectView(R.id.tv_content)
    TextView mCatalogView;
    CatalogHelper mCatalogHelper;
    @InjectView(R.id.container)
    private LetterIndexView mIndexView;
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
        if (observable instanceof ContactModel) {
            ContactModel model = (ContactModel) observable;
            if (model.getCount() == 0) {
                mLLCatalog.setVisibility(GONE);
            }

        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        DCWAnnotation.inject(this);
        mCatalogHelper = new CatalogHelper(mLLCatalog, R.id.catalog_bar);
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
        mCatalogHelper.onScroll(view);
        if (mCatalogHelper.isExist(view.getChildAt(0)) && mListener != null) {
            mListener.onCatalogViewShouldUpdate(mCatalogView, firstVisibleItem);
        }
//        View firstVisibleItemView = view.getChildAt(0);
//        if (firstVisibleItemView != null) {
//            int mLLCatalogHeight = mLLCatalog.getHeight();
//            int bottom = firstVisibleItemView.getBottom();
//            int top = firstVisibleItemView.getTop();
//            ViewGroup.MarginLayoutParams mLLCatalogLayoutParams = (ViewGroup.MarginLayoutParams) mLLCatalog.getLayoutParams();
//            if (bottom < mLLCatalogHeight) {
//                LinearLayout secondVisibleItemView = (LinearLayout) view.getChildAt(1);
//                if (secondVisibleItemView != null) {
//                    if (secondVisibleItemView.getChildCount() > 0) {
//                        if (!secondVisibleItemView.getChildAt(0).isShown()) {
//                            return;
//                        }
//                    }
//                }
//                float pushedDistance = bottom - mLLCatalogHeight;
//                mLLCatalogLayoutParams.topMargin = (int) pushedDistance;
//                mLLCatalog.setLayoutParams(mLLCatalogLayoutParams);
//                mLLCatalog.setVisibility(View.VISIBLE);
//            } else {
//                if (mLLCatalogLayoutParams.topMargin != 0) {
//                    mLLCatalogLayoutParams.topMargin = 0;
//                    mLLCatalog.setLayoutParams(mLLCatalogLayoutParams);
//                }
//                if (firstVisibleItem == 0) {
//                    if (top < 0) {
//                        mLLCatalog.setVisibility(View.VISIBLE);
//                    } else {
//                        mLLCatalog.setVisibility(View.GONE);
//                        return;
//                    }
//                }
//            }
//            if (view.getItemAtPosition(firstVisibleItem) != null) {
//                mLLCatalog.setVisibility(View.VISIBLE);
//                if (mListener != null) {
//                    mListener.onCatalogViewShouldUpdate(mCatalogView, firstVisibleItem);
//                }
//            } else {
//                mLLCatalog.setVisibility(View.GONE);
//            }
//        } else {
//            mLLCatalog.setVisibility(View.VISIBLE);
//        }
//        if (mOnScrollListener != null) {
//            mOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
//        }
    }

    public interface ViewListener {

        void onCatalogViewShouldUpdate(TextView catalogView, int firstVisibleItem);
    }
}
