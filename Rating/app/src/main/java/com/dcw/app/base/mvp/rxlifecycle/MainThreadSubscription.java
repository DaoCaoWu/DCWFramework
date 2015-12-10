package com.dcw.app.base.mvp.rxlifecycle;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Keep;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

import rx.Subscription;

/**
 * create by adao12.vip@gmail.com on 15/12/3
 *
 * @author JiaYing.Cheng
 * @version 1.0
 */
public abstract class MainThreadSubscription implements Subscription, Runnable {
    private static final Handler MAIN_THREAD = new Handler(Looper.getMainLooper());


    @Keep
    @SuppressWarnings("unused") // Updated by 'UNSUBSCRIBED_UPDATER' object.
    private volatile int unsubscribed;
    private static final AtomicIntegerFieldUpdater<MainThreadSubscription> UNSUBSCRIBED_UPDATER =
            AtomicIntegerFieldUpdater.newUpdater(MainThreadSubscription.class, "unsubscribed");


    @Override public final boolean isUnsubscribed() {
        return unsubscribed != 0;
    }


    @Override public final void unsubscribe() {
        if (UNSUBSCRIBED_UPDATER.compareAndSet(this, 0, 1)) {
            if (Looper.getMainLooper() == Looper.myLooper()) {
                onUnsubscribe();
            } else {
                MAIN_THREAD.post(this);
            }
        }
    }


    @Override public final void run() {
        onUnsubscribe();
    }


    protected abstract void onUnsubscribe();
}
