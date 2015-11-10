package com.dcw.app.ui.adapter.viewholder;

import com.dcw.app.ui.adapter.model.ListDataModel;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/10/25.
 */
public interface OnBindDataListener<M extends ListDataModel> {

    void onBindData(M model, int position);
}
