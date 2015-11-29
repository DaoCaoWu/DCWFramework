package com.dcw.app.biz.account.fragment;

import android.app.Activity;
import android.content.Intent;

import com.dcw.app.R;
import com.dcw.app.app.App;
import com.dcw.app.biz.account.LoginController;
import com.dcw.app.biz.account.LoginView;
import com.dcw.app.biz.account.UserModel;
import com.dcw.app.biz.toolbar.ToolbarController;
import com.dcw.app.biz.toolbar.ToolbarModel;
import com.dcw.app.di.component.DaggerUIComponent;
import com.dcw.app.di.component.UIComponent;
import com.dcw.app.di.module.UIModule;
import com.dcw.framework.view.annotation.InjectLayout;
import com.umeng.login.LoginManager;
import com.umeng.socialize.bean.SHARE_MEDIA;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
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

    @OnClick(R.id.btn_submit)
    public void previewPhotos() {
        PhotoPickerIntent intent = new PhotoPickerIntent(getActivity());
        intent.setPhotoCount(9);
        intent.setShowCamera(true);
        intent.setShowGif(true);
        startActivityForResult(intent, Activity.RESULT_OK);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.btn_qq)
    public void onClick() {
        LoginManager.getInstance().login(getActivity(), SHARE_MEDIA.QQ);
    }

}
