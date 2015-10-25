package com.dcw.app.rating.biz.contact.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dcw.app.rating.biz.contact.adapter.viewholder.ItemViewHolder;
import com.dcw.app.rating.biz.contact.adapter.viewholder.RecyclerViewHolder;
import com.dcw.app.rating.biz.contact.model.ListDataModel;
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
    private Class<? extends ItemViewHolder<M, D>> mViewHolderClazz;
    private LayoutInflater mInflater;
    private Object mViewHolderListener;

    public RecyclerViewAdapter(@NonNull Context context, @NonNull M model, @LayoutRes int layoutResId, @NonNull Class<? extends ItemViewHolder<M, D>> viewHolderClazz) {
        this(context, model, layoutResId, viewHolderClazz, null);
    }

    public RecyclerViewAdapter(@NonNull Context context, @NonNull M model, @LayoutRes int layoutResId, @NonNull Class<? extends ItemViewHolder<M, D>> viewHolderClazz, Object viewHolderListener) {
        mContext = context;
        mModel = model;
        mLayoutResId = layoutResId;
        mViewHolderClazz = viewHolderClazz;
        mModel.addObserver(this);
        mInflater = LayoutInflater.from(mContext);
        mViewHolderListener = viewHolderListener;
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
            Constructor<? extends ItemViewHolder<M, D>> constructor = mViewHolderClazz.getConstructor(View.class);
            return new RecyclerViewHolder<M, D>(constructor.newInstance(mInflater.inflate(mLayoutResId, parent, false)));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Unable to find a public constructor that takes an argument View in " +
                    mViewHolderClazz.getSimpleName(), e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e.getTargetException());
        } catch (InstantiationException e) {
            throw new RuntimeException("Unable to instantiate " + mViewHolderClazz.getSimpleName(), e);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder<M, D> holder, int position) {
        holder.mdItemViewHolder.setListener(mViewHolderListener);
        holder.mdItemViewHolder.onBindViewEvent(getModel(), position);
        holder.mdItemViewHolder.onBindData(getModel(), position);
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
