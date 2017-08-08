package com.cainiao.factory.event;

import io.rong.imlib.model.Message;

/**
 * Created by wuyinlei on 2017/8/7.
 *
 * @function 消息事件  用于接收到的推送消息
 */

public class MessageEvent {


    public Message mMessage;

    public MessageEvent(Message message) {
        this.mMessage = message;
    }

}
