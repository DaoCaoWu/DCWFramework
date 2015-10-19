package com.dcw.app.rating.biz.account;

import com.dcw.app.rating.ui.mvc.Model;

/**
 * Created by adao12 on 2015/10/17.
 */
public class UserModel extends Model {

    private Account mAccount;

    public UserModel() {
        mAccount = new Account();
    }

    public Account getAccount() {
        return mAccount;
    }

    public void setAccount(String account) {
        mAccount.setAccount(account);
    }

    public void setPassword(String password) {
        mAccount.setPassword(password);
    }
}
