package com.dcw.app.rating.biz.select;

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
 * @create 15/10/24
 */
public interface OnBindDataListener<M extends ListDataModel<D>, D> {

    void onBindData(AbsRecyclerViewAdapter adapter, M model, int position);
}
