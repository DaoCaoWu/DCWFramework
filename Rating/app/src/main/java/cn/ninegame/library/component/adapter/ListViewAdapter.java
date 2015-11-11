package cn.ninegame.library.component.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import cn.ninegame.library.component.adapter.model.ListDataModel;
import cn.ninegame.library.component.adapter.viewholder.ItemViewHolder;
import cn.ninegame.library.component.adapter.viewholder.ItemViewHolderBean;
import cn.ninegame.library.component.mvc.core.Observable;
import cn.ninegame.library.component.mvc.core.Observer;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/10/25.
 */
public class ListViewAdapter<M extends ListDataModel> extends BaseAdapter implements Observer {

    private Context mContext;
    private M mModel;
    private LayoutInflater mInflater;

    public ListViewAdapter(@NonNull Context context, @NonNull M model) {
        mContext = context;
        mModel = model;
        mModel.addObserver(this);
        mInflater = LayoutInflater.from(mContext);
    }

    public ListViewAdapter(@NonNull Context context, @NonNull M model, @LayoutRes int layoutResId, @NonNull Class<? extends ItemViewHolder<M>> viewHolderClazz) {
        this(context, model, layoutResId, viewHolderClazz, null);
    }

    public ListViewAdapter(@NonNull Context context, @NonNull M model, @LayoutRes int layoutResId, @NonNull Class<? extends ItemViewHolder<M>> viewHolderClazz, Object listener) {
        this(context, model);
        getModel().addItemViewHolderBean(0, new ItemViewHolderBean(layoutResId, viewHolderClazz, listener));
    }

    public LayoutInflater getInflater() {
        return mInflater;
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
        ItemViewHolder<M> holder;
        if (convertView == null) {
            //Create a new view holder using reflection
            holder = onCreateViewHolder(parent, getItemViewType(position));
            convertView = holder.itemView;
            convertView.setTag(holder);
        } else {
            //Reuse the view holder
            holder = (ItemViewHolder<M>) convertView.getTag();
        }
        onBindViewHolder(holder, position);
        return convertView;
    }

    @SuppressWarnings("unchecked")
    public ItemViewHolder<M> onCreateViewHolder(ViewGroup parent, int viewType) {
        try {
            //Create a new view holder using reflection
            Constructor<? extends ItemViewHolder> constructor = getModel().getItemViewHolderBean(viewType).getItemViewHolderClazz().getConstructor(View.class);
            return constructor.newInstance(getInflater().inflate(getModel().getItemViewHolderBean(viewType).getItemViewHolderLayoutId(), parent, false));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Unable to find a public constructor that takes an argument View in " +
                    getModel().getItemViewHolderBean(viewType).getItemViewHolderClazz().getSimpleName(), e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e.getTargetException());
        } catch (InstantiationException e) {
            throw new RuntimeException("Unable to instantiate " + getModel().getItemViewHolderBean(viewType).getItemViewHolderClazz().getSimpleName(), e);
        }
    }

    public void onBindViewHolder(ItemViewHolder<M> holder, int position) {
        holder.setListener(getModel().getItemViewHolderBean(getItemViewType(position)).getViewHolderListener());
        holder.onBindViewEvent(getModel(), position);
        holder.onBindData(getModel(), position);
    }

    @Override
    public <T> void update(Observable observable, T data, Object... args) {
        notifyDataSetChanged();
    }

}
