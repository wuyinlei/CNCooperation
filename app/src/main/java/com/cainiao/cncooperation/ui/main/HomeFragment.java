package com.cainiao.cncooperation.ui.main;


import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cainiao.cncooperation.R;
import com.cainiao.common.base.BaseFragment;
import com.cainiao.common.widget.logger.CNLogger;
import com.cainiao.factory.Account;
import com.cainiao.factory.model.MyUser;
import com.cainiao.factory.model.circle.FriendCircle;
import com.cainiao.factory.model.circle.FriendCircleComment;
import com.cainiao.factory.rongyun.FakeServer;
import com.cainiao.factory.rongyun.HttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.message.TextMessage;

import static io.rong.imlib.statistics.Statistics.TAG;

/**
 * @function 首页界面
 */
public class HomeFragment extends BaseFragment {


    private static final String TAG = "HomeFragment";

    @Override
    public int setLayoutId() {
        return R.layout.fragment_home;
    }


    @BindView(R.id.btnRequest)
    Button mButton;

    String token;


    @Override
    protected void initView(View view) {
        super.initView(view);

    }

    @OnClick(R.id.collectRongCloud)
    public void collectRong() {
        RongIMClient.connect(token, new RongIMClient.ConnectCallback() {

            /**
             * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的 Token
             */
            @Override
            public void onTokenIncorrect() {
                CNLogger.d("HomeFragment", "Token 错误---onTokenIncorrect---" + '\n');
            }

            /**
             * 连接融云成功
             * @param userid 当前 token
             */
            @Override
            public void onSuccess(String userid) {
                Log.d("HomeFragment", "连接融云成功---onSuccess---用户ID:" + userid + '\n');
            }

            /**
             * 连接融云失败
             * @param errorCode 错误码，可到官网 查看错误码对应的注释
             */
            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.d("HomeFragment", "连接融云失败, 错误码: " + errorCode + '\n');
            }
        });

    }

    @OnClick(R.id.sendTextMessage)
    public void sendTxtMessage(){

        TextMessage message = TextMessage.obtain("Hello Daibai");
        message.setExtra("若兰dddd");

        RongIMClient.getInstance().sendMessage(Conversation.ConversationType.PRIVATE, "f168dd00b6",
                message, null, null, new IRongCallback.ISendMessageCallback() {
                    @Override
                    public void onAttached(Message message) {
                        Log.d(TAG, "发送的文本消息已保存至本地数据库中");
                    }

                    @Override
                    public void onSuccess(Message message) {
                        if (message.getContent() instanceof TextMessage) {
                            Log.d(TAG, "成功发送文本消息: " + ((TextMessage) message.getContent()).getContent());
                            Log.d(TAG, "文本消息的附加信息: " + ((TextMessage) message.getContent()).getExtra() + '\n');
                        }
                    }

                    @Override
                    public void onError(Message message, RongIMClient.ErrorCode errorCode) {
                        Log.d(TAG, "发送消息失败，错误码: " + errorCode.getValue() + '\n');
                    }
                });
    }


    /**
     * 获取会话(Conversation)信息
     */
    @OnClick(R.id.conversationData)
    public void getConversation() {
//        RongIMClient.getInstance().getConversation(Conversation.ConversationType.PRIVATE, "80cf8a6a7e",
//                new RongIMClient.ResultCallback<Conversation>() {
//                    @Override
//                    public void onSuccess(Conversation conversation) {
//                        if (conversation != null) {
//                            Log.d(TAG, "获取的会话信息:");
//                            Log.d(TAG, "会话类型: " + conversation.getConversationType());
//                            Log.d(TAG, "发送者ID: " + conversation.getSenderUserId());
//                            Log.d(TAG, "接收者ID: " + conversation.getTargetId());
//                            Log.d(TAG, "最新一条消息的ID: " + conversation.getLatestMessageId());
//                            Log.d(TAG, "最新一条消息内容:");
////                            handleMessage(conversation.getLatestMessage());
//                            Log.d(TAG, "消息提醒状态: " + ((conversation.getNotificationStatus().getValue() == 1) ? "接收消息" : "消息免打扰"));
//                            Log.d(TAG, "未读消息数: " + conversation.getUnreadMessageCount() + '\n');
//                        } else
//                            Log.d(TAG, "会话不存在" + '\n');
//                    }
//
//                    @Override
//                    public void onError(RongIMClient.ErrorCode errorCode) {
//                        Log.d(TAG, "获取会话信息失败，错误码: " + errorCode.getValue() + '\n');
//                    }
//                });

        /**
         * 读取远程服务器的消息
         */
            RongIMClient.getInstance().getRemoteHistoryMessages(Conversation.ConversationType.PRIVATE, "80cf8a6a7e", 0, 10, new RongIMClient.ResultCallback<List<Message>>() {
                @Override
                public void onSuccess(List<Message> messages) {
                    if (messages != null) {
                        Log.d(TAG, "远端服务器存储的历史消息个数为 " + messages.size());

                        for (Message message: messages) {
//                            handleMessage(message.getContent());
                        }
                    } else
                        Log.d(TAG, "远端服务器存储的历史消息个数为 0" + '\n');
                }

                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    Log.d(TAG, "读取远端服务器存储的历史消息失败，错误码: " + errorCode.getValue() + '\n');
                }
            });
    }

    @OnClick(R.id.btnRequest)
    public void btnRequest() {

        //80cf8a6a7e     f168dd00b6
        FakeServer.getToken("80cf8a6a7e", "若兰or明月", "http://loveruolan.oss-cn-shanghai.aliyuncs.com/portrait/201707/6e8ad1e62302f9c446b78c00d51b9cff.jpg", new HttpUtil.OnResponse() {
            @Override
            public void onResponse(int code, String body) {
                if (code == 200) {
                    JSONObject jsonObj = null;
                    try {
                        jsonObj = new JSONObject(body);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    token = jsonObj.optString("token");
                    Log.i("HomeFragment", "获取的 token 值为:\n" + token + '\n');
                } else {
                    Log.i("HomeFragment", "获取 token 失败" + '\n');
                }
            }
        });


    }

}
