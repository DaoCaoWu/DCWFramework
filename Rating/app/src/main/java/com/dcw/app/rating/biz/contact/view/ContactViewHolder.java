package com.dcw.app.rating.biz.contact.view;

import android.view.View;

import com.dcw.app.rating.ui.adapter.viewholder.ItemViewHolder;
import com.dcw.app.rating.biz.contact.model.ContactModel;
import com.dcw.app.rating.biz.contact.model.bean.Contact;

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
    public void onBindViewEvent(final ContactModel model, final int position) {
        mCatalogItemView.getItemView().itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactViewHolderListener listener = getListener();
                if (listener != null) {
                    listener.onItemViewClicked(v, model.getItem(position));
                }
            }
        });
    }

    @Override
    public void onBindData(ContactModel model, int position) {
        Contact contact = model.getItem(position);
        mCatalogItemView.getLLCatalog().setVisibility(model.getPositionForSection(model.getSectionForPosition(position)) == position ? View.VISIBLE : View.GONE);
        if (contact != null) {
            mCatalogItemView.update(contact.getSortKey(), null, contact.getName(), contact.getPhoneNum());
        }
    }

    public interface ContactViewHolderListener {

        void onItemViewClicked(View itemView, Contact contact);
    }
}
