package com.dcw.app.presentation.select;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.dcw.app.R;
import com.dcw.app.presentation.contact.model.ContactModel;
import com.dcw.app.presentation.contact.model.bean.Contact;
import com.dcw.app.presentation.contact.view.CatalogItemView;
import cn.ninegame.library.component.adapter.viewholder.ItemViewHolder;

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
public class SelectItemView1 extends ItemViewHolder<ContactModel> {

    CatalogItemView mItemView;
    CheckBox mCbSelect;

    public SelectItemView1(View itemView) {
        super(itemView);
        View convertView = itemView.findViewById(R.id.container);
        mCbSelect = (CheckBox) itemView.findViewById(R.id.cb_select1);
        View containerView = LayoutInflater.from(itemView.getContext()).inflate(R.layout.layout_list_item_catalog, (ViewGroup) convertView);
        mItemView = new CatalogItemView(containerView);
    }

    @Override
    public void onBindData(final ContactModel model, final int position) {
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
