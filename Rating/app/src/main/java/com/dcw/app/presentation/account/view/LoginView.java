package com.dcw.app.presentation.account.view;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/12/10.
 */
public interface LoginView {

    void initComponent();

    String getAccount();

    void setAccount(String account);

    String getPassword();

    void setPassword(String password);
}
