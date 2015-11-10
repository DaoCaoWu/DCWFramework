package com.dcw.app.ui.framework;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.dcw.app.ui.adapter.model.ListDataModel;
import com.dcw.app.ui.framework.BaseFragmentWrapper;
import com.dcw.app.ui.mvc.core.Observable;
import com.dcw.app.ui.mvc.core.Observer;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/11/6.
 */
public class FragmentAdapter<M extends ListDataModel<D>, D extends BaseFragmentWrapper> extends FragmentStatePagerAdapter implements Observer {

    private M mModel;

    public FragmentAdapter(@NonNull FragmentManager fm, @NonNull M model) {
        super(fm);
        mModel = model;
        mModel.addObserver(this);
    }

    @Override
    public Fragment getItem(int position) {
        return mModel.getItem(position);
    }

    @Override
    public int getCount() {
        return mModel.getCount();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return getItem(position).getClass().getSimpleName();
    }

    @Override
    public <T> void update(Observable observable, T data, Object... args) {
        notifyDataSetChanged();
    }
}
