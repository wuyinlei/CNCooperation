package com.cainiao.cncooperation.ui.main;


import android.view.View;
import android.widget.Button;

import com.cainiao.cncooperation.R;
import com.cainiao.common.base.BaseFragment;
import com.cainiao.common.constant.Common;
import com.cainiao.common.utils.SharedUtils;

import butterknife.BindView;
import butterknife.OnClick;

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

    }


    /**
     * 获取会话(Conversation)信息
     */
    @OnClick(R.id.conversationData)
    public void getConversation() {



//        ChatActivity.show(getActivity(), "80cf8a6a7e", Common.Constance.SINGLE_TYPE);
    }

    @OnClick(R.id.btnRequest)
    public void btnRequest() {


    }

}
