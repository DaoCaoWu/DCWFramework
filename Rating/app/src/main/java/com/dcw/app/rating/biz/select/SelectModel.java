package com.dcw.app.rating.biz.select;

import android.view.View;

import com.dcw.app.rating.biz.contact.model.ListDataModel;

import java.util.ArrayList;
import java.util.List;

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
public class SelectModel<T> extends ListDataModel<T> implements ISelect<T> {

    /**
     * you can select only one data in list
     */
    public static final int TYPE_SELECT_SINGLE = 1;
    /**
     * you can select more than one data in list
     */
    public static final int TYPE_SELECT_MULTI = 2;

    private OnItemSelectedListener mOnSelectedListener = null;

    /**
     * The default is single select mode.
     */
    private int mSelectedMode = TYPE_SELECT_SINGLE;
    /**
     * 用来控制CheckBox的选中状况，默认都没有被选择
     */
    private Byte[] mSelectArray;
    /**
     * 可以被选择的列表，默认都可被选择
     */
    private Byte[] mCanBeSelectedArray;
    /**
     * 最大选择数，默认只能选一个
     */
    private int mMaxSelectNum = 1;
    /**
     * 上次选择的位置
     * -1 代表没有任何一个被选中
     */
    private int mLastSelectedIndex = -1;

    /**
     * 上次选择的位置
     * -1 代表没有任何一个被选中
     */
    private long mInitSelectedId = -1;


    /**
     * 初始化selectedMap、canBeSelectedMap的数据
     */
    private void initDate(List<T> dataList) {

        int newSize = getCount(dataList);
        mSelectArray = new Byte[newSize];
        mCanBeSelectedArray = new Byte[newSize];
        for (int i = 0; i < newSize; i++) {
            mSelectArray[i] = 0; //默认都没有被选择
            mCanBeSelectedArray[i] = 1; //默认都可被选择
        }
        //single
        mLastSelectedIndex = -1;
    }

    public void notifyDataSetChanged() {
        onDataListChange();
    }

    private void onDataListChange() {
        int newSize = getCount(getDataList());
        Byte[] selectArrayTmp = mSelectArray;
        Byte[] canBeSelectedArrayTmp = mCanBeSelectedArray;
        mSelectArray = new Byte[newSize];
        mCanBeSelectedArray = new Byte[newSize];
        for (int i = 0; i < newSize; i++) {
            mSelectArray[i] = 0; //默认都没有被选择
            mCanBeSelectedArray[i] = 1; //默认都可被选择
        }
        int length = selectArrayTmp == null ? 0 : selectArrayTmp.length;
        length = length <= mSelectArray.length ? length : mSelectArray.length;//求较小值
        if (selectArrayTmp != null) {
            for (int i = 0; i < length; i++) {
                mSelectArray[i] = selectArrayTmp[i];
                mCanBeSelectedArray[i] = canBeSelectedArrayTmp[i];
            }
        }
    }


    private int getCount(List<T> dataList) {

        return dataList == null ? 0 : dataList.size();
    }

    /**
     * 设置要绑定的数据
     * 分页时，保留前一页的选择状态，如果不需要保存(或不做分页)，请重新实现些方法
     *
     * @param dataList 传入的数据
     */
    public void setDataList(List<T> dataList) {
        // 初始化数据
        initDate(dataList);
        if (isSingleSelectedMode()) {
            setMaxSelectNum(1);
        }
        super.setDataList(dataList);
        notifyDataSetChanged();
    }

    public int getMaxSelectNum() {

        return mMaxSelectNum;
    }

    public void setMaxSelectNum(int maxSelectNum) {

        if (!isSingleSelectedMode()) {

            this.mMaxSelectNum = maxSelectNum;
        }
    }

    public OnItemSelectedListener getOnItemSelectedListener() {
        return mOnSelectedListener;
    }

    public void setOnItemSelectedListener(OnItemSelectedListener onSelectedListener) {
        this.mOnSelectedListener = onSelectedListener;
    }

