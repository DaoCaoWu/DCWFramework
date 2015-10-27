package com.dcw.app.rating.biz.select;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.dcw.app.rating.R;
import com.dcw.app.rating.biz.MainActivity;
import com.dcw.app.rating.biz.contact.SearchContactFragment;
import com.dcw.app.rating.biz.contact.model.ContactModel;
import com.dcw.app.rating.biz.contact.model.bean.Contact;
import com.dcw.app.rating.biz.toolbar.ToolbarController;
import com.dcw.app.rating.biz.toolbar.ToolbarModel;
import com.dcw.app.rating.ui.framework.BaseFragmentWrapper;
import com.dcw.app.rating.util.TaskExecutor;
import com.dcw.framework.view.annotation.InjectLayout;

import java.util.List;

@InjectLayout(R.layout.fragment_contact_recycler_view)
public class SelectFragment extends BaseFragmentWrapper implements MenuItem.OnMenuItemClickListener {

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
        final SelectContactController scc = new SelectContactController((ContactRecyclerView) findViewById(R.id.root_view), new ContactModel());
        scc.getModel().loadContactListAsyn(getActivity(), new TaskExecutor.RunnableCallback<List<Contact>>() {
            @Override
            public void onRun(List<Contact> data) {
                scc.getModel().setMaxSelectNum(scc.getModel().getCount());
            }
        });
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
