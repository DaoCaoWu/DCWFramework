package cn.ninegame.library.component.adapter.viewholder;

import android.view.View;

import cn.ninegame.library.component.adapter.model.ListDataModel;

/**
 * create by adao12.vip@gmail.com on 15/12/8
 *
 * @author JiaYing.Cheng
 * @version 1.0
 */
public class SimpleItemViewHolder<M extends ListDataModel> extends ItemViewHolder<M> {

    public SimpleItemViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void onBindData(M model, int position) {

    }
}
