package com.dcw.app.rating.biz.contact;

import android.database.Cursor;

/**
 * <p>Title: ucweb</p>
 * <p/>
 * <p>Description: </p>
 * ......
 * <p>Copyright: Copyright (c) 2015</p>
 * <p/>
 * <p>Company: ucweb.com</p>
 *
 * @author JiaYing.Cheng
 * @version 1.0
 * @email adao12.vip@gmail.com
 * @create 15/10/22
 */
public class CursorModel<D> {

    Cursor mCursor;

    public CursorModel() {
    }

    public CursorModel(Cursor cursor) {
        mCursor = cursor;
    }

    public void setCursor(Cursor cursor) {
        mCursor = cursor;
    }

    public Cursor getCursor() {
        return mCursor;
    }

    public void closeCursor() {
        if (mCursor != null && !mCursor.isClosed()) {
            mCursor.close();
            mCursor.getPosition();
        }
    }


}
