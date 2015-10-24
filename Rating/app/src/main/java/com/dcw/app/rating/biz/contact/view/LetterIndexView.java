package com.dcw.app.rating.biz.contact.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.dcw.app.rating.R;
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
public class LetterIndexView extends FrameLayout {

    @InjectView(R.id.sidebar)
    SideBar mSideBar;
    @InjectView(R.id.tv_float)
    TextView mFloatView;

    SideBar.OnTouchingLetterChangedListener mTouchingLetterChangedListener;

    public LetterIndexView(Context context) {
        super(context);
    }

    public LetterIndexView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LetterIndexView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setTouchingLetterChangedListener(SideBar.OnTouchingLetterChangedListener touchingLetterChangedListener) {
        mTouchingLetterChangedListener = touchingLetterChangedListener;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        DCWAnnotation.inject(this);
        mSideBar.setTextView(mFloatView);
        mSideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                if (mTouchingLetterChangedListener != null) {
                    mTouchingLetterChangedListener.onTouchingLetterChanged(s);
                }
            }
        });
    }
}
