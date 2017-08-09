package com.cainiao.cncooperation.ui.main;


import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.cainiao.cncooperation.R;
import com.cainiao.cncooperation.ui.im.ChatActivity;
import com.cainiao.common.base.BaseFragment;
import com.cainiao.common.constant.Common;
import com.cainiao.common.utils.SharedUtils;
import com.cainiao.common.widget.logger.CNLogger;
import com.cainiao.factory.utils.rongyun.FakeServer;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

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

        token = SharedUtils.getString(getContext(), Common.Constance.RONGYUNG_IM_TOKEN, "");

    }

    @OnClick(R.id.collectRongCloud)
    public void collectRong() {


    }

    @OnClick(R.id.sendTextMessage)
    public void sendTxtMessage() {

//        TextMessage message = TextMessage.obtain("Hello Daibai");
//        message.setExtra("若兰dddd");
//
//        RongIMClient.getInstance().sendMessage(Conversation.ConversationType.PRIVATE, "f168dd00b6",
//                message, null, null, new IRongCallback.ISendMessageCallback() {
//                    @Override
//                    public void onAttached(Message message) {
//                        Log.d(TAG, "发送的文本消息已保存至本地数据库中");
//                    }
//
//                    @Override
//                    public void onSuccess(Message message) {
//                        if (message.getContent() instanceof TextMessage) {
//                            Log.d(TAG, "成功发送文本消息: " + ((TextMessage) message.getContent()).getContent());
//                            Log.d(TAG, "文本消息的附加信息: " + ((TextMessage) message.getContent()).getExtra() + '\n');
//                        }
//                    }
//
//                    @Override
//                    public void onError(Message message, RongIMClient.ErrorCode errorCode) {
//                        Log.d(TAG, "发送消息失败，错误码: " + errorCode.getValue() + '\n');
//                    }
//                });

        RongIMClient.getInstance().getConversationList(new RongIMClient.ResultCallback<List<Conversation>>() {
            @Override
            public void onSuccess(List<Conversation> conversations) {
                if (conversations != null && conversations.size() > 0) {
                    Log.d(TAG, "conversations.size():" + conversations.size());
                }
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
            }
        });
    }


    /**
     * 获取会话(Conversation)信息
     */
    @OnClick(R.id.conversationData)
    public void getConversation() {

        RongIMClient.connect(token, new RongIMClient.ConnectCallback() {

            /**
             * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的 Token
             */
            @Override
            public void onTokenIncorrect() {
                CNLogger.d("HomeFragment", "Token 错误---onTokenIncorrect---" + '\n');
                FakeServer.getRongYunToken(getActivity());

            }

            /**
             * 连接融云成功
             * @param userid 当前 token
             */
            @Override
            public void onSuccess(String userid) {
//                Toast.makeText(getContext(), "连接融云成功---onSuccess---用户ID:" + userid + '\n', Toast.LENGTH_SHORT).show();
                Log.d("HomeFragment", "连接融云成功---onSuccess---用户ID:" + userid + '\n');

                //链接成功之后开启聊天 80cf8a6a7e       f168dd00b6   大白思密达
                ChatActivity.show(getActivity(), "80cf8a6a7e", Common.Constance.SINGLE_TYPE);

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

    @OnClick(R.id.btnRequest)
    public void btnRequest() {


    }

}
