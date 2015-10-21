package com.dcw.app.rating.biz.contact;

import android.widget.TextView;

import com.dcw.app.rating.ui.mvc.Controller;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/10/21.
 */
public class ContactController extends Controller<ContactListView, ContactModel> implements ContactListView.ViewListener {

    public ContactController(ContactListView view, ContactModel model) {
        super(view, model);
        new LetterIndexController(getView().getIndexView(), model);
        getModel().addObserver(getView());
        getModel().addObserver(getView().getIndexView());
        getView().setViewListener(this);
        view.getListView().setAdapter(new ContactAdapter(view.getContext(), getModel()));
    }

    @Override
    public void onCatalogViewShouldUpdate(TextView catalogView, int firstVisibleItem) {
        catalogView.setText(((Contact) getView().getListView().getItemAtPosition(firstVisibleItem)).getSortKey());
    }
}
