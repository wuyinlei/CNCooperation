package com.cainiao.factory.helper;

import com.cainiao.factory.Factory;

import io.rong.imlib.model.Message;

/**
 * Created by wuyinlei on 2017/8/6.
 */

public class MessageHelper {


    public static void disposeMessage(Message message) {

        Factory.runOnAsync(new Runnable() {
            @Override
            public void run() {

            }
        });
    }
}
