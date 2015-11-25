package com.dcw.framework.container;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

interface Environment {
    Activity getCurrentActivity();
    void setCurrentActivity(Activity activity);
    Context getApplicationContext();
    void setApplicationContext(Context context);
    void startFragment(String fragmentName, Bundle param);
    /**
     * 启动Fragment
     * @param fragmentName 目标Fragment的class name
     * @param param 启动参数
     * @param useAnim 是否使用切换动画
     * @param mode 启动模式：
     *       BaseFragment.LAUNCHER_MODE_NORMAL -> 新建Fragment并压入栈中；
     *       BaseFragment.LAUNCHER_MODE_REUSE -> 若在栈中匹配到Fragment，则将Fragment移到栈顶，没有匹配到则新建Fragment并压入栈中 (目前该功能还未实现)；
     *       BaseFragment.LAUNCHER_MODE_POP_BACK_STACK -> 若在栈中匹配到Fragment，则将目标Fragment之上的其他Fragment弹出，没有匹配到则新建Fragment并压入栈中
     */
    void startFragment(String fragmentName, Bundle param, boolean useAnim, int mode);
    void startFragmentForResult(String fragmentName, Bundle param, IResultListener listener, boolean useAnim, boolean forceNew);
    void startFragmentForResult(String fragmentID, Bundle param, IResultListener listener, boolean useAnim, int mode);
    void startFragmentWithFlag(String fragmentName, Bundle param, boolean useAnim, int intentFlag);
    void startDialogFragment(String fragmentName, Bundle param);
    void startDialogFragment(String fragmentName, Bundle param, IResultListener listener);
}
