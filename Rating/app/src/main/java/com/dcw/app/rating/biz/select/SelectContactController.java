package com.dcw.app.rating.biz.select;

import android.widget.TextView;

import com.dcw.app.rating.R;
import com.dcw.app.rating.biz.contact.adapter.ContactAdapter;
import com.dcw.app.rating.biz.contact.controller.LetterIndexController;
import com.dcw.app.rating.biz.contact.model.ContactModel;
import com.dcw.app.rating.biz.contact.model.bean.Contact;
import com.dcw.app.rating.biz.contact.view.SideBar;
import com.dcw.app.rating.biz.contact.view.StickyListView;
import com.dcw.app.rating.ui.mvc.Controller;
import com.dcw.app.rating.ui.mvc.core.Observable;
import com.dcw.app.rating.ui.mvc.core.Observer;

/**
 * <p>Title: ucweb</p>
 * <p/>
 * <p>Description: </p>
 * ......
 * <p>Copyright: Copyright (c) 2015</p>
 * <p/>
 * <p>Company: ucweb.com</p>
 *
 * @author JiaYing.Cheng
 * @version 1.0
 * @email adao12.vip@gmail.com
 * @create 15/10/24
 */
public class SelectContactController extends Controller<ContactRecyclerView, ContactModel> implements StickyListView.ViewListener, SideBar.OnTouchingLetterChangedListener, Observer {

    public SelectContactController(ContactRecyclerView view, ContactModel model) {
        super(view, model);
        new LetterIndexController(getView().getIndexView(), model);
        AbsRecyclerViewAdapter<Contact, ContactModel, SelectItemView> adapter
                = new AbsRecyclerViewAdapter<Contact, ContactModel, SelectItemView>(
                getView().getContext(), getModel(), R.layout.item_view_select, SelectItemView.class);
        getView().getRecyclerView().setAdapter(adapter);
        getView().setViewListener(this);
        getModel().addObserver(this);
        getModel().addObserver(adapter);
        getModel().addObserver(getView());
        getModel().addObserver(getView().getIndexView());
        getView().getIndexView().setTouchingLetterChangedListener(this);
    }

    @Override
    public void onCatalogViewShouldUpdate(TextView catalogView, int firstVisibleItem) {
        catalogView.setText(getModel().getItem(firstVisibleItem).getSortKey());
    }

    @Override
    public void onTouchingLetterChanged(String s) {
        //该字母首次出现的位置
        int position = getModel().getPositionForSection(s.charAt(0));
        if (position != -1) {
            getView().getRecyclerView().scrollToPosition(position);
        }
    }

    @Override
    public <T> void update(Observable observable, T data, Object... args) {
    }
}
