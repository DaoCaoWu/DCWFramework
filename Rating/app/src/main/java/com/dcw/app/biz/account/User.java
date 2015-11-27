package com.dcw.app.biz.account;

import cn.bmob.v3.BmobObject;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/11/21.
 */
public class User extends BmobObject {

    private String mUserName;
    private String mPassword;
    private String mEmail;

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }
}
