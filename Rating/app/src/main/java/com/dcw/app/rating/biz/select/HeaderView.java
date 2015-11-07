package com.dcw.app.rating.biz.select;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.dcw.app.rating.R;
import com.dcw.app.rating.biz.contact.model.ContactModel;
import com.dcw.app.rating.biz.contact.model.bean.Contact;
import com.dcw.app.rating.biz.contact.view.CatalogItemView;
import com.dcw.app.rating.ui.adapter.viewholder.ItemViewHolder;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/11/6.
 */
public class HeaderView extends ItemViewHolder<ContactModel, Contact> {

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
        public void onBindData(final ContactModel model, final int position) {
                mItemView.update((String)model.getItemData(position), (String)model.getItemData(position),(String)model.getItemData(position),(String)model.getItemData(position));
//                Contact contact = model.getItem(position);
//                int section = model.getSectionForPosition(position);
//                if (position == model.getPositionForSection(section)) {
//                        mItemView.getLLCatalog().setVisibility(View.VISIBLE);
//                } else {
//                        mItemView.getLLCatalog().setVisibility(View.GONE);
//                }
//                if (contact != null) {
//                        mItemView.update(contact.getSortKey(), null, contact.getName(), contact.getPhoneNum());
//                        itemView.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                        model.onCheckBoxClicked(position, v);
//                                }
//                        });
//                        mCbSelect.setChecked(model.isSelected(position));
//                }
        }
}

