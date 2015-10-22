package com.dcw.app.rating.biz.contact.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dcw.app.rating.R;
import com.dcw.app.rating.biz.contact.model.ContactModel;
import com.dcw.app.rating.biz.contact.model.bean.Contact;
import com.dcw.app.rating.biz.contact.view.CatalogItemView;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/10/21.
 */
public class ContactAdapter extends AbsListAdapter<Contact, ContactModel> {

    public ContactAdapter(Context context, ContactModel model) {
        super(context, model);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CatalogItemView holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.layout_list_item_catalog, null);
            holder = new CatalogItemView(convertView);
            convertView.setTag(holder);
        } else {
            holder = (CatalogItemView) convertView.getTag();
        }

        Contact contact = getItem(position);
        int section = getModel().getSectionForPosition(position);
        if (position == getModel().getPositionForSection(section)) {
            holder.getLLCatalog().setVisibility(View.VISIBLE);
        } else {
            holder.getLLCatalog().setVisibility(View.GONE);
        }
        if (contact != null) {
            holder.update(contact.getSortKey(), null, contact.getName(), contact.getPhoneNum());
        }
        return convertView;
    }
}
