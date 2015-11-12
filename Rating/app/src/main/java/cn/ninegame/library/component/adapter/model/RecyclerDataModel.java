package cn.ninegame.library.component.adapter.model;

import android.util.SparseArray;
import android.util.SparseIntArray;

import java.util.List;

import cn.ninegame.library.component.adapter.viewholder.FixViewHolderBean;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/11/5.
 */
public class RecyclerDataModel<D> extends ListDataModel<D> {

    public static final int ITEM_VIEW_TYPE_HEADER = Integer.MIN_VALUE;
    public static final int ITEM_VIEW_TYPE_FOOTER = Integer.MAX_VALUE / 2;

    private SparseArray<FixViewHolderBean> mHeaderBeans;
    private SparseArray<FixViewHolderBean> mFooterBeans;
    private SparseIntArray mHViewTypePositionMap;
    private SparseIntArray mFViewTypePositionMap;

    public RecyclerDataModel() {
        mHeaderBeans = new SparseArray<FixViewHolderBean>();
        mFooterBeans = new SparseArray<FixViewHolderBean>();
        mHViewTypePositionMap = new SparseIntArray();
        mFViewTypePositionMap = new SparseIntArray();
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
        return viewType < 0 && getHeaderPosition(viewType) != -1;
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
        return viewType >= ITEM_VIEW_TYPE_FOOTER && getFooterPosition(viewType) != -1;
    }

    /**
     * This method was extracted to encourage VM to inline callers.
     * TODO: when we have a VM that can actually inline, move the test in here too!
     */
    static IndexOutOfBoundsException throwIndexOutOfBoundsException(int index, int size) {
        throw new IndexOutOfBoundsException("Invalid index " + index + ", size is " + size);
    }

    /**
     * @param bean the {@link FixViewHolderBean} you want to binding with header view
     */
    public void addHeaderViewHolderBean(FixViewHolderBean bean) {
        int adjKey = mHeaderBeans.size() == 0 ? 0 : mHeaderBeans.keyAt(mHeaderBeans.size() - 1) + 1;
        mHViewTypePositionMap.append(ITEM_VIEW_TYPE_HEADER + adjKey, adjKey);
        mHeaderBeans.append(adjKey, bean);
        notifyObservers();
    }

    /**
     * @param position the position that you want add header view into
     * @param bean     the {@link FixViewHolderBean} you want to binding with header view
     */
    public void addHeaderViewHolderBean(int position, FixViewHolderBean bean) {
        if (position < 0) return;
        if (mHeaderBeans.size() > 0 && position <= mHeaderBeans.keyAt(mHeaderBeans.size() - 1)) {
            SparseArray<FixViewHolderBean> tempBeans = mHeaderBeans;
            mHeaderBeans = new SparseArray<FixViewHolderBean>();
            boolean shouldMove = false;
            for (int i = 0; i < tempBeans.size(); i++) {
                if (tempBeans.keyAt(i) > position) {
                    mHeaderBeans.append(position, bean);
                    mHeaderBeans.append(tempBeans.keyAt(i), tempBeans.valueAt(i));
                } else if (tempBeans.keyAt(i) == position) {
                    mHeaderBeans.append(position, bean);
                    shouldMove = true;
                    mHeaderBeans.append(tempBeans.keyAt(i) + 1, tempBeans.valueAt(i));
                } else {
                    mHeaderBeans.append(tempBeans.keyAt(i) + (shouldMove ? 1 : 0), tempBeans.valueAt(i));
                }
            }
            mHViewTypePositionMap.clear();
            for (int i = 0; i < mHeaderBeans.size(); i++) {
                mHViewTypePositionMap.append(ITEM_VIEW_TYPE_HEADER + mHeaderBeans.keyAt(i), mHeaderBeans.keyAt(i));
            }
        } else {
            mHViewTypePositionMap.append(ITEM_VIEW_TYPE_HEADER + position, position);
            mHeaderBeans.append(position, bean);
        }
        notifyObservers();
    }

    /**
     * @param position the adjust position of the header view list
     */
    public FixViewHolderBean getHeaderViewHolderBean(int position) {
        return mHeaderBeans.get(position);
    }

    public FixViewHolderBean getHeaderViewHolderBeanByIndex(int index) {
        return mHeaderBeans.valueAt(index);
    }

    public FixViewHolderBean getFooterViewHolderBeanByIndex(int index) {
        return mFooterBeans.valueAt(index);
    }

    /**
     * @param bean the {@link FixViewHolderBean} you want to binding with footer view
     */
    public void addFooterViewHolderBean(FixViewHolderBean bean) {
        int adjKey = mFooterBeans.size() == 0 ? 0 : mFooterBeans.keyAt(mFooterBeans.size() - 1) + 1;
        mFViewTypePositionMap.append(ITEM_VIEW_TYPE_FOOTER + adjKey, adjKey);
        mFooterBeans.append(adjKey, bean);
        notifyObservers();
    }