    public int getSelectedMode() {
        return mSelectedMode;
    }

    public void setSelectedMode(int selectedMode) {
        this.mSelectedMode = selectedMode;
    }

    public Byte[] getCanBeSelectedArray() {
        return mCanBeSelectedArray;
    }

    public void setCanBeSelectedArray(Byte[] canBeSelectedArray) {
        this.mCanBeSelectedArray = canBeSelectedArray;
    }

    public Byte[] getSelectArray() {
        return mSelectArray;
    }

    public int getLastSelectedIndex() {
        return mLastSelectedIndex;
    }

    public void setLastSelectedIndex(int lastSelectedIndex) {
        this.mLastSelectedIndex = lastSelectedIndex;
    }

    public long getInitSelectedId() {
        return mInitSelectedId;
    }

    public int getInitSelectedIndex() {

        int index = -1;
        if (isSingleSelectedMode()) {

            int idHashCode = String.valueOf(mInitSelectedId).hashCode();
            for (T obj : getDataList()) {
                if (idHashCode == obj.hashCode()) {
                    index = getDataList().indexOf(obj);
                    break;
                }
            }
            return index;
        }
        return index;
    }

    @Override
    public void setInitSelectedId(long id) {

        if (isSingleSelectedMode()) {

            int index = -1;
            mInitSelectedId = id;
            int idHashCode = String.valueOf(id).hashCode();
            for (T obj : getDataList()) {
                if (idHashCode == obj.hashCode()) {
                    index = getDataList().indexOf(obj);
                    break;
                }
            }
            setLastSelectedIndex(index);
            notifyDataSetChanged();
        }
    }


    /**
     * default restore the selectedArray, mCanBeSelectedArray, mLastSelectedIndex
     */
    @Override
    public void restore() {

        for (int i = 0; i < getCount(getDataList()); i++) {

            if (!isSingleSelectedMode()) { // multi

                mSelectArray[i] = 0; //默认都没有被选择
            }
            mCanBeSelectedArray[i] = 1; //默认都可被选择
        }
        //single
        mLastSelectedIndex = -1;
    }

    @Override
    public void restore(int maxSelectedNum) {

        for (int i = 0; i < getCount(getDataList()); i++) {

            if (!isSingleSelectedMode()) { // multi

                mSelectArray[i] = 0; //默认都没有被选择
                setMaxSelectNum(maxSelectedNum);
            }
            mCanBeSelectedArray[i] = 1; //默认都可被选择
        }
        //single
        mLastSelectedIndex = -1;
    }

