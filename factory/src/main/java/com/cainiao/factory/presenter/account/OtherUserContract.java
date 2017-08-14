package com.cainiao.factory.presenter.account;


import com.cainiao.common.presenter.BaseContract;
import com.cainiao.factory.model.MyUser;
import com.cainiao.factory.utils.BmobUtils;

/**
 * Created by wuyinlei on 2017/8/14.
 *
 * @function 契约类
 */

public interface OtherUserContract {

    public interface View extends BaseContract.View<OtherUserContract.Presenter> {

        void onQueryUserInfoFailure(int errorCode, String errMsg);

        void onQueryUserInfoSuccess(MyUser user);


        void isFriend(boolean isFriend);

        void addFriendSuccess(String data);
    }


    public interface Presenter extends BaseContract.Presenter {


        void queryUserInfo(String objectId);


        void addFriend(MyUser currentUser, String alias, MyUser friend);

        void queryIsFriend(String objectId);

    }

}
