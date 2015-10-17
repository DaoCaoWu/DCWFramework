package com.dcw.app.rating.ui.mvc;

import com.dcw.app.rating.ui.mvc.core.Observable;

/**
 * Created by adao12 on 2015/10/17.
 */
public class ViewController<V extends View, M extends Observable> {

    private V mView;

    private M mModel;

    public ViewController(V view, M model) {
        mView = view;
        mModel = model;
        registerObserver();
    }

    public V getView() {
        return mView;
    }

    public M getModel() {
        return mModel;
    }

    public void registerObserver() {
        getModel().addObserver(getView());
    }

    public void unregisterObserver() {
        getModel().deleteObserver(getView());
    }
}
