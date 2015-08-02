package com.dcw.framework.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.UnderlineSpan;

/**
 * A builder help you make rich text by {@link @Spannable} in a simple way.
 *
 * @author JiaYing.Cheng
 */
public class RichTextBuilder {

    private static final String LABLE_BITMAP = "[bitmap]";

    private SpannableStringBuilder mBuilder;

    private boolean mUseSpecifyColor = false;

    private int mColor;
    private int mPosition = 0;

    private Context mContext;

    public RichTextBuilder(Context context) {
        mBuilder = new SpannableStringBuilder();
        mContext = context;
    }

    public RichTextBuilder(Context context, CharSequence cs) {
        this.mBuilder = new SpannableStringBuilder(cs);
        mPosition = cs.length();
        mContext = context;
    }

    public RichTextBuilder append(CharSequence cs) {
        mBuilder.append(cs);
        setColorIfNeed(mPosition, mPosition + cs.length());
        mPosition += cs.length();
        return this;
    }

    public RichTextBuilder append(CharSequence cs, int start, int end) {
        mBuilder.append(cs, start, end);
        setColorIfNeed(mPosition, mPosition + end - start + 1);
        mPosition += (end - start + 1);
        return this;
    }

    public RichTextBuilder append(char c) {
        mBuilder.append(c);
        setColorIfNeed(mPosition, mPosition + 1);
        mPosition++;
        return this;
    }

    public RichTextBuilder appendTextResource(int resId) {
        append(mContext.getText(resId));
        return this;
    }

    private RichTextBuilder setColorIfNeed(int start, int end) {
        if (mUseSpecifyColor) {
            mBuilder.setSpan(new ForegroundColorSpan(mColor), start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        }

        return this;
    }

    public RichTextBuilder appendUnderLine(CharSequence cs) {
        mBuilder.append(cs);
        setColorIfNeed(mPosition, mPosition + cs.length());
        setUnderLine(mPosition, mPosition + cs.length());
        mPosition += cs.length();
        return this;
    }

    public RichTextBuilder appendUnderLine(CharSequence cs, int start, int end) {
        mBuilder.append(cs, start, end);
        setColorIfNeed(mPosition, mPosition + end - start + 1);
        setUnderLine(mPosition, mPosition + end - start + 1);
        mPosition += (end - start + 1);
        return this;
    }

    private RichTextBuilder setUnderLine(int start, int end) {
        mBuilder.setSpan(new UnderlineSpan(), start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        return this;
    }

    public RichTextBuilder setColorForStr(String str) {
        if (TextUtils.isEmpty(str)) return this;

        if (mUseSpecifyColor) {
            String content = mBuilder.toString();
            int start = content.indexOf(str);
            int end = start + str.length();

            mBuilder.setSpan(new ForegroundColorSpan(mColor), start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        }

        return this;
    }

    private RichTextBuilder setTouchableEdge(int start, int end, TouchableSpan.OnClickListener listener, String... url) {
        int touchableColor = TouchableSpan.DEFAULT_NOMAL_TEXT_COLOR;
        if (mUseSpecifyColor) {
            touchableColor = mColor;
        }
        TouchableSpan touchableSpan = new TouchableSpan(url == null || url.length == 0 ? "" : url[0], touchableColor, listener);
        mBuilder.setSpan(touchableSpan, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return this;
    }


    public RichTextBuilder appendTouchableEdge(int start, int end, TouchableSpan.OnClickListener listener, String... url) {
        if (mPosition - start < end - start) return this;
        setTouchableEdge(start, end, listener, url);
        return this;
    }

    public RichTextBuilder appendTouchableText(CharSequence cs, TouchableSpan.OnClickListener listener, String... url) {
        if (TextUtils.isEmpty(cs)) return this;

        appendTouchableText(cs, 0, cs.length(), listener, url);
        return this;
    }

    public RichTextBuilder appendTouchableText(CharSequence cs, int start, int end, TouchableSpan.OnClickListener listener, String... url) {
        if (TextUtils.isEmpty(cs) || end - start > cs.length()) return this;

        mBuilder.append(cs, start, end);
        int length = end - start;
        setTouchableEdge(mPosition, mPosition + length, listener, url);
        mPosition += length;
        return this;
    }

    public RichTextBuilder appendImage(int drawableRes) {
        return appendImage(drawableRes, DynamicDrawableSpan.ALIGN_BOTTOM);
    }

    public RichTextBuilder appendImage(int drawableRes, int verticalAlignment) {
        mBuilder.append(LABLE_BITMAP);
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), drawableRes);
        return appendImage(bitmap, verticalAlignment);
    }

    public RichTextBuilder appendImage(Bitmap icon) {
        return appendImage(icon, DynamicDrawableSpan.ALIGN_BOTTOM);
    }

    public RichTextBuilder appendImage(Bitmap icon, int verticalAlignment) {
        mBuilder.setSpan(new ImageSpan(mContext, icon, verticalAlignment),
                mPosition, mPosition + LABLE_BITMAP.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mPosition += LABLE_BITMAP.length();
        return this;
    }

    public RichTextBuilder withColor(int color) {
        mColor = color;
        mUseSpecifyColor = true;
        return this;
    }

    public RichTextBuilder withColorResource(int resId) {
        return withColor(mContext.getResources().getColor(resId));
    }

    public RichTextBuilder withDefaultColor() {
        mUseSpecifyColor = false;
        return this;
    }

    public RichTextBuilder clear() {
        mBuilder.clear();
        withDefaultColor();
        mPosition = 0;
        return this;
    }

    public Spannable build() {
        return mBuilder;
    }

    public Spannable buildInstance() {
        return new SpannableString(mBuilder);
    }

    public SpannableStringBuilder toBuilder() {
        return mBuilder;
    }

    @Override
    public String toString() {
        return mBuilder.toString();
    }
}
