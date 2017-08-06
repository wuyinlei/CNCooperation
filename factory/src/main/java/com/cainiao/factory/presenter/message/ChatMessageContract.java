package com.cainiao.factory.presenter.message;

import com.cainiao.common.presenter.BaseContract;

import java.util.List;

import io.rong.imlib.model.Message;

/**
 * Created by wuyinlei on 2017/8/6.
 *
 * @function 聊天的契约
 */

public interface ChatMessageContract {


    interface Presenter extends BaseContract.Presenter {

        //发送文字
        void pushText(String content);

        //发送语音
        void pushAudio(String path, long time);

        //发送图片
        void pushImages(String[] paths);

        //重新发送一个消息  是否调度成功
        boolean rePush(Message message);
    }


    interface ChatView extends BaseContract.RecyclerView<Presenter, Message> {

        void loadData(List<Message> messages);

        void publishSuccess(Message message);

    }


}
