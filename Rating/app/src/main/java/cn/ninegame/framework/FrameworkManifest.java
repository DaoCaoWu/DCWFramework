package cn.ninegame.framework;


import com.dcw.framework.pac.basic.IFrameworkManifest;

import cn.ninegame.framework.adapter.LauncherController;

public class FrameworkManifest implements IFrameworkManifest {
    @Override
    public String[] getControllers() {
        String[] controllers = new String[]{
                LauncherController.class.getName(),
        };
        return controllers;
    }

    @Override
    public String[] getDynamicContorllers() {
        return null;
    }
}
