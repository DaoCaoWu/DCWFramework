package com.dcw.app.rating.biz.select;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.dcw.app.rating.R;
import com.dcw.app.rating.biz.contact.model.ContactModel;
import com.dcw.app.rating.biz.contact.model.bean.Contact;
import com.dcw.app.rating.biz.contact.view.CatalogItemView;
import com.dcw.app.rating.biz.contact.view.ItemView;

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
public class SelectItemView extends AbsRecyclerViewHolder<ContactModel, Contact> {

    CatalogItemView mItemView;
    CheckBox mCbSelect;

    public SelectItemView(View itemView) {
        super(itemView);
        View convertView = itemView.findViewById(R.id.container);
        mCbSelect = (CheckBox)itemView.findViewById(R.id.cb_select);
        View containerView = LayoutInflater.from(itemView.getContext()).inflate(R.layout.layout_list_item_catalog, (ViewGroup)convertView);
        mItemView = new CatalogItemView(containerView);
    }

    @Override
    public void onBindData(AbsRecyclerViewAdapter adapter, final ContactModel model, final int position) {
        Contact contact = model.getItem(position);
        int section = model.getSectionForPosition(position);
        if (position == model.getPositionForSection(section)) {
            mItemView.getLLCatalog().setVisibility(View.VISIBLE);
        } else {
            mItemView.getLLCatalog().setVisibility(View.GONE);
        }
        if (contact != null) {
            mItemView.update(contact.getSortKey(), null, contact.getName(), contact.getPhoneNum());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    model.onCheckBoxClicked(position, v);
                }
            });
            mCbSelect.setChecked(model.isSelected(position));
        }
    }
}
