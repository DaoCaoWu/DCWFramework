package com.dcw.app.rating.biz.contact;

import android.widget.TextView;

import com.dcw.app.rating.ui.mvc.Controller;
import com.dcw.app.rating.ui.mvc.core.Observable;
import com.dcw.app.rating.ui.mvc.core.Observer;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/10/21.
 */
public class ContactController extends Controller<ContactListView, ContactModel> implements ContactListView.ViewListener, Observer {

    public ContactController(ContactListView view, ContactModel model) {
        super(view, model);
        new LetterIndexController(getView().getIndexView(), model);
        getModel().addObserver(this);
        getModel().addObserver(getView());
        getModel().addObserver(getView().getIndexView());
        getView().setViewListener(this);

        getModel().getContactListAsyn(getView().getContext(), new Callback<List<Contact>>() {
            @Override
            public void success(List<Contact> contacts, Response response) {
                getModel().setDataList(contacts);
                getView().getListView().setAdapter(new ContactAdapter(getView().getContext(), getModel()));
            }

            @Override
            public void failure(RetrofitError error) {

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
