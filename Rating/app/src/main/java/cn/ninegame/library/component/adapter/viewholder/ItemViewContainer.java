package cn.ninegame.library.component.adapter.viewholder;

/**
 * create by adao12.vip@gmail.com on 15/12/8
 *
 * @author JiaYing.Cheng
 * @version 1.0
 */
public interface ItemViewContainer {

    public void add(int viewType, ItemViewHolderBean bean);

    public void remove(int viewType);

    public void remove(ItemViewHolderBean bean);

    public ItemViewHolderBean get(int viewType);

    public int getViewTypeCount();

    public int getItemViewType(int position);

    public interface ViewTypeDelegate {

        int getItemViewType(int position);
    }
}
