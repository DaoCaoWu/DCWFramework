package com.dcw.app.biz.account.fragment;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dcw.app.R;
import com.dcw.app.app.App;
import com.dcw.app.biz.account.LoginController;
import com.dcw.app.biz.account.LoginView;
import com.dcw.app.biz.account.UserModel;
import com.dcw.app.biz.toolbar.NavigationBar;
import com.dcw.app.biz.toolbar.ToolbarController;
import com.dcw.app.biz.toolbar.ToolbarModel;
import com.dcw.app.di.component.DaggerUIComponent;
import com.dcw.app.di.component.UIComponent;
import com.dcw.app.di.module.UIModule;
import com.dcw.framework.view.annotation.InjectLayout;
import com.fragmentmaster.app.Request;
import com.umeng.login.LoginManager;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import cn.ninegame.framework.ToastManager;
import cn.ninegame.framework.adapter.BaseFragmentWrapper;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import me.iwf.photopicker.utils.PhotoPickerIntent;

@InjectLayout(R.layout.fragment_login)
public class LoginFragment extends BaseFragmentWrapper {
    UIComponent mUIComponent;
    EventHandler mEventHandler;
    @Inject
    ToastManager mToastManager;
    @Bind(R.id.btn_submit)
    Button mBtnSubmit;
    @Bind(R.id.btn_wechat)
    ImageButton mBtnWechat;
    @Bind(R.id.btn_qq)
    ImageButton mBtnQq;
    @Bind(R.id.btn_sina)
    ImageButton mBtnSina;
    @Bind(R.id.container)
    LinearLayout mContainer;
    @Bind(R.id.btn_next)
    Button mBtnNext;
    @Bind(R.id.til_account)
    TextInputLayout mTilAccount;
    @Bind(R.id.til_password)
    TextInputLayout mTilPassword;
    @Bind(R.id.toolbar)
    NavigationBar mToolbar;
    @Bind(R.id.tv_reset)
    TextView mTvReset;

    @Override
    public void initData() {
        mUIComponent = DaggerUIComponent.builder()
                .appComponent(((App) getActivity().getApplication()).getAppComponent())
                .uIModule(new UIModule())
                .build();
        mUIComponent.inject(this);
        SMSSDK.initSDK(getActivity(), "cbf2e2e5ff90", "66a5a1d3ffb628eb5e5f30c7c95cd980");
        mEventHandler = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {

                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取验证码成功
                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        //返回支持发送验证码的国家列表
                    }
                } else {
                    ((Throwable) data).printStackTrace();
                }
            }
        };
        SMSSDK.registerEventHandler(mEventHandler);
    }

    @Override
    public void initUI() {
        new LoginController((LoginView) findViewById(R.id.root_view), new UserModel());
        mToolbarController = new ToolbarController(mToolbar
                , new ToolbarModel(this.getClass().getSimpleName(), false));
    }

    @OnClick(R.id.btn_submit)
    public void onSignIn(View view) {
        Snackbar.make(view, "hehe", Snackbar.LENGTH_SHORT).show();
//        PhotoPickerIntent intent = new PhotoPickerIntent(getActivity());
//        intent.setPhotoCount(9);
//        intent.setShowCamera(true);
//        intent.setShowGif(true);
//        startActivityForResult(intent, Activity.RESULT_OK);
    }

    @OnClick(R.id.btn_next)
    public void onSignUp() {
        startFragment(PhoneFragment.class);
    }

    @OnTextChanged(value = {R.id.et_account, R.id.et_password},
            callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void afterAccountAndPasswordTextChanged() {
        if (TextUtils.isEmpty(mTilAccount.getEditText().getText().toString())
                || isPhoneNumber(mTilAccount.getEditText().getText().toString().trim())) {
            mTilAccount.setHint(getString(R.string.phone));
        } else {
            mTilAccount.setHint(getString(R.string.please_input_the_right_phone_number));
        }
        if (TextUtils.isEmpty(mTilPassword.getEditText().getText().toString())
                || isFormatPassword(mTilPassword.getEditText().getText().toString().trim())) {
            mTilPassword.setHint(getString(R.string.password));
        } else {
            mTilPassword.setHint(getString(R.string.please_input_the_format_password));
        }
        mBtnSubmit.setEnabled(isFormatPassword(mTilPassword.getEditText().getText().toString().trim())
                && isPhoneNumber(mTilAccount.getEditText().getText().toString().trim()));
    }

    /**
     * 是否是合法的手机号
     */
    public static boolean isPhoneNumber(CharSequence phone) {
        if (TextUtils.isEmpty(phone)) {
            return false;
        }
        Pattern p = Pattern
                .compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(phone);
        return m.matches();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(mEventHandler);

    }

    UIComponent getUIComponent() {
        return mUIComponent;
    }

    @OnClick(R.id.btn_qq)
    public void onClick() {
        LoginManager.getInstance().login(getActivity(), SHARE_MEDIA.QQ);
    }

    @OnClick(R.id.tv_reset)
    public void onTvResetClicked() {
        Request request = new Request(PhoneFragment.class);
        request.putExtra("for", "reset");
        startFragment(request);
    }

    @OnClick({R.id.et_account, R.id.et_password, R.id.btn_submit, R.id.btn_wechat, R.id.btn_qq, R.id.btn_sina, R.id.btn_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.et_account:
                break;
            case R.id.et_password:
                break;
            case R.id.btn_submit:
                break;
            case R.id.btn_wechat:
                break;
            case R.id.btn_qq:
                break;
            case R.id.btn_sina:
                break;
            case R.id.btn_next:
                break;
        }
    }
}
