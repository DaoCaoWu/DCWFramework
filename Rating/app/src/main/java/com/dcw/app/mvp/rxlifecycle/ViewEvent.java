package com.dcw.app.mvp.rxlifecycle;

/**
 * create by adao12.vip@gmail.com on 15/12/3
 *
 * @author JiaYing.Cheng
 * @version 1.0
 */

import android.support.annotation.NonNull;
import android.view.View;

/**
 * A target view on which an event occurred (e.g., click).
 * <p>
 * <strong>Warning:</strong> Instances keep a strong reference to the view. Operators that cache
 * instances have the potential to leak the associated {@link Context}.
 */
public abstract class ViewEvent<T extends View> {
    private final T view;


    protected ViewEvent(@NonNull T view) {
        this.view = view;
    }


    /** The view from which this event occurred. */
    public @NonNull T view() {
        return view;
    }
}
