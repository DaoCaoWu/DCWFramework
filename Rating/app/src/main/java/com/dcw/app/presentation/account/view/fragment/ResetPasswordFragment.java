package com.dcw.app.presentation.account.view.fragment;

import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.dcw.app.R;
import com.dcw.app.presentation.toolbar.NavigationBar;
import com.dcw.app.presentation.toolbar.ToolbarController;
import com.dcw.app.presentation.toolbar.ToolbarModel;
import com.dcw.framework.view.annotation.InjectLayout;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import cn.ninegame.framework.adapter.BaseFragmentWrapper;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/11/29.
 */
@InjectLayout(R.layout.fragment_reset_password)
public class ResetPasswordFragment extends BaseFragmentWrapper {
    @Bind(R.id.toolbar)
    NavigationBar mToolbar;
    @Bind(R.id.et_password)
    EditText mEtPassword;
    @Bind(R.id.til_password)
    TextInputLayout mTilPassword;
    @Bind(R.id.btn_next)
    Button mBtnNext;

    @Override
    public void initUI() {
        mToolbarController = new ToolbarController(mToolbar
                , new ToolbarModel(this.getClass().getSimpleName(), true));
    }

    @OnClick(R.id.btn_next)
    public void onResetPassword() {
        finish();
    }

    @OnTextChanged(value = R.id.et_password,
            callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void afterUserNameAndPasswordTextChanged() {
        if (TextUtils.isEmpty(mTilPassword.getEditText().getText().toString())
                || isFormatPassword(mTilPassword.getEditText().getText().toString().trim())) {
            mTilPassword.setHint(getString(R.string.password));
        } else {
            mTilPassword.setHint(getString(R.string.please_input_the_format_password));
        }
        mBtnNext.setEnabled(isFormatPassword(mTilPassword.getEditText().getText().toString().trim()));
    }

    /**
     * 是否是合法的手机号
     */
    public static boolean isFormatPassword(CharSequence password) {
        if (TextUtils.isEmpty(password) || password.length() < 6) {
            return false;
        }
        return true;
    }
}
