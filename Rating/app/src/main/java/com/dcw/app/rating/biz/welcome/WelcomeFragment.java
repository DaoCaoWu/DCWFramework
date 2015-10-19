package com.dcw.app.rating.biz.welcome;

import android.os.Handler;

import com.dcw.app.rating.R;
import com.dcw.app.rating.biz.MainActivity;
import com.dcw.app.rating.biz.account.LoginFragment;
import com.dcw.app.rating.ui.adapter.BaseFragmentWrapper;
import com.dcw.framework.view.annotation.InjectLayout;

/**
 * Created by adao12 on 2015/8/4.
 */
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
