package com.dcw.app.rating.biz.contact.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.dcw.app.rating.R;
import com.dcw.app.rating.ui.mvc.core.Observable;
import com.dcw.framework.view.DCWAnnotation;
import com.dcw.framework.view.annotation.InjectView;

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
public class LetterIndexView extends FrameLayout implements com.dcw.app.rating.ui.mvc.View<LetterIndexView.ViewListener> {

    @InjectView(R.id.sidebar)
    SideBar mSideBar;
    ListView mListView;
    @InjectView(R.id.tv_float)
    TextView mFloatView;

    ViewListener mListener;

    public LetterIndexView(Context context) {
        super(context);
    }

    public LetterIndexView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LetterIndexView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setViewListener(ViewListener listener) {
        mListener = listener;
    }

    public ListView getListView() {
        return mListView;
    }

    public void setListView(ListView listView) {
        mListView = listView;
    }

    @Override
    public <T> void update(Observable observable, T data, Object... args) {

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        DCWAnnotation.inject(this);
        mSideBar.setTextView(mFloatView);
        mSideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                if (SideBar.b[0].equals(s)) {
                    mListView.smoothScrollToPosition(0);
                    return;
                }
                if (mListener != null) {
                    mListener.onTouchingLetterChanged(s);
                }
            }
        });
    }

    public interface ViewListener {
        void onTouchingLetterChanged(String s);
    }
}
