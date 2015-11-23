package com.dcw.app.biz.account;

import cn.ninegame.framework.ToastManager;
import cn.ninegame.library.component.mvc.ViewController;

/**
 * Created by adao12 on 2015/10/17.
 */
public class LoginController extends ViewController<LoginView, UserModel> implements LoginView.ViewListener {

    public LoginController(LoginView view, UserModel model) {
        super(view, model);
        getView().setViewListener(this);
    }

    @Override
    public void registerObserver() {
    }

    @Override
    public void unregisterObserver() {
    }

    @Override
    public void afterAccountTextChanged(String s) {
        getModel().setAccount(s);
    }

    @Override
    public void afterPasswordTextChanged(String s) {
        getModel().setPassword(s);
    }

    @Override
    public void onSubmitBtnClicked(String account, String password) {
        ToastManager.getInstance().showToast(account + "+" + password);
    }
}
