package com.cainiao.factory.net.compat;

import android.media.tv.TvContentRating;

import com.cainiao.common.base.BaseBean;
import com.cainiao.factory.net.exception.ApiException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by wuyinlei on 2017/7/23.
 *
 * @function 线程切换并且再此转换数据
 */

public class RxResponseCompat {

    public static <T> ObservableTransformer<BaseBean<T>,T> compatResult(){

        return new ObservableTransformer<BaseBean<T>, T>(){
            @Override
            public ObservableSource<T> apply(@NonNull Observable<BaseBean<T>> baseBeanObservable) {

                return baseBeanObservable.flatMap(new Function<BaseBean<T>, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(@NonNull final BaseBean<T> tBaseBean) {

                        if (tBaseBean.success()) {

                            return new Observable<T>() {
                                @Override
                                protected void subscribeActual(Observer observer) {
                                    try {
                                        observer.onNext(tBaseBean.getResults());
                                        observer.onComplete();
                                    } catch (Exception e) {
                                        observer.onError(e);
                                    }

                                }
                            };
                        } else {
                            return Observable.error(new ApiException(tBaseBean.getErrorCode(), tBaseBean.getErrorStr()));
                        }
                    }
                }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io());
            }

        };
    }


}
