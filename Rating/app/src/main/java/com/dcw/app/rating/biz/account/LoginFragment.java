package com.dcw.app.rating.biz.account;

import com.dcw.app.rating.R;
import com.dcw.app.rating.biz.MainActivity;
import com.dcw.app.rating.ui.adapter.BaseFragmentWrapper;
import com.dcw.framework.view.annotation.InjectLayout;

/**
 * Created by adao12 on 2015/8/12.
 */
@InjectLayout(R.layout.fragment_login)
public class LoginFragment extends BaseFragmentWrapper {

    @Override
    public Class getHostActivity() {
        return MainActivity.class;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initUI() {
        new LoginController((LoginView)findViewById(R.id.root_view), new UserModel());
    }

    @Override
    public void initListeners() {

    }
}
