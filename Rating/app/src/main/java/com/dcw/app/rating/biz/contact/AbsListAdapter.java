package com.dcw.app.rating.biz.contact;

import android.content.Context;
import android.widget.BaseAdapter;

import com.dcw.app.rating.ui.mvc.core.Observable;
import com.dcw.app.rating.ui.mvc.core.Observer;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/10/21.
 */
public abstract class AbsListAdapter<D> extends BaseAdapter implements Observer {

    /**
     * the activity context from the activity where adapter is in.
     */
    private Context mContext = null;

    private ListDataModel<D> mModel;

    public AbsListAdapter(Context context, ListDataModel<D> model) {
        mContext = context;
        mModel = model;
        mModel.addObserver(this);
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public void setModel(ListDataModel<D> model) {
        if (mModel.equals(model)) {
            return;
        }
        mModel.deleteObserver(this);
        mModel = model;
        mModel.addObserver(this);
    }

    public ListDataModel<D> getModel() {
        return mModel;
    }

    @Override
    public int getCount() {
        return mModel.getCount();
    }

    @Override
    public D getItem(int position) {
        return mModel.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return mModel.getItemId(position);
    }

    @Override
    public <T> void update(Observable observable, T data, Object... args) {
        notifyDataSetChanged();
    }
}
