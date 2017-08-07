package com.cainiao.common.rxbus.helper;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by wuyinlei on 2017/1/5.
 * @function 订阅管理类  防止内存泄漏
 */

public class RxSubscriptions {

    private static CompositeSubscription mSubscriptions = new CompositeSubscription();

    public static boolean isUnsubscribed(){
        return mSubscriptions.isUnsubscribed();
    }

    public static void add(Subscription s){
        if (s != null){
            mSubscriptions.add(s);
        }
    }

    public static void remove(Subscription s){
        if (s != null){
            mSubscriptions.remove(s);
        }
    }

    public static void clear(){
        mSubscriptions.clear();
    }

    public static void unsubscribe(){
        mSubscriptions.unsubscribe();
    }

    public static boolean hasSubscriptions(){
        return mSubscriptions.hasSubscriptions();
    }
}
