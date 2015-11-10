package com.dcw.app.ui.framework;


import com.dcw.framework.pac.basic.IFrameworkManifest;

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
