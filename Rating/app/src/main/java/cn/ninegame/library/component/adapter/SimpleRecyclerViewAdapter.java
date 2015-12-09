package cn.ninegame.library.component.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import cn.ninegame.library.component.adapter.model.ListDataModel;
import cn.ninegame.library.component.adapter.viewholder.ItemViewHolder;
import cn.ninegame.library.component.adapter.viewholder.ItemViewHolderBean;
import cn.ninegame.library.component.adapter.viewholder.RecyclerViewHolder;
import cn.ninegame.library.component.mvc.core.Observable;
import cn.ninegame.library.component.mvc.core.Observer;

/**
 * create by adao12.vip@gmail.com on 15/12/9
 *
 * @author JiaYing.Cheng
 * @version 1.0
 */
public class SimpleRecyclerViewAdapter<M extends ListDataModel> extends RecyclerView.Adapter<RecyclerViewHolder<M>> implements Observer {

    private Context mContext;
    private M mModel;
    private LayoutInflater mInflater;

    /**
     * @param context the context to get LayoutInflater @see android.view.LayoutInflater
     * @param model   the model of model layer, which contains the data set to show
     */
    public SimpleRecyclerViewAdapter(Context context, M model) {
        mContext = context;
        mModel = model;
        mModel.addObserver(this);
        mInflater = LayoutInflater.from(mContext);
    }

    /**
     * @param context         the context to get LayoutInflater @see android.view.LayoutInflater
     * @param model           the model of model layer, which contains the data set to show
     * @param layoutResId     the view's layoutId that would be used to inflating item view
     * @param viewHolderClazz the class of the {@link ItemViewHolder}'s implement
     */
    public SimpleRecyclerViewAdapter(@NonNull Context context, @NonNull M model, @LayoutRes int layoutResId, @NonNull Class<? extends ItemViewHolder<M>> viewHolderClazz) {
        this(context, model, layoutResId, viewHolderClazz, null);
    }

    /**
     * @param context         the context to get LayoutInflater @see android.view.LayoutInflater
     * @param model           the model of model layer, which contains the data set to show
     * @param layoutResId     the view's layoutId that would be used to inflating item view
     * @param viewHolderClazz the class of the {@link ItemViewHolder}'s implement
     * @param listener        the listener of events that views included in {@link ItemViewHolder} dispatched
     * @param <L>             the class type of the class implement listener
     */
    public <L> SimpleRecyclerViewAdapter(@NonNull Context context, @NonNull M model, @LayoutRes int layoutResId, @NonNull Class<? extends ItemViewHolder<M>> viewHolderClazz, L listener) {
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
        return mModel.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return getModel().getCount();
    }

    @Override
    @SuppressWarnings("unchecked")
    public RecyclerViewHolder<M> onCreateViewHolder(ViewGroup parent, int viewType) {
        try {
            Constructor<? extends ItemViewHolder> constructor = getModel().getItemViewHolderBean(viewType).getItemViewHolderClazz().getConstructor(View.class);
            return new RecyclerViewHolder<M>(constructor.newInstance(getInflater().inflate(getModel().getItemViewHolderBean(viewType).getItemViewHolderLayoutId(), parent, false)));
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
        holder.mdItemViewHolder.setListener(getModel().getItemViewHolderBean(getItemViewType(position)).getViewHolderListener());
        holder.mdItemViewHolder.onBindViewEvent(getModel(), position);    //adjust position
        holder.mdItemViewHolder.onBindData(getModel(), position); //adjust position
    }

    @Override
    public <T> void update(Observable observable, T data, Object... args) {
        notifyDataSetChanged();
    }
}
