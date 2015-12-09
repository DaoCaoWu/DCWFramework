package cn.ninegame.library.component.adapter.model;

import android.support.annotation.NonNull;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;

import cn.ninegame.library.component.adapter.viewholder.ItemViewHolder;
import cn.ninegame.library.component.adapter.viewholder.SimpleItemViewHolder;

/**
 * create by adao12.vip@gmail.com on 15/12/8
 *
 * @author JiaYing.Cheng
 * @version 1.0
 */
public class FixViewModel {

    private final int mItemViewTypeBase;
    private SparseArray<ItemViewHolder> mHolders;
    private SparseIntArray mViewTypeToPositions;

    public FixViewModel(int itemViewTypeBase) {
        mItemViewTypeBase = itemViewTypeBase;
        mHolders = new SparseArray<ItemViewHolder>();
        mViewTypeToPositions = new SparseIntArray();
    }

    /**
     * @param viewType header view's view type for adapter, base on {@link FixViewModel#mItemViewTypeBase}
     */
    public boolean containsViewType(int viewType) {
        return getPosition(viewType) != -1;
    }

    /**
     * @param holder the {@link ItemViewHolder} you want to binding with header view
     */
    public void add(@NonNull ItemViewHolder holder) {
        int adjKey = mHolders.size() == 0 ? 0 : mHolders.keyAt(mHolders.size() - 1) + 1;
        mViewTypeToPositions.append(mItemViewTypeBase + adjKey, adjKey);
        mHolders.append(adjKey, holder);
    }

    public void add(@NonNull View view) {
        add(new SimpleItemViewHolder(view));
    }

    public void add(View view, Object listener) {
        final ItemViewHolder holder = new SimpleItemViewHolder(view);
        holder.setListener(listener);
        add(holder);
    }

    public void add(@NonNull View view, Object listener, Object data) {
        final ItemViewHolder holder = new SimpleItemViewHolder(view);
        holder.setListener(listener);
        holder.setData(data);
        add(holder);
    }

    /**
     * @param position the position that you want add header view into
     * @param holder   the {@link ItemViewHolder} you want to binding with header view
     */
    public void add(int position, @NonNull ItemViewHolder holder) {
        if (position < 0) return;
        if (mHolders.size() > 0 && position <= mHolders.keyAt(mHolders.size() - 1)) {
            SparseArray<ItemViewHolder> tempBeans = mHolders;
            mHolders = new SparseArray<ItemViewHolder>();
            boolean shouldMove = false;
            for (int i = 0; i < tempBeans.size(); i++) {
                if (tempBeans.keyAt(i) > position) {
                    mHolders.append(position, holder);
                    mHolders.append(tempBeans.keyAt(i), tempBeans.valueAt(i));
                } else if (tempBeans.keyAt(i) == position) {
                    mHolders.append(position, holder);
                    shouldMove = true;
                    mHolders.append(tempBeans.keyAt(i) + 1, tempBeans.valueAt(i));
                } else {
                    mHolders.append(tempBeans.keyAt(i) + (shouldMove ? 1 : 0), tempBeans.valueAt(i));
                }
            }
            mViewTypeToPositions.clear();
            for (int i = 0; i < mHolders.size(); i++) {
                mViewTypeToPositions.append(mItemViewTypeBase + mHolders.keyAt(i), mHolders.keyAt(i));
            }
        } else {
            mViewTypeToPositions.append(mItemViewTypeBase + position, position);
            mHolders.append(position, holder);
        }
    }

    /**
     * @param position the adjust position of the header view list
     */
    public ItemViewHolder get(int position) {
        return mHolders.get(position);
    }

    public ItemViewHolder getItemViewHolder(int index) {
        return mHolders.valueAt(index);
    }

    public void remove(ItemViewHolder holder) {
        int index = mHolders.indexOfValue(holder);
        if (index != -1) {
            int key = mHolders.keyAt(index);
            mViewTypeToPositions.removeAt(mViewTypeToPositions.indexOfKey(mItemViewTypeBase + key));
            mHolders.remove(key);
        }
    }

    public void remove(int position) {
        for (int i = 0; i < mViewTypeToPositions.size(); i++) {
            if (mViewTypeToPositions.keyAt(i) == mItemViewTypeBase + position) {
                mViewTypeToPositions.removeAt(i);
            }
        }
        mHolders.remove(position);
    }

    public void remove(View view) {
        for (int i = 0; i < mHolders.size(); i++) {
            ItemViewHolder holder = mHolders.valueAt(i);
            if (holder.itemView == view) {
                int position = mHolders.keyAt(i);
                remove(position);
                break;
            }
        }
    }

    public int getCount() {
        return mHolders == null ? 0 : mHolders.size();
    }

    /**
     * get the adjust position of the header view list according to the view type of header view
     *
     * @param viewType header view's view type equal the sum of {@link FixViewModel#mItemViewTypeBase} and position
     */
    public int getPosition(int viewType) {
        return mViewTypeToPositions.get(viewType, -1);
    }

    public int getViewType(int index) {
        return mViewTypeToPositions.keyAt(index);
    }

    public void setData(int position, Object data) {
        ItemViewHolder holder = get(position);
        holder.setData(data);
    }

    public void setData(ItemViewHolder holder, Object data) {
        holder.setData(data);
    }

    public void setData(View view, Object data) {
        for (int i = 0; i < mHolders.size(); i++) {
            ItemViewHolder holder = mHolders.valueAt(i);
            if (holder.itemView == view) {
                int position = mHolders.keyAt(i);
                setData(position, data);
                break;
            }
        }
    }
}
