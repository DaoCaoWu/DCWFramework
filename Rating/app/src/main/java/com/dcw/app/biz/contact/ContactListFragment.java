package com.dcw.app.biz.contact;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.dcw.app.R;
import com.dcw.app.biz.MainActivity;
import com.dcw.app.biz.contact.controller.ContactController;
import com.dcw.app.biz.contact.model.ContactModel;
import com.dcw.app.biz.contact.view.StickyListView;
import com.dcw.app.biz.toolbar.ToolbarController;
import com.dcw.app.biz.toolbar.ToolbarModel;
import cn.ninegame.framework.adapter.BaseFragmentWrapper;
import com.dcw.framework.view.annotation.InjectLayout;

@InjectLayout(R.layout.fragment_contact_list)
public class ContactListFragment extends BaseFragmentWrapper implements MenuItem.OnMenuItemClickListener {

    @Override
    public Class getHostActivity() {
        return MainActivity.class;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initUI() {
        ToolbarModel model = new ToolbarModel(this.getClass().getSimpleName(), R.menu.menu_home, true);
        mToolbarController = new ToolbarController(findViewById(R.id.toolbar), model);
        new ContactController((StickyListView) findViewById(R.id.root_view), new ContactModel()).getModel().loadContactListAsyn(getActivity());
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
