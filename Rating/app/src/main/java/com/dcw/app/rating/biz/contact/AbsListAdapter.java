package com.dcw.app.rating.biz.contact;

import android.content.Context;
import android.widget.BaseAdapter;

import com.dcw.app.rating.ui.mvc.core.Observable;
import com.dcw.app.rating.ui.mvc.core.Observer;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/10/21.
 */
public abstract class AbsListAdapter<D, M extends ListDataModel<D>> extends BaseAdapter implements Observer {

    /**
     * the activity context from the activity where adapter is in.
     */
    private Context mContext = null;

    private M mModel;

    public AbsListAdapter(Context context, M model) {
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

    public M getModel() {
        return mModel;
    }

    public void setModel(M model) {
        if (mModel.equals(model)) {
            return;
        }
        mModel.deleteObserver(this);
        mModel = model;
        mModel.addObserver(this);
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
