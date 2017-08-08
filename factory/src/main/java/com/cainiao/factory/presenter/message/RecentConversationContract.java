package com.cainiao.factory.presenter.message;

import com.cainiao.common.presenter.BaseContract;

import java.util.List;

import io.rong.imlib.model.Conversation;

/**
 * Created by wuyinlei on 2017/8/8.
 *
 * @function 最近会话契约
 */

public interface RecentConversationContract {

    interface View extends BaseContract.RecyclerView<RecentConversationContract.Presenter, Conversation> {

        void loadData(List<Conversation> messages);

        void loadMoreData(List<Conversation> messages);

    }

    interface Presenter extends BaseContract.Presenter {

        void requestData();

        void loadMoreData();

    }



}
