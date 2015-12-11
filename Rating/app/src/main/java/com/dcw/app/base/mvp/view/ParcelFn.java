package com.dcw.app.base.mvp.view;

/**
 * create by adao12.vip@gmail.com on 15/12/2
 *
 * @author JiaYing.Cheng
 * @version 1.0
 */
import android.os.Parcel;

class ParcelFn {

    private static final ClassLoader CLASS_LOADER = ParcelFn.class.getClassLoader();

    static <T> T unMarshall(byte[] array) {
        Parcel parcel = Parcel.obtain();
        parcel.unmarshall(array, 0, array.length);
        parcel.setDataPosition(0);
        Object value = parcel.readValue(CLASS_LOADER);
        parcel.recycle();
        return (T)value;
    }

    static byte[] marshall(Object o) {
        Parcel parcel = Parcel.obtain();
        parcel.writeValue(o);
        byte[] result = parcel.marshall();
        parcel.recycle();
        return result;
    }
}
