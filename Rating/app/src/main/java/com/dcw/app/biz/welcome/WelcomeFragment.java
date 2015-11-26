package com.dcw.app.biz.welcome;

import android.os.Bundle;
import android.os.Handler;

import com.dcw.app.R;
import com.dcw.app.biz.MainActivity;
import com.dcw.app.biz.account.LoginFragment;
import cn.ninegame.framework.adapter.BaseFragmentWrapper;

import com.dcw.app.biz.test.RichTextFragment;
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

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRootView.postDelayed(new Runnable() {
            @Override
            public void run() {
                getActivity().getSupportFragmentManager().popBackStack();
                startFragment(LoginFragment.class);
            }
        }, 1500);
    }
}
