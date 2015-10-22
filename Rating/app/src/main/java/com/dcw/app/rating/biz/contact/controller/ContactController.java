package com.dcw.app.rating.biz.contact.controller;

import android.widget.TextView;

import com.dcw.app.rating.biz.contact.adapter.ContactAdapter;
import com.dcw.app.rating.biz.contact.model.ContactModel;
import com.dcw.app.rating.biz.contact.model.bean.Contact;
import com.dcw.app.rating.biz.contact.view.StickyListView;
import com.dcw.app.rating.ui.mvc.Controller;
import com.dcw.app.rating.ui.mvc.core.Observable;
import com.dcw.app.rating.ui.mvc.core.Observer;
import com.dcw.app.rating.util.TaskExecutor;

import java.util.List;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/10/21.
 */
public class ContactController extends Controller<StickyListView, ContactModel> implements StickyListView.ViewListener, Observer {

    public ContactController(StickyListView view, ContactModel model) {
        super(view, model);
        new LetterIndexController(getView().getIndexView(), model);
        getModel().addObserver(this);
        getModel().addObserver(getView());
        getModel().addObserver(getView().getIndexView());
        getView().setViewListener(this);
    }

    public void loadContacts() {
        getModel().getContactListAsyn(getView().getContext(), new TaskExecutor.RunnableCallback<List<Contact>>() {
            @Override
            public void onRun(List<Contact> contacts) {
                getModel().setDataList(contacts);
                getView().getListView().setAdapter(new ContactAdapter(getView().getContext(), getModel()));
            }
        });
    }

    public void searchContacts(ContactModel model, String keyword) {
        getModel().search(model, keyword, new TaskExecutor.RunnableCallback<List<Contact>>() {
            @Override
            public void onRun(List<Contact> contacts) {
                getModel().setDataList(contacts);
                getView().getListView().setAdapter(new ContactAdapter(getView().getContext(), getModel()));
            }
        });
    }

    @Override
    public void onCatalogViewShouldUpdate(TextView catalogView, int firstVisibleItem) {
        catalogView.setText(((Contact) getView().getListView().getItemAtPosition(firstVisibleItem)).getSortKey());
    }

    @Override
    public <T> void update(Observable observable, T data, Object... args) {
    }
}
