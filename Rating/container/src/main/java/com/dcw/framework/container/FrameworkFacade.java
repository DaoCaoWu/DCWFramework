package com.dcw.framework.container;

import android.content.Context;
import android.os.Looper;

/**
 * 职责：承担了IOC中容器的角色，负责初始化整个框架层，并给相关的核心类设置依赖关系，注入依赖对象。
 */
public final class FrameworkFacade {
    private static FrameworkFacade sInstance = null;
    private boolean mIsStarted = false;

    EnvironmentCenter mEnvironmentCenter;

    private FrameworkFacade() {
    }

    public static FrameworkFacade getInstance() {
        if (sInstance == null) {
            synchronized (FrameworkFacade.class) {
                if (sInstance == null) {
                    sInstance = new FrameworkFacade();
                }
            }
        }

        return sInstance;
    }

    /**
     * 启动框架，运行作为Launcher的Controller
     *
     * @param applicationContext 应用程序的ApplicationContext
     */
    public void start(Context applicationContext) {
        if (mIsStarted) {
            return;
        } else {
            mIsStarted = true;
        }
        if (applicationContext == null) {
            return;
        }

        checkThread();

        EnvironmentImpl environment = new EnvironmentImpl();
        environment.setApplicationContext(applicationContext);

        FragmentCenter fragmentCenter = new FragmentCenter();
        fragmentCenter.setEnvironment(environment);
        environment.setFragmentCenter(fragmentCenter);

        mEnvironmentCenter = new EnvironmentCenter();
        mEnvironmentCenter.setEnvironment(environment);
    }

    public Environment getEnvironment() {
        return mEnvironmentCenter.getEnvironment();
    }

    private static void checkThread() {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new RuntimeException("Framework must be started in UI Thread");
        }
    }
}
