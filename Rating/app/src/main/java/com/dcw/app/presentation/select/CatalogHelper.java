package com.dcw.app.presentation.select;

import android.support.annotation.IdRes;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/10/24.
 */
public class CatalogHelper {

    @IdRes
    int mCatalogId;
    View mFloatCatalogLayout;

    public CatalogHelper(View floatCatalogLayout, @IdRes int catalogId) {
        mCatalogId = catalogId;
        mFloatCatalogLayout = floatCatalogLayout;
    }

    public void onScroll(ViewGroup scrollView) {
        View mFirstVisibleView = scrollView.getChildAt(0);
        View mSecondVisibleView = scrollView.getChildAt(1);
        if (isExist(mFirstVisibleView)) {
            if (isFloatCatalogLayoutLongerInVisibleRect(mFirstVisibleView)) {
                if (isCatalogLayoutExist(mSecondVisibleView)) {
                    moveFloatCatalogLayoutHeightAccordingTo(mFirstVisibleView);
                }
                showFloatCatalogLayout(true);
            } else {
                setMarginTop(0);
                showFloatCatalogLayout(true);
            }
        } else {
            showFloatCatalogLayout(false);
        }
    }

    public void moveFloatCatalogLayoutHeightAccordingTo(View itemView) {
        int itemViewVisibleHeight = itemView.getBottom();
        int catalogHeight = mFloatCatalogLayout.getHeight();
        setMarginTop(itemViewVisibleHeight - catalogHeight);
    }

    public void setMarginTop(int marginTop) {
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mFloatCatalogLayout.getLayoutParams();
        params.topMargin = marginTop;
        mFloatCatalogLayout.setLayoutParams(params);
    }

    public void showFloatCatalogLayout(boolean isShow) {
        mFloatCatalogLayout.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    public boolean isCatalogLayoutExist(View itemView) {
        if (itemView == null) {
            return false;
        }
        View catalogLayout = itemView.findViewById(mCatalogId);
        return catalogLayout != null && catalogLayout.isShown();
    }

    public boolean isFloatCatalogLayoutLongerInVisibleRect(View itemView) {
        int itemViewVisibleHeight = itemView.getBottom();
        int catalogHeight = mFloatCatalogLayout.getHeight();
        return itemViewVisibleHeight < catalogHeight;
    }

    public boolean isExist(View itemView) {
        return itemView != null;
    }
}
