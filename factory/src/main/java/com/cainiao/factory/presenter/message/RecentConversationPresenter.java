package com.cainiao.factory.presenter.message;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.cainiao.common.presenter.BasePresenter;

/**
 * Created by wuyinlei on 2017/8/8.
 *
 * @function 最近会话列表Presenter
 */

public class RecentConversationPresenter extends BasePresenter<RecentConversationContract.View> implements RecentConversationContract.Presenter {

    private Context mContext;

    public RecentConversationPresenter(RecentConversationContract.View view) {
        super(view);
        this.mContext = (FragmentActivity) view;
    }

    @Override
    public void requestData() {

    }

    @Override
    public void loadMoreData() {

    }
}