    /**
     * @param position the position that you want add footer view into
     * @param bean     the {@link FixViewHolderBean} you want to binding with footer view
     */
    public void addFooterViewHolderBean(int position, FixViewHolderBean bean) {
        if (position < 0) return;
        if (mFooterBeans.size() > 0 && position <= mFooterBeans.keyAt(mFooterBeans.size() - 1)) {
            SparseArray<FixViewHolderBean> tempBeans = mFooterBeans;
            mFooterBeans = new SparseArray<FixViewHolderBean>();
            boolean shouldMove = false;
            for (int i = 0; i < tempBeans.size(); i++) {
                if (tempBeans.keyAt(i) > position) {
                    mFooterBeans.append(position, bean);
                    mFooterBeans.append(tempBeans.keyAt(i), tempBeans.valueAt(i));
                } else if (tempBeans.keyAt(i) == position) {
                    mFooterBeans.append(position, bean);
                    shouldMove = true;
                    mFooterBeans.append(tempBeans.keyAt(i) + 1, tempBeans.valueAt(i));
                } else {
                    mFooterBeans.append(tempBeans.keyAt(i) + (shouldMove ? 1 : 0), tempBeans.valueAt(i));
                }
            }
            mFViewTypePositionMap.clear();
            for (int i = 0; i < mFooterBeans.size(); i++) {
                mFViewTypePositionMap.append(ITEM_VIEW_TYPE_FOOTER + mFooterBeans.keyAt(i), mFooterBeans.keyAt(i));
            }
        } else {
            mFViewTypePositionMap.append(ITEM_VIEW_TYPE_FOOTER + position, position);
            mFooterBeans.append(position, bean);
        }
        notifyObservers();
    }

    public void removeHeaderViewHolderBean(FixViewHolderBean bean) {
        int key = mHeaderBeans.keyAt(mHeaderBeans.indexOfValue(bean));
        mHViewTypePositionMap.removeAt(ITEM_VIEW_TYPE_HEADER + key);
        mHeaderBeans.remove(key);
        notifyObservers();
    }

    public void removeHeaderViewHolderBean(int position) {
        for (int i = 0; i < mHViewTypePositionMap.size(); i++) {
            if (mHViewTypePositionMap.keyAt(i) == ITEM_VIEW_TYPE_HEADER + position) {
                mHViewTypePositionMap.removeAt(i);
            }
        }
        mHeaderBeans.remove(position);
        notifyObservers();
    }


    public void removeFooterViewHolderBean(FixViewHolderBean bean) {
        int key = mFooterBeans.keyAt(mFooterBeans.indexOfValue(bean));
        mFViewTypePositionMap.removeAt(ITEM_VIEW_TYPE_FOOTER + key);
        mFooterBeans.remove(key);
        notifyObservers();
    }

    public void removeFooterViewHolderBean(int position) {
        for (int i = 0; i < mFViewTypePositionMap.size(); i++) {
            if (mFViewTypePositionMap.keyAt(i) == ITEM_VIEW_TYPE_FOOTER + position) {
                mFViewTypePositionMap.removeAt(i);
            }
        }
        mFooterBeans.remove(position);
        notifyObservers();
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
     * get the adjust position of the header view list according to the view type of header view
     *
     * @param viewType header view's view type equal the sum of {@link RecyclerDataModel#ITEM_VIEW_TYPE_HEADER} and position
     */
    public int getHeaderPosition(int viewType) {
        return mHViewTypePositionMap.get(viewType, -1);
    }

    public int getHeaderViewType(int index) {
        return mHViewTypePositionMap.keyAt(index);
    }

    /**
     * get the adjust position of the footer view list according to the view type of footer view
     *
     * @param viewType footer view's view type equal the sum of {@link RecyclerDataModel#ITEM_VIEW_TYPE_FOOTER} and position
     */
    public int getFooterPosition(int viewType) {
        return mFViewTypePositionMap.get(viewType, -1);
    }

    public int getFooterViewType(int index) {
        return mFViewTypePositionMap.keyAt(index);
    }

    /**
     * @param position the real position, include header view and normal item view, and footer view
     */
    @SuppressWarnings("unchecked")
    public <T> T getHeaderOrFooterItem(int position) throws ClassCastException {
        if (isHeader(position)) {
            return (T) getHeaderViewHolderBeanByIndex(position).getData();
        } else if (isFooter(position)) {
            return (T) getFooterViewHolderBeanByIndex(position - getCount() - getHeaderViewCount()).getData();
        }
        return null;
    }

    /**
     * @param position the real position, include header view and normal item view, and footer view
     * @param data     the data would bind to head view in the position
     */
    public <T> void setHeaderData(int position, T data) {
        int headerViewType = ITEM_VIEW_TYPE_HEADER + position;
        if (isHeaderViewType(headerViewType)) {
            FixViewHolderBean bean = getHeaderViewHolderBean(position);
            if (bean != null) {
                bean.setData(data);
                notifyObservers();
            }
        }
    }

    /**
     * @param position the real position, include header view and normal item view, and footer view
     * @param data     the data would bind to head view in the position
     */
    public <T> void setFooterData(int position, T data) {
        int footerViewType = ITEM_VIEW_TYPE_FOOTER + position;
        if (isFooterViewType(footerViewType)) {
            FixViewHolderBean bean = getFooterViewHolderBean(position);
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
            return super.getViewTypeCount() + mHViewTypePositionMap.size() + mFViewTypePositionMap.size();
        }
        return 1;
    }
}
