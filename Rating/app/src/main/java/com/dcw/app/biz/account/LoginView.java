package com.dcw.app.biz.account;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.dcw.app.R;
import cn.ninegame.library.component.mvc.BaseView;
import cn.ninegame.library.component.mvc.core.Observable;
import com.dcw.framework.view.DCWAnnotation;
import com.dcw.framework.view.annotation.InjectView;

public class LoginView extends LinearLayout implements BaseView<LoginView.ViewListener> {

    @InjectView(R.id.et_account)
    private EditText mEtAccount;
    @InjectView(R.id.et_password)
    private EditText mEtPassword;
    @InjectView(R.id.btn_submit)
    private View mBtnSubmit;

    /**
     * The listener reference for sending events
     */
    private ViewListener mViewListener;

    public LoginView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setViewListener(ViewListener mViewListener) {
        this.mViewListener = mViewListener;
    }

    @Override
    public <T> void update(Observable observable, T data, Object... args) {

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        DCWAnnotation.inject(this);
        mEtAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mViewListener.afterAccountTextChanged(s.toString());
            }
        });
        mEtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mViewListener.afterPasswordTextChanged(s.toString());
            }
        });
        mBtnSubmit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewListener.onSubmitBtnClicked(mEtAccount.getText().toString().trim(), mEtPassword.getText().toString().trim());
            }
        });
    }

    public interface ViewListener {

        void afterAccountTextChanged(String s);

        void afterPasswordTextChanged(String s);

        void onSubmitBtnClicked(String account, String password);
    }
}
