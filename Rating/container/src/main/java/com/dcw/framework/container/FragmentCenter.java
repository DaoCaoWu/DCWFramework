package com.dcw.framework.container;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.dcw.framework.container.tools.ReflectHelper;

import java.util.HashMap;

public class FragmentCenter {

    private Environment mEnvironment;

    public void setEnvironment(Environment environment) {
        mEnvironment = environment;
    }

    public void startFragmentForResult(String fragmentID, Bundle param, IResultListener listener, boolean useAnim, int mode) {
        BaseFragment fragment = ReflectHelper.loadClass(fragmentID);
        if (fragment == null) {
            return;
        }
        fragment.setEnvironment(mEnvironment);
        if (param != null) {
            fragment.setBundleArguments(param);
        }
        fragment.setResultListener(listener);
        fragment.setUseAnim(useAnim);
        fragment.hideKeyboard();

        pushFragment(mEnvironment.getCurrentActivity(), fragment, mode);
    }

    public void startDialogFragment(String fragmentID, Bundle param, IResultListener listener) {

        if (!(mEnvironment.getCurrentActivity() instanceof BaseActivity)) {
            // 容错处理
            return;
        }
        BaseDialogFragment fragment = ReflectHelper.loadClass(fragmentID);
        if (fragment == null) {
            return;
        }
        fragment.setEnvironment(mEnvironment);
        if (param != null) {
            fragment.setBundleArguments(param);
        }
        fragment.setResultListener(listener);

        ((BaseActivity) mEnvironment.getCurrentActivity()).showDialogFragment(fragment);
    }

    public void startFragmentWithFlag(String fragmentID, Bundle param, boolean useAnim, int intentFlag) {

        BaseFragment fragment = ReflectHelper.loadClass(fragmentID);
        if (fragment == null) {
            return;
        }
        fragment.setEnvironment(mEnvironment);
        if (param != null) {
            fragment.setBundleArguments(param);
        }
        fragment.setUseAnim(useAnim);
        fragment.hideKeyboard();
        pushFragmentWithFlag(mEnvironment.getCurrentActivity(), fragment, intentFlag);
    }

    private static HashMap<String, BaseFragment> gSwitchCache = new HashMap<String, BaseFragment>(2);

    private void pushFragment(Activity baseActivity, BaseFragment fragment, int mode) {

        Class hostActivity = fragment.getHostActivity();

        if (baseActivity != null) {
            if (!baseActivity.isFinishing()
                    && baseActivity.getClass() == hostActivity
                    && baseActivity instanceof BaseActivity
                    && ((BaseActivity) baseActivity).isForeground()) {
                //current Activity is hostActivity
                ((BaseActivity) baseActivity).pushFragment(fragment, mode);

            } else if (hostActivity != null) {
                //cache Fragment
                gSwitchCache.put(fragment.getName(), fragment);

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setClass(baseActivity, hostActivity);
                intent.putExtra(BaseActivity.INTENT_EXTRA_FRAGMENT_TAG, fragment.getName());
                intent.putExtra(BaseActivity.INTENT_EXTRA_LAUNCHER_MODE, mode);

                baseActivity.startActivity(intent);

                //use Anim only start another activity
                if (fragment.isUseAnim()) {
                    baseActivity.overridePendingTransition(fragment.mEnterAnimRes, fragment.mExitAnimRes);
                } else {
                    baseActivity.overridePendingTransition(0, 0);
                }

            } else if (baseActivity instanceof BaseActivity && ((BaseActivity) baseActivity).isForeground()) {
                //hotActivity为空，添加到当前activity
                ((BaseActivity) baseActivity).pushFragment(fragment, mode);
            }
        } else if (hostActivity != null) {
            //cache Fragment
            gSwitchCache.put(fragment.getName(), fragment);

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setClass(mEnvironment.getApplicationContext(), hostActivity);
            intent.putExtra(BaseActivity.INTENT_EXTRA_FRAGMENT_TAG, fragment.getName());
            intent.putExtra(BaseActivity.INTENT_EXTRA_LAUNCHER_MODE, mode);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            mEnvironment.getApplicationContext().startActivity(intent);
        } else {
            throw new RuntimeException("Framework can't handle startFragment: baseActivity == null && hostActivity == null");
        }
    }

    private void pushFragmentWithFlag(Activity baseActivity, BaseFragment fragment, int intentFlag) {
        Class hostActivity = fragment.getHostActivity();
        if (hostActivity == null) {
            hostActivity = baseActivity.getClass();
        }

        //cache Fragment
        gSwitchCache.put(fragment.getName(), fragment);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setClass(baseActivity, hostActivity);
        intent.putExtra(BaseActivity.INTENT_EXTRA_FRAGMENT_TAG, fragment.getName());
        intent.putExtra(BaseActivity.INTENT_EXTRA_LAUNCHER_MODE, BaseFragment.LAUNCHER_MODE_NORMAL);
        intent.setFlags(intentFlag);

        baseActivity.startActivity(intent);
    }

    static BaseFragment popCacheFragment(String fragmentTag) {
        return gSwitchCache.remove(fragmentTag);
    }
}
