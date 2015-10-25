package com.dcw.app.rating.biz.contact.controller;

import android.widget.TextView;

import com.dcw.app.rating.R;
import com.dcw.app.rating.biz.contact.adapter.ListViewAdapter;
import com.dcw.app.rating.biz.contact.model.ContactModel;
import com.dcw.app.rating.biz.contact.model.bean.Contact;
import com.dcw.app.rating.biz.contact.view.ContactViewHolder;
import com.dcw.app.rating.biz.contact.view.SideBar;
import com.dcw.app.rating.biz.contact.view.StickyListView;
import com.dcw.app.rating.ui.mvc.Controller;
import com.dcw.app.rating.ui.mvc.core.Observable;
import com.dcw.app.rating.ui.mvc.core.Observer;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/10/21.
 */
public class ContactController extends Controller<StickyListView, ContactModel> implements StickyListView.ViewListener, SideBar.OnTouchingLetterChangedListener, Observer {

    public ContactController(StickyListView view, ContactModel model) {
        super(view, model);
        getView().getListView().setAdapter(new ListViewAdapter<ContactModel, Contact>(
                getView().getContext(), getModel(), R.layout.layout_list_item_catalog, ContactViewHolder.class));
        getView().setViewListener(this);
        getModel().addObserver(this);
        getModel().addObserver(getView());
        getView().getIndexView().setTouchingLetterChangedListener(this);
    }

    @Override
    public void onCatalogViewShouldUpdate(TextView catalogView, int firstVisibleItem) {
        catalogView.setText(((Contact) getView().getListView().getItemAtPosition(firstVisibleItem)).getSortKey());
    }

    @Override
    public void onTouchingLetterChanged(String s) {
        if (SideBar.b[0].equals(s)) {
            getView().getListView().setSelection(0);
            return;
        }
        int position = getModel().getPositionForSection(s.charAt(0));
        if (position != -1) {
            getView().getListView().setSelection(position + getView().getListView().getHeaderViewsCount());
        }
    }

    @Override
    public <T> void update(Observable observable, T data, Object... args) {
    }
}
