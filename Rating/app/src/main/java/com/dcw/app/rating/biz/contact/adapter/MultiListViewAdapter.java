package com.dcw.app.rating.biz.contact.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.dcw.app.rating.biz.contact.adapter.viewholder.ItemViewHolder;
import com.dcw.app.rating.biz.contact.model.ListDataModel;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * <p>Title: ucweb</p>
 * <p/>
 * <p>Description: </p>
 * ......
 * <p>Copyright: Copyright (c) 2015</p>
 * <p/>
 * <p>Company: ucweb.com</p>
 *
 * @author JiaYing.Cheng
 * @version 1.0
 * @email adao12.vip@gmail.com
 * @create 15/10/26
 */
public class MultiListViewAdapter<M extends ListDataModel<D>, D> extends ListViewAdapter<M, D> {

    public MultiListViewAdapter(@NonNull Context context, @NonNull M model) {
        super(context, model);
    }

    public MultiListViewAdapter(@NonNull Context context, @NonNull M model, Object viewHolderListener) {
        super(context, model);
        setViewHolderListener(viewHolderListener);
    }

    @Override
    public ItemViewHolder<M, D> onCreateViewHolder(ViewGroup parent, int viewType) {
        try {
            Constructor<? extends ItemViewHolder<M, D>> constructor = getModel().getItemViewHolderBean(viewType).<M>getItemViewHolderClazz().getConstructor(View.class);
            return constructor.newInstance(getInflater().inflate(getModel().getItemViewHolderBean(viewType).getItemViewHolderLayoutId(), parent, false));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Unable to find a public constructor that takes an argument View in " +
                    getModel().getItemViewHolderBean(viewType).<M>getItemViewHolderClazz().getSimpleName(), e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e.getTargetException());
        } catch (InstantiationException e) {
            throw new RuntimeException("Unable to instantiate " + getModel().getItemViewHolderBean(viewType).<M>getItemViewHolderClazz().getSimpleName(), e);
        }
    }
}
