package com.dcw.app.rating.biz.search;

import com.dcw.app.rating.R;
import com.dcw.app.rating.biz.MainActivity;
import com.dcw.app.rating.biz.toolbar.SearchToolbar;
import com.dcw.app.rating.ui.adapter.BaseFragmentWrapper;
import com.dcw.framework.view.annotation.InjectLayout;

/**
 * Created by adao12 on 2015/8/16.
 */
@InjectLayout(R.layout.fragment_search)
public class SearchFragment extends BaseFragmentWrapper<SearchToolbar> {

    @Override
    public Class getHostActivity() {
        return MainActivity.class;
    }

    @Override
    public Class getToolbar() {
        return SearchToolbar.class;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initUI() {

    }

    @Override
    public void initListeners() {

    }
}
