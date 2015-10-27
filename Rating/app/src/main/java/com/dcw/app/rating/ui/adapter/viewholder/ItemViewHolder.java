package com.dcw.app.rating.ui.adapter.viewholder;

import android.support.annotation.Nullable;
import android.view.View;

import com.dcw.app.rating.ui.adapter.model.ListDataModel;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/10/25.
 */
public abstract class ItemViewHolder<M extends ListDataModel<D>, D> implements OnBindDataListener<M, D>, OnBindViewEventListener<M, D> {

    public final View itemView;
    Object mListener;

    public ItemViewHolder(View itemView) {
        if (itemView == null) {
            throw new IllegalArgumentException("itemView may not be null");
        }
        this.itemView = itemView;
    }

    /**
     * Gets the listener object that was passed into the Adapter through its constructor and cast
     * it to a given type.
     *
     * @return the listener casted to the given type or null if not listener was set into the Adapter.
     */
    @SuppressWarnings("unchecked")
    public
    @Nullable
    <L> L getListener() {
        return (L) mListener;
    }

    public void setListener(Object listener) {
        this.mListener = listener;
    }

    /**
     * Implement this method to add listeners to the views. This method is only called once when
     * the Adapter is created.
     *
     * @param model    the data model that is used to populate the holder views.
     * @param position the position of the item on the list.
     */
    @Override
    public void onBindViewEvent(M model, int position) {

    }

    /**
     * Must implement this method to populate the views with the data in the item object.
     *
     * @param model    the data model that is used to populate the holder views.
     * @param position the position of the item on the list.
     */
    @Override
    public abstract void onBindData(M model, int position);
}
