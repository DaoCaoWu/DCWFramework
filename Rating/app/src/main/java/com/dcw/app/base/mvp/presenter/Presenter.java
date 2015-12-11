package com.dcw.app.base.mvp.presenter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * create by adao12.vip@gmail.com on 15/11/30
 *
 * @author JiaYing.Cheng
 * @version 1.0
 */
public class Presenter<V> implements PresenterLifeCycle<V> {

    private String mId;
    private V mView;

    public Presenter() {
        mId = generatePresenterId();
    }

    @Nullable
    public String getId() {
        return mId;
    }

    //最大程度保持线程安全。虽然主线程是非常安全的。
    private String generatePresenterId() {
        return hashCode() + "/" + System.nanoTime() + "/" + (int) (Math.random() * Integer.MAX_VALUE);
    }

    protected final V getView() {
        return mView;
    }

    protected final boolean hasView() {
        return mView == null;
    }

    public void takeView(@NonNull V view) {
        if (mView != view) {
            if (mView != null) {
                dropView(mView);
            }
            mView = view;
            onTakeView(mView);
        }
    }

    public void dropView(@NonNull V view) {
        if (mView == view) {
            mView = null;
            onDropView();
        }
    }

    public void create(@Nullable Bundle savedInstanceState) {
        onCreate(savedInstanceState);
    }

    public void destroy() {
        onDestroy();
    }

    public void load(Bundle savedInstanceState) {
        if (hasView()) {
            onLoad(savedInstanceState);
        }
    }

    public void save(Bundle savedInstanceState) {
        onSave(savedInstanceState);
    }

    public void dropView() {
        onDropView();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onLoad(Bundle savedInstanceState) {

    }

    @Override
    public void onSave(Bundle savedInstanceState) {

    }

    @Override
    public void onTakeView(V view) {

    }

    @Override
    public void onDropView() {

    }

    public interface Factory {

        Presenter<?> getPresenter();
    }

}
