package com.dcw.app.rating.ui.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dcw.app.rating.config.BundleConstant;
import com.dcw.app.rating.R;
import com.dcw.app.rating.ui.SpinningDialog;
import com.dcw.app.rating.util.Util;
import com.dcw.framework.pac.ui.BaseFragment;
import com.dcw.framework.view.DCWAnnotation;


public abstract class BaseFragmentWrapper extends BaseFragment implements ICreateTemplate{

    public static final String ARGS_URL = "url";
    public static final String ARGS_TAB_INDEX = "args_tab_index";
    public static final String ARGS_H5_PARAMS = "h5Params";

    private boolean mIsDestroy;
    private long mTraceStartTime;
    protected TextView mTvBack;
    protected TextView mTvTitle;
    protected TextView mTvRight;
    private SpinningDialog mSpinningDialog;
    protected View mRootView;

    public BaseFragmentWrapper(){
        super();
        setUseAnim(false);
        setCustomAnimations(R.anim.open_slide_in, R.anim.open_slide_out,R.anim.close_slide_in, R.anim.close_slide_out);
    }

    public abstract Class getHostActivity();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIsDestroy = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = DCWAnnotation.inject(this, inflater);
            initData();
            initUI();
            initListeners();
        }
        if (mRootView != null) {
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (parent != null) {
                parent.removeAllViewsInLayout();
            }
            return mRootView;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initTopBar();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mIsDestroy = true;
    }

    public boolean isFragmentDestroy() {
        return mIsDestroy;
    }

    public int getFragmentType(){
        int fragmentType = 0;
        Bundle bundle = getBundleArguments();
        if(bundle != null){
            fragmentType = bundle.getInt(BundleConstant.KEY_FRAGMENT_TYPE);
        }
        return  fragmentType;
    }

    protected void initTopBar() {
        if (mRootView == null) {
            throw new NullPointerException("mRootView is null, This is called after inflated view ");
        }

        if (mTvBack != null) return;

        mTvTitle = findViewById(R.id.tv_title);

        mTvBack = findViewById(R.id.btn_back);
        if (mTvBack != null) {
            mTvBack.setOnClickListener(this);
        }

        mTvRight = findViewById(R.id.btn_right);
        if (mTvRight != null) {
            mTvRight.setOnClickListener(this);
        }
    }

    protected <T> T findViewById(int id) {
        return mRootView == null ? null : (T)mRootView.findViewById(id);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                onBackButtonClicked();
                break;

            case R.id.btn_right:
                onRightButtonClicked();
                break;

            default:
                break;
        }
    }

    protected void setBackButtonVisibility(int visibility) {
        if (mTvBack != null) {
            mTvBack.setVisibility(visibility);
        }
    }

    protected void setRightButtonVisibility(int visibility) {
        if (mTvRight != null) {
            mTvRight.setVisibility(visibility);
        }
    }

    protected void enableBackButton(boolean enable) {
        if (mTvBack != null) {
            mTvBack.setEnabled(enable);
        }
    }

    protected void enableRightButton(boolean enable) {
        if (mTvRight != null) {
            mTvRight.setEnabled(enable);
        }
    }

    protected void setBackButtonText(String text) {
        if (mTvBack != null) {
            mTvBack.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            mTvBack.setText(text);
        }
    }

    protected void setTitleText(String text) {
        if (mTvTitle != null) {
            mTvTitle.setText(text);
        }
    }

    protected void setCommRight(String text) {
        if (mTvRight != null) {
            mTvRight.setText(text);
        }
    }

    protected void setCommRightDrawableLeft(int left) {
        if (mTvRight != null) {
            mTvRight.setCompoundDrawablesWithIntrinsicBounds(left, 0, 0, 0);
        }
    }

    protected void onBackButtonClicked() {
        onBackPressed();
    }

    protected void onRightButtonClicked() {

    }

    protected void hideKeyboard() {
        Activity activity = getActivity();
        if(activity == null) return;
        try {
            View v = getActivity().getCurrentFocus();
            if(v != null){
                Util.hideKeyboard(getActivity(), v.getWindowToken());
            }
        } catch (Exception e) {
        }
    }

    /**
     * 显示加载对话框
     */
    public void showWaitDialog(int strResId, boolean isSmallStyle) {
        Activity activity = getActivity();
        if(activity == null) return;

        if (mSpinningDialog == null) {
            mSpinningDialog = new SpinningDialog(activity, activity.getString(strResId), isSmallStyle);
        }

        if (!mSpinningDialog.isShowing()) {
            mSpinningDialog.showDialog();
        }
    }

    /**
     * 显示加载对话框
     */
    public void showWaitDialog(int strResId, boolean isSmallStyle, int bgRes) {
        Activity activity = getActivity();
        if(activity == null) return;

        if (mSpinningDialog == null) {
            mSpinningDialog = new SpinningDialog(activity, activity.getString(strResId), isSmallStyle, bgRes);
        }

        if (!mSpinningDialog.isShowing()) {
            mSpinningDialog.showDialog();
        }
    }

    public void dismissWaitDialog() {
        if (mSpinningDialog != null && mSpinningDialog.isShowing()) {
            mSpinningDialog.dismissDialog();
        }
    }

    /**
     *  在BaseFragment里面取更多菜单的按钮判断展示还是隐藏
     *  FIXME 需要所有子界面在命名以及排版上都统一，否则会出现获取不到这个按钮或者标题部分偏右的UI问题
     * **/
    private void toggleMoreBtn(int size){
        if (mTvRight != null) {
            mTvRight.setVisibility(size > 0 ? View.VISIBLE : View.GONE);
        }
    }

    public void showErrorToast(String msg){
        if(TextUtils.isEmpty(msg)){
            ToastManager.getInstance().showToast(R.string.operate_fail);
        }else{
            ToastManager.getInstance().showToast(msg);
        }
    }

}
