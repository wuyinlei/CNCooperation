package com.cainiao.common.rxbus;

import rx.Subscriber;

/**
 * Created by wuyinlei on 2017/1/5.
 */

public abstract class RxBusSubscriber<T> extends Subscriber<T> {
    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
    }

    @Override
    public void onNext(T t) {
        try{
            onEvent(t);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public abstract void onEvent(T t);
}
