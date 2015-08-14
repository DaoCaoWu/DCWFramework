package com.dcw.app.rating.net.loader;

import retrofit.RetrofitError;
import retrofit.client.Response;

class RetrofitResponse<D> {

    private RetrofitError mError;
    private Response mResponse;
    private boolean mIsSuccess;
    private D mResult;

    public RetrofitError getError() {
        return mError;
    }

    public void setError(RetrofitError error) {
        this.mError = error;
    }

    public Response getResponse() {
        return mResponse;
    }

    public void setResponse(Response response) {
        this.mResponse = response;
    }

    public boolean isSuccess() {
        return mIsSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.mIsSuccess = isSuccess;
    }

    public D getResult() {
        return mResult;
    }

    public void setResult(D result) {
        this.mResult = result;
    }
}
