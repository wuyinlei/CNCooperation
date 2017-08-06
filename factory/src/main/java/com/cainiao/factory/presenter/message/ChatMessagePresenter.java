package com.cainiao.factory.presenter.message;


import android.text.TextUtils;

import com.cainiao.common.presenter.BasePresenter;
import com.cainiao.common.widget.logger.CNLogger;
import com.cainiao.factory.Account;
import com.cainiao.factory.R;

import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.message.TextMessage;

/**
 * Created by wuyinlei on 2017/8/6.
 *
 * @function 聊天的Presenter
 */

public class ChatMessagePresenter extends BasePresenter<ChatMessageContract.ChatView> implements ChatMessageContract.Presenter {

    private static final String TAG = ChatMessagePresenter.class.getSimpleName();

    private String mReceiverId;
    public Conversation.ConversationType mConversationType;
    private String mPushCotent = "";//接收方离线时需要显示的push消息内容。
    private String mPushData = "";//接收方离线时需要在push消息中携带的非显示内容。
    private int mMessageCount = 10;//一次获取历史消息的最大数量

    public ChatMessagePresenter(ChatMessageContract.ChatView view,
                                String receiverId,
                                Conversation.ConversationType conversationType) {
        super(view);
        mReceiverId = receiverId;
        mConversationType = conversationType;
    }


    @Override
    public void pushText(String content) {

        if (TextUtils.isEmpty(content)) {
            getView().showError(R.string.say_something);
            return;
        }

        TextMessage message = TextMessage.obtain(content);

        RongIMClient.getInstance().sendMessage(Conversation.ConversationType.PRIVATE, mReceiverId,
                message, null, null, new IRongCallback.ISendMessageCallback() {
                    @Override
                    public void onAttached(Message message) {
                        CNLogger.d(TAG, "发送的文本消息已保存至本地数据库中");
                    }

                    @Override
                    public void onSuccess(Message message) {
                        if (message.getContent() instanceof TextMessage) {

                            message.setExtra(Account.getAvatar());

                            getView().getRecyclerViewAadpter().add(message);

                            CNLogger.d(TAG, "成功发送文本消息: " + ((TextMessage) message.getContent()).getContent());
                            CNLogger.d(TAG, "文本消息的附加信息: " + ((TextMessage) message.getContent()).getExtra() + '\n');
                        }
                    }

                    @Override
                    public void onError(Message message, RongIMClient.ErrorCode errorCode) {
                        CNLogger.d(TAG, "发送消息失败，错误码: " + errorCode.getValue() + '\n');
                    }
                });

    }

    @Override
    public void pushAudio(String path, long time) {


    }

    @Override
    public void pushImages(String[] paths) {


    }

    @Override
    public boolean rePush(Message message) {

        return false;
    }
}