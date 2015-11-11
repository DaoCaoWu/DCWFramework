package cn.ninegame.library.component.adapter.viewholder;

import android.support.annotation.LayoutRes;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/10/25.
 */
public class ItemViewHolderBean {

    private
    @LayoutRes
    int mItemViewHolderLayoutId;

    private Class<? extends ItemViewHolder> mItemViewHolderClazz;

    private Object mViewHolderListener;

    public ItemViewHolderBean(@LayoutRes int itemViewHolderLayoutId, Class<? extends ItemViewHolder> itemViewHolderClazz) {
        this(itemViewHolderLayoutId, itemViewHolderClazz, null);
    }

    public <L> ItemViewHolderBean(@LayoutRes int itemViewHolderLayoutId, Class<? extends ItemViewHolder> itemViewHolderClazz, L listener) {
        mItemViewHolderLayoutId = itemViewHolderLayoutId;
        mItemViewHolderClazz = itemViewHolderClazz;
        mViewHolderListener = listener;
    }

    @SuppressWarnings("unchecked")
    public <L> L getViewHolderListener() {
        return (L)mViewHolderListener;
    }

    public
    @LayoutRes
    int getItemViewHolderLayoutId() {
        return mItemViewHolderLayoutId;
    }

    public Class<? extends ItemViewHolder> getItemViewHolderClazz() {
        return mItemViewHolderClazz;
    }
}

