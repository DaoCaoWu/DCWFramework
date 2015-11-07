package com.dcw.app.rating.ui.adapter.viewholder;

import android.support.annotation.LayoutRes;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/11/6.
 */
public class FixViewHolderBean extends ItemViewHolderBean {

    private Object mData;

    public FixViewHolderBean(@LayoutRes int itemViewHolderLayoutId, Class<? extends ItemViewHolder> itemViewHolderClazz) {
        super(itemViewHolderLayoutId, itemViewHolderClazz);
    }

    public FixViewHolderBean(@LayoutRes int itemViewHolderLayoutId, Class<? extends ItemViewHolder> itemViewHolderClazz, Object data) {
        this(itemViewHolderLayoutId, itemViewHolderClazz, data, null);
    }

    public FixViewHolderBean(@LayoutRes int itemViewHolderLayoutId, Class<? extends ItemViewHolder> itemViewHolderClazz, Object data, Object listener) {
        super(itemViewHolderLayoutId, itemViewHolderClazz, listener);
        mData = data;
    }

    public Object getData() {
        return mData;
    }
}
