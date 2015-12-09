package cn.ninegame.library.component.deprecated;

import android.support.annotation.LayoutRes;

import cn.ninegame.library.component.adapter.viewholder.ItemViewHolder;
import cn.ninegame.library.component.adapter.viewholder.ItemViewHolderBean;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/11/6.
 */
public class FixViewHolderBean extends ItemViewHolderBean {

    /**
     * the data binding the {@link ItemViewHolder}, and you can get it by {@link RecyclerDataModel#getHeaderOrFooterItem(int)}
     */
    private Object mData;

    /**
     * @param itemViewHolderLayoutId the view's layoutId that would be used to inflating item view
     * @param itemViewHolderClazz the class of the {@link ItemViewHolder}'s implement
     */
    public FixViewHolderBean(@LayoutRes int itemViewHolderLayoutId, Class<? extends ItemViewHolder> itemViewHolderClazz) {
        super(itemViewHolderLayoutId, itemViewHolderClazz);
    }

    /**
     * @param itemViewHolderLayoutId the view's layoutId that would be used to inflating item view
     * @param itemViewHolderClazz the class of the {@link ItemViewHolder}'s implement
     * @param data the data binding the {@link ItemViewHolder}, and you can get it by {@link RecyclerDataModel#getHeaderOrFooterItem(int)}
     * @param <D>  the class type of data
     */
    public <D> FixViewHolderBean(@LayoutRes int itemViewHolderLayoutId, Class<? extends ItemViewHolder> itemViewHolderClazz, D data) {
        this(itemViewHolderLayoutId, itemViewHolderClazz, data, null);
    }

    /**
     * @param itemViewHolderLayoutId the view's layoutId that would be used to inflating item view
     * @param itemViewHolderClazz the class of the {@link ItemViewHolder}'s implement
     * @param data the data binding the {@link ItemViewHolder}, and you can get it by {@link RecyclerDataModel#getHeaderOrFooterItem(int)}
     * @param listener the listener of events that views included in {@link ItemViewHolder} dispatched
     * @param <D> the class type of data
     * @param <L> the class type of the class implement listener
     */
    public <D, L> FixViewHolderBean(@LayoutRes int itemViewHolderLayoutId, Class<? extends ItemViewHolder> itemViewHolderClazz, D data, L listener) {
        super(itemViewHolderLayoutId, itemViewHolderClazz, listener);
        mData = data;
    }

    /**
     * the data binding the {@link ItemViewHolder}, and you can get it by {@link RecyclerDataModel#getHeaderOrFooterItem(int)}
     * @param <D> the class type of data
     * @return the specific data
     */
    @SuppressWarnings("unchecked")
    public <D> D getData() {
        return (D)mData;
    }

    /**
     *
     * @param data the data binding the {@link ItemViewHolder}, and you can get it by {@link RecyclerDataModel#getHeaderOrFooterItem(int)}
     * @param <D> the class type of data
     */
    public <D> void setData(D data) {
        mData = data;
    }
}
