package com.dcw.app.rating.biz.contact.adapter.viewholder;

import android.support.v7.widget.RecyclerView;

import com.dcw.app.rating.biz.contact.model.ListDataModel;

public final class RecyclerViewHolder<M extends ListDataModel<D>, D> extends RecyclerView.ViewHolder {

    public ItemViewHolder<M, D> mdItemViewHolder;

    public RecyclerViewHolder(ItemViewHolder<M, D> itemViewHolder) {
        super(itemViewHolder.itemView);
        mdItemViewHolder = itemViewHolder;
    }
}
