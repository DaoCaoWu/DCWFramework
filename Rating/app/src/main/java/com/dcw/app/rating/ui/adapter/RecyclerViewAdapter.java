package com.dcw.app.rating.ui.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dcw.app.rating.ui.adapter.model.RecyclerDataModel;
import com.dcw.app.rating.ui.adapter.viewholder.ItemViewHolder;
import com.dcw.app.rating.ui.adapter.viewholder.RecyclerViewHolder;
import com.dcw.app.rating.ui.adapter.viewholder.ItemViewHolderBean;
import com.dcw.app.rating.ui.adapter.model.ListDataModel;
import com.dcw.app.rating.ui.mvc.core.Observable;
import com.dcw.app.rating.ui.mvc.core.Observer;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/10/25.
 */
public class RecyclerViewAdapter<M extends ListDataModel<D>, D> extends RecyclerView.Adapter<RecyclerViewHolder<M, D>> implements Observer {

    private Context mContext;
    private M mModel;
    private LayoutInflater mInflater;

    public RecyclerViewAdapter(Context context, M model) {
        mContext = context;
        mModel = model;
        mModel.addObserver(this);
        mInflater = LayoutInflater.from(mContext);
    }

    public RecyclerViewAdapter(@NonNull Context context, @NonNull M model, @LayoutRes int layoutResId, @NonNull Class<? extends ItemViewHolder<M, D>> viewHolderClazz) {
        this(context, model, layoutResId, viewHolderClazz, null);
    }

    public RecyclerViewAdapter(@NonNull Context context, @NonNull M model, @LayoutRes int layoutResId, @NonNull Class<? extends ItemViewHolder<M, D>> viewHolderClazz, Object listener) {
        this(context, model);
        getModel().addItemViewHolderBean(0, new ItemViewHolderBean<M, D>(layoutResId, viewHolderClazz, listener));
    }

    public LayoutInflater getInflater() {
        return mInflater;
    }

    public M getModel() {
        return mModel;
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public int getItemCount() {
        if (mModel instanceof RecyclerDataModel) {
            return ((RecyclerDataModel) mModel).getHeaderViewCount() + mModel.getCount() + ((RecyclerDataModel) mModel).getFooterViewCount();
        }
        return mModel.getCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (mModel instanceof RecyclerDataModel) {
            if (((RecyclerDataModel) mModel).isHeader(position)) {
                return RecyclerDataModel.ITEM_VIEW_TYPE_HEADER + position;
            } else if (((RecyclerDataModel) mModel).isFooter(position)) {
                return RecyclerDataModel.ITEM_VIEW_TYPE_FOOTER + position;
            }
        }
        return mModel.getItemViewType(position);
    }

    @Override
    public RecyclerViewHolder<M, D> onCreateViewHolder(ViewGroup parent, int viewType) {
        try {
            if (getModel().<M>getItemViewHolderBean(viewType) == null) {
                int a = 1;
                a++;
            }
            Constructor<? extends ItemViewHolder<M, D>> constructor = getModel().<M>getItemViewHolderBean(viewType).getItemViewHolderClazz().getConstructor(View.class);
            return new RecyclerViewHolder<M, D>(constructor.newInstance(getInflater().inflate(getModel().getItemViewHolderBean(viewType).getItemViewHolderLayoutId(), parent, false)));
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

    @Override
    public void onBindViewHolder(RecyclerViewHolder<M, D> holder, int position) {
        if (mModel instanceof RecyclerDataModel) {
            if (((RecyclerDataModel) mModel).isHeader(position)) {
                holder.mdItemViewHolder.setListener(((RecyclerDataModel) getModel()).getHeaderViewHolderBean(position).getViewHolderListener());
            } else if (((RecyclerDataModel) mModel).isFooter(position)) {
                holder.mdItemViewHolder.setListener(((RecyclerDataModel) getModel()).getFooterViewHolderBean(position).getViewHolderListener());
            } else {
                holder.mdItemViewHolder.setListener(getModel().<M>getItemViewHolderBean(getItemViewType(position)).getViewHolderListener());
            }
        } else {
            holder.mdItemViewHolder.setListener(getModel().<M>getItemViewHolderBean(getItemViewType(position)).getViewHolderListener());
        }
        holder.mdItemViewHolder.onBindViewEvent(getModel(), position);
        holder.mdItemViewHolder.onBindData(getModel(), position);
    }

    @Override
    public <T> void update(Observable observable, T data, Object... args) {
        notifyDataSetChanged();
    }
}
