package com.dcw.app.biz.select;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.dcw.app.R;
import com.dcw.app.biz.contact.SearchContactFragment;
import com.dcw.app.biz.contact.model.ContactModel;
import com.dcw.app.biz.contact.model.bean.Contact;
import com.dcw.app.biz.toolbar.ToolbarController;
import com.dcw.app.biz.toolbar.ToolbarModel;
import com.dcw.app.ui.loadmore.LoadMoreContainer;
import com.dcw.app.ui.loadmore.LoadMoreHandler;
import com.dcw.app.ui.loadmore.LoadMoreRecyclerViewContainer;
import com.dcw.app.util.TaskExecutor;
import com.dcw.framework.view.annotation.InjectLayout;

import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.ninegame.framework.adapter.BaseFragmentWrapper;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

@InjectLayout(R.layout.fragment_contact_recycler_view)
public class SelectFragment extends BaseFragmentWrapper implements MenuItem.OnMenuItemClickListener {

    @Override
    public void initData() {

    }

    @Override
    public void initUI() {
        ToolbarModel model = new ToolbarModel(this.getClass().getSimpleName(), R.menu.menu_home, true);
        mToolbarController = new ToolbarController(findViewById(R.id.toolbar), model);
        final SelectContactController scc = new SelectContactController((ContactRecyclerView) findViewById(R.id.root_view), new ContactModel());
        scc.getModel().loadContactListAsyn(getActivity(), new TaskExecutor.RunnableCallback<List<Contact>>() {
            @Override
            public void onRun(List<Contact> data) {
                scc.getModel().setMaxSelectNum(scc.getModel().getCount());
            }
        });
        //first load
        Observable.timer(2, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                ((LoadMoreRecyclerViewContainer) scc.getView().findViewById(R.id.LoadMoreRecyclerViewContainer)).loadMoreFinish(false, true);
            }
        });
        //next Page
        ((LoadMoreRecyclerViewContainer)scc.getView().findViewById(R.id.LoadMoreRecyclerViewContainer)).setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(final LoadMoreContainer loadMoreContainer) {
                Observable.timer(6, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        loadMoreContainer.loadMoreFinish(false, true);
                    }
                });
            }
        });
//        ((LoadMoreRecyclerViewContainer)scc.getView().findViewById(R.id.LoadMoreRecyclerViewContainer)).useDefaultFooter();
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        final MenuItem searchItem = menu.findItem(R.id.btn_right);
        searchItem.setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btn_right:
                startFragment(SearchContactFragment.class);
                break;
        }
        return false;
    }
}
