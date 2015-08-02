package com.dcw.app.rating.biz;

import android.os.Bundle;
import android.view.Menu;
import android.view.ViewConfiguration;

import com.dcw.app.rating.R;
import com.dcw.app.rating.ui.adapter.BaseActivityWrapper;
import com.dcw.app.rating.ui.adapter.FrameworkManifest;
import com.dcw.app.rating.ui.adapter.ToastManager;
import com.dcw.framework.pac.basic.FrameworkFacade;

import java.lang.reflect.Field;


public class MainActivity extends BaseActivityWrapper {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getOverflowMenu();
        startFramework();
    }

    public void startFramework() {
        FrameworkFacade.getInstance().start(new FrameworkManifest(), this);
        ToastManager.getInstance().init(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void getOverflowMenu() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if(menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
