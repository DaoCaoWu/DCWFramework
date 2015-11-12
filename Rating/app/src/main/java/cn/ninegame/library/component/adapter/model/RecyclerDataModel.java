package cn.ninegame.library.component.adapter.model;

import android.util.SparseArray;
import android.util.SparseIntArray;

import cn.ninegame.library.component.adapter.viewholder.FixViewHolderBean;

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

    /**
     * @param position the real position, include header view and normal item view, and footer view
     */
    public boolean isHeader(int position) {
        return mHeaderBeans != null && position < mHeaderBeans.size();
    }

    /**
     * @param viewType header view's view type
     */
    public boolean isHeaderViewType(int viewType) {
        return viewType < 0 && getHeaderOrFooterPosition(viewType) != -1;
    }

    /**
     * @param position the real position, include header view and normal item view, and footer view
     */
    public boolean isFooter(int position) {
        return mFooterBeans != null && (position >= (mHeaderBeans.size() + getCount()) && position < (mHeaderBeans.size() + getCount() + mFooterBeans.size()));
    }

    /**
     * @param viewType footer view's view type
     */
    public boolean isFooterViewType(int viewType) {
        return viewType >= ITEM_VIEW_TYPE_FOOTER && getHeaderOrFooterPosition(viewType) != -1;
    }

    /**
     * @param position the position that you want add header view into
     * @param bean the {@link FixViewHolderBean} you want to binding with header view
     */
    public void addHeaderViewHolderBean(int position, FixViewHolderBean bean) {
        mHeaderBeans.append(position, bean);
        mViewTypePositionMap.append(ITEM_VIEW_TYPE_HEADER + position, position);
    }

    /**
     * @param position the adjust position of the header view list
     */
    public FixViewHolderBean getHeaderViewHolderBean(int position) {
        return mHeaderBeans.get(position);
    }

    /**
     * @param position the position that you want add footer view into
     * @param bean the {@link FixViewHolderBean} you want to binding with footer view
     */
    public void addFooterViewHolderBean(int position, FixViewHolderBean bean) {
        mFooterBeans.append(position, bean);
        mViewTypePositionMap.append(ITEM_VIEW_TYPE_FOOTER + position, position);
    }

    /**
     * @param position the adjust position of the footer view list
     */
    public FixViewHolderBean getFooterViewHolderBean(int position) {
        return mFooterBeans.get(position);
    }

    public int getHeaderViewCount() {
        return mHeaderBeans == null ? 0 : mHeaderBeans.size();
    }

    public int getFooterViewCount() {
        return mFooterBeans == null ? 0 : mFooterBeans.size();
    }

    /**
     * get the adjust position of the header or footer view list according to the view type of header or footer view
     * @param viewType header view's view type equal the sum of {@link RecyclerDataModel#ITEM_VIEW_TYPE_HEADER} and position
     *                 footer view's view type equal the sum of {@link RecyclerDataModel#ITEM_VIEW_TYPE_FOOTER} and position
     */
    public int getHeaderOrFooterPosition(int viewType) {
        return mViewTypePositionMap.get(viewType, -1);
    }

    /**
     * @param position the real position, include header view and normal item view, and footer view
     */
    @SuppressWarnings("unchecked")
    public <T> T getHeaderOrFooterItem(int position) throws ClassCastException {
        if (isHeader(position)) {
            return (T) mHeaderBeans.get(position).getData();
        } else if (isFooter(position)) {
            return (T) mFooterBeans.get(position - getCount() - getHeaderViewCount()).getData();
        }
        return null;
    }

    /**
     * @param position the real position, include header view and normal item view, and footer view
     * @param data the data would bind to head view in the position
     */
    public <T> void setHeaderData(int position, T data) {
        int headerViewType = ITEM_VIEW_TYPE_HEADER + position;
        if (isHeaderViewType(headerViewType)) {
            FixViewHolderBean bean = mHeaderBeans.get(getHeaderOrFooterPosition(headerViewType));
            if (bean != null) {
                bean.setData(data);
                notifyObservers();
            }
        }
    }

    /**
     * @param position the real position, include header view and normal item view, and footer view
     * @param data the data would bind to head view in the position
     */
    public <T> void setFooterData(int position, T data) {
        int footerViewType = ITEM_VIEW_TYPE_FOOTER + position;
        if (isFooterViewType(footerViewType)) {
            FixViewHolderBean bean = mFooterBeans.get(getHeaderOrFooterPosition(footerViewType));
            if (bean != null) {
                bean.setData(data);
                notifyObservers();
            }
        }
    }

    /**
     * @param position the real position, include header view and normal item view, and footer view
     */
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
