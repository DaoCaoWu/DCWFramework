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
import com.fragmentmaster.app.Request;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import cn.ninegame.framework.adapter.BaseFragmentWrapper;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/11/29.
 */
@InjectLayout(R.layout.fragment_register_phone)
public class PhoneFragment extends BaseFragmentWrapper {

    @Bind(R.id.toolbar)
    NavigationBar mToolbar;
    @Bind(R.id.et_account)
    EditText mEtAccount;
    @Bind(R.id.til_account)
    TextInputLayout mTilAccount;
    @Bind(R.id.btn_next)
    Button mBtnNext;

    @Override
    public void initUI() {
        mToolbarController = new ToolbarController(mToolbar
                , new ToolbarModel(this.getClass().getSimpleName(), true));
        mEtAccount.requestFocus();
    }

    @OnTextChanged(value = R.id.et_account,
            callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void afterPhoneTextChanged() {
        if (TextUtils.isEmpty(mTilAccount.getEditText().getText().toString())
                || LoginFragment.isPhoneNumber(mTilAccount.getEditText().getText().toString().trim())) {
            mTilAccount.setHint(getString(R.string.please_input_phone));
        } else {
            mTilAccount.setHint(getString(R.string.please_input_the_right_phone_number));
        }
        mBtnNext.setEnabled(LoginFragment.isPhoneNumber(mTilAccount.getEditText().getText().toString().trim()));
    }

    @OnClick(R.id.btn_next)
    public void onClick() {
        finish();
        Request request = new Request(SmsCodeFragment.class);
        request.putExtra("for", getRequest().getStringExtra("for"));
        startFragment(request);
    }
}
