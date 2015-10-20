package com.dcw.app.rating.biz.contact;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dcw.app.rating.R;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/10/21.
 */
public class ContactAdapter extends AbsListAdapter<Contact> {

    public ContactAdapter(Context context, ListDataModel<Contact> model) {
        super(context, model);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemView holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.layout_list_item_contact, null);
            holder = new ItemView(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ItemView) convertView.getTag();
        }

        Contact contact = getItem(position);
        if (contact != null) {
            holder.update(null, contact.getName(), contact.getPhoneNum());
        }
        return convertView;
    }
}
