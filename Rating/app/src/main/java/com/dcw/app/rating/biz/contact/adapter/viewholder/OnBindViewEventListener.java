package com.dcw.app.rating.biz.contact.adapter.viewholder;

import com.dcw.app.rating.biz.contact.adapter.model.ListDataModel;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/10/25.
 */
public interface OnBindViewEventListener<M extends ListDataModel<D>, D> {

    void onBindViewEvent(M model, int position);
}
