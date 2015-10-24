package com.dcw.app.rating.biz.select;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dcw.app.rating.biz.contact.model.ListDataModel;

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
 * @create 15/10/23
 */
public abstract class AbsRecyclerViewHolder<M extends ListDataModel<D>, D> extends RecyclerView.ViewHolder implements OnBindDataListener<M, D> {

    public AbsRecyclerViewHolder(View itemView) {
        super(itemView);
    }
}
