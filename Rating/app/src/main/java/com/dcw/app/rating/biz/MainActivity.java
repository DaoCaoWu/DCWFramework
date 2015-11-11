package com.dcw.app.rating.biz;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;

import com.dcw.app.ui.framework.BaseActivityWrapper;
import com.dcw.app.ui.framework.FrameworkManifest;
import com.dcw.app.ui.framework.ToastManager;
import com.dcw.framework.pac.basic.FrameworkFacade;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.lang.reflect.Field;

;


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
