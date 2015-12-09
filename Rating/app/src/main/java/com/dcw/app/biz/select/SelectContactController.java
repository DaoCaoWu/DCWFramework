package com.dcw.app.biz.select;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.dcw.app.R;
import com.dcw.app.biz.contact.model.ContactModel;
import com.dcw.app.biz.contact.view.SideBar;
import com.dcw.app.biz.contact.view.StickyListView;
import cn.ninegame.library.component.adapter.RecyclerViewAdapter;
import cn.ninegame.library.component.adapter.model.select.SelectModel;
import cn.ninegame.library.component.adapter.viewholder.FixViewHolderBean;
import cn.ninegame.library.component.adapter.viewholder.ItemViewHolderBean;
import cn.ninegame.library.component.mvc.Controller;
import cn.ninegame.library.component.mvc.core.Observable;
import cn.ninegame.library.component.mvc.core.Observer;

public class SelectContactController extends Controller<ContactRecyclerView, ContactModel> implements StickyListView.ViewListener, SideBar.OnTouchingLetterChangedListener, SwipeRefreshLayout.OnRefreshListener, Observer {

    public SelectContactController(ContactRecyclerView view, ContactModel model) {
        super(view, model);
        final RecyclerViewAdapter<ContactModel> adapter = new RecyclerViewAdapter<ContactModel>(getView().getContext(), getModel());
        final HeaderView headerView = new HeaderView(LayoutInflater.from(getView().getContext()).inflate(R.layout.item_view_select, null));
        getView().postDelayed(new Runnable() {
            @Override
            public void run() {
//                getModel().addHeaderViewHolderBean(3, new FixViewHolderBean(R.layout.item_view_select, HeaderView.class, "哈哈0"));
//                getModel().addFooterViewHolderBean(5, new FixViewHolderBean(R.layout.item_view_select, HeaderView.class, "哈哈0"));
//                getModel().setFooterData(4, "哈哈1");
                adapter.removeHeaderView(headerView);
//                adapter.removeFooterView(headerView);
                adapter.notifyDataSetChanged();
//                getModel().removeHeaderViewHolderBean(2);
//                getModel().removeFooterViewHolderBean(4);
            }
        }, 3000);

//        getModel().addHeaderViewHolderBean(2, new FixViewHolderBean(R.layout.item_view_select, HeaderView.class, "哈哈1"));
//        getModel().addHeaderViewHolderBean(0, new FixViewHolderBean(R.layout.item_view_select, HeaderView.class, "哈哈2"));
//        getModel().addHeaderViewHolderBean(4, new FixViewHolderBean(R.layout.item_view_select, HeaderView.class, "哈哈3"));
//        getModel().addFooterViewHolderBean(4,new FixViewHolderBean(R.layout.item_view_select, HeaderView.class));
//        getModel().addFooterViewHolderBean(5,new FixViewHolderBean(R.layout.item_view_select, HeaderView.class, "abc"));
//        getModel().addItemViewHolderBean(0, new ItemViewHolderBean(R.layout.item_view_select, SelectItemView.class));
//        getModel().addItemViewHolderBean(1, new ItemViewHolderBean(R.layout.item_view_select_1, SelectItemView1.class));
//        getModel().setFooterData(0, "哈哈4");
//        getModel().setFooterData(4, "哈哈1");
        adapter.addHeaderView(headerView);
//        adapter.addFooterView(headerView);
        getView().getRecyclerView().setAdapter(adapter);
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
//        getModel().loadContactListAsyn(getView().getContext(), new TaskExecutor.RunnableCallback<List<Contact>>() {
//            @Override
//            public void onRun(List<Contact> data) {
//                getView().setRefreshing(false);
//            }
//        });
    }

    @Override
    public <T> void update(Observable observable, T data, Object... args) {
    }
}
