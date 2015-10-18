package com.dcw.app.rating.ui.mvc;

import com.dcw.app.rating.ui.mvc.core.Observable;

/**
 * Created by adao12 on 2015/10/17.
 */
public class ViewController<V extends View, M extends Observable> extends Controller<V, M> {

    public ViewController(V view, M model) {
        super(view, model);
        registerObserver();
    }

    public void registerObserver() {
        getModel().addObserver(getView());
    }

    public void unregisterObserver() {
        getModel().deleteObserver(getView());
    }
}
