package com.dcw.framework.domain;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * create by adao12.vip@gmail.com on 15/12/2
 *
 * @author JiaYing.Cheng
 * @version 1.0
 */
public class RxJavaTest {

    public static void main(String[] argc) {

        final Action1<String> helloAction = new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println("hello " + s + "!");
            }
        };
        Observable.just("aa", "bb").subscribe(helloAction);

        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    for (int i = 0; i < 7; i++) {
                        if (subscriber.isUnsubscribed()) {
                            return;
                        }
                        subscriber.onNext("i"+i);
                    }
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onCompleted();
                    }
                } catch (Throwable t) {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onError(t);
                    }
                }
            }
        }).skip(1).take(6).map(new Func1<String, Integer>() {
            @Override
            public Integer call(String s) {
                return s.hashCode();
            }
        }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                System.out.println("hello " + integer + "!");
            }
        });

        Observable.just("aa", "bb", "cc", "dd", "ee").zipWith(Observable.just(1, 1, 3, 999), new Func2<String, Integer, String>() {
            @Override
            public String call(String s, Integer integer) {
                return s + integer;
            }
        }).compose(new Observable.Transformer<String, String>() {
            @Override
            public Observable<String> call(Observable<String> stringObservable) {
                return stringObservable.doOnNext(helloAction).doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        helloAction.call(throwable.getMessage() + "1");
                    }
                }).doOnCompleted(new Action0() {
                    @Override
                    public void call() {
                        helloAction.call("sb");
                    }
                });
            }
        }).subscribe(helloAction);

        Observable.combineLatest(Observable.just(1, 1, 3, 999), Observable.just("aa", "bb", "cc", "dd", "ee"), new Func2<Integer, String, String>() {
            @Override
            public String call(Integer integer, String s) {
                return s + integer;
            }
        }).subscribe(helloAction);

    }
}
