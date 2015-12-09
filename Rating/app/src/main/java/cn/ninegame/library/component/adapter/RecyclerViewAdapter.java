package cn.ninegame.library.component.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.ninegame.library.component.adapter.model.ListDataModel;
import cn.ninegame.library.component.adapter.model.RecyclerDataModel;
import cn.ninegame.library.component.adapter.viewholder.HeaderViewWrapper;
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
public class RecyclerViewAdapter<M extends ListDataModel> extends RecyclerView.Adapter<RecyclerViewHolder<M>> implements Observer {

    private Context mContext;
    private M mModel;
    private LayoutInflater mInflater;
    private HeaderViewWrapper mHeaders;
    private HeaderViewWrapper mFooters;

    /**
     * @param context the context to get LayoutInflater @see android.view.LayoutInflater
     * @param model the model of model layer, which contains the data set to show
     */
    public RecyclerViewAdapter(Context context, M model) {
        mContext = context;
        mModel = model;
        mModel.addObserver(this);
        mInflater = LayoutInflater.from(mContext);
        mHeaders = new HeaderViewWrapper();
        mFooters = new HeaderViewWrapper();
    }

    /**
     * @param context the context to get LayoutInflater @see android.view.LayoutInflater
     * @param model the model of model layer, which contains the data set to show
     * @param layoutResId the view's layoutId that would be used to inflating item view
     * @param viewHolderClazz the class of the {@link ItemViewHolder}'s implement
     */
    public RecyclerViewAdapter(@NonNull Context context, @NonNull M model, @LayoutRes int layoutResId, @NonNull Class<? extends ItemViewHolder<M>> viewHolderClazz) {
        this(context, model, layoutResId, viewHolderClazz, null);
    }

    /**
     * @param context the context to get LayoutInflater @see android.view.LayoutInflater
     * @param model the model of model layer, which contains the data set to show
     * @param layoutResId the view's layoutId that would be used to inflating item view
     * @param viewHolderClazz the class of the {@link ItemViewHolder}'s implement
     * @param listener the listener of events that views included in {@link ItemViewHolder} dispatched
     * @param <L> the class type of the class implement listener
     */
    public <L> RecyclerViewAdapter(@NonNull Context context, @NonNull M model, @LayoutRes int layoutResId, @NonNull Class<? extends ItemViewHolder<M>> viewHolderClazz, L listener) {
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
        if (getHeaderViewCount() > 0 && position < getHeaderViewCount()) {
            return mHeaders.getViewType(position);
        } else if (getFooterViewCount() > 0 && position - getHeaderViewCount() + mModel.getCount() < getFooterViewCount()) {
            return mFooters.getViewType(position - getHeaderViewCount() + mModel.getCount());
        } else {
            return getModel().getItemViewType(position- mHeaders.getCount());
        }
    }

    @Override
    public int getItemCount() {
        return getHeaderViewCount() + getModel().getCount() + getFooterViewCount();
    }

    @Override
    @SuppressWarnings("unchecked")
    public RecyclerViewHolder<M> onCreateViewHolder(ViewGroup parent, int viewType) {
        try {
            if (mHeaders.containsViewType(viewType)) {
                return new RecyclerViewHolder<M>(mHeaders.get(mHeaders.getPosition(viewType)));
            } else if (mFooters.containsViewType(viewType)) {
                return new RecyclerViewHolder<M>(mFooters.get(mFooters.getPosition(viewType)));
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
        } catch (ClassCastException e) {
            throw new RuntimeException("the type of " + getModel().getItemViewHolderBean(viewType).getItemViewHolderClazz().getSimpleName() + "is not right.", e);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder<M> holder, int position) {
        if (getHeaderViewCount() > 0 && position < getHeaderViewCount()) {
            holder.mdItemViewHolder.setListener(mHeaders.getItemViewHolder(position).getListener());
            holder.mdItemViewHolder.setData(mHeaders.getItemViewHolder(position).getData());
            holder.mdItemViewHolder.onBindViewEvent(getModel(), position);  //real position
            holder.mdItemViewHolder.onBindData(getModel(), position);   //real position
        } else if (getFooterViewCount() > 0 && position - getHeaderViewCount() + mModel.getCount() < getFooterViewCount()) {
            int adjPosition = position - getHeaderViewCount() + mModel.getCount();
            holder.mdItemViewHolder.setListener(mFooters.getItemViewHolder(adjPosition).getListener());
            holder.mdItemViewHolder.setData(mFooters.getItemViewHolder(adjPosition).getData());
            holder.mdItemViewHolder.onBindViewEvent(getModel(), adjPosition);  //real position
            holder.mdItemViewHolder.onBindData(getModel(), adjPosition);   //real position
        } else {
            holder.mdItemViewHolder.setListener(getModel().getItemViewHolderBean(getItemViewType(position)).getViewHolderListener());
            holder.mdItemViewHolder.onBindViewEvent(getModel(), position - getHeaderViewCount());    //adjust position
            holder.mdItemViewHolder.onBindData(getModel(), position - getHeaderViewCount()); //adjust position
        }
    }

    @Override
    public <T> void update(Observable observable, T data, Object... args) {
        notifyDataSetChanged();
    }

    public void addHeaderView(ItemViewHolder holder) {
        mHeaders.add(holder);
    }

    public void addHeaderView(View view) {
        mHeaders.add(view);
    }

    public void addHeaderView(View view, Object listener) {
        mHeaders.add(view, listener);
    }

    public void addHeaderView(View view, Object listener, Object data) {
        mHeaders.add(view, listener, data);
    }

    public void addHeaderView(int position, ItemViewHolder holder) {
        mHeaders.add(position, holder);
    }

    public void removeHeaderView(ItemViewHolder holder) {
        mHeaders.remove(holder);
    }

    public void removeHeaderView(View view) {
        mHeaders.remove(view);
    }

    public void removeHeaderView(int position) {
        mHeaders.remove(position);
    }

    public int getHeaderViewCount() {
        return mHeaders.getCount();
    }

    public void addFooterView(ItemViewHolder holder) {
        mFooters.add(holder);
    }

    public void addFooterView(View view) {
        mFooters.add(view);
    }

    public void addFooterView(View view, Object listener) {
        mFooters.add(view, listener);
    }

    public void addFooterView(View view, Object listener, Object data) {
        mFooters.add(view, listener, data);
    }

    public void addFooterView(int position, ItemViewHolder holder) {
        mFooters.add(position, holder);
    }

    public void removeFooterView(ItemViewHolder holder) {
        mFooters.remove(holder);
    }

    public void removeFooterView(int position) {
        mFooters.remove(position);
    }

    public void removeFooterView(View view) {
        mFooters.remove(view);
    }

    public int getFooterViewCount() {
        return mFooters.getCount();
    }
}
