package com.dcw.app.rating.biz.contact;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.dcw.app.rating.R;
import com.dcw.app.rating.ui.mvc.View;
import com.dcw.app.rating.ui.mvc.core.Observable;
import com.dcw.framework.view.DCWAnnotation;
import com.dcw.framework.view.annotation.InjectView;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/10/21.
 */
public class ContactListView extends LinearLayout implements View<ContactListView.ViewListener> {
    @InjectView(R.id.lv_list)
    private ListView mListView;
    private ViewListener mListener;

    public ContactListView(Context context) {
        super(context);
    }

    public ContactListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListView getListView() {
        return mListView;
    }

    @Override
    public void setViewListener(ViewListener listener) {
        mListener = listener;
    }

    @Override
    public <T> void update(Observable observable, T data, Object... args) {

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        DCWAnnotation.inject(this);
    }

    public static interface ViewListener {

    }
}
