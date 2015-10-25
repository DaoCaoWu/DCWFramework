package com.dcw.app.rating.biz.contact.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dcw.app.rating.biz.contact.model.ListDataModel;
import com.dcw.app.rating.biz.contact.view.viewholder.RecyclerViewHolder;
import com.dcw.app.rating.ui.mvc.core.Observable;
import com.dcw.app.rating.ui.mvc.core.Observer;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class RecyclerViewAdapter<M extends ListDataModel<D>, D> extends RecyclerView.Adapter<RecyclerViewHolder<M, D>> implements Observer {

    private Context mContext;
    private M mModel;
    private
    @LayoutRes
    int mLayoutResId;
    private Class<? extends RecyclerViewHolder<M, D>> mVHClass;

    public RecyclerViewAdapter(Context context, M model, @LayoutRes int layoutResId, Class<? extends RecyclerViewHolder<M, D>> vhClass) {
        mContext = context;
        mModel = model;
        mLayoutResId = layoutResId;
        mVHClass = vhClass;
        mModel.addObserver(this);
    }

    public M getModel() {
        return mModel;
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public RecyclerViewHolder<M, D> onCreateViewHolder(ViewGroup parent, int viewType) {
        try {
            Constructor<? extends RecyclerViewHolder<M, D>> constructor = mVHClass.getConstructor(View.class);
            return constructor.newInstance(LayoutInflater.from(getContext()).inflate(mLayoutResId, parent, false));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Unable to find a public constructor that takes an argument View in " +
                    mVHClass.getSimpleName(), e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e.getTargetException());
        } catch (InstantiationException e) {
            throw new RuntimeException("Unable to instantiate " + mVHClass.getSimpleName(), e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onBindViewHolder(RecyclerViewHolder<M, D> holder, int position) {
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

    public interface OnBindDataListener<M extends ListDataModel<D>, D> {

        void onBindData(RecyclerViewAdapter adapter, M model, int position);
    }
}
