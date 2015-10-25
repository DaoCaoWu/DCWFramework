package com.dcw.app.rating.biz.contact.view.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dcw.app.rating.biz.contact.adapter.RecyclerViewAdapter;
import com.dcw.app.rating.biz.contact.model.ListDataModel;

public abstract class RecyclerViewHolder<M extends ListDataModel<D>, D> extends RecyclerView.ViewHolder implements RecyclerViewAdapter.OnBindDataListener<M, D> {

    public RecyclerViewHolder(View itemView) {
        super(itemView);
    }
}
