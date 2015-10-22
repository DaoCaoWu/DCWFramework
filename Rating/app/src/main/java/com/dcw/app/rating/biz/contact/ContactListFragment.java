package com.dcw.app.rating.biz.contact;

import com.dcw.app.rating.R;
import com.dcw.app.rating.biz.MainActivity;
import com.dcw.app.rating.biz.toolbar.ToolbarController;
import com.dcw.app.rating.biz.toolbar.ToolbarModel;
import com.dcw.app.rating.ui.adapter.BaseFragmentWrapper;
import com.dcw.framework.view.annotation.InjectLayout;

@InjectLayout(R.layout.fragment_contact_list)
public class ContactListFragment extends BaseFragmentWrapper {

    @Override
    public Class getHostActivity() {
        return MainActivity.class;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initUI() {
        mToolbarController = new ToolbarController(findViewById(R.id.toolbar), new ToolbarModel(this.getClass().getSimpleName()));
        new ContactController((ContactListView) findViewById(R.id.root_view), new ContactModel());
    }

    @Override
    public void initListeners() {

    }
}
