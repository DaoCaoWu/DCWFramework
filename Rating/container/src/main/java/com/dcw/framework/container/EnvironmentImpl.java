package com.dcw.framework.container;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

final class EnvironmentImpl implements Environment {

    Activity mActivity;
    FragmentCenter mFragmentCenter;
    Context mApplicationContext;

    public void setFragmentCenter(FragmentCenter center) {
        mFragmentCenter = center;
    }

    @Override
    public void setCurrentActivity(Activity activity) {
        mActivity = activity;
    }

    @Override
    public Context getApplicationContext() {
        return mApplicationContext;
    }

    @Override
    public void setApplicationContext(Context applicationContext) {
        mApplicationContext = applicationContext;
    }

    @Override
    public Activity getCurrentActivity() {
        return  mActivity;
    }

    @Override
    public void startFragment(String fragmentID, Bundle param){
        startFragment(fragmentID, param, false, BaseFragment.LAUNCHER_MODE_NORMAL);
    }

    @Override
    public void startFragmentForResult(String fragmentID, Bundle param, IResultListener listener, boolean useAnim, boolean forceNew) {
        mFragmentCenter.startFragmentForResult(fragmentID, param, listener, useAnim,
                forceNew ? BaseFragment.LAUNCHER_MODE_NORMAL : BaseFragment.LAUNCHER_MODE_REUSE);
    }

    public void startDialogFragment(String fragmentID, Bundle param){
        startDialogFragment(fragmentID, param, null);
    }

    public void startDialogFragment(String fragmentID, Bundle param, IResultListener listener){
        mFragmentCenter.startDialogFragment(fragmentID, param, listener);
    }

    @Override
    public void startFragmentWithFlag(String fragmentName, Bundle param, boolean useAnim, int intentFlag){
        mFragmentCenter.startFragmentWithFlag(fragmentName, param, useAnim, intentFlag);
    }

    @Override
    public void startFragment(String fragmentName, Bundle param, boolean useAnim, int mode){
        mFragmentCenter.startFragmentForResult(fragmentName, param, null, useAnim, mode);
    }

    @Override
    public void startFragmentForResult(String fragmentID, Bundle param, IResultListener listener, boolean useAnim, int mode) {
        mFragmentCenter.startFragmentForResult(fragmentID, param, listener, useAnim, mode);
    }
}
