package com.dcw.app.base.mvp.factory;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.dcw.app.base.mvp.presenter.Presenter;

import java.util.HashMap;
import java.util.Map;

/**
 * create by adao12.vip@gmail.com on 15/12/1
 *
 * @author JiaYing.Cheng
 * @version 1.0
 */
public class PresenterRepository implements IPresenterRepository {

    private static IPresenterRepository sInstance;

    public static IPresenterRepository getInstance() {
        if (sInstance == null) {
            sInstance = new PresenterRepository();
        }
        return sInstance;
    }

    //使用id当索引保持所有presenter引用，虽然view与presenter是一一对应关系。但避免保持View引用
    private Map<String, Presenter> mIdToPresenter;

    public PresenterRepository() {
        mIdToPresenter = new HashMap<String, Presenter>();
    }

    @Override
    public boolean isExist(String id) {
        return mIdToPresenter.containsKey(id);
    }

    @Nullable
    @Override
    public Presenter<?> get(String id) {
        return mIdToPresenter.get(id);
    }

    @Override
    public void add(@NonNull Presenter<?> presenter) {
        mIdToPresenter.put(providePresenterId(presenter), presenter);
    }

    @Override
    public void remove(String id) {
        mIdToPresenter.remove(id);
    }

    //最大程度保持线程安全。虽然主线程是非常安全的。
    private String providePresenterId(@NonNull Presenter<?> presenter) {
        return  presenter.hashCode() + "/" + System.nanoTime() + "/" + (int)(Math.random() * Integer.MAX_VALUE);
    }
}
