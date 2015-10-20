package com.dcw.app.rating.biz.contact;

import com.dcw.app.rating.ui.mvc.Controller;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/10/21.
 */
public class ContactController extends Controller<ContactListView, ContactModel> {

    public ContactController(ContactListView view, ContactModel model) {
        super(view, model);
        getModel().addObserver(getView());
        view.getListView().setAdapter(new ContactAdapter(view.getContext(), getModel()));
    }
}
