package com.dcw.app.rating.biz.select;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dcw.app.rating.biz.contact.model.ListDataModel;
import com.dcw.app.rating.ui.mvc.core.Observable;
import com.dcw.app.rating.ui.mvc.core.Observer;

import java.lang.reflect.Constructor;

/**
 * <p>Title: ucweb</p>
 * <p/>
 * <p>Description: </p>
 * ......
 * <p>Copyright: Copyright (c) 2015</p>
 * <p/>
 * <p>Company: ucweb.com</p>
 *
 * @author JiaYing.Cheng
 * @version 1.0
 * @email adao12.vip@gmail.com
 * @create 15/10/23
 */
public class AbsRecyclerViewAdapter<D, M extends ListDataModel<D>, VH extends AbsRecyclerViewHolder<M, D>> extends RecyclerView.Adapter<VH> implements Observer {

    private Context mContext;
    private M mModel;
    private @LayoutRes int mLayoutResId;
    private Class<VH> mVHClass;

    public AbsRecyclerViewAdapter(Context context, M model, @LayoutRes int layoutResId, Class<VH> vhClass) {
        mContext = context;
        mModel = model;
        mLayoutResId = layoutResId;
        mVHClass = vhClass;
    }

    public M getModel() {
        return mModel;
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        try {
            Constructor<VH> constructor = mVHClass.getConstructor(View.class);
            return constructor.newInstance(LayoutInflater.from(getContext()).inflate(mLayoutResId, parent, false));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onBindViewHolder(VH holder, int position) {
        holder.onBindData(this, getModel(), position);
    }

    @Override
    public int getItemCount() {
        return mModel.getCount();
    }

    @Override
    public <T> void update(Observable observable, T data, Object... args) {
        notifyDataSetChanged();
    }
}
