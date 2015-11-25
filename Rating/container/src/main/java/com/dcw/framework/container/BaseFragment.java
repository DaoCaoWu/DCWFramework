package com.dcw.framework.container;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;


public abstract class BaseFragment extends Fragment implements View.OnClickListener, EnvironmentCallback {

    private IResultListener mResultListener;

    private static final long ITEM_CLICK_COOLING_TIME_IN_MS = 500;
    private long mLastItemClickTimeInMS;
    private Bundle mBundle = new Bundle();

    private Environment mEnv;

    private boolean mUseAnim;

    public int mEnterAnimRes;
    public int mExitAnimRes;
    public int mPopEnterAnimRes;
    public int mPopExitAnimRes;

    /**
     *  Fragment启动模式 -> 新建Fragment并压入栈中；
     */
    public static final int LAUNCHER_MODE_NORMAL = 0;
    /**
     *  Fragment启动模式 -> 若在栈中匹配到Fragment，则将Fragment移到栈顶，没有匹配到则新建Fragment并压入栈中 (目前该功能还未实现)；
     */
    public static final int LAUNCHER_MODE_REUSE = 1;
    /**
     *  Fragment启动模式 -> 若在栈中匹配到Fragment，则将目标Fragment之上的其他Fragment弹出，没有匹配到则新建Fragment并压入栈中
     */
    public static final int LAUNCHER_MODE_POP_BACK_STACK = 2;

    public abstract Class getHostActivity();

    public int getContainer(){
        return android.R.id.content;
    }

    public void setUseAnim(boolean useAnim){
        mUseAnim = useAnim;
    }

    public boolean isUseAnim(){
        return mUseAnim;
    }

    public void setCustomAnimations(int enterAnimRes, int exitAnimRes, int popEnterAnimRes, int popExitAnimRes){
        mEnterAnimRes = enterAnimRes;
        mExitAnimRes = exitAnimRes;
        mPopEnterAnimRes = popEnterAnimRes;
        mPopExitAnimRes = popExitAnimRes;
    }

    @Deprecated
    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }

    public void setBundleArguments(Bundle bundle){
        mBundle = bundle;
    }

    public Bundle getBundleArguments(){
        return mBundle;
    }

    public String getName(){
        return this.getClass().getName();
    }

    public void onResult(Bundle result){
        if(mResultListener != null){
            mResultListener.onResult(result);
        }
    }

    public void setResultListener(IResultListener listener) {
        this.mResultListener = listener;
    }

    @Override
    public void setEnvironment(Environment environment) {
        mEnv = environment;
    }

    @Override
    public Environment getEnvironment() {
        return mEnv;
    }

    public void startFragment(Class fragment){
        startFragment(fragment, null);
    }

    public void startFragment(Class fragment, Bundle param){
        if(mEnv != null){
            mEnv.startFragment(fragment.getName(), param);
        }
    }

    public void startFragment(Class fragment, Bundle param, boolean useAnim, boolean forceNew){
        if(mEnv != null){
            mEnv.startFragment(fragment.getName(), param, useAnim, forceNew ? BaseFragment.LAUNCHER_MODE_NORMAL : BaseFragment.LAUNCHER_MODE_REUSE);
        }
    }

    public void startFragmentForResult(Class fragment, Bundle param, IResultListener listener){
        if(mEnv != null){
            mEnv.startFragmentForResult(fragment.getName(), param, listener, false, BaseFragment.LAUNCHER_MODE_NORMAL);
        }
    }

    public void startFragmentWithFlag(Class fragment, Bundle param, boolean useAnim, int intentFlag){
        if(mEnv != null){
            mEnv.startFragmentWithFlag(fragment.getName(), param, useAnim, intentFlag);
        }
    }


    public void onBackPressed() {
        if (getActivity() != null) {
            hideKeyboard();
            getActivity().onBackPressed();
        }
    }

    public void hideKeyboard(){
        Activity activity = null;
        if(mEnv != null){
            activity = mEnv.getCurrentActivity();
        }

        if(activity == null){
            activity = getActivity();
        }

        if (activity != null && activity.getCurrentFocus() != null) {
            IBinder binder = activity.getCurrentFocus().getWindowToken();
            if( binder != null){
                hideKeyboard(activity, binder);
            }
        }
    }

    private boolean hideKeyboard(Context ctx, IBinder binder) {
        InputMethodManager imm = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm != null && imm.isActive() && imm.hideSoftInputFromWindow(binder, 0);
    }

    /**
     * 处理返回事件
     * @return true表示在fragment内部已经完成了对返回事件的处理，外部不需要再处理;
     * false表示外部需要对返回事件继续处理
     * */
    public boolean goBack() {
        return false;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onClick(View view) {
        //防止用户点击速度过快而引起多个窗口or对话框弹出.//FIXME lihq
        if (overCoolingTime()){
            this.mLastItemClickTimeInMS  = System.currentTimeMillis();
        }
    }

    private boolean overCoolingTime() {
        return (System.currentTimeMillis() - this.mLastItemClickTimeInMS) >= ITEM_CLICK_COOLING_TIME_IN_MS;
    }

}
