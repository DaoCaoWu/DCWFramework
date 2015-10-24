package com.dcw.app.rating.biz.select;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dcw.app.rating.R;
import com.dcw.app.rating.biz.contact.model.ContactModel;
import com.dcw.app.rating.biz.contact.view.LetterIndexView;
import com.dcw.app.rating.biz.contact.view.StickyListView;
import com.dcw.app.rating.ui.mvc.core.Observable;
import com.dcw.framework.view.DCWAnnotation;
import com.dcw.framework.view.annotation.InjectView;

/**
 * <p>Title: ucweb</p>
 * <p/>
 * <p>Description: </p>
 * ......
 * <p>Copyright: Copyright (c) 2015</p>
 * <p/>
 * <p>Company: ucweb.com</p>
 *
 * @author JiaYing.Cheng
 * @version 1.0
 * @email adao12.vip@gmail.com
 * @create 15/10/24
 */
public class ContactRecyclerView extends LinearLayout implements com.dcw.app.rating.ui.mvc.View<StickyListView.ViewListener> {

    @InjectView(R.id.lv_list)
    RecyclerView mRecyclerView;
    @InjectView(R.id.catalog_bar)
    View mLLCatalog;
    @InjectView(R.id.tv_content)
    TextView mCatalogView;
    @InjectView(R.id.container)
    private LetterIndexView mIndexView;
    RecyclerViewPositionHelper mPositionHelper;
    CatalogHelper mCatalogHelper;
    StickyListView.ViewListener mListener;

    public ContactRecyclerView(Context context) {
        super(context);
    }

    public ContactRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setViewListener(StickyListView.ViewListener listener) {
        mListener = listener;
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

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public LetterIndexView getIndexView() {
        return mIndexView;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        DCWAnnotation.inject(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setOnScrollListener(mOnScrollListener);
        mCatalogHelper = new CatalogHelper(mLLCatalog, R.id.catalog_bar);
    }

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            mPositionHelper = RecyclerViewPositionHelper.createHelper(recyclerView);
            mCatalogHelper.onScroll(recyclerView);
            if (mListener != null) {
                mListener.onCatalogViewShouldUpdate(mCatalogView, mPositionHelper.findFirstVisibleItemPosition());
            }
//            int firstVisibleItem = mPositionHelper.findFirstVisibleItemPosition();
//            View firstVisibleItemView = recyclerView.getChildAt(0);
//            if (firstVisibleItemView != null) {
//                int catalogHeight = mLLCatalog.getHeight();
//                int bottom = firstVisibleItemView.getBottom();
//                int top = firstVisibleItemView.getTop();
//                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mLLCatalog.getLayoutParams();
//                if (bottom < catalogHeight) {
//                    RelativeLayout secondVisibleItemViewWrap = (RelativeLayout) recyclerView.getChildAt(1);
//                    if (secondVisibleItemViewWrap != null) {
//                        ViewGroup secondVisibleItemView = (ViewGroup)((ViewGroup)secondVisibleItemViewWrap.getChildAt(0)).getChildAt(0);
//                        if (secondVisibleItemView != null) {
//                            if (secondVisibleItemView.getChildCount() > 0) {
//                                if (secondVisibleItemView.getChildAt(0).getVisibility() != View.VISIBLE) {
//                                    return;
//                                }
//                            }
//                        }
//                    }
//                    float pushedDistance = bottom - catalogHeight;
//                    params.topMargin = (int) pushedDistance;
//                    mLLCatalog.setLayoutParams(params);
//                    mLLCatalog.setVisibility(View.VISIBLE);
//                } else {
//                    if (params.topMargin != 0) {
//                        params.topMargin = 0;
//                        mLLCatalog.setLayoutParams(params);
//                    }
//                    if (firstVisibleItem == 0) {
//                        if (top < 0) {
//                            mLLCatalog.setVisibility(View.VISIBLE);
//                        } else {
//                            mLLCatalog.setVisibility(View.GONE);
//                            return;
//                        }
//                    }
//                }
//                if (recyclerView.findViewHolderForAdapterPosition(firstVisibleItem) != null) {
//                    mLLCatalog.setVisibility(View.VISIBLE);
//                    if (mListener != null) {
//                        mListener.onCatalogViewShouldUpdate(mCatalogView, firstVisibleItem);
//                    }
//                } else {
//                    mLLCatalog.setVisibility(View.GONE);
//                }
//            } else {
//                mLLCatalog.setVisibility(View.VISIBLE);
//            }
        }
    };
}
