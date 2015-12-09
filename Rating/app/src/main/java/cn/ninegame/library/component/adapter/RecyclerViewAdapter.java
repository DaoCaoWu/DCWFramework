package cn.ninegame.library.component.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.ninegame.library.component.adapter.model.ListDataModel;
import cn.ninegame.library.component.adapter.model.FixViewModel;
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

    public static final int ITEM_VIEW_TYPE_HEADER = Integer.MIN_VALUE;
    public static final int ITEM_VIEW_TYPE_FOOTER = Integer.MAX_VALUE / 2;

    private Context mContext;
    private M mModel;
    private LayoutInflater mInflater;
    private FixViewModel mHeaders;
    private FixViewModel mFooters;

    /**
     * @param context the context to get LayoutInflater @see android.view.LayoutInflater
     * @param model the model of model layer, which contains the data set to show
     */
    public RecyclerViewAdapter(Context context, M model) {
        mContext = context;
        mModel = model;
        mModel.addObserver(this);
        mInflater = LayoutInflater.from(mContext);
        mHeaders = new FixViewModel(ITEM_VIEW_TYPE_HEADER);
        mFooters = new FixViewModel(ITEM_VIEW_TYPE_FOOTER);
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

    public FixViewModel getHeaders() {
        return mHeaders;
    }

    public void setHeaders(FixViewModel headers) {
        mHeaders = headers;
    }

    public FixViewModel getFooters() {
        return mFooters;
    }

    public void setFooters(FixViewModel footers) {
        mFooters = footers;
    }

    @Override
    public int getItemViewType(int position) {
        if (getHeaderViewCount() > 0 && position < getHeaderViewCount()) {
            return mHeaders.getViewType(position);
        } else if (getFooterViewCount() > 0
                && position - getHeaderViewCount() - mModel.getCount() >= 0
                && position - getHeaderViewCount() - mModel.getCount() < getFooterViewCount()) {
            return mFooters.getViewType(position - getHeaderViewCount() - mModel.getCount());
        } else {
            return getModel().getItemViewType(position- mHeaders.getCount());
        }
    }

    @Override
    public int getItemCount() {
        return getHeaderViewCount() + mModel.getCount() + getFooterViewCount();
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
                Constructor<? extends ItemViewHolder> constructor = mModel.getItemViewHolderBean(viewType).getItemViewHolderClazz().getConstructor(View.class);
                return new RecyclerViewHolder<M>(constructor.newInstance(getInflater().inflate(mModel.getItemViewHolderBean(viewType).getItemViewHolderLayoutId(), parent, false)));
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
            holder.mdItemViewHolder.onBindViewEvent(mModel, position);  //real position
            holder.mdItemViewHolder.onBindData(mModel, position);   //real position
        } else if (getFooterViewCount() > 0
                && position - getHeaderViewCount() - mModel.getCount() >= 0
                && position - getHeaderViewCount() - mModel.getCount() < getFooterViewCount()) {
            int adjPosition = position - getHeaderViewCount() - mModel.getCount();
            holder.mdItemViewHolder.setListener(mFooters.getItemViewHolder(adjPosition).getListener());
            holder.mdItemViewHolder.setData(mFooters.getItemViewHolder(adjPosition).getData());
            holder.mdItemViewHolder.onBindViewEvent(mModel, adjPosition);  //real position
            holder.mdItemViewHolder.onBindData(mModel, adjPosition);   //real position
        } else {
            holder.mdItemViewHolder.setListener(mModel.getItemViewHolderBean(getItemViewType(position)).getViewHolderListener());
            holder.mdItemViewHolder.onBindViewEvent(mModel, position - getHeaderViewCount());    //adjust position
            holder.mdItemViewHolder.onBindData(mModel, position - getHeaderViewCount()); //adjust position
        }
    }

    @Override
    public <T> void update(Observable observable, T data, Object... args) {
        notifyDataSetChanged();
    }



    public void addHeaderView(ItemViewHolder holder) {
        mHeaders.add(holder);
        notifyItemInserted(getHeaderViewCount());
    }

    public void addHeaderView(View view) {
        mHeaders.add(view);
        notifyItemInserted(getHeaderViewCount());
    }

    public void addHeaderView(View view, Object listener) {
        mHeaders.add(view, listener);
        notifyItemInserted(getHeaderViewCount());
    }

    public void addHeaderView(View view, Object listener, Object data) {
        mHeaders.add(view, listener, data);
        notifyItemInserted(getHeaderViewCount());
    }

    public void addHeaderView(int position, ItemViewHolder holder) {
        mHeaders.add(position, holder);
        notifyDataSetChanged();
    }

    public void removeHeaderView(ItemViewHolder holder) {
        mHeaders.remove(holder);
        notifyDataSetChanged();
    }

    public void removeHeaderView(View view) {
        mHeaders.remove(view);
        notifyDataSetChanged();
    }

    public void removeHeaderView(int position) {
        mHeaders.remove(position);
        notifyDataSetChanged();
    }

    public int getHeaderViewCount() {
        return mHeaders.getCount();
    }

    public void addFooterView(ItemViewHolder holder) {
        mFooters.add(holder);
        notifyItemChanged(getItemCount());
    }

    public void addFooterView(View view) {
        mFooters.add(view);
        notifyItemChanged(getItemCount());
    }

    public void addFooterView(View view, Object listener) {
        mFooters.add(view, listener);
        notifyItemInserted(getItemCount());
    }

    public void addFooterView(View view, Object listener, Object data) {
        mFooters.add(view, listener, data);
        notifyItemInserted(getItemCount());
    }

    public void addFooterView(int position, ItemViewHolder holder) {
        mFooters.add(position, holder);
        notifyDataSetChanged();
    }

    public void removeFooterView(ItemViewHolder holder) {
        mFooters.remove(holder);
        notifyDataSetChanged();
    }

    public void removeFooterView(int position) {
        mFooters.remove(position);
        notifyDataSetChanged();
    }

    public void removeFooterView(View view) {
        mFooters.remove(view);
        notifyDataSetChanged();
    }

    public int getFooterViewCount() {
        return mFooters.getCount();
    }
}
