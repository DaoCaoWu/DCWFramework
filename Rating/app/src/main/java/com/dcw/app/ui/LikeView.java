package com.dcw.app.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.CheckedTextView;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.dcw.app.rating.R;

/**
 * <p>Title: ucweb</p>
 * <p/>
 * <p>Description: </p>
 * ......
 * <p>Copyright: Copyright (c) 2015</p>
 * <p/>
 * <p>Company: ucweb.com</p>
 *
 * @author JiaYing.Cheng
 * @version 1.0
 * @email adao12.vip@gmail.com
 * @create 15/9/17
 */
public class LikeView extends CheckedTextView {

    private FrameLayout targetParentView;
    private CheckedTextView mIvLike;
    private View mIvLike1;
    private View mIvLike2;
    private boolean mIsLiked = false;

    public LikeView(Context context) {
        super(context);
    }

    public LikeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mIvLike = this;
    }

    public LikeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mIvLike = this;
    }

    public void setTargetParentView(FrameLayout targetParentView) {
        this.targetParentView = (FrameLayout) getParent();
    }

    public void setIvLike(CheckedTextView mIvLike) {
//        this.mIvLike = mIvLike;
    }

    public void setIvLike1(ImageView view) {
        int[] likeLocation = new int[2];
        mIvLike.getLocationInWindow(likeLocation);
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) mIvLike.getLayoutParams();
        this.mIvLike1 = view;
        if (mIvLike1 == null) {
            mIvLike1 = new ImageView(mIvLike.getContext());
            mIvLike1.setBackgroundResource(R.drawable.btn_icon_like_press);
            targetParentView.addView(mIvLike1, layoutParams);
            mIvLike1.setVisibility(GONE);
        }

//        mIvLike1.getLocationInWindow(likeLocation[0] + targetParentView.getLeft());
//        mIvLike1.setY(likeLocation[1] + targetParentView.getTop());
//        targetParentView.requestLayout();
//        targetParentView.invalidate();
    }

    public void setIvLike2(ImageView view) {
        this.mIvLike2 = view;
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) mIvLike.getLayoutParams();
        if (mIvLike2 == null) {
            mIvLike2 = new ImageView(mIvLike.getContext());
            mIvLike2.setBackgroundResource(R.drawable.btn_icon_like_press);
            targetParentView.addView(mIvLike2, layoutParams);
            mIvLike2.setVisibility(GONE);
        }
//        int[] likeLocation = new int[2];
//        mIvLike.getLocationInWindow(likeLocation);
//        mIvLike2.setY(likeLocation[1] + targetParentView.getLeft());
//        mIvLike2.setX(likeLocation[0] + targetParentView.getTop());
//        targetParentView.requestLayout();
//        targetParentView.invalidate();
    }

    public void changeViewPosition() {
    }

    public void toggle() {
        changeViewPosition();
        if (!mIsLiked) {
            playLikeAnimation();
        } else {
            playUnlikeAnimation();
        }
    }

    private void playUnlikeAnimation() {
        mIsLiked = false;
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(new ScaleAnimation(0.9f, 0f, 0.9f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f));
        animationSet.addAnimation(new AlphaAnimation(0.8f, 0f));
        animationSet.setDuration(600);
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.unlike);
        animation.setDuration(600);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mIvLike1.setVisibility(VISIBLE);
                mIvLike.setCompoundDrawablesWithIntrinsicBounds(R.drawable.btn_icon_like_normal, 0, 0, 0);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mIvLike1.setVisibility(GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        mIvLike.setCompoundDrawablesWithIntrinsicBounds(R.drawable.btn_icon_like_normal, 0, 0, 0);
        mIvLike1.setVisibility(VISIBLE);
        mIvLike1.startAnimation(animation);
    }

    private void playLikeAnimation() {
        mIsLiked = true;
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(new ScaleAnimation(1f, 0.6f, 1f, 0.6f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, -2.5f));
        animationSet.addAnimation(new AlphaAnimation(0.8f, 0f));
        animationSet.setDuration(600);
        animationSet.setRepeatCount(Animation.INFINITE);
        animationSet.setRepeatMode(Animation.RESTART);

        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.liked);
        animation.setDuration(600);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mIvLike1.setVisibility(VISIBLE);
                mIvLike.setCompoundDrawablesWithIntrinsicBounds(R.drawable.btn_icon_like_press, 0, 0, 0);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mIvLike1.setVisibility(GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        AnimationSet animationSet2 = new AnimationSet(true);
        animationSet2.addAnimation(new ScaleAnimation(1f, 0.6f, 1f, 0.6f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, -2.5f));
        animationSet2.addAnimation(new AlphaAnimation(0.8f, 0f));
        animationSet2.setDuration(600);
        animationSet2.setStartOffset(200);
        Animation animation2 = AnimationUtils.loadAnimation(getContext(), R.anim.liked);
        animation2.setDuration(600);
        animation2.setStartOffset(200);
        animation2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mIvLike2.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mIvLike2.setVisibility(GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mIvLike.setCompoundDrawablesWithIntrinsicBounds(R.drawable.btn_icon_like_press, 0, 0, 0);
        mIvLike1.setVisibility(VISIBLE);
        mIvLike2.setVisibility(VISIBLE);
        mIvLike1.startAnimation(animation);
        mIvLike2.startAnimation(animation2);
    }

}
