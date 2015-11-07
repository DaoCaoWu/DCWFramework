package com.dcw.app.rating.ui.adapter.model;

import android.util.SparseArray;

import com.dcw.app.rating.ui.adapter.viewholder.ItemViewHolderBean;
import com.dcw.app.rating.ui.mvc.Model;

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

    private SparseArray<ItemViewHolderBean<? extends ListDataModel<D>, D>> mHolderBeans;

    public ListDataModel() {
    }

    public ListDataModel(List<D> dataList) {
        setDataList(dataList);
    }

    /**
     * get 绑定的数据
     */
    public List<D> getDataList() {
        return mDataList;
    }

    /**
     * 设置要绑定的数据
     *
     * @param dataList 传入的数据
     */
    public void setDataList(List<D> dataList) {
        this.mDataList = dataList == null ? new ArrayList<D>() : dataList;
        notifyObservers();
    }

    /**
     * 设置要绑定的数据
     *
     * @param dataList 传入的数据
     */
    public void setDataListNoNotify(List<D> dataList) {
        this.mDataList = dataList == null ? new ArrayList<D>() : dataList;
    }

    public void addItemViewHolderBean(int viewType, ItemViewHolderBean<? extends ListDataModel<D>, D> bean) {
        if (mHolderBeans == null) {
            mHolderBeans = new SparseArray<ItemViewHolderBean<? extends ListDataModel<D>, D>>();
        }
        mHolderBeans.append(viewType, bean);
    }

    public <M extends ListDataModel<D>> ItemViewHolderBean<M, D> getItemViewHolderBean(int viewType) {
        return (ItemViewHolderBean<M, D>) mHolderBeans.get(viewType);
    }

    public SparseArray<ItemViewHolderBean<? extends ListDataModel<D>, D>> getHolderBeans() {
        return mHolderBeans;
    }

    /**
     * 清除绑定数据数据
     */
    public void clearData() {
        if (getCount() == 0) return;

        mDataList.clear();
        notifyObservers();
    }

    /**
     * 添加列表项
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
     * 添加列表数据
     */
    public void addItems(List<D> items) {
        if (items == null) return;

        if (mDataList == null) {
            mDataList = new ArrayList<D>();
        }

        this.mDataList.addAll(items);
        notifyObservers();
    }


    public boolean isEnabled(int position) {
        return mDataList != null && position >= 0 && position < mDataList.size();
    }

    public int getCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    public D getItem(int position) {
        if (position >= getCount() || position < 0)
            return null;
        else
            return mDataList.get(position);
    }

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
     * 替换所有
     */
    public void replaceAll(List<D> items) {
        if (items == null) return;

        if (mDataList == null) {
            mDataList = new ArrayList<D>();
        }
        mDataList.clear();
        mDataList.addAll(items);
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

