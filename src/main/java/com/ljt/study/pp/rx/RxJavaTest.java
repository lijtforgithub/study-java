package com.ljt.study.pp.rx;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 响应式编程 RxJava (Observer + Asynchronous)
 *
 * @author LiJingTang
 * @date 2019-11-15 21:02
 */
public class RxJavaTest {

    public static void main(String[] args) {
        Observable<String> observable = Observable.create(emitter -> {
            emitter.onNext("积极向上");
            emitter.onNext("坚持学习");
            emitter.onNext("响应式编程RxJava");
        });

        observable.observeOn(Schedulers.newThread()).subscribe(t -> System.out.println(Thread.currentThread() + " : " + t));

        observable.subscribe(new Observer<String>() {

            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(String t) {
                System.out.println(Thread.currentThread() + " : " + t);
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        });

        for (;;) ;
    }

}
