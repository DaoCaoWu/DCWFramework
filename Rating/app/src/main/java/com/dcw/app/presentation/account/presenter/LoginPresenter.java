package com.dcw.app.presentation.account.presenter;

import com.dcw.app.base.mvp.presenter.Presenter;
import com.dcw.app.presentation.account.view.LoginView;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/12/10.
 */
public class LoginPresenter extends Presenter<LoginView> {

    public LoginPresenter() {
    }

    @Override
    public void onTakeView(LoginView view) {
        super.onTakeView(view);
        getView().initComponent();
    }
}
