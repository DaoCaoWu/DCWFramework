package com.dcw.framework.container;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public abstract class IResultListener implements Parcelable{
    public abstract void onResult(Bundle result);
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
