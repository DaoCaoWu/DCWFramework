package com.dcw.app.rating.ui.adapter;

import android.content.Context;
import android.widget.Toast;

public class ToastManager {

    Context mContext;

    private static ToastManager gInstance = new ToastManager();

    private ToastManager(){

    }

    public static ToastManager getInstance(){
        return gInstance;
    }

    public void showToast(int textId, int DurationType){
        Toast.makeText(mContext, textId, DurationType).show();
    }

    public void showToast(CharSequence text, int DurationType){
        Toast.makeText(mContext, text, DurationType).show();
    }

    public void showToast(int textId){
        showToast(textId, Toast.LENGTH_SHORT);
    }

    public void showToast(CharSequence text){
        showToast(text, Toast.LENGTH_SHORT);
    }

    public void init(Context context){
        mContext = context;
    }
}
