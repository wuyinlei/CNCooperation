package com.cainiao.factory.presenter.message;


import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.cainiao.common.presenter.BasePresenter;
import com.cainiao.common.utils.ImageUtils;
import com.cainiao.common.utils.luban.Luban;
import com.cainiao.common.widget.logger.CNLogger;
import com.cainiao.factory.app.Account;
import com.cainiao.factory.R;
import com.cainiao.factory.utils.UploadHelper;

import java.io.File;
import java.io.IOException;
import java.security.spec.MGF1ParameterSpec;
import java.util.ArrayList;
import java.util.List;

import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;
import io.rong.message.ImageMessage;
import io.rong.message.TextMessage;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by wuyinlei on 2017/8/6.
 *
 * @function 聊天的Presenter
 */

public class ChatMessagePresenter extends BasePresenter<ChatMessageContract.ChatView> implements ChatMessageContract.Presenter {

    private static final String TAG = ChatMessagePresenter.class.getSimpleName();

    private Context mContext;

    private String mReceiverId;
    public Conversation.ConversationType mConversationType;
    private String mPushCotent = "";//接收方离线时需要显示的push消息内容。
    private String mPushData = "";//接收方离线时需要在push消息中携带的非显示内容。
    private int mMessageCount = 5;//一次获取历史消息的最大数量

    public ChatMessagePresenter(ChatMessageContract.ChatView view,
                                String receiverId,
                                Conversation.ConversationType conversationType) {
        super(view);
        mContext = (Activity) view;
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

        //携带用户信息
        UserInfo userInfo = new UserInfo(Account.getUser().getObjectId(),
                Account.getUserName(),
                Uri.parse(Account.getAvatar()));

        //设置用户信息
        message.setUserInfo(userInfo);

        RongIMClient.getInstance().sendMessage(Conversation.ConversationType.PRIVATE,
                mReceiverId,
                message,
                null,
                null,
                new RongIMClient.SendMessageCallback() {

                    @Override
                    public void onSuccess(Integer integer) {
//                        updateMessageStatus(integer);
                    }

                    @Override
                    public void onError(Integer integer, RongIMClient.ErrorCode errorCode) {

                    }
                }, new RongIMClient.ResultCallback<Message>() {
                    @Override
                    public void onSuccess(Message message) {
                        if (message.getContent() instanceof TextMessage) {

                            message.setExtra(Account.getAvatar());

                            getView().getRecyclerViewAadpter().add(message);

                            getView().scrollRecyclerToPosition(getView().getRecyclerViewAadpter().getItemCount() - 1);

                            CNLogger.d(TAG, "成功发送文本消息: " + ((TextMessage) message.getContent()).getContent());
                            CNLogger.d(TAG, "文本消息的附加信息: " + ((TextMessage) message.getContent()).getExtra() + '\n');
                        }
                    }

                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {
                        CNLogger.d(TAG, "发送消息失败，错误码: " + errorCode.getValue() + '\n');
                    }
                });

    }

