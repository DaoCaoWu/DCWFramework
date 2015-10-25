package com.dcw.app.rating.biz.select;

import android.widget.TextView;

import com.dcw.app.rating.R;
import com.dcw.app.rating.biz.contact.adapter.RecyclerViewAdapter;
import com.dcw.app.rating.biz.contact.model.ContactModel;
import com.dcw.app.rating.biz.contact.model.bean.Contact;
import com.dcw.app.rating.biz.contact.view.SideBar;
import com.dcw.app.rating.biz.contact.view.StickyListView;
import com.dcw.app.rating.ui.mvc.Controller;
import com.dcw.app.rating.ui.mvc.core.Observable;
import com.dcw.app.rating.ui.mvc.core.Observer;

public class SelectContactController extends Controller<ContactRecyclerView, ContactModel> implements StickyListView.ViewListener, SideBar.OnTouchingLetterChangedListener, Observer {

    public SelectContactController(ContactRecyclerView view, ContactModel model) {
        super(view, model);
        RecyclerViewAdapter<ContactModel, Contact> adapter
                = new RecyclerViewAdapter<ContactModel, Contact>(
                getView().getContext(), getModel(), R.layout.item_view_select, SelectItemView.class);
        getView().getRecyclerView().setAdapter(adapter);
        getView().setViewListener(this);
        getModel().addObserver(this);
        getModel().addObserver(getView());
        getView().getIndexView().setTouchingLetterChangedListener(this);
        getModel().setSelectedMode(SelectModel.TYPE_SELECT_MULTI);
    }

    @Override
    public void onCatalogViewShouldUpdate(TextView catalogView, int firstVisibleItem) {
        catalogView.setText(getModel().getItem(firstVisibleItem).getSortKey());
    }

    @Override
    public void onTouchingLetterChanged(String s) {
        if (SideBar.b[0].equals(s)) {
            getView().getRecyclerView().scrollToPosition(0);
            return;
        }
        int position = getModel().getPositionForSection(s.charAt(0));
        if (position != -1) {
            getView().getRecyclerView().scrollToPosition(position);
        }
    }

    @Override
    public <T> void update(Observable observable, T data, Object... args) {
    }
}
