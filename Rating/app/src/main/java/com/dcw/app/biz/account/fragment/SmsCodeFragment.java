package com.dcw.app.biz.account.fragment;

import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.dcw.app.R;
import com.dcw.app.biz.toolbar.NavigationBar;
import com.dcw.app.biz.toolbar.ToolbarController;
import com.dcw.app.biz.toolbar.ToolbarModel;
import com.dcw.framework.view.annotation.InjectLayout;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import cn.ninegame.framework.adapter.BaseFragmentWrapper;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/11/29.
 */
@InjectLayout(R.layout.fragment_register_sms_code)
public class SmsCodeFragment extends BaseFragmentWrapper {

    @Bind(R.id.btn_next)
    Button mBtnNext;
    @Bind(R.id.til_account)
    TextInputLayout mTilAccount;
    @Bind(R.id.et_sms_code)
    EditText mEtSmsCode;
    @Bind(R.id.toolbar)
    NavigationBar mToolbar;

    @Override
    public void initUI() {
        mToolbarController = new ToolbarController(mToolbar
                , new ToolbarModel(this.getClass().getSimpleName(), true));
        mEtSmsCode.requestFocus();
    }

    @OnTextChanged(value = R.id.et_sms_code,
            callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void afterSmsTextChanged() {
        if (TextUtils.isEmpty(mTilAccount.getEditText().getText().toString())
                || isFormat(mTilAccount.getEditText().getText().toString().trim())) {
            mTilAccount.setHint(getString(R.string.please_input_phone));
        } else {
            mTilAccount.setHint(getString(R.string.please_input_the_right_phone_number));
        }
        mBtnNext.setEnabled(isFormat(mTilAccount.getEditText().getText().toString().trim()));
    }

    /**
     * 是否是合法的手机号
     */
    public static boolean isFormat(CharSequence str) {
        if (TextUtils.isEmpty(str) || str.length() < 6) {
            return false;
        }
        return true;
    }

    @OnClick(R.id.btn_next)
    public void onBtnNextClicked() {
        finish();
        if ("reset".equals(getRequest().getStringExtra("for"))) {
            startFragment(ResetPasswordFragment.class);
        } else {
            startFragment(RegisterFragment.class);
        }
    }
}
