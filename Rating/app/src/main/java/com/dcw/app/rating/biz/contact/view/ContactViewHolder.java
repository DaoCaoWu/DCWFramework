package com.dcw.app.rating.biz.contact.view;

import android.view.View;

import com.dcw.app.rating.biz.contact.adapter.ListViewAdapter;
import com.dcw.app.rating.biz.contact.model.ContactModel;
import com.dcw.app.rating.biz.contact.model.bean.Contact;
import com.dcw.app.rating.biz.contact.view.viewholder.ItemViewHolder;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/10/25.
 */
public class ContactViewHolder extends ItemViewHolder<ContactModel, Contact> {
    CatalogItemView mCatalogItemView;

    public ContactViewHolder(View itemView) {
        super(itemView);
        mCatalogItemView = new CatalogItemView(itemView);
    }

    @Override
    public void onBindData(ListViewAdapter<ContactModel, Contact> adapter, ContactModel model, int position) {
        Contact contact = model.getItem(position);
        mCatalogItemView.getLLCatalog().setVisibility(model.getPositionForSection(model.getSectionForPosition(position)) == position ? View.VISIBLE : View.GONE);
        if (contact != null) {
            mCatalogItemView.update(contact.getSortKey(), null, contact.getName(), contact.getPhoneNum());
        }
    }
}
