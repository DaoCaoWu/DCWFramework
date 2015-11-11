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

    public ItemViewHolderBean(@LayoutRes int itemViewHolderLayoutId, Class<? extends ItemViewHolder> itemViewHolderClazz, Object listener) {
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

    public Class<? extends ItemViewHolder> getItemViewHolderClazz() {
        return mItemViewHolderClazz;
    }
}

