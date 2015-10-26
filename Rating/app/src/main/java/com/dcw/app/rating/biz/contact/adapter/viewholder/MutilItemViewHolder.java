package com.dcw.app.rating.biz.contact.adapter.viewholder;

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
 * @create 15/10/26
 */
public abstract class MutilItemViewHolder<M extends ListDataModel<D>, D> extends ItemViewHolder<M, D> implements OnBindViewEventListener<M, D> {

    public MutilItemViewHolder(View itemView) {
        super(itemView);
    }
}
