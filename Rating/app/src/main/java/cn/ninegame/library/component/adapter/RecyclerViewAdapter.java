package cn.ninegame.library.component.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.ninegame.library.component.adapter.model.RecyclerDataModel;
import cn.ninegame.library.component.adapter.viewholder.ItemViewHolder;
import cn.ninegame.library.component.adapter.viewholder.ItemViewHolderBean;
import cn.ninegame.library.component.adapter.viewholder.RecyclerViewHolder;
import cn.ninegame.library.component.mvc.core.Observable;
import cn.ninegame.library.component.mvc.core.Observer;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/10/25.
 */
public class RecyclerViewAdapter<M extends RecyclerDataModel> extends RecyclerView.Adapter<RecyclerViewHolder<M>> implements Observer {

    private Context mContext;
    private M mModel;
    private LayoutInflater mInflater;

    public RecyclerViewAdapter(Context context, M model) {
        mContext = context;
        mModel = model;
        mModel.addObserver(this);
        mInflater = LayoutInflater.from(mContext);
    }

    public RecyclerViewAdapter(@NonNull Context context, @NonNull M model, @LayoutRes int layoutResId, @NonNull Class<? extends ItemViewHolder<M>> viewHolderClazz) {
        this(context, model, layoutResId, viewHolderClazz, null);
    }

    public RecyclerViewAdapter(@NonNull Context context, @NonNull M model, @LayoutRes int layoutResId, @NonNull Class<? extends ItemViewHolder<M>> viewHolderClazz, Object listener) {
        this(context, model);
        getModel().addItemViewHolderBean(0, new ItemViewHolderBean(layoutResId, viewHolderClazz, listener));
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
    public int getItemViewType(int position) {
        if (getModel().isHeader(position)) {
            return RecyclerDataModel.ITEM_VIEW_TYPE_HEADER + position;
        } else if (getModel().isFooter(position)) {
            return RecyclerDataModel.ITEM_VIEW_TYPE_FOOTER + position - getModel().getCount() - getModel().getHeaderViewCount();
        } else {
            return getModel().getItemViewType(position - getModel().getHeaderViewCount());
        }
    }

    @Override
    public int getItemCount() {
        return getModel().getHeaderViewCount() + getModel().getCount() + getModel().getFooterViewCount();
    }

    @Override
    @SuppressWarnings("unchecked")
    public RecyclerViewHolder<M> onCreateViewHolder(ViewGroup parent, int viewType) {
        try {
            if (getModel().isHeaderViewType(viewType)) {
                Constructor<? extends ItemViewHolder> constructor = getModel().getHeaderViewHolderBean(getModel().getHeaderOrFooterPosition(viewType)).getItemViewHolderClazz().getConstructor(View.class);
                return new RecyclerViewHolder<M>(constructor.newInstance(getInflater().inflate(getModel().getHeaderViewHolderBean(getModel().getHeaderOrFooterPosition(viewType)).getItemViewHolderLayoutId(), parent, false)));
            } else if (getModel().isFooterViewType(viewType)) {
                Constructor<? extends ItemViewHolder> constructor = getModel().getFooterViewHolderBean(getModel().getHeaderOrFooterPosition(viewType)).getItemViewHolderClazz().getConstructor(View.class);
                return new RecyclerViewHolder<M>(constructor.newInstance(getInflater().inflate(getModel().getFooterViewHolderBean(getModel().getHeaderOrFooterPosition(viewType)).getItemViewHolderLayoutId(), parent, false)));
            } else {
                Constructor<? extends ItemViewHolder> constructor = getModel().getItemViewHolderBean(viewType).getItemViewHolderClazz().getConstructor(View.class);
                return new RecyclerViewHolder<M>(constructor.newInstance(getInflater().inflate(getModel().getItemViewHolderBean(viewType).getItemViewHolderLayoutId(), parent, false)));
            }
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

    @Override
    public void onBindViewHolder(RecyclerViewHolder<M> holder, int position) {
        if (getModel().isHeader(position)) {
            holder.mdItemViewHolder.setListener(getModel().getHeaderViewHolderBean(position).getViewHolderListener());
            holder.mdItemViewHolder.onBindViewEvent(getModel(), position);
            holder.mdItemViewHolder.onBindData(getModel(), position);
        } else if (getModel().isFooter(position)) {
            holder.mdItemViewHolder.setListener(getModel().getFooterViewHolderBean(position - getModel().getCount() - getModel().getHeaderViewCount()).getViewHolderListener());
            holder.mdItemViewHolder.onBindViewEvent(getModel(), position);
            holder.mdItemViewHolder.onBindData(getModel(), position);
        } else {
            holder.mdItemViewHolder.setListener(getModel().getItemViewHolderBean(getItemViewType(position)).getViewHolderListener());
            holder.mdItemViewHolder.onBindViewEvent(getModel(), position - getModel().getHeaderViewCount());
            holder.mdItemViewHolder.onBindData(getModel(), position - getModel().getHeaderViewCount());
        }
    }

    @Override
    public <T> void update(Observable observable, T data, Object... args) {
        notifyDataSetChanged();
    }
}
