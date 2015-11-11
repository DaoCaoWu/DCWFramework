package cn.ninegame.library.component.adapter.viewholder;

import android.support.annotation.LayoutRes;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/11/6.
 */
public class FixViewHolderBean extends ItemViewHolderBean {

    private Object mData;

    public FixViewHolderBean(@LayoutRes int itemViewHolderLayoutId, Class<? extends ItemViewHolder> itemViewHolderClazz) {
        super(itemViewHolderLayoutId, itemViewHolderClazz);
    }

    public <D> FixViewHolderBean(@LayoutRes int itemViewHolderLayoutId, Class<? extends ItemViewHolder> itemViewHolderClazz, D data) {
        this(itemViewHolderLayoutId, itemViewHolderClazz, data, null);
    }

    public <D, L> FixViewHolderBean(@LayoutRes int itemViewHolderLayoutId, Class<? extends ItemViewHolder> itemViewHolderClazz, D data, L listener) {
        super(itemViewHolderLayoutId, itemViewHolderClazz, listener);
        mData = data;
    }

    @SuppressWarnings("unchecked")
    public <D> D getData() {
        return (D)mData;
    }

    public <D> void setData(D data) {
        mData = data;
    }
}
