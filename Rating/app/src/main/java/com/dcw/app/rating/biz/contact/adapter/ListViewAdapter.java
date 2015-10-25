package com.dcw.app.rating.biz.contact.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.dcw.app.rating.biz.contact.model.ListDataModel;
import com.dcw.app.rating.biz.contact.view.viewholder.ItemViewHolder;
import com.dcw.app.rating.biz.contact.view.viewholder.ItemViewInfo;
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
    private
    @LayoutRes
    int mLayoutResId;
    private Class<? extends ItemViewHolder<M, D>> mVHClass;

    public ListViewAdapter(Context context, M model, int layoutResId, Class<? extends ItemViewHolder<M, D>> VHClass) {
        mContext = context;
        mModel = model;
        mLayoutResId = layoutResId;
        mVHClass = VHClass;
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
    @SuppressWarnings("unchecked")
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemViewHolder<M, D> holder;
        if (convertView == null) {
            //Create a new view holder using reflection
            holder = onCreateViewHolder(parent, getItemViewType(position));
            holder.setItemViewInfo(new ItemViewInfo(getItemViewType(position), position));
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
            Constructor<? extends ItemViewHolder<M, D>> constructor = mVHClass.getConstructor(View.class);
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

    public void onBindViewHolder(ItemViewHolder<M, D> holder, int position) {
        holder.onBindData(this, getModel(), position);
    }

    @Override
    public <T> void update(Observable observable, T data, Object... args) {
        notifyDataSetChanged();
    }

    public interface OnBindDataListener<M extends ListDataModel<D>, D> {

        void onBindData(ListViewAdapter<M, D> adapter, M model, int position);
    }
}
