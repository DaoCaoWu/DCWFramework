package com.dcw.app.rating.biz.contact.view.viewholder;

import android.view.View;

import com.dcw.app.rating.biz.contact.adapter.ListViewAdapter;
import com.dcw.app.rating.biz.contact.model.ListDataModel;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/10/25.
 */
public abstract class ItemViewHolder<M extends ListDataModel<D>, D> implements ListViewAdapter.OnBindDataListener<M, D> {

    public final View itemView;
    ItemViewInfo mItemViewInfo;

    public ItemViewHolder(View itemView) {
        if (itemView == null) {
            throw new IllegalArgumentException("itemView may not be null");
        }
        this.itemView = itemView;
    }

    public ItemViewInfo getItemViewInfo() {
        return mItemViewInfo;
    }

    public void setItemViewInfo(ItemViewInfo itemViewInfo) {
        mItemViewInfo = itemViewInfo;
    }
}
