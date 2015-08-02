package com.dcw.app.rating.util;

import android.content.Context;
import android.os.IBinder;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class Util {

    public static void showKeyboard(Context ctx) {
        InputMethodManager imm = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public static void showKeyboard(Context ctx, View token) {
        if(token == null){
            return;
        }

        InputMethodManager imm = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(token, InputMethodManager.SHOW_IMPLICIT);
    }
    /*public static boolean hideKeyboard(Context ctx) {
        InputMethodManager imm = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);

        if (imm.isActive()) {
            imm.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY);
            return true;
        }

        return false;
    }*/

    public static boolean hideKeyboard(Context ctx, IBinder binder) {
        InputMethodManager imm = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);

        if (imm.isActive()) {
            return imm.hideSoftInputFromWindow(binder, 0);
        }

        return false;
    }
}