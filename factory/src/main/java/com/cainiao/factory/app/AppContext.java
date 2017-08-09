package com.cainiao.factory.app;

import android.content.Context;
import android.util.Log;

import com.cainiao.common.widget.logger.CNLogger;
import com.cainiao.factory.model.im.CustomizeMessage;
import com.cainiao.factory.db.DataSource;
import com.cainiao.factory.helper.MessageHelper;

import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Message;
import io.rong.message.ContactNotificationMessage;
import io.rong.message.FileMessage;
import io.rong.message.GroupNotificationMessage;
import io.rong.message.ImageMessage;
import io.rong.message.TextMessage;
import io.rong.message.VoiceMessage;

/**
 * Created by Beyond on 27/10/2016.
 *
 * @function AppContext
 */

public class AppContext {
    private static final String TAG = "AppContext";
    private int mMessageId;

    private static AppContext appContext = new AppContext();
    private Context mContext;

    private AppContext() {
    }

    public static AppContext getInstance() {
        return appContext;
    }

    public int getMessageId() {
        return mMessageId;
    }

    private void setMessageId(int mMessageId) {
        this.mMessageId = mMessageId;
    }

    public void init(Context context) {
        mContext = context.getApplicationContext();
    }

    public void registerReceiveMessageListener() {
        RongIMClient.setOnReceiveMessageListener(onReceiveMessageListener);
    }

    /**
     * 设置接收消息的监听器
     */
    private RongIMClient.OnReceiveMessageListener onReceiveMessageListener = new RongIMClient.OnReceiveMessageListener() {
        @Override
        public boolean onReceived(Message message, int i) {
            if (message.getContent() instanceof ContactNotificationMessage) {
                ContactNotificationMessage contactNotificationMessage = (ContactNotificationMessage) message.getContent();

                if (contactNotificationMessage.getOperation().equals(ContactNotificationMessage.CONTACT_OPERATION_REQUEST)) {
                    //对方发送来的好友请求信息
                } else {
                    //对方同意我的好友请求信息

                }
            } else if (message.getContent() instanceof GroupNotificationMessage) {
                //群组消息信息
                GroupNotificationMessage groupNotificationMessage = (GroupNotificationMessage) message.getContent();
                String groupId = message.getTargetId();
//                GroupNotificationMessageData data = null;

                //群组创建的消息
                if (groupNotificationMessage.getOperation().equals(GroupNotificationMessage.GROUP_OPERATION_CREATE)) {

                }else if (groupNotificationMessage.getOperation().equals(GroupNotificationMessage.GROUP_OPERATION_DISMISS)) {
                    //解散

                }else if (groupNotificationMessage.getOperation().equals(GroupNotificationMessage.GROUP_OPERATION_KICKED)) {
                    //剔除群

                }

            }

            else if (message.getContent() instanceof TextMessage) {
                CNLogger.d(TAG, "收到文本消息: " + ((TextMessage) message.getContent()).getContent());
                CNLogger.d(TAG, "文本消息的附加信息: " + ((TextMessage) message.getContent()).getExtra() + '\n');
                setMessageRead(message); //设置收到的消息为已读消息
            } else if (message.getContent() instanceof ImageMessage) {
                CNLogger.d(TAG, "收到图片消息, Uri --> " + ((ImageMessage) message.getContent()).getThumUri() + '\n');
                setMessageRead(message);
            } else if (message.getContent() instanceof VoiceMessage) {
                CNLogger.d(TAG, "收到语音消息,Uri --> " + ((VoiceMessage) message.getContent()).getUri());
                CNLogger.d(TAG, "语音消息时长: " + ((VoiceMessage) message.getContent()).getDuration() + '\n');
            } else if (message.getContent() instanceof FileMessage) {
                CNLogger.d(TAG, "服务端 Uri --> " + ((FileMessage) message.getContent()).getFileUrl() + '\n');
            } else if (message.getContent() instanceof CustomizeMessage) {
                CNLogger.d(TAG, "成功发送自定义消息，它的时间戳: " + ((CustomizeMessage) message.getContent()).getSendTime());
                CNLogger.d(TAG, "自定义消息的内容: " + ((CustomizeMessage) message.getContent()).getContent() + '\n');
            }
            setMessageId(message.getMessageId());
            return false;
        }
    };


    //和presenter交互的回调
    private DataSource.SuccessCallback<Message> mCallback;

    public void setCallback(DataSource.SuccessCallback<Message> callback) {
        mCallback = callback;
    }

    /**
     * 设置消息为已读消息
     */
    private void setMessageRead(Message message) {
        if (message.getMessageId() > 0) {
            Message.ReceivedStatus status = message.getReceivedStatus();
            status.setRead();
            message.setReceivedStatus(status);
            RongIMClient.getInstance().setMessageReceivedStatus(message.getMessageId(), status, null);
            Log.d(TAG, "该条消息已设置为已读");

            if (mCallback != null){
                mCallback.onDataLoaded(message);
            }

            MessageHelper.disposeMessage(message);

        }
    }
}
