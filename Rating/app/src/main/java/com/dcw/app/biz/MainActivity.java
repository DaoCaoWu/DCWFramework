package com.dcw.app.biz;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewConfiguration;

import com.dcw.app.BuildConfig;
import com.dcw.app.biz.welcome.WelcomeFragment;
import com.fragmentmaster.app.FragmentMaster;
import com.fragmentmaster.app.IMasterFragment;

import java.lang.reflect.Field;

import cn.ninegame.framework.ToastManager;
import cn.ninegame.framework.adapter.BaseActivityWrapper;

;


public class MainActivity extends BaseActivityWrapper {

    public static final String TAG = "MainActivity";

    private FragmentMaster.FragmentLifecycleCallbacks mLifecycleCallbacks =
            new FragmentMaster.SimpleFragmentLifecycleCallbacks() {
                @Override
                public void onFragmentResumed(IMasterFragment fragment) {
                    if (BuildConfig.DEBUG)
                        Log.d(TAG, "[onResume]     " + fragment.toString());
                }

                @Override
                public void onFragmentActivated(IMasterFragment fragment) {
                    if (BuildConfig.DEBUG)
                        Log.d(TAG, "[onActivated]  " + fragment.toString());
                }

                @Override
                public void onFragmentDeactivated(IMasterFragment fragment) {
                    if (BuildConfig.DEBUG)
                        Log.d(TAG, "[onDeactivated]" + fragment.toString());
                }

                @Override
                public void onFragmentPaused(IMasterFragment fragment) {
                    if (BuildConfig.DEBUG)
                        Log.d(TAG, "[onPaused]     " + fragment.toString());
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getOverflowMenu();
        startFramework();
    }

    public void startFramework() {
        getSupportFragmentManager().beginTransaction().add(android.R.id.content, new WelcomeFragment()).commit();
        getFragmentMaster().registerFragmentLifecycleCallbacks(mLifecycleCallbacks);
        ToastManager.getInstance().init(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getFragmentMaster().unregisterFragmentLifecycleCallbacks(mLifecycleCallbacks);
    }

    private void getOverflowMenu() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
