package com.dcw.app.rating.biz.welcome;

import android.os.Handler;

import com.dcw.app.R;
import com.dcw.app.rating.biz.MainActivity;
import com.dcw.app.rating.biz.home.HomeFragment;
import com.dcw.app.rating.biz.share.ShareFragment;
import com.dcw.app.ui.framework.BaseFragmentWrapper;
import com.dcw.framework.view.annotation.InjectLayout;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

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
                startFragment(ShareFragment.class);
            }
        }, 1500);
    }
}
