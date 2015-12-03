//package com.dcw.app.mvp.rxlifecycle;
//
//import android.support.annotation.NonNull;
//import android.view.View;
//
//import rx.Observable;
//import rx.Subscriber;
//
//import static com.dcw.app.mvp.rxlifecycle.Preconditions.checkUiThread;
//
///**
// * create by adao12.vip@gmail.com on 15/12/3
// *
// * @author JiaYing.Cheng
// * @version 1.0
// */
//final class ViewAttachEventOnSubscribe implements Observable.OnSubscribe<ViewAttachEvent> {
//    private final View view;
//
//
//    ViewAttachEventOnSubscribe(View view) {
//        this.view = view;
//    }
//
//
//    @Override
//    public void call(final Subscriber<? super ViewAttachEvent> subscriber) {
//        checkUiThread();
//
//
//        final View.OnAttachStateChangeListener listener = new View.OnAttachStateChangeListener() {
//            @Override
//            public void onViewAttachedToWindow(@NonNull final View v) {
//                if (!subscriber.isUnsubscribed()) {
//                    subscriber.onNext(ViewAttachEvent.create(view, ViewAttachEvent.Kind.ATTACH));
//                }
//            }
//
//
//            @Override
//            public void onViewDetachedFromWindow(@NonNull final View v) {
//                if (!subscriber.isUnsubscribed()) {
//                    subscriber.onNext(ViewAttachEvent.create(view, ViewAttachEvent.Kind.DETACH));
//                }
//            }
//        };
//
//        view.addOnAttachStateChangeListener(listener);
//
//
//        subscriber.add(new MainThreadSubscription() {
//            @Override
//            protected void onUnsubscribe() {
//                view.removeOnAttachStateChangeListener(listener);
//            }
//        });
//    }
//}
//
