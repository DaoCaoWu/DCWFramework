package com.dcw.app.rating.biz.contact.model;

import android.support.annotation.LayoutRes;

import com.dcw.app.rating.biz.contact.adapter.viewholder.ItemViewHolder;

/**
 * <p>Title: ucweb</p>
 * <p/>
 * <p>Description: </p>
 * ......
 * <p>Copyright: Copyright (c) 2015</p>
 * <p/>
 * <p>Company: ucweb.com</p>
 *
 * @author JiaYing.Cheng
 * @version 1.0
 * @email adao12.vip@gmail.com
 * @create 15/10/26
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
