package com.dcw.app.rating.biz.contact.adapter.viewholder;

import android.support.v7.widget.RecyclerView;

import com.dcw.app.rating.biz.contact.adapter.model.ListDataModel;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/10/25.
 */
public final class RecyclerViewHolder<M extends ListDataModel<D>, D> extends RecyclerView.ViewHolder {

    public ItemViewHolder<M, D> mdItemViewHolder;

    public RecyclerViewHolder(ItemViewHolder<M, D> itemViewHolder) {
        super(itemViewHolder.itemView);
        mdItemViewHolder = itemViewHolder;
    }
}
