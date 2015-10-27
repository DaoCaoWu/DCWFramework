package com.dcw.app.rating.biz;

import android.os.Bundle;
import android.view.ViewConfiguration;

import com.dcw.app.rating.ui.framework.BaseActivityWrapper;
import com.dcw.app.rating.ui.framework.FrameworkManifest;
import com.dcw.app.rating.ui.framework.ToastManager;
import com.dcw.framework.pac.basic.FrameworkFacade;

import java.lang.reflect.Field;


public class MainActivity extends BaseActivityWrapper {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getOverflowMenu();
        startFramework();
    }

    public void startFramework() {
        FrameworkFacade.getInstance().start(new FrameworkManifest(), this);
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
