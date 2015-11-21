package com.dcw.app.biz.welcome;

import android.os.Handler;

import com.dcw.app.R;
import com.dcw.app.biz.MainActivity;
import com.dcw.app.biz.account.LoginFragment;
import cn.ninegame.library.ui.framework.BaseFragmentWrapper;
import com.dcw.framework.view.annotation.InjectLayout;

@InjectLayout(R.layout.fragment_welcome)
public class WelcomeFragment extends BaseFragmentWrapper {

    @Override
    public Class getHostActivity() {
        return MainActivity.class;
    }

    @Override
    public void initData() {
    }

    @Override
    public void initUI() {

    }

    @Override
    public void initListeners() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getActivity().getSupportFragmentManager().popBackStack();
                startFragment(LoginFragment.class);
            }
        }, 1500);
    }
}
