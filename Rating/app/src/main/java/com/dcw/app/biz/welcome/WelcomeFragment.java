package com.dcw.app.biz.welcome;

import android.os.Bundle;

import com.dcw.app.R;
import com.dcw.app.biz.account.fragment.LoginFragment;
import com.dcw.framework.view.annotation.InjectLayout;
import com.fragmentmaster.app.Request;

import java.util.concurrent.TimeUnit;

import cn.ninegame.framework.adapter.BaseFragmentWrapper;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

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
        Observable.timer(2, TimeUnit.SECONDS, AndroidSchedulers.mainThread()).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                getActivity().getSupportFragmentManager().beginTransaction().hide(WelcomeFragment.this).remove(WelcomeFragment.this).commit();
                getFragmentMaster().install(android.R.id.content, new Request(LoginFragment.class), true);
            }
        });
    }
}
