package com.dcw.app.ui.adapter.viewholder;

import android.support.annotation.Nullable;
import android.view.View;

import com.dcw.app.ui.adapter.model.ListDataModel;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/10/25.
 */
public abstract class ItemViewHolder<M extends ListDataModel> implements OnBindDataListener<M>, OnBindViewEventListener<M> {

    public final View itemView;
    /**
     * Views indexed with their IDs
     */
    private final ViewHolderHelper mHelper;
    Object mListener;

    public ItemViewHolder(View itemView) {
        if (itemView == null) {
            throw new IllegalArgumentException("itemView may not be null");
        }
        this.itemView = itemView;
        this.mHelper = new ViewHolderHelper(itemView.getContext(), itemView);
    }

    public ViewHolderHelper getHelper() {
        return mHelper;
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
