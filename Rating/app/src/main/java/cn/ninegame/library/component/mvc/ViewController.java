package cn.ninegame.library.component.mvc;

import cn.ninegame.library.component.mvc.core.Observable;

/**
 * Created by adao12 on 2015/10/17.
 */
public class ViewController<V extends BaseView, M extends Observable> extends Controller<V, M> {

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
