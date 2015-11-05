package com.dcw.app.rating.ui.adapter.viewholder;

import android.support.annotation.LayoutRes;

import com.dcw.app.rating.ui.adapter.model.ListDataModel;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/10/25.
 */
public class ItemViewHolderBean<M extends ListDataModel<D>, D> {

    private
    @LayoutRes
    int mItemViewHolderLayoutId;

    private Class<? extends ItemViewHolder<M, D>> mItemViewHolderClazz;

    private Object mViewHolderListener;

    public ItemViewHolderBean(@LayoutRes int itemViewHolderLayoutId, Class<? extends ItemViewHolder<M, D>> itemViewHolderClazz) {
        this(itemViewHolderLayoutId, itemViewHolderClazz, null);
    }

    public ItemViewHolderBean(@LayoutRes int itemViewHolderLayoutId, Class<? extends ItemViewHolder<M, D>> itemViewHolderClazz, Object listener) {
        mItemViewHolderLayoutId = itemViewHolderLayoutId;
        mItemViewHolderClazz = itemViewHolderClazz;
        mViewHolderListener = listener;
    }

    public Object getViewHolderListener() {
        return mViewHolderListener;
    }

    public
    @LayoutRes
    int getItemViewHolderLayoutId() {
        return mItemViewHolderLayoutId;
    }

    public Class<? extends ItemViewHolder<M, D>> getItemViewHolderClazz() {
        return mItemViewHolderClazz;
    }
}

