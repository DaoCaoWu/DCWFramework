package cn.ninegame.library.component.adapter.viewholder;

import android.support.annotation.LayoutRes;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/10/25.
 */
public class ItemViewHolderBean {

    /**
     * the view's layoutId that would be used to inflating item view
     */
    private
    @LayoutRes
    int mItemViewHolderLayoutId;

    /**
     * the class of the {@link ItemViewHolder}'s implement
     */
    private Class<? extends ItemViewHolder> mItemViewHolderClazz;

    /**
     * the listener casted to the given type or null if not listener was set into the Adapter.
     */
    private Object mViewHolderListener;

    /**
     * @param itemViewHolderLayoutId the view's layoutId that would be used to inflating item view
     * @param itemViewHolderClazz the class of the {@link ItemViewHolder}'s implement
     */
    public ItemViewHolderBean(@LayoutRes int itemViewHolderLayoutId, Class<? extends ItemViewHolder> itemViewHolderClazz) {
        this(itemViewHolderLayoutId, itemViewHolderClazz, null);
    }

    /**
     * @param itemViewHolderLayoutId the view's layoutId that would be used to inflating item view
     * @param itemViewHolderClazz the class of the {@link ItemViewHolder}'s implement
     * @param listener the listener of events that views included in {@link ItemViewHolder} dispatched
     * @param <L> the class type of the class implement listener
     */
    public <L> ItemViewHolderBean(@LayoutRes int itemViewHolderLayoutId, Class<? extends ItemViewHolder> itemViewHolderClazz, L listener) {
        mItemViewHolderLayoutId = itemViewHolderLayoutId;
        mItemViewHolderClazz = itemViewHolderClazz;
        mViewHolderListener = listener;
    }

    /**
     * Gets the listener object that was passed into the Adapter through its constructor and cast
     * it to a given type.
     *
     * @return the listener casted to the given type or null if not listener was set into the Adapter.
     */
    @SuppressWarnings("unchecked")
    public <L> L getViewHolderListener() throws ClassCastException {
        return (L)mViewHolderListener;
    }

    public
    @LayoutRes
    int getItemViewHolderLayoutId() {
        return mItemViewHolderLayoutId;
    }

    /**
     * @return Returns the class of the {@link ItemViewHolder}'s implement
     */
    public Class<? extends ItemViewHolder> getItemViewHolderClazz() {
        return mItemViewHolderClazz;
    }
}

