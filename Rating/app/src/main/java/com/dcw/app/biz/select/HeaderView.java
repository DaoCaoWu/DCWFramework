package com.dcw.app.biz.select;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.dcw.app.R;
import com.dcw.app.biz.contact.model.ContactModel;
import com.dcw.app.biz.contact.view.CatalogItemView;
import cn.ninegame.library.component.adapter.viewholder.ItemViewHolder;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/11/6.
 */
public class HeaderView extends ItemViewHolder<ContactModel> {

    CatalogItemView mItemView;
    CheckBox mCbSelect;

    public HeaderView(View itemView) {
        super(itemView);
        View convertView = itemView.findViewById(R.id.container);
        mCbSelect = (CheckBox) itemView.findViewById(R.id.cb_select);
        View containerView = LayoutInflater.from(itemView.getContext()).inflate(R.layout.layout_list_item_catalog, (ViewGroup) convertView);
        mItemView = new CatalogItemView(containerView);
    }

    @Override
    public void onBindData(ContactModel model, int position) {
        String s = "aadafs";
        mItemView.update(s, s, s, s);
        getHelper().setTextColor(R.id.tv_title, R.color.color_99);
    }
}

