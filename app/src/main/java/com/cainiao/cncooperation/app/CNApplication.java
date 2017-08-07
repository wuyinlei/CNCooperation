package com.cainiao.cncooperation.app;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cainiao.common.base.BaseApplication;
import com.cainiao.common.widget.nineimage.NineGridView;
import com.cainiao.factory.app.AppContext;
import com.cainiao.factory.model.im.CustomizeMessage;
import com.cainiao.factory.app.Factory;

import cn.bmob.v3.Bmob;
import io.rong.imlib.AnnotationNotFoundException;
import io.rong.imlib.RongIMClient;
import io.rong.message.FileMessage;

/**
 * Created by wuyinlei on 2017/7/22.
 *
 * @function 全局的application
 *
 */

public class CNApplication extends BaseApplication {


    private static final String APP_KEY = "sfci50a7s1b2i";

    @Override
    public void onCreate() {
        super.onCreate();

        Factory.setUp();

        /**
         * OnCreate 会被多个进程重入，这段保护代码，确保只有您需要使用 RongIMClient 的进程和 Push 进程执行了 init。
         * io.rong.push 为融云 push 进程名称，不可修改。
         */
        if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext())) ||
                "io.rong.push".equals(getCurProcessName(getApplicationContext()))) {
            RongIMClient.init(this, APP_KEY);
        }

        /**
         * 用于自定义消息的注册, 注册后方能正确识别自定义消息, 建议在init后及时注册，保证自定义消息到达时能正确解析。
         */
        try {
            RongIMClient.registerMessageType(FileMessage.class);
            RongIMClient.registerMessageType(CustomizeMessage.class);
        } catch (AnnotationNotFoundException e) {
            e.printStackTrace();
        }

        AppContext.getInstance().init(getApplicationContext());
        AppContext.getInstance().registerReceiveMessageListener();

//        RongIM.init(this);
//        RongIMClient.init(this, "sfci50a7s1b2i");

        //第一：默认初始化
        Bmob.initialize(this, "d6bc5f51d6b9294f22c8d4a1a863f910");
        //第一：默认初始化
//        Bmob.initialize(this, "d6bc5f51d6b9294f22c8d4a1a863f910");

        NineGridView.setImageLoader(new PicassoImageLoader());

    }


    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid)
                return appProcess.processName;
        }
        return null;
    }

    /**
     * Glide加载
     */
    private class PicassoImageLoader implements NineGridView.ImageLoader {

        @Override
        public void onDisplayImage(Context context, ImageView imageView, String url) {
            Glide.with(context).load(url)//
                    .into(imageView);
        }

        @Override
        public Bitmap getCacheImage(String url) {
            return null;
        }
    }

}
