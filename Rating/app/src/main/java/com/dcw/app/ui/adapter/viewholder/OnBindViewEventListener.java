package com.dcw.app.ui.adapter.viewholder;

import com.dcw.app.ui.adapter.model.ListDataModel;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/10/25.
 */
public interface OnBindViewEventListener<M extends ListDataModel> {

    void onBindViewEvent(M model, int position);
}
