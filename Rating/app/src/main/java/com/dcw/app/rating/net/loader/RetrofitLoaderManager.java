package com.dcw.app.rating.net.loader;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import retrofit.Callback;

public class RetrofitLoaderManager {

    public static <D, R> void initLoader(final Fragment fragment,
                                         final RetrofitLoader<D, R> loader,
                                         final Callback<D> callback) {
        fragment.getLoaderManager().initLoader(loader.getClass().hashCode(), Bundle.EMPTY, new LoaderManager.LoaderCallbacks<RetrofitResponse<D>>() {

            @Override
            public Loader<RetrofitResponse<D>> onCreateLoader(int id, Bundle args) {

                return loader;
            }

            @Override
            public void onLoadFinished(Loader<RetrofitResponse<D>> loader, RetrofitResponse<D> response) {

                if (response.isSuccess()) {
                    callback.success(response.getResult(), response.getResponse());
                } else {
                    callback.failure(response.getError());
                }
            }

            @Override
            public void onLoaderReset(Loader<RetrofitResponse<D>> loader) {

                //Nothing to do here
            }


        });
    }
}