    private void updateMessageStatus(int messageId) {
        RongIMClient.getInstance().getMessage(messageId, new RongIMClient.ResultCallback<Message>() {
            @Override
            public void onSuccess(Message message) {

                List<Message> items = getView().getRecyclerViewAadpter().getItems();

                for (int i = 0; i < items.size(); i++) {
                    Message msg = items.get(i);
                    if (msg.getMessageId() == message.getMessageId()) {
                        items.remove(i);
                        items.add(i, message);
                        getView().getRecyclerViewAadpter().replace(items);
                        break;
                    }
                }
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });
    }


    @Override
    public void pushAudio(String path, long time) {


    }

    @Override
    public void pushImages(ArrayList<String> paths) {

        for (final String path : paths) {


            Uri imageFileThumbUri = Uri.fromFile(ImageUtils.genThumbImgFile(path));
            Uri imageFileSourceUri = Uri.fromFile(new File(path));
//                sendImgMsg(imageFileThumbUri, imageFileSourceUri);

//            String portrait = UploadHelper.uploadPortrait(path);

            final ImageMessage imgMsg = ImageMessage.obtain(imageFileThumbUri, imageFileSourceUri);

            //携带用户信息
            UserInfo userInfo = new UserInfo(Account.getUser().getObjectId(),
                    Account.getUserName(),
                    Uri.parse(Account.getAvatar()));

            //设置用户信息
            imgMsg.setUserInfo(userInfo);

            //这个是上传到自己的服务器上
            RongIMClient.getInstance()
                    .sendImageMessage(Message.obtain(mReceiverId, Conversation.ConversationType.PRIVATE, imgMsg), null, null,
                            new RongIMClient.SendImageMessageWithUploadListenerCallback() {

                                @Override
                                public void onAttached(Message message, final RongIMClient.UploadImageStatusListener uploadImageStatusListener) {

                                    Observable.just(new File(path))
                                            .observeOn(Schedulers.io())
                                            .map(new Func1<File, File>() {
                                                @Override
                                                public File call(File file) {
                                                    try {
                                                        return Luban.with(mContext).load(file).get();
                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                    }
                                                    return null;
                                                }
                                            })
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(new Action1<File>() {
                                                @Override
                                                public void call(File file) {

                                                    String serverUrl = UploadHelper.uploadImage(path, null);
                                                    uploadImageStatusListener.success(Uri.parse(serverUrl));
                                                }
                                            });

                                }

                                @Override
                                public void onError(Message message, RongIMClient.ErrorCode errorCode) {

                                }

                                @Override
                                public void onSuccess(Message message) {
                                    if (message.getContent() instanceof ImageMessage) {

                                        message.setExtra(Account.getAvatar());

                                        getView().getRecyclerViewAadpter().add(message);

                                        getView().scrollRecyclerToPosition(getView().getRecyclerViewAadpter().getItemCount() - 1);

                                    }
                                }

                                @Override
                                public void onProgress(Message message, int progress) {
                                    //此处为您将图片上传至自己服务器的操作。

                                    // TODO: 2017/8/9 暂时没有用到

                                }
                            });

//            //一下的这个是上传到了融云的服务器   默认保存一个月  其实也就是七牛服务器
//            RongIMClient.getInstance().sendImageMessage(mConversationType, mReceiverId, imgMsg, null, null,
//                    new RongIMClient.SendImageMessageCallback() {
//                        @Override
//                        public void onAttached(Message message) {
//
//                            String portrait = UploadHelper.uploadPortrait(path);
//
//
////                              /*上传图片到自己的服务器*/
////                            uploadImg(imgMsg.getPicFilePath(), new UploadListener() {
////                                @Override
////                                public void onSuccess(String url) {
////                                    // 上传成功，回调 SDK 的 success 方法，传递回图片的远端地址
////                                    uploadImageStatusListener.success(Uri.parse(url));
////                                }
//
//                            //保存数据库成功
////                            mAdapter.addLastItem(message);
////                            rvMoveToBottom();
//                        }
//
//                        @Override
//                        public void onError(Message message, RongIMClient.ErrorCode errorCode) {
//                            //发送失败
////                            updateMessageStatus(message);
//                        }
//
//                        @Override
//                        public void onSuccess(Message message) {
//                            //发送成功
////                            updateMessageStatus(message);
//
//                            if (message.getContent() instanceof ImageMessage) {
//
//                                message.setExtra(Account.getAvatar());
//
//                                getView().getRecyclerViewAadpter().add(message);
//
//                                getView().scrollRecyclerToPosition(getView().getRecyclerViewAadpter().getItemCount() - 1);
//
////                                CNLogger.d(TAG, "成功发送图片消息: " + ((TextMessage) message.getContent()).getContent());
////                                CNLogger.d(TAG, "文本消息的附加信息: " + ((TextMessage) message.getContent()).getExtra() + '\n');
//                            }
//
//                        }
//
//                        @Override
//                        public void onProgress(Message message, int progress) {
//                            //发送进度
//                            message.setExtra(progress + "");
////                            updateMessageStatus(message);
//                        }
//                    });
//
//            //这个地方要把图片上传到云服务器  然后返回一个真实的外网的图片地址  赋值给path  然后在发送消息
//
//
////            MsgCreateModel model = new MsgCreateModel.Builder()
////                    .receiver(mReceiverId,mReceiverType)
////                    .content(path,Message.TYPE_PIC)
////                    .build();
////            //发送消息
////            MessageHelper.push(model);
        }

    }


    //单聊、群聊、讨论组、客服的历史消息从远端获取
    public void getRemoteHistoryMessages() {
        //消息中的 sentTime；第一次可传 0，获取最新 count 条。
        long dateTime = 0;
        if (getView().getRecyclerViewAadpter().getItemCount() > 0) {
            dateTime = getView().getRecyclerViewAadpter().getItems().get(0).getSentTime();
        } else {
            dateTime = 0;
        }

        RongIMClient.getInstance().getRemoteHistoryMessages(mConversationType, mReceiverId, dateTime, mMessageCount,
                new RongIMClient.ResultCallback<List<Message>>() {
                    @Override
                    public void onSuccess(List<Message> messages) {
                        updateMessage(messages);
                    }

                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {
                        CNLogger.d(TAG, errorCode.getMessage());
                    }
                });
    }

    /**
     * 更新本地历史消息
     *
     * @param messages 消息集合
     */
    private void updateMessage(List<Message> messages) {

        if (messages != null && messages.size() > 0) {
            for (Message msg : messages) {
                Message.ReceivedStatus receivedStatus = msg.getReceivedStatus();
                if (!receivedStatus.isRead()){
                    receivedStatus.setRead();
                    // 更新数据库中消息的状态
                    RongIMClient.getInstance().setMessageReceivedStatus(msg.getMessageId(), msg.getReceivedStatus(), null);
                }
                getView().getRecyclerViewAadpter().getItems().add(0, msg);
            }
            Log.d(TAG, "messages.size():" + messages.size());
            CNLogger.d(TAG, "messages.size():" + messages.size());

            getView().scrollRecyclerToPosition(messages.size() - 1);
        }


    }


    //获取会话中，从指定消息之前、指定数量的最新消息实体
    public void getLocalHistoryMessage() {
        //没有消息第一次调用应设置为:-1。
        int messageId = -1;
        if (getView().getRecyclerViewAadpter().getItems().size() > 0) {
            messageId = getView().getRecyclerViewAadpter().getItems().get(0).getMessageId();
        } else {
            messageId = -1;
        }

        RongIMClient.getInstance().getHistoryMessages(mConversationType, mReceiverId, messageId, mMessageCount, new RongIMClient.ResultCallback<List<Message>>() {
            @Override
            public void onSuccess(List<Message> messages) {
                if (messages == null || messages.size() == 0)
                    getRemoteHistoryMessages();
                else
                    updateMessage(messages);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });
    }


    @Override
    public boolean rePush(Message message) {

        return false;
    }

    @Override
    public void request() {
        getLocalHistoryMessage();
    }


}
