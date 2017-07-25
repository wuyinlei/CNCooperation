package com.cainiao.factory.net.compat;

import com.cainiao.common.base.BaseBean;
import com.cainiao.factory.net.exception.ApiException;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * Created by wuyinlei on 2017/7/23.
 *
 * @function 线程切换并且再此转换数据
 */

public class RxResponseCompat {

    /**
     * 数据转换
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<BaseBean<T>, T> compatResult() {
        return new Observable.Transformer<BaseBean<T>, T>() {
            @Override
            public Observable<T> call(Observable<BaseBean<T>> baseBeanObservable) {

                return baseBeanObservable.flatMap(new Func1<BaseBean<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(final BaseBean<T> tBaseBean) {
                        if (tBaseBean.success()) {

                            return Observable.create(new Observable.OnSubscribe<T>() {
                                @Override
                                public void call(Subscriber<? super T> subscriber) {
                                    try {
                                        subscriber.onNext(tBaseBean.getResults());
                                        subscriber.onCompleted();
                                    } catch (Exception e
                                            ) {
                                        subscriber.onError(e);
                                    }

                                }
                            });

                        } else {
                            return Observable.error(new ApiException(tBaseBean.getErrorCode(), tBaseBean.getErrorStr()));
                        }
                    }
                }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io());
            }
        };
    }
}
