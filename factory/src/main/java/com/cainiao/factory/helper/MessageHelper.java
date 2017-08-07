package com.cainiao.factory.helper;

import com.cainiao.factory.app.Factory;
import com.cainiao.factory.event.MessageEvent;
import com.cainiao.common.rxbus.RxBus;

import io.rong.imlib.model.Message;

/**
 * Created by wuyinlei on 2017/8/6.
 *
 * @function 消息分发器
 */

public class MessageHelper {

    public static void disposeMessage(final Message message) {
        Factory.runOnAsync(new Runnable() {
            @Override
            public void run() {
                RxBus.getDefault().post(new MessageEvent(message));
            }
        });
    }
}
