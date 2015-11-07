package com.dcw.app.rating.ui.adapter.viewholder;

import android.support.v7.widget.RecyclerView;

import com.dcw.app.rating.ui.adapter.model.ListDataModel;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/10/25.
 */
public final class RecyclerViewHolder<M extends ListDataModel> extends RecyclerView.ViewHolder {

    public ItemViewHolder<M> mdItemViewHolder;

    public RecyclerViewHolder(ItemViewHolder<M> itemViewHolder) {
        super(itemViewHolder.itemView);
        mdItemViewHolder = itemViewHolder;
    }
}
