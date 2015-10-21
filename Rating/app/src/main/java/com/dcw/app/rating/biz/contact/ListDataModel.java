package com.dcw.app.rating.biz.contact;

import android.content.Context;
import android.view.LayoutInflater;

import com.dcw.app.rating.ui.mvc.core.Observable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/10/21.
 */
public class ListDataModel<D> extends Observable {

    protected LayoutInflater mInflater;
    /**
     * the list store the data to show.
     */
    private List<D> mDataList = null;
    /**
     * the activity context from the activity where adapter is in.
     */
    private Context mContext = null;

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
}
