package com.cainiao.factory.presenter.message;

import android.util.Log;

import com.cainiao.common.presenter.BasePresenter;

import java.util.List;

import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

/**
 * Created by wuyinlei on 2017/8/8.
 *
 * @function 最近会话列表Presenter
 */

public class RecentConversationPresenter extends BasePresenter<RecentConversationContract.View> implements RecentConversationContract.Presenter {


    public RecentConversationPresenter(RecentConversationContract.View view) {
        super(view);
    }

    @Override
    public void requestData() {

        RongIMClient.getInstance().getConversationList(new RongIMClient.ResultCallback<List<Conversation>>() {
            @Override
            public void onSuccess(List<Conversation> conversations) {
                if (conversations != null  && conversations.size() > 0){
//                    getView().getRecyclerViewAadpter().clear();
                    filterData(conversations);

                }
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.d("RecentConversationPrese", errorCode.getMessage());
            }
        });

    }

    //转换消息
    private void filterData(List<Conversation> conversations) {

        for (int i = 0; i < conversations.size(); i++) {
            Conversation item = conversations.get(i);
            //其他消息会话不显示（比如：系统消息）
            if (!(item.getConversationType() == Conversation.ConversationType.PRIVATE || item.getConversationType() == Conversation.ConversationType.GROUP)) {
                conversations.remove(i);
                i--;
                continue;
            }
            if (item.getConversationType() == Conversation.ConversationType.GROUP) {
                //群消息

            } else if (item.getConversationType() == Conversation.ConversationType.PRIVATE) {
                //判断不是是不是好友的消息

            }

           if (item.getLatestMessage().getUserInfo()==null){
               conversations.remove(i);
               i--;
               continue;
           }

        }

        getView().loadData(conversations);

    }

    @Override
    public void loadMoreData() {

    }
}
