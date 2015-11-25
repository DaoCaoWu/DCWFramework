package com.dcw.framework.container;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.Window;

public class BaseDialogFragment extends DialogFragment implements EnvironmentCallback {

    private Bundle mBundle = new Bundle();

    private IResultListener mResultListener;
    protected Environment mEnvironment;

    @Override
    public void setEnvironment(Environment environment) {
        mEnvironment = environment;
    }

    @Override
    public Environment getEnvironment() {
        return mEnvironment;
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

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        initDialogStyle(dialog);
        return dialog;
    }

    protected void initDialogStyle(Dialog dialog) {
        dialog.setCanceledOnTouchOutside(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}
