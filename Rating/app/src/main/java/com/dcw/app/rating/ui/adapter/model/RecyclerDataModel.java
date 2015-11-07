package com.dcw.app.rating.ui.adapter.model;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.widget.AdapterView;

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
    private SparseIntArray mViewTypePositionMap;

    public RecyclerDataModel() {
        mHeaderBeans = new SparseArray<FixViewHolderBean<? extends ListDataModel<D>, D>>();
        mFooterBeans = new SparseArray<FixViewHolderBean<? extends ListDataModel<D>, D>>();
        mViewTypePositionMap = new SparseIntArray();
    }

    public RecyclerDataModel(List<D> dataList) {
        this();
        setDataList(dataList);
    }

    public boolean isHeader(int position) {
        return mHeaderBeans != null && position < mHeaderBeans.size();
    }

    public boolean isFooter(int position) {
        return mFooterBeans != null && (position >= (mHeaderBeans.size() + getCount()) && position < (mHeaderBeans.size() + getCount() + mFooterBeans.size()));
    }

    public void addHeaderViewHolderBean(int position, FixViewHolderBean<? extends ListDataModel<D>, D> bean) {
        mHeaderBeans.append(position, bean);
        mViewTypePositionMap.append(ITEM_VIEW_TYPE_HEADER + position, position);
    }

    public <M extends ListDataModel<D>> FixViewHolderBean<M, D> getHeaderViewHolderBean(int position) {
        return (FixViewHolderBean<M, D>) mHeaderBeans.get(position);
    }

    public void addFooterViewHolderBean(int position, FixViewHolderBean<? extends ListDataModel<D>, D> bean) {
        mFooterBeans.append(position, bean);
        mViewTypePositionMap.append(ITEM_VIEW_TYPE_FOOTER + position, position);
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

    public int getHeaderOrFooterPosition(int viewType) {
        return mViewTypePositionMap.get(viewType, -1);
    }

    public Object getItemData(int position) {
        // Header (negative positions will throw an IndexOutOfBoundsException)
        int numHeaders = getHeaderViewCount();
        if (position < numHeaders) {
            if (mHeaderBeans != null) {
                return mHeaderBeans.get(position).getData();
            }
        }

//        // Adapter
//        final int adjPosition = position - numHeaders;
//        int adapterCount = 0;
//        if (getCount() != 0) {
//            adapterCount = getCount();
//            if (adjPosition < adapterCount) {
//                return super.getItem(adjPosition);
//            }
//        }
        if (mFooterBeans != null) {
            return mFooterBeans.get(position - getCount() - getHeaderViewCount()).getData();
        }
        // Footer (off-limits positions will throw an IndexOutOfBoundsException)
        return null;
    }

    @Override
    public D getItem(int position) {
        return super.getItem(position - getHeaderViewCount());
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

//    @Override
//    public int getItemViewType(int position) {
//        int numHeaders = getHeaderViewCount();
//        if (getCount() != 0 && position >= numHeaders) {
//            int adjPosition = position - numHeaders;
//            int adapterCount = getCount();
//            if (adjPosition < adapterCount) {
//                return super.getItemViewType(adjPosition);
//            }
//        }
//        if (position < numHeaders) {
//            return ITEM_VIEW_TYPE_HEADER + position;
//        }
//        return ITEM_VIEW_TYPE_FOOTER + position - getCount() - mHeaderBeans.size();
//    }

    @Override
    public int getViewTypeCount() {
        if (getCount() > 0) {
            return super.getViewTypeCount() + 1;
        }
        return 1;
    }
}
