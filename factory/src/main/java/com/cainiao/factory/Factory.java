package com.cainiao.factory;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.cainiao.common.base.BaseApplication;
import com.google.gson.GsonBuilder;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by wuyinlei on 2017/7/26.
 *
 * @function 工具类 可以异步执行数据
 */

public class Factory {

    //全局的线程池
    private final Executor defaultExecutor;

    private static final Factory instance;


    static {
        instance = new Factory();
    }

    public Factory() {
        defaultExecutor = Executors.newFixedThreadPool(4);//新建一个线程池
    }


    public static Application app() {
        return BaseApplication.getInstance();
    }

    /**
     * 异步执行的方法
     *
     * @param runnable runnable
     */
    public static void runOnAsync(Runnable runnable) {
        //拿到单利  拿到线程池
        instance.defaultExecutor.execute(runnable);  //执行runnable
    }

    public static void runOnUiThread(Activity context){
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });
    }


    public static void dispathPushMessage(){

    }

}