    /**
     * checkbox点击事件封装
     */
    public void onCheckBoxClicked(final int pos, View v) {

        if (isSelected(pos)) {
            select(pos, false);
        } else {
            select(pos, true);
        }
        if (mOnSelectedListener != null) {

            if (isSingleSelectedMode()) {

                mOnSelectedListener.onSelected(v, pos, isSelected(pos));
            } else {

                mOnSelectedListener.onSelected(v, pos, !isSelected(pos));
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getSelectedCount() {
        if (isSingleSelectedMode()) {
            return 1;
        }
        int selectedCount = 0;
        for (int i = 0; i < (mSelectArray == null ? 0 : mSelectArray.length); i++) {
            if (byteToBoolean(mSelectArray[i])) {
                selectedCount++;
            }
        }
        return selectedCount;
    }

    public boolean isInitSelected() {

        if (isSingleSelectedMode()) {

            if (mLastSelectedIndex == getInitSelectedIndex()) {

                return true;
            }
        }
        return false;
    }

    @Override
    public T getLastSelected() {

        return getItem(mLastSelectedIndex);
    }

    /**
     * get the data selected
     *
     * @return return a new list, or null when selectedCount no more than 0.
     */
    @Override
    public List<T> getSelectedData() {

        if (getSelectedCount() <= 0) {

            return null;
        }

        if (isSingleSelectedMode()) {

            if (-1 == mLastSelectedIndex) {

                return null;
            } else {

                List<T> selectedData = new ArrayList<T>();
                selectedData.add(getDataList().get(mLastSelectedIndex));
                return selectedData;
            }
        }

        int addCount = 0;
        List<T> selectedData = new ArrayList<T>();
        int selectedCount = getSelectedCount();
        for (int i = 0; i < mSelectArray.length; i++) {

            if (byteToBoolean(mSelectArray[i])) {

                selectedData.add(getDataList().get(i));
                addCount++;

                if (selectedCount == addCount) { // 处理分页数据时，可以减少不必要的遍历

                    break;
                }
            }

        }
        return selectedData;
    }

    /**
     * return true only when the value of mCanBeSelectedArray[pos] is true.
     * and if the length of mCanBeSelectedArray is less then dataSource's size,
     * false will be return when the position is among length and size.
     */
    @Override
    public boolean canBeSelected(int pos) {

        if (pos >= (getDataList() == null ? 0 : getDataList().size()) || pos < 0) {

            return false;
        }

        if (mCanBeSelectedArray == null || pos >= mCanBeSelectedArray.length) {

            return false;
        }

        return byteToBoolean(mCanBeSelectedArray[pos]);
    }

    /**
     * set a specific value, which will control which can be selected, on specific position.
     */
    @Override
    public void setCanBeSelected(int pos, boolean canBeSelected) {

        if (isSelected(pos)) { // if the position has been selected, then reset it to be unselected state.

            select(pos, !isSelected(pos));
        }
        mCanBeSelectedArray[pos] = booleanToByte(canBeSelected);
    }

    /**
     * judge that weather the specific position can be selected or not.
     */
    @Override
    public boolean isSelected(int pos) {
        if (pos >= mSelectArray.length) {
            return false;
        }
        if (isSingleSelectedMode()) {

            return mLastSelectedIndex == pos;
        }

        return byteToBoolean(mSelectArray[pos]);
    }

    public boolean isSingleSelectedMode() {

        return mSelectedMode == TYPE_SELECT_SINGLE;
    }

    public void select(int from, int to, boolean isSelected) {
        if (isSingleSelectedMode()) {
            return;
        }
        if (from > to) {
            return;
        }
        int selectArrayLength = mSelectArray == null ? 0 : mSelectArray.length;
        int length = to > selectArrayLength ? selectArrayLength : to;
        for (int i = from > 0 ? 0 : from; i < length; i++) {
            int selectedCount = getSelectedCount();
            //最大选择数的限制条件
            if (selectedCount >= mMaxSelectNum && isSelected) {

                if (mOnSelectedListener != null && mOnSelectedListener instanceof OnSelectedListener) {

                    ((OnSelectedListener) mOnSelectedListener).onSelectedOverFlow(i);
                }
                return;
            }
            mSelectArray[i] = booleanToByte(isSelected);
        }
        notifyDataSetChanged();
    }

    @Override
    public void select(int pos, boolean isSelected) {

        //不可选择item的限制条件
        if (!canBeSelected(pos)) {

            return;
        }

        if (isSingleSelectedMode()) {

            if (isSelected) { //single

                mLastSelectedIndex = pos;
            }
        } else {
            int selectedCount = getSelectedCount();
            //最大选择数的限制条件
            if (selectedCount >= mMaxSelectNum && isSelected) {

                if (mOnSelectedListener != null && mOnSelectedListener instanceof OnSelectedListener) {

                    ((OnSelectedListener) mOnSelectedListener).onSelectedOverFlow(pos);
                }
                return;
            }
            mSelectArray[pos] = booleanToByte(isSelected);
        }
        notifyDataSetChanged();
    }

    private Boolean byteToBoolean(byte b) {

        return b == 1;
    }

    private Byte booleanToByte(boolean b) {

        return (byte) (b ? 1 : 0);
    }
}
