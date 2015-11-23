package cn.ninegame.framework;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import cn.ninegame.framework.adapter.BaseFragmentWrapper;
import cn.ninegame.library.component.adapter.model.ListDataModel;

import cn.ninegame.library.component.mvc.core.Observable;
import cn.ninegame.library.component.mvc.core.Observer;

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
