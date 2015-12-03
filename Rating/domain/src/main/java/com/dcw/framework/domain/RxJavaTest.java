package com.dcw.framework.domain;

import rx.Notification;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.subjects.AsyncSubject;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;
import rx.subjects.ReplaySubject;
import rx.subjects.Subject;

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

        Subscription behaviorSubject = Observable.combineLatest(Observable.just(1, 1, 3, 999), Observable.just("aa", "bb", "cc", "dd", "ee"), new Func2<Integer, String, String>() {
            @Override
            public String call(Integer integer, String s) {
                return s + integer;
            }
        }).subscribe(helloAction);

        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("a");
                subscriber.onNext("b");
                subscriber.onNext("c");
            }
        }).subscribe(helloAction);

        Subject bs = BehaviorSubject.create();
        bs.onNext("11");
        bs.onNext("0");
        bs.subscribe(helloAction);
        bs.onNext("1");
        bs.onNext("2");
        bs.onNext("3");

        bs = PublishSubject.create();
        bs.onNext("11");
        bs.onNext("0");
        bs.subscribe(helloAction);
        bs.onNext("1");
        bs.onNext("2");
        bs.onNext("3");
        bs.onNext("4");

        bs = AsyncSubject.<String>create();
        bs.onNext("11");
        bs.onNext("0");
        bs.subscribe(helloAction);
        bs.onNext("1");
        bs.onNext("2");
        bs.onNext("3");
        bs.onNext("4");
        bs.onNext("5!!");
        bs.onCompleted();

        final ReplaySubject<String> rs = ReplaySubject.create();
        rs.onNext("11");
        rs.onNext("0");
        rs.subscribe(helloAction);
        rs.onNext("1");
        rs.onNext("2");
        rs.onNext("3");
        rs.onNext("4");
        rs.onNext("5");
        rs.onNext("6");

        Observable.combineLatest(Observable.just(1, 1, 3, 999), Observable.just("aa", "bb", "cc", "dd", "ee"), new Func2<Integer, String, String>() {
            @Override
            public String call(Integer integer, String s) {
                return s + integer;
            }
        }).materialize().take(1).switchMap(new Func1<Notification<String>, Observable<Notification<String>>>() {
            @Override
            public Observable<Notification<String>> call(Notification<String> stringNotification) {
                return rs.map(new Func1<String, Notification<String>>() {
                    @Override
                    public Notification<String> call(String s) {
                        return Notification.createOnNext(s + s);
                    }
                });
            }
        }).filter(new Func1<Notification<String>, Boolean>() {
            @Override
            public Boolean call(Notification<String> stringNotification) {
                return true;
            }
        }).doOnNext(new Action1<Notification<String>>() {
            @Override
            public void call(Notification<String> stringNotification) {
                System.out.println(stringNotification.getKind() + " || " + stringNotification.getValue());
            }
        }).doOnCompleted(new Action0() {
            @Override
            public void call() {
                System.out.println("doOnCompleted");
            }
        }).doOnUnsubscribe(new Action0() {
            @Override
            public void call() {
                System.out.println("doOnUnsubscribe");
            }
        }).subscribe(new Action1<Notification<String>>() {
            @Override
            public void call(Notification<String> stringNotification) {
                System.out.println(stringNotification.getKind() + " ||| " + stringNotification.getValue());
            }
        });
    }
}
