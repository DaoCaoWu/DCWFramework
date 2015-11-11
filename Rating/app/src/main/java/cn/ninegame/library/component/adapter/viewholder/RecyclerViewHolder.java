package cn.ninegame.library.component.adapter.viewholder;

import android.support.v7.widget.RecyclerView;

import cn.ninegame.library.component.adapter.model.ListDataModel;

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
