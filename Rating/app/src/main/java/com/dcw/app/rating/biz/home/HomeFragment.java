package com.dcw.app.rating.biz.home;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.dcw.app.rating.R;
import com.dcw.app.rating.biz.MainActivity;
import com.dcw.app.rating.biz.account.LoginFragment;
import com.dcw.app.rating.biz.contact.ContactListFragment;
import com.dcw.app.rating.biz.search.SearchFragment;
import com.dcw.app.rating.biz.select.SelectFragment;
import com.dcw.app.rating.biz.test.AbsListFragment;
import com.dcw.app.rating.biz.test.RichTextFragment;
import com.dcw.app.rating.biz.test.StateViewFragment;
import com.dcw.app.rating.biz.toolbar.DrawerBarController;
import com.dcw.app.rating.biz.toolbar.ToolbarController;
import com.dcw.app.rating.biz.toolbar.ToolbarModel;
import com.dcw.app.ui.framework.FragmentAdapter;
import cn.ninegame.library.component.adapter.model.ListDataModel;
import com.dcw.app.ui.framework.BaseFragmentWrapper;
import com.dcw.framework.view.annotation.InjectLayout;
import com.dcw.framework.view.annotation.InjectView;

@InjectLayout(R.layout.fragment_home)
public class HomeFragment extends BaseFragmentWrapper implements ToolbarController.OnInitToolbarListener
        , Toolbar.OnMenuItemClickListener, NavigationView.OnNavigationItemSelectedListener {
    @InjectView(R.id.tab_layout)
    TabLayout mTabLayout;
    @InjectView(R.id.view_pager)
    ViewPager mViewPager;
    @InjectView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;

    @Override
    public Class getHostActivity() {
        return MainActivity.class;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initUI() {
        ToolbarModel model = new ToolbarModel(getString(R.string.home), R.menu.menu_home, true);
        mToolbarController = new DrawerBarController(findViewById(R.id.toolbar), model);
        mToolbarController.setOnInitToolbarListener(this);
        mToolbarController.setOnMenuItemClickListener(this);
        ((DrawerBarController) mToolbarController).setOnNavigationItemSelectedListener(this);
        ListDataModel<BaseFragmentWrapper> fragmentModel = new ListDataModel<BaseFragmentWrapper>();
        fragmentModel.addItem(new SelectFragment());
        fragmentModel.addItem(new ContactListFragment());
        fragmentModel.addItem(new LoginFragment());
//        for (int i = 0; i < fragmentModel.getCount(); i++) {
//            mTabLayout.addTab(mTabLayout.newTab().setText(fragmentModel.getItem(i).getClass().getSimpleName()));
//        }
        FragmentAdapter<ListDataModel<BaseFragmentWrapper>, BaseFragmentWrapper> fragmentAdapter = new FragmentAdapter<ListDataModel<BaseFragmentWrapper>, BaseFragmentWrapper>(getActivity().getSupportFragmentManager(), fragmentModel);
        mViewPager.setAdapter(fragmentAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(fragmentAdapter);
        mCollapsingToolbarLayout.setTitle(getClass().getSimpleName());
    }

    @Override
    public void initListeners() {
    }

    @Override
    public void onInitToolbar(Toolbar toolbar, ActionBar actionBar) {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        ((DrawerBarController) mToolbarController).setNavigationView(navigationView);
        ((DrawerBarController) mToolbarController).setDrawerLayout(drawerLayout);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btn_right:
                startFragment(SearchFragment.class);
                break;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        // Create a new fragment and specify the planet to show based on
        // position
        Class fragmentClass;
        switch (menuItem.getItemId()) {
            case R.id.nav_first_fragment:
                fragmentClass = RichTextFragment.class;
                break;
            case R.id.nav_second_fragment:
                fragmentClass = AbsListFragment.class;
                break;
            case R.id.nav_third_fragment:
                fragmentClass = StateViewFragment.class;
                break;
            default:
                fragmentClass = SearchFragment.class;
        }
//        toggle();
//        mDrawerToggle.onOptionsItemSelected(menuItem);
        // Insert the fragment by replacing any existing fragment
        startFragment(fragmentClass);
        return true;
    }


}
