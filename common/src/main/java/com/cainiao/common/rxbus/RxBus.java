package com.cainiao.common.rxbus;



import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import rx.Observable;
import rx.Subscriber;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * PublishSubject: 只会在订阅发生的时间点之后来自原始的Observable的数据发送给观观察者
 */
public class RxBus {

    private static volatile RxBus mDefaustInstance;
    private final Subject<Object,Object> mBus;

    private final Map<Class<?>,Object> mStickEventMap;

    public RxBus(){
        mBus = new SerializedSubject<>(PublishSubject.create());
        mStickEventMap = new ConcurrentHashMap<>();
    }

    /**
     * 单例模式的使用，保证只有一个实例
     * @return  RxBus
     */
    public static RxBus getDefault(){
        if (mDefaustInstance == null){
            synchronized (RxBus.class){
                mDefaustInstance = new RxBus();
            }
        }
        return mDefaustInstance;
    }

    /**
     * 发送事件
     * @param event  事件
     */
    public void post(Object event){
        mBus.onNext(event);
    }

    /**
     * 根据传递的eventType 类型来返回特定的类型(EventType)给被观察者
     * @param eventType  类型
     * @param <T>  数据类型
     * @return  被观察者事件
     */
    public <T> Observable<T> toObservable(Class<T> eventType){
        return mBus.ofType(eventType);
    }

    /**
     * 返回是否有订阅者
     * @return  true  /  false
     */
    public boolean hasObservers(){
        return mBus.hasObservers();
    }

    /*重置**/
    public void reset(){
        mDefaustInstance = null;
    }

    public void postSticky(Object event){
        synchronized (mStickEventMap){
            mStickEventMap.put(event.getClass(),event);
        }
        post(event);
    }

    /**
     * 根据传递的eventType  类型返回特定类型的被观察者
     * @param eventType  类型
     * @param <T>  泛型
     * @return  粘性事件类型
     */
    public <T> Observable<T> toObservableSticky(final Class<T> eventType){
        synchronized (mStickEventMap){
            Observable<T> observable = mBus.ofType(eventType);
            final Object event = mStickEventMap.get(eventType);

            if (event != null){
                return observable.mergeWith(Observable.create(new Observable.OnSubscribe<T>(){
                    @Override
                    public void call(Subscriber<? super T> subscriber) {
                        subscriber.onNext(eventType.cast(event));
                    }
                }));
            } else {
                return observable;
            }
        }
    }

    /**
     * 根据eventType获取Sticky事件
     * @param eventType  类型
     * @param <T> 泛型
     * @return  粘性事件
     */
    public <T> T getStickyEvent(Class<T> eventType){
        synchronized (mStickEventMap){
            return eventType.cast(mStickEventMap.get(eventType));
        }
    }

    /**
     * 移除指定eventType的Sticky事件
     * @param eventType  类型
     * @param <T> 泛型
     * @return 粘性事件
     */
    public <T> T removeStickyEvent(Class<T> eventType){
        synchronized (mStickEventMap){
            return eventType.cast(mStickEventMap.remove(eventType));
        }
    }

    /**
     * 移除所有的Sticky事件
     */
    public void removeAllStickyEvents(){
        synchronized (mStickEventMap){
            mStickEventMap.clear();
        }
    }


}
