package com.dcw.framework.container;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;

public class BaseActivity extends FragmentActivity {

    public final static String INTENT_EXTRA_FRAGMENT_TAG = "ftag";
    public final static String INTENT_EXTRA_LAUNCHER_MODE = "launcherMode";
    private Environment mEnv = null;
    private boolean mIsForeground = false;

    public final static String DIALOG_FRAGMENT_TAG = "dialog";

    public void setEnvironment(Environment env) {
        mEnv = env;
    }

    public Environment getEnvironment() {
        return  mEnv;
    }

    public boolean isForeground(){
        return mIsForeground;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        handleIntent(intent);
    }

    public void handleIntent(Intent intent){
        if (intent == null) {
            return;
        }
        setIntent(intent);

        String fragmentTag = intent.getStringExtra(INTENT_EXTRA_FRAGMENT_TAG);
        int mode = intent.getIntExtra(INTENT_EXTRA_LAUNCHER_MODE, BaseFragment.LAUNCHER_MODE_NORMAL);


        if(!TextUtils.isEmpty(fragmentTag)){
            BaseFragment fragment = FragmentCenter.popCacheFragment(fragmentTag);
            if(fragment == null) {
                return;
            }
            pushFragment(fragment, mode);
            if(fragment.getEnvironment() != null) {
                setEnvironment(fragment.getEnvironment());
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mEnv == null){
            mEnv = FrameworkFacade.getInstance().getEnvironment();
        }
        if(mEnv != null){
            mEnv.setCurrentActivity(this);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mIsForeground = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        mIsForeground = false;
    }

    /**
     * 压入fragment
     * @param fragment 需要被压入的fragment
     */
    protected void pushFragment(BaseFragment fragment, int mode) {
        if(fragment == null) {
            return;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fT = fragmentManager.beginTransaction();

        if (fragment.isUseAnim()) {
            fT.setCustomAnimations(fragment.mEnterAnimRes, fragment.mExitAnimRes,
                    fragment.mPopEnterAnimRes, fragment.mPopExitAnimRes);
        }

        String fragmentTag = fragment.getClass().getName();

        switch (mode){
            case BaseFragment.LAUNCHER_MODE_POP_BACK_STACK:
                BaseFragment fragmentInBackstack = (BaseFragment)fragmentManager.findFragmentByTag(fragmentTag);
                if(fragmentInBackstack != null){
                    fragmentManager.popBackStackImmediate(fragmentTag, 0);
                    return ;
                }
                break;
            case BaseFragment.LAUNCHER_MODE_NORMAL:
            case BaseFragment.LAUNCHER_MODE_REUSE:
                break;
        }

        //防止重复添加同一个fragment
        if (fragment == fragmentManager.findFragmentById(fragment.getContainer())) {
            return;
        }

        fT.replace(android.R.id.content, fragment, fragmentTag);
        fT.addToBackStack(fragmentTag);

        fT.commit();
//        fragmentManager.executePendingTransactions();//可以让UI线程马上执行这个commit
    }

    public void showDialogFragment(DialogFragment diagFragment) {
        if(diagFragment == null) return;

        FragmentTransaction fragAction = getSupportFragmentManager().beginTransaction();

        // Create and show the dialog.
        diagFragment.show(fragAction, DIALOG_FRAGMENT_TAG);
    }

    @Override
    public void onBackPressed() {
        Fragment baseFragment = getCurrentFragment();
        if(baseFragment instanceof  BaseFragment){
            if(((BaseFragment)baseFragment).goBack()){
                return;
            }
        }

        popCurrentFragment();
    }

    public void popCurrentFragment(){
         //解决fragment addToBackStack后，按返回键出现空白的Activity问题
        if (getSupportFragmentManager().getBackStackEntryCount() <= 1) {
            finish();
        } else {
            try {
                super.onBackPressed();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentById(android.R.id.content);
    }

}
