package com.dcw.app.rating.net.loader;

public interface RequestCallback<D> {

    void onFailure(Exception ex);

    void onSuccess(D result);
}
