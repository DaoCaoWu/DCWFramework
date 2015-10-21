package com.dcw.app.rating.biz.contact;

import com.dcw.app.rating.R;
import com.dcw.app.rating.biz.MainActivity;
import com.dcw.app.rating.biz.toolbar.ToolbarController;
import com.dcw.app.rating.biz.toolbar.ToolbarModel;
import com.dcw.app.rating.ui.adapter.BaseFragmentWrapper;
import com.dcw.framework.view.annotation.InjectLayout;

import java.util.ArrayList;
import java.util.List;

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
        List<Contact> contacts = new ArrayList<Contact>();
        contacts.add(new Contact("adao12", "13570320927"));
        ContactController c = new ContactController((ContactListView) findViewById(R.id.root_view), new ContactModel(contacts));
        c.getModel().addItem(new Contact("cdao13", "13570320927"));
        c.getModel().addItem(new Contact("cdao13", "13570320927"));
        c.getModel().addItem(new Contact("zdao13", "13570320927"));
        c.getModel().addItem(new Contact("cdao13", "13570320927"));
        c.getModel().addItem(new Contact("xdao13", "13570320927"));
        c.getModel().addItem(new Contact("jdao13", "13570320927"));
        c.getModel().addItem(new Contact("edao13", "13570320927"));
        c.getModel().addItem(new Contact("qdao13", "13570320927"));
        c.getModel().addItem(new Contact("ydao13", "13570320927"));
        c.getModel().addItem(new Contact("ydao13", "13570320927"));
        c.getModel().addItem(new Contact("udao13", "13570320927"));
        c.getModel().addItem(new Contact("idao13", "13570320927"));
        c.getModel().addItem(new Contact("odao13", "13570320927"));
        c.getModel().addItem(new Contact("pdao13", "13570320927"));
        c.getModel().addItem(new Contact("ldao13", "13570320927"));
    }

    @Override
    public void initListeners() {

    }
}
