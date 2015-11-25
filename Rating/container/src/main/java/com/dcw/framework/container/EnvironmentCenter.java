package com.dcw.framework.container;

public class EnvironmentCenter implements EnvironmentCallback {

    private Environment mEnvironment = null;

    public EnvironmentCenter() {
    }

    @Override
    public void setEnvironment(Environment environment) {
        mEnvironment = environment;
    }

    @Override
    public Environment getEnvironment() {
        return mEnvironment;
    }
}
