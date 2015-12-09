package cn.ninegame.library.component.adapter.viewholder;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewDebug;

import cn.ninegame.library.component.adapter.model.ListDataModel;

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
    Object mData;

    /**
     * @param itemView the view shows as a item of {@link android.widget.AbsListView} or {@link android.support.v7.widget.RecyclerView}
     */
    public ItemViewHolder(View itemView) {
        if (itemView == null) {
            throw new IllegalArgumentException("itemView may not be null");
        }
        this.itemView = itemView;
        this.mHelper = new ViewHolderHelper(itemView.getContext(), itemView);
    }

    /**
     * Returns the {@link ViewHolderHelper}
     *
     * @return The holder's ViewHolderHelper
     */
    public ViewHolderHelper getHelper() {
        return mHelper;
    }

    /**
     * Returns the context the itemView is running in, through which it can
     * access the current theme, resources, etc.
     *
     * @return The itemView's Context.
     */
    @ViewDebug.CapturedViewProperty
    public Context getContext() {
        return itemView.getContext();
    }

    public <D> D getData() {
        return (D) mData;
    }

    public void setData(Object data) {
        mData = data;
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
    <L> L getListener() throws ClassCastException {
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
     * @param position when {@link ItemViewHolder} is header or footer the position is the real of the item on the list.
     *                 while the adjust position of the item on the list, which exclude the head view count
     */
    @Override
    public void onBindViewEvent(M model, int position) {

    }

    /**
     * Must implement this method to populate the views with the data in the item object.
     *
     * @param model    the data model that is used to populate the holder views.
     * @param position when {@link ItemViewHolder} is header or footer the position is the real of the item on the list.
     *                 while the adjust position of the item on the list, which exclude the head view count
     */
    @Override
    public abstract void onBindData(M model, int position);
}
