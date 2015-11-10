package com.dcw.app.ui.adapter.viewholder;

import android.view.View;

import com.dcw.app.ui.adapter.model.ListDataModel;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/11/5.
 */
public class RecyclerFooterViewHolder<M extends ListDataModel<D>, D> extends ItemViewHolder<M> {

    public RecyclerFooterViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void onBindData(M model, int position) {

    }
}
