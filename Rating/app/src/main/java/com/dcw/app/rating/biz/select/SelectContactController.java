package com.dcw.app.rating.biz.select;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.dcw.app.rating.R;
import com.dcw.app.rating.ui.adapter.RecyclerViewAdapter;
import com.dcw.app.rating.biz.contact.model.ContactModel;
import com.dcw.app.rating.ui.adapter.model.ListDataModel;
import com.dcw.app.rating.ui.adapter.viewholder.FixViewHolderBean;
import com.dcw.app.rating.ui.adapter.viewholder.ItemViewHolderBean;
import com.dcw.app.rating.biz.contact.model.bean.Contact;
import com.dcw.app.rating.biz.contact.view.SideBar;
import com.dcw.app.rating.biz.contact.view.StickyListView;
import com.dcw.app.rating.ui.mvc.Controller;
import com.dcw.app.rating.ui.mvc.core.Observable;
import com.dcw.app.rating.ui.mvc.core.Observer;
import com.dcw.app.rating.util.TaskExecutor;

import java.util.List;

public class SelectContactController extends Controller<ContactRecyclerView, ContactModel> implements StickyListView.ViewListener, SideBar.OnTouchingLetterChangedListener, SwipeRefreshLayout.OnRefreshListener, Observer {

    public SelectContactController(ContactRecyclerView view, ContactModel model) {
        super(view, model);
        RecyclerViewAdapter<ContactModel, Contact> adapter
                = new RecyclerViewAdapter<ContactModel, Contact>(
                getView().getContext(), getModel(), R.layout.item_view_select, SelectItemView.class);
        getModel().addHeaderViewHolderBean(0, new FixViewHolderBean<ContactModel, Contact>(R.layout.item_view_select, HeaderView.class, "abc", null));
        getModel().addHeaderViewHolderBean(1, new FixViewHolderBean<ContactModel, Contact>(R.layout.item_view_select, HeaderView.class, "bbc", null));
//        getModel().addFooterViewHolderBean(0, new FixViewHolderBean<ContactModel, Contact>(R.layout.item_view_select, HeaderView.class, "bbc", null));
//        getModel().addFooterViewHolderBean(1, new FixViewHolderBean<ContactModel, Contact>(R.layout.item_view_select, HeaderView.class, "abc", null));
//        getModel().addItemViewHolderBean(0, new ItemViewHolderBean<ContactModel, Contact>(R.layout.item_view_select, SelectItemView.class));
//        getModel().addItemViewHolderBean(1, new ItemViewHolderBean<ContactModel, Contact>(R.layout.item_view_select_1, SelectItemView1.class));
        getView().getRecyclerView().setAdapter(new RecyclerViewAdapter<ContactModel, Contact>(getView().getContext(), getModel()));
        getView().setViewListener(this);
        getView().setColorSchemeResources(R.color.holo_blue_light, R.color.holo_red_light, R.color.holo_orange_light, R.color.holo_green_light);
        getView().setOnRefreshListener(this);
        getModel().addObserver(this);
        getModel().addObserver(getView());
        getView().getIndexView().setTouchingLetterChangedListener(this);
        getModel().setSelectedMode(SelectModel.TYPE_SELECT_MULTI);
    }

    @Override
    public void onCatalogViewShouldUpdate(TextView catalogView, int firstVisibleItem) {
        catalogView.setText(getModel().getItem(firstVisibleItem) == null ? "" : getModel().getItem(firstVisibleItem).getSortKey());
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
    public void onRefresh() {
        getModel().loadContactListAsyn(getView().getContext(), new TaskExecutor.RunnableCallback<List<Contact>>() {
            @Override
            public void onRun(List<Contact> data) {
                getView().setRefreshing(false);
            }
        });
    }

    @Override
    public <T> void update(Observable observable, T data, Object... args) {
    }
}
