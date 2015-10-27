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

    public ItemViewHolderBean(@LayoutRes int itemViewHolderLayoutId, Class<? extends ItemViewHolder<M, D>> itemViewHolderClazz) {
        mItemViewHolderLayoutId = itemViewHolderLayoutId;
        mItemViewHolderClazz = itemViewHolderClazz;
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
