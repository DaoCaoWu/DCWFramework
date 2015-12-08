package cn.ninegame.library.component.adapter.model;

import android.support.annotation.NonNull;
import android.util.SparseArray;

/**
 * create by adao12.vip@gmail.com on 15/12/8
 *
 * @author JiaYing.Cheng
 * @version 1.0
 */
public class NormalViewModel<ViewInfo> {

    private SparseArray<ViewInfo> mViewInfos;
    private ViewTypeDelegate mViewTypeDelegate;

    public NormalViewModel() {
        mViewInfos = new SparseArray<ViewInfo>();
    }

    public NormalViewModel(@NonNull SparseArray<ViewInfo> viewInfos) {
        this();
        mViewInfos = viewInfos;
    }

    public void setViewTypeDelegate(ViewTypeDelegate viewTypeDelegate) {
        mViewTypeDelegate = viewTypeDelegate;
    }

    public void add(int viewType, ViewInfo bean) {
        if (mViewInfos == null) {
            mViewInfos = new SparseArray<ViewInfo>();
        }
        mViewInfos.append(viewType, bean);
    }

    public void remove(int viewType) {
        if (mViewInfos != null) {
            mViewInfos.remove(viewType);
        }
    }

    public void remove(ViewInfo bean) {
        if (mViewInfos != null) {
            int viewType = mViewInfos.keyAt(mViewInfos.indexOfValue(bean));
            mViewInfos.remove(viewType);
        }
    }

    public ViewInfo get(int viewType) {
        return mViewInfos.get(viewType);
    }

    public SparseArray<ViewInfo> getViewInfos() {
        return mViewInfos;
    }

    public int getViewTypeCount() {
        return mViewInfos == null ? 0 : mViewInfos.size();
    }

    public int getItemViewType(int position) {
        if (mViewTypeDelegate == null) {
            return 0;
        } else {
            return mViewTypeDelegate.getItemViewType(position);
        }
    }
}
