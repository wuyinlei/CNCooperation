package com.cainiao.common.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.cainiao.common.constant.Common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuyinlei on 2017/7/22.
 *
 * @function application
 */

public class BaseApplication extends Application {

    protected static Application instance;

    private List<BaseActivity> mActivities = new ArrayList<>();


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        instance.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                if (activity instanceof BaseActivity)
                    mActivities.add((BaseActivity) activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                mActivities.remove(activity);
            }
        });
    }

    /**
     * 移除所有的activity
     * @param context  上下文
     */
    public void finishAll(Context context) {
        for (BaseActivity activity : mActivities) {
            activity.finish();
        }

        showAccount(context);
    }

    /**
     * 到用户登录界面
     * @param context  上下文
     */
    protected void showAccount(Context context) {

    }

}
