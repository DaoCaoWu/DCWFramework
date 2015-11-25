package com.dcw.app.biz;

import android.os.Bundle;
import android.view.ViewConfiguration;

import com.dcw.app.biz.welcome.WelcomeFragment;
import com.dcw.framework.container.FrameworkFacade;

import java.lang.reflect.Field;

import cn.ninegame.framework.ToastManager;
import cn.ninegame.framework.adapter.BaseActivityWrapper;

;


public class MainActivity extends BaseActivityWrapper {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getOverflowMenu();
        startFramework();
    }

    public void startFramework() {
        FrameworkFacade.getInstance().start(getApplicationContext());
        FrameworkFacade.getInstance().getEnvironment().setCurrentActivity(this);
        findViewById(android.R.id.content).post(new Runnable() {
            @Override
            public void run() {
                FrameworkFacade.getInstance().getEnvironment().startFragment(WelcomeFragment.class);
            }
        });
        ToastManager.getInstance().init(this);
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
