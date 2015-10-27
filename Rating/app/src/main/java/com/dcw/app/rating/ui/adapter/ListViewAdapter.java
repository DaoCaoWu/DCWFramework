package com.dcw.app.rating.ui.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.dcw.app.rating.ui.adapter.viewholder.ItemViewHolder;
import com.dcw.app.rating.ui.adapter.viewholder.ItemViewHolderBean;
import com.dcw.app.rating.ui.adapter.model.ListDataModel;
import com.dcw.app.rating.ui.mvc.core.Observable;
import com.dcw.app.rating.ui.mvc.core.Observer;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/10/25.
 */
public class ListViewAdapter<M extends ListDataModel<D>, D> extends BaseAdapter implements Observer {

    private Context mContext;
    private M mModel;
    private LayoutInflater mInflater;
    private Object mViewHolderListener;

    public ListViewAdapter(@NonNull Context context, @NonNull M model) {
        this(context, model, null);
    }

    public ListViewAdapter(@NonNull Context context, @NonNull M model, Object listener) {
        mContext = context;
        mModel = model;
        mModel.addObserver(this);
        mInflater = LayoutInflater.from(mContext);
        mViewHolderListener = listener;
    }

    public ListViewAdapter(@NonNull Context context, @NonNull M model, @LayoutRes int layoutResId, @NonNull Class<? extends ItemViewHolder<M, D>> viewHolderClazz) {
        this(context, model, layoutResId, viewHolderClazz, null);
    }

    public ListViewAdapter(@NonNull Context context, @NonNull M model, @LayoutRes int layoutResId, @NonNull Class<? extends ItemViewHolder<M, D>> viewHolderClazz, Object listener) {
        this(context, model, listener);
        getModel().addItemViewHolderBean(0, new ItemViewHolderBean<M, D>(layoutResId, viewHolderClazz));
    }

    public LayoutInflater getInflater() {
        return mInflater;
    }

    public void setViewHolderListener(Object viewHolderListener) {
        mViewHolderListener = viewHolderListener;
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

    @Override
    public int getCount() {
        return mModel.getCount();
    }

    @Override
    public Object getItem(int position) {
        return mModel.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return mModel.getItemId(position);
    }

    @Override
    public int getViewTypeCount() {
        return mModel.getViewTypeCount();
    }

    @Override
    public int getItemViewType(int position) {
        return mModel.getItemViewType(position);
    }

    @Override
    @SuppressWarnings("unchecked")
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemViewHolder<M, D> holder;
        if (convertView == null) {
            //Create a new view holder using reflection
            holder = onCreateViewHolder(parent, getItemViewType(position));
            convertView = holder.itemView;
            convertView.setTag(holder);
        } else {
            //Reuse the view holder
            holder = (ItemViewHolder<M, D>) convertView.getTag();
        }
        onBindViewHolder(holder, position);
        return convertView;
    }

    public ItemViewHolder<M, D> onCreateViewHolder(ViewGroup parent, int viewType) {
        try {
            //Create a new view holder using reflection
            Constructor<? extends ItemViewHolder<M, D>> constructor = getModel().<M>getItemViewHolderBean(viewType).getItemViewHolderClazz().getConstructor(View.class);
            return constructor.newInstance(getInflater().inflate(getModel().getItemViewHolderBean(viewType).getItemViewHolderLayoutId(), parent, false));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Unable to find a public constructor that takes an argument View in " +
                    getModel().<M>getItemViewHolderBean(viewType).getItemViewHolderClazz().getSimpleName(), e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e.getTargetException());
        } catch (InstantiationException e) {
            throw new RuntimeException("Unable to instantiate " + getModel().<M>getItemViewHolderBean(viewType).getItemViewHolderClazz().getSimpleName(), e);
        }
    }

    public void onBindViewHolder(ItemViewHolder<M, D> holder, int position) {
        holder.setListener(mViewHolderListener);
        holder.onBindViewEvent(getModel(), position);
        holder.onBindData(getModel(), position);
    }

    @Override
    public <T> void update(Observable observable, T data, Object... args) {
        notifyDataSetChanged();
    }

}
