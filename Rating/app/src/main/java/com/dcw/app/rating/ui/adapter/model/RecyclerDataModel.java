package com.dcw.app.rating.ui.adapter.model;

import android.util.SparseArray;

import com.dcw.app.rating.ui.adapter.viewholder.FixViewHolderBean;
import com.dcw.app.rating.ui.adapter.viewholder.ItemViewHolderBean;

import java.util.List;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/11/5.
 */
public class RecyclerDataModel<D> extends ListDataModel<D> {

    public static final int ITEM_VIEW_TYPE_HEADER = Integer.MIN_VALUE;
    public static final int ITEM_VIEW_TYPE_FOOTER = 100000;

    private SparseArray<FixViewHolderBean<? extends ListDataModel<D>, D>> mHeaderBeans;
    private SparseArray<FixViewHolderBean<? extends ListDataModel<D>, D>> mFooterBeans;

    public boolean isHeader(int position) {
        return mHeaderBeans != null && position < mHeaderBeans.size();
    }

    public boolean isFooter(int position) {
        return mFooterBeans != null && (position >= (mHeaderBeans.size() + getCount()) && position < (mHeaderBeans.size() + getCount() + mFooterBeans.size()));
    }

    public void addHeaderViewHolderBean(int position, FixViewHolderBean<? extends ListDataModel<D>, D> bean) {
        if (mHeaderBeans == null) {
            mHeaderBeans = new SparseArray<FixViewHolderBean<? extends ListDataModel<D>, D>>();
        }
        mHeaderBeans.append(position, bean);
        addItemViewHolderBean(ITEM_VIEW_TYPE_HEADER + position, bean);
    }

    public <M extends ListDataModel<D>> FixViewHolderBean<M, D> getHeaderViewHolderBean(int position) {
        return (FixViewHolderBean<M, D>) mHeaderBeans.get(position);
    }

    public void addFooterViewHolderBean(int position, FixViewHolderBean<? extends ListDataModel<D>, D> bean) {
        if (mFooterBeans == null) {
            mFooterBeans = new SparseArray<FixViewHolderBean<? extends ListDataModel<D>, D>>();
        }
        mFooterBeans.append(position, bean);
        addItemViewHolderBean(ITEM_VIEW_TYPE_FOOTER + position, bean);
    }

    public <M extends ListDataModel<D>> FixViewHolderBean<M, D> getFooterViewHolderBean(int position) {
        return (FixViewHolderBean<M, D>) mFooterBeans.get(position);
    }

    public int getHeaderViewCount() {
        return mHeaderBeans == null ? 0 : mHeaderBeans.size();
    }

    public int getFooterViewCount() {
        return mFooterBeans == null ? 0 : mFooterBeans.size();
    }

    public Object getHeaderOrFooterItem(int position) {
        if (isHeader(position)) {
            if (mHeaderBeans != null) {
                return mHeaderBeans.get(position).getData();
            }
        } else if (isFooter(position)) {
            if (mFooterBeans != null) {
                return mFooterBeans.get(position).getData();
            }
        }
        return null;
    }

    @Override
    public D getItem(int position) {
        return super.getItem(position - getHeaderViewCount());
    }
}
