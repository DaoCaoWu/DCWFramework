package com.dcw.app.util;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * create by adao12.vip@gmail.com on 15/12/12
 *
 * @author JiaYing.Cheng
 * @version 1.0
 */
public class LogHistory implements Parcelable {

    List<Pair<String, Long>> mPairs;

    public void addEntry(String first, Long second) {
        if (mPairs == null) {
            mPairs = new ArrayList<Pair<String, Long>>();
        }
        mPairs.add(new Pair<String, Long>(first, second));
    }

    public List<Pair<String, Long>> getData() {
        return mPairs;
    }

    public LogHistory() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.mPairs);
    }

    protected LogHistory(Parcel in) {
        this.mPairs = new ArrayList<Pair<String, Long>>();
        in.readList(this.mPairs, List.class.getClassLoader());
    }

    public static final Creator<LogHistory> CREATOR = new Creator<LogHistory>() {
        public LogHistory createFromParcel(Parcel source) {
            return new LogHistory(source);
        }

        public LogHistory[] newArray(int size) {
            return new LogHistory[size];
        }
    };
}
