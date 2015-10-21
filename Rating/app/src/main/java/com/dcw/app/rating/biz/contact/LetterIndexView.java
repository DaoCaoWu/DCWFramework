package com.dcw.app.rating.biz.contact;

import android.view.View;
import android.widget.ListView;

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
 * @create 15/10/21
 */
public class LetterIndexView {

    ListView mListView;
    View mFloatView;
    View mCatalogView;

    public LetterIndexView(ListView mListView, View mFloatView, View mCatalogView) {
        this.mListView = mListView;
        this.mFloatView = mFloatView;
        this.mCatalogView = mCatalogView;
    }
}
