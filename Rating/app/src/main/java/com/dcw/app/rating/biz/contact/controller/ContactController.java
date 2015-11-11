package com.dcw.app.rating.biz.contact.controller;

import android.view.View;
import android.widget.TextView;

import com.dcw.app.rating.R;
import com.dcw.app.rating.biz.contact.model.ContactModel;
import com.dcw.app.rating.biz.contact.model.bean.Contact;
import com.dcw.app.rating.biz.contact.view.ContactViewHolder;
import com.dcw.app.rating.biz.contact.view.SideBar;
import com.dcw.app.rating.biz.contact.view.StickyListView;
import com.dcw.app.rating.biz.select.SelectItemView;
import com.dcw.app.rating.biz.select.SelectItemView1;
import cn.ninegame.library.component.adapter.ListViewAdapter;
import cn.ninegame.library.component.adapter.viewholder.ItemViewHolderBean;
import com.dcw.app.ui.framework.ToastManager;
import cn.ninegame.library.component.mvc.Controller;
import cn.ninegame.library.component.mvc.core.Observable;
import cn.ninegame.library.component.mvc.core.Observer;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/10/21.
 */
public class ContactController extends Controller<StickyListView, ContactModel> implements StickyListView.ViewListener, SideBar.OnTouchingLetterChangedListener, ContactViewHolder.ContactViewHolderListener, Observer {

    public ContactController(StickyListView view, ContactModel model) {
        super(view, model);
        getModel().addItemViewHolderBean(0, new ItemViewHolderBean(R.layout.item_view_select, SelectItemView.class));
        getModel().addItemViewHolderBean(1, new ItemViewHolderBean(R.layout.item_view_select_1, SelectItemView1.class));
        getView().getListView().setAdapter(new ListViewAdapter<ContactModel>(
                getView().getContext(), getModel()));
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
    public void onItemViewClicked(View itemView, Contact contact) {
        ToastManager.getInstance().showToast(contact.getName());
    }

    @Override
    public <T> void update(Observable observable, T data, Object... args) {
    }
}
