package com.cainiao.factory.event;

import io.rong.imlib.model.Message;

/**
 * Created by wuyinlei on 2017/8/7.
 */

public class MessageEvent {


    public Message mMessage;

    public MessageEvent(Message message) {
        this.mMessage = message;
    }

}
