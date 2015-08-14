package com.dcw.app.rating.net.loader;


import android.content.Context;
import android.support.v4.content.Loader;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public abstract class RetrofitLoader<D, R> extends Loader<RetrofitResponse<D>> {

    private final R mService;

    private RetrofitResponse<D> mCachedResponse;

    public RetrofitLoader(Context context, R service) {
        super(context);
        mService = service;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        call(mService, new Callback<D>() {
            @Override
            public void success(D d, Response response) {
                mCachedResponse = new RetrofitResponse<D>();
                mCachedResponse.setIsSuccess(true);
                mCachedResponse.setResult(d);
                mCachedResponse.setResponse(response);
                deliverResult(mCachedResponse);
            }

            @Override
            public void failure(RetrofitError error) {
                mCachedResponse = new RetrofitResponse<D>();
                mCachedResponse.setIsSuccess(false);
                mCachedResponse.setError(error);
                deliverResult(mCachedResponse);
            }
        });
    }

    @Override
    protected void onForceLoad() {
        super.onForceLoad();
    }

    @Override
    protected void onReset() {
        super.onReset();
        mCachedResponse = null;
    }

    public abstract void call(R service, Callback<D> callback);
}
