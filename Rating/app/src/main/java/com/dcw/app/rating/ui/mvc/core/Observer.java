package com.dcw.app.rating.ui.mvc.core;

public interface Observer {

    /**
     * This method is called if the specified {@code Observable} object's
     * {@code notifyObservers} method is called (because the {@code Observable}
     * object has been updated.
     *
     * @param observable the {@link com.dcw.app.rating.ui.mvc.core.Observable} object.
     * @param data       the data passed to {@link com.dcw.app.rating.ui.mvc.core.Observable#notifyObservers(Object)}.
     */
    <T> void update(Observable observable, T data, Object... args);
}
