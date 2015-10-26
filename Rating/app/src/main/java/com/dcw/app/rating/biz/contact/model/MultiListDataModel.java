package com.dcw.app.rating.biz.contact.model;

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
public abstract class MultiListDataModel<D> extends ListDataModel<D> {

    public abstract int getItemViewHolderLayoutId(int viewType);

    public abstract <M extends MultiListDataModel<D>> Class<? extends ItemViewHolder<M, D>> getItemViewHolderClazz(int viewType);

}
