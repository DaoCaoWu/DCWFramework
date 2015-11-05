package com.dcw.app.rating.ui.adapter.viewholder;

import android.support.annotation.LayoutRes;

import com.dcw.app.rating.ui.adapter.model.ListDataModel;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/11/6.
 */
public class FixViewHolderBean<M extends ListDataModel<D>, D> extends ItemViewHolderBean<M, D> {

    private Object mData;

    public FixViewHolderBean(@LayoutRes int itemViewHolderLayoutId, Class<? extends ItemViewHolder<M, D>> itemViewHolderClazz) {
        super(itemViewHolderLayoutId, itemViewHolderClazz);
    }

    public FixViewHolderBean(@LayoutRes int itemViewHolderLayoutId, Class<? extends ItemViewHolder<M, D>> itemViewHolderClazz, Object listener) {
        super(itemViewHolderLayoutId, itemViewHolderClazz, listener);
    }

    public FixViewHolderBean(@LayoutRes int itemViewHolderLayoutId, Class<? extends ItemViewHolder<M, D>> itemViewHolderClazz, Object data, Object listener) {
        super(itemViewHolderLayoutId, itemViewHolderClazz, listener);
        mData = data;
    }

    public Object getData() {
        return mData;
    }
}
