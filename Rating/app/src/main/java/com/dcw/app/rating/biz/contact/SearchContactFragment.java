package com.dcw.app.rating.biz.contact;

import android.widget.AbsListView;

import com.dcw.app.rating.R;
import com.dcw.app.rating.biz.contact.controller.ContactController;
import com.dcw.app.rating.biz.contact.model.ContactModel;
import com.dcw.app.rating.biz.contact.view.StickyListView;
import com.dcw.app.rating.biz.search.SearchFragment;
import com.dcw.framework.view.annotation.InjectLayout;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/10/22.
 */
@InjectLayout(R.layout.fragment_contact_list)
public class SearchContactFragment extends SearchFragment implements AbsListView.OnScrollListener {

    ContactModel mCacheModel;
    ContactController mController;

    @Override
    public void initUI() {
        super.initUI();
        mCacheModel = new ContactModel();
        mCacheModel.loadContactListAsyn(getActivity());
        mController = new ContactController((StickyListView) findViewById(R.id.root_view), new ContactModel());
        mController.getView().setOnScrollListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
//        mController.searchContacts(mCacheModel, query);
        return super.onQueryTextSubmit(query);
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mController.searchContacts(mCacheModel, newText);
        return super.onQueryTextChange(newText);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_TOUCH_SCROLL) {
            hideKeyboard();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }
}
