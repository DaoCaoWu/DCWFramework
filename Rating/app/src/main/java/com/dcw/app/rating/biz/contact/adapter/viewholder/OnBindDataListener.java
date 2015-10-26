package com.dcw.app.rating.biz.contact.adapter.viewholder;

import com.dcw.app.rating.biz.contact.adapter.model.ListDataModel;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/10/25.
 */
public interface OnBindDataListener<M extends ListDataModel<D>, D> {

    void onBindData(M model, int position);
}
