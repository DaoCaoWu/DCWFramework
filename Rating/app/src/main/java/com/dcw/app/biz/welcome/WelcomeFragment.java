package com.dcw.app.biz.welcome;

import android.os.Bundle;

import com.dcw.app.R;
import com.dcw.app.biz.account.fragment.LoginFragment;
import com.dcw.app.biz.test.RichTextFragment;
import com.dcw.framework.view.annotation.InjectLayout;
import com.fragmentmaster.app.Request;

import cn.ninegame.framework.adapter.BaseFragmentWrapper;

@InjectLayout(R.layout.fragment_welcome)
public class WelcomeFragment extends BaseFragmentWrapper {

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
                getActivity().getSupportFragmentManager().beginTransaction().remove(WelcomeFragment.this).commit();
                getFragmentMaster().install(android.R.id.content, new Request(RichTextFragment.class), true);
            }
        }, 1500);
    }
}
