package com.dcw.app.biz.account;

import android.view.View;
import android.widget.Button;

import com.dcw.app.R;
import com.dcw.app.app.App;
import com.dcw.app.biz.MainActivity;
import com.dcw.app.biz.toolbar.ToolbarController;
import com.dcw.app.biz.toolbar.ToolbarModel;
import com.dcw.app.di.component.DaggerUIComponent;
import com.dcw.app.di.component.UIComponent;
import com.dcw.app.di.module.UIModule;
import com.dcw.framework.view.annotation.InjectLayout;
import com.dcw.framework.view.annotation.InjectView;

import javax.inject.Inject;

import cn.ninegame.framework.ToastManager;
import cn.ninegame.framework.adapter.BaseFragmentWrapper;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

@InjectLayout(R.layout.fragment_login)
public class LoginFragment extends BaseFragmentWrapper {

    UIComponent mUIComponent;
    @Inject
    ToastManager mToastManager;

    @InjectView(R.id.btn_submit)
    Button mBtnSubmit;

    EventHandler mEventHandler;

    @Override
    public Class getHostActivity() {
        return MainActivity.class;
    }

    @Override
    public void initData() {
        mUIComponent = DaggerUIComponent.builder().appComponent(((App) getActivity().getApplication()).getAppComponent()).uIModule(new UIModule()).build();
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
        mToolbarController = new ToolbarController(findViewById(R.id.toolbar), new ToolbarModel(this.getClass().getSimpleName(), false));
        mToastManager.showToast("kwkwkwkww");
//        Toast.makeText(getFragmentComponent().fragment().getActivity(), "dkfjsdjflsjl", Toast.LENGTH_LONG).show();
    }

    @Override
    public void initListeners() {
        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SMSSDK.getVerificationCode("86", "13570320927");
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(mEventHandler);
    }

    UIComponent getUIComponent() {
        return mUIComponent;
    }
}
