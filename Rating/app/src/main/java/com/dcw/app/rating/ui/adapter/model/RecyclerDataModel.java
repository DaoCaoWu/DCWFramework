package com.dcw.app.rating.ui.adapter.model;

import android.util.SparseArray;
import android.util.SparseIntArray;

import com.dcw.app.rating.ui.adapter.viewholder.FixViewHolderBean;

import java.util.List;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/11/5.
 */
public class RecyclerDataModel<D> extends ListDataModel<D> {

    public static final int ITEM_VIEW_TYPE_HEADER = Integer.MIN_VALUE;
    public static final int ITEM_VIEW_TYPE_FOOTER = Integer.MAX_VALUE / 2;

    private SparseArray<FixViewHolderBean> mHeaderBeans;
    private SparseArray<FixViewHolderBean> mFooterBeans;
    private SparseIntArray mViewTypePositionMap;

    public RecyclerDataModel() {
        mHeaderBeans = new SparseArray<FixViewHolderBean>();
        mFooterBeans = new SparseArray<FixViewHolderBean>();
        mViewTypePositionMap = new SparseIntArray();
    }

    public RecyclerDataModel(List<D> dataList) {
        this();
        setDataList(dataList);
    }

    public boolean isHeader(int position) {
        return mHeaderBeans != null && position < mHeaderBeans.size();
    }

    public boolean isHeaderViewType(int viewType) {
        return viewType < 0 && getHeaderOrFooterPosition(viewType) != -1;
    }

    public boolean isFooter(int position) {
        return mFooterBeans != null && (position >= (mHeaderBeans.size() + getCount()) && position < (mHeaderBeans.size() + getCount() + mFooterBeans.size()));
    }

    public boolean isFooterViewType(int viewType) {
        return viewType >= ITEM_VIEW_TYPE_FOOTER && getHeaderOrFooterPosition(viewType) != -1;
    }

    public void addHeaderViewHolderBean(int position, FixViewHolderBean bean) {
        mHeaderBeans.append(position, bean);
        mViewTypePositionMap.append(ITEM_VIEW_TYPE_HEADER + position, position);
    }

    public FixViewHolderBean getHeaderViewHolderBean(int position) {
        return mHeaderBeans.get(position);
    }

    public void addFooterViewHolderBean(int position, FixViewHolderBean bean) {
        mFooterBeans.append(position, bean);
        mViewTypePositionMap.append(ITEM_VIEW_TYPE_FOOTER + position, position);
    }

    public FixViewHolderBean getFooterViewHolderBean(int position) {
        return mFooterBeans.get(position);
    }

    public int getHeaderViewCount() {
        return mHeaderBeans == null ? 0 : mHeaderBeans.size();
    }

    public int getFooterViewCount() {
        return mFooterBeans == null ? 0 : mFooterBeans.size();
    }

    public int getHeaderOrFooterPosition(int viewType) {
        return mViewTypePositionMap.get(viewType, -1);
    }

    @SuppressWarnings("unchecked")
    public <T> T getHeaderOrFooterItem(int position) {
        if (isHeader(position)) {
            return (T) mHeaderBeans.get(position).getData();
        } else if (isFooter(position)) {
            return (T) mFooterBeans.get(position - getCount() - getHeaderViewCount()).getData();
        }
        return null;
    }

    public void setHeaderData(int viewType, Object data) {
        int headerViewType = ITEM_VIEW_TYPE_HEADER + viewType;
        if (isHeaderViewType(headerViewType)) {
            FixViewHolderBean bean = mHeaderBeans.get(getHeaderOrFooterPosition(headerViewType));
            if (bean != null) {
                bean.setData(data);
                notifyObservers();
            }
        }
    }

    public void setFooterData(int viewType, Object data) {
        int footerViewType = ITEM_VIEW_TYPE_FOOTER + viewType;
        if (isFooterViewType(footerViewType)) {
            FixViewHolderBean bean = mFooterBeans.get(getHeaderOrFooterPosition(footerViewType));
            if (bean != null) {
                bean.setData(data);
                notifyObservers();
            }
        }
    }

    @Override
    public long getItemId(int position) {
        int numHeaders = getHeaderViewCount();
        if (getCount() != 0 && position >= numHeaders) {
            int adjPosition = position - numHeaders;
            int adapterCount = getCount();
            if (adjPosition < adapterCount) {
                return super.getItemId(adjPosition);
            }
        }
        return -1;
    }

    @Override
    public int getViewTypeCount() {
        if (getCount() > 0) {
            return super.getViewTypeCount() + mViewTypePositionMap.size();
        }
        return 1;
    }
}
