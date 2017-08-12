package com.cainiao.factory.app;

import android.app.Activity;
import android.app.Application;

import com.cainiao.common.base.BaseApplication;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

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

    public Factory() {
        defaultExecutor = Executors.newFixedThreadPool(4);//新建一个线程池
    }

    /**
     * Factory中的初始化
     */
    public static void setUp() {

        FlowManager.init(new FlowConfig.Builder(app())
                .openDatabasesOnInit(true)  //数据库初始化的时候打开数据库
                .build());

    }

    public static void dispathPushMessage(){

    }

}
