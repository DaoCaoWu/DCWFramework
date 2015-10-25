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

public class ContactRecyclerView extends LinearLayout implements com.dcw.app.rating.ui.mvc.View<StickyListView.ViewListener> {

    @InjectView(R.id.lv_list)
    RecyclerView mRecyclerView;
    @InjectView(R.id.catalog_bar)
    View mLLCatalog;
    @InjectView(R.id.tv_content)
    TextView mCatalogView;
    RecyclerViewPositionHelper mPositionHelper;
    CatalogHelper mCatalogHelper;
    StickyListView.ViewListener mListener;
    @InjectView(R.id.container)
    private LetterIndexView mIndexView;
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
        }
    };

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
}
