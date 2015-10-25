package com.dcw.app.rating.biz.contact.view.viewholder;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/10/25.
 */
public class ItemViewInfo {

    int mViewTyp;
    int mPosition;

    public ItemViewInfo() {
    }

    public ItemViewInfo(int viewTyp, int position) {
        mViewTyp = viewTyp;
        mPosition = position;
    }

    public int getViewTyp() {
        return mViewTyp;
    }

    public void setViewTyp(int viewTyp) {
        mViewTyp = viewTyp;
    }

    public int getPosition() {
        return mPosition;
    }

    public void setPosition(int position) {
        mPosition = position;
    }
}
