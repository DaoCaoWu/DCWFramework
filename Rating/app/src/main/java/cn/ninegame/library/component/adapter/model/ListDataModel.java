package cn.ninegame.library.component.adapter.model;

import android.util.SparseArray;

import cn.ninegame.library.component.adapter.viewholder.ItemViewHolderBean;
import cn.ninegame.library.component.mvc.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/10/21.
 */
public class ListDataModel<D> extends Model {

    /**
     * the list store the data to show.
     */
    private List<D> mDataList = null;

    private SparseArray<ItemViewHolderBean> mHolderBeans;

    public ListDataModel() {
    }

    public ListDataModel(List<D> dataList) {
        setDataList(dataList);
    }

    /**
     * get the data list binding adapter
     */
    public List<D> getDataList() {
        return mDataList;
    }

    /**
     * binding adapter with data list and notify adapter that data set changed
     *
     * @param dataList the data would binding to adapter
     */
    public void setDataList(List<D> dataList) {
        this.mDataList = dataList == null ? new ArrayList<D>() : dataList;
        notifyObservers();
    }

    /**
     * binding adapter with data list and don't notify adapter that data set changed
     *
     * @param dataList 传入的数据
     */
    public void setDataListNoNotify(List<D> dataList) {
        this.mDataList = dataList == null ? new ArrayList<D>() : dataList;
    }

    public void addItemViewHolderBean(int viewType, ItemViewHolderBean bean) {
        if (mHolderBeans == null) {
            mHolderBeans = new SparseArray<ItemViewHolderBean>();
        }
        mHolderBeans.append(viewType, bean);
    }

    public ItemViewHolderBean getItemViewHolderBean(int viewType) {
        return mHolderBeans.get(viewType);
    }

    public SparseArray<ItemViewHolderBean> getHolderBeans() {
        return mHolderBeans;
    }

    /**
     * clear data binding into adapter
     */
    public void clearData() {
        if (getCount() == 0) return;

        mDataList.clear();
        notifyObservers();
    }

    /**
     * add single data into data set
     */
    public void addItem(D item) {
        if (item == null) return;

        if (mDataList == null) {
            mDataList = new ArrayList<D>();
        }

        this.mDataList.add(item);
        notifyObservers();
    }

    /**
     * append a data set into the origin data set
     */
    public void addItems(List<D> items) {
        if (items == null) return;

        if (mDataList == null) {
            mDataList = new ArrayList<D>();
        }

        this.mDataList.addAll(items);
        notifyObservers();
    }

    /**
     * not working in these version
     */
    @Deprecated
    public boolean isEnabled(int position) {
        return mDataList != null && position >= 0 && position < mDataList.size();
    }

    public int getCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    /**
     * @param position the position exclude header views
     * @return Returns the data of item in the position
     */
    public D getItem(int position) {
        if (position >= getCount() || position < 0)
            return null;
        else
            return mDataList.get(position);
    }

    /**
     * simple return the position, and you can override the method to make your work well
     * @param position the position exclude header views
     * @return Returns the position
     */
    public long getItemId(int position) {
        return position;
    }

    public int getViewTypeCount() {
        return mHolderBeans == null ? 0 : mHolderBeans.size();
    }

    public int getItemViewType(int position) {
        return 0;
    }

    /**
     * replace the old data set with {@param items}
     * @param items the new data set
     */
    public void replaceAll(List<D> items) {
        if (mDataList == null) {
            mDataList = new ArrayList<D>();
        }
        mDataList.clear();
        if (items != null) {
            mDataList.addAll(items);
        }
        notifyObservers();
    }

    @Override
    public void notifyObservers() {
        setChanged();
        super.notifyObservers();
    }

    @Override
    public <T> void notifyObservers(T data, Object... args) {
        setChanged();
        super.notifyObservers(data, args);
    }
}

