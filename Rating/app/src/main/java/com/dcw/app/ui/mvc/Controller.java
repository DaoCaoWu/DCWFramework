package com.dcw.app.ui.mvc;

/**
 * Created by adao12 on 2015/10/18.
 */
public class Controller<V, M> {

    private V mView;

    private M mModel;

    public Controller(V view, M model) {
        mView = view;
        mModel = model;
    }

    public V getView() {
        return mView;
    }

    public M getModel() {
        return mModel;
    }
}
