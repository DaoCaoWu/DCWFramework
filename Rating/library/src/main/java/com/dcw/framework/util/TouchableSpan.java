package com.dcw.framework.util;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

/**
 * @author JiaYing.Cheng
 */
public class TouchableSpan extends ClickableSpan {

    public static final int DEFAULT_NOMAL_TEXT_COLOR = 0xff0000EE;
    public static final int DEFAULT_PRESSED_TEXT_COLOR = 0xff05c5cf;
    public static final int DEFAULT_PRESSED_BACKGROUND_COLOR = 0xffeeeeee;

    private OnClickListener mListener;
    private final String mContent;
    private boolean mIsPressed;
    private boolean mIsUnderlineEnable;
    private int mNormalTextColor = DEFAULT_NOMAL_TEXT_COLOR;
    private int mPressedTextColor = DEFAULT_PRESSED_TEXT_COLOR;
    private int mPressedBackgroundColor = DEFAULT_PRESSED_BACKGROUND_COLOR;

    public TouchableSpan(String str) {
        this(str, DEFAULT_NOMAL_TEXT_COLOR, null);
    }

    public TouchableSpan(String str, OnClickListener listener) {
        this(str, DEFAULT_NOMAL_TEXT_COLOR, listener);
    }

    public TouchableSpan(String str, int color, OnClickListener listener) {
        this.mListener = listener;
        this.mContent = str;
        this.mNormalTextColor = color;
    }

    public void setOnClickListener(OnClickListener listener) {
        this.mListener = listener;
    }

    public OnClickListener getOnClickListener() {
        return mListener;
    }

    public void setTouchColor(int normalTextColor, int pressedTextColor, int pressedBackgroundColor) {
        mNormalTextColor = normalTextColor;
        mPressedTextColor = pressedTextColor;
        mPressedBackgroundColor = pressedBackgroundColor;
    }

    public void setPressed(boolean isSelected) {
        mIsPressed = isSelected;
    }

    /**
     * Makes the text underlined.
     */
    public void setUnderlineEnable(boolean isUnderlineEnable) {
        mIsUnderlineEnable = isUnderlineEnable;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setColor(mIsPressed ? mPressedTextColor : mNormalTextColor);
        ds.bgColor = mIsPressed ? mPressedBackgroundColor : 0xffeeeeee;
        ds.setUnderlineText(mIsUnderlineEnable);
    }

    @Override
    public void onClick(View widget) {
        View parent = (View) widget.getParent();

        if (parent != null) {
            parent.setPressed(false);
        }

        if (mListener != null) {
            mListener.onClick(mContent);
        }
    }

    public String getContent() {
        return mContent;
    }

    public interface OnClickListener {
        void onClick(String content);
    }

}
