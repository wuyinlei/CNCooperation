package com.cainiao.factory.presenter.account;

import android.text.TextUtils;

import com.cainiao.common.presenter.BasePresenter;
import com.cainiao.factory.R;
import com.cainiao.factory.model.MyUser;
import com.cainiao.factory.model.im.CircleFriend;
import com.cainiao.factory.utils.BmobUtils;
import com.google.gson.internal.bind.util.ISO8601Utils;

/**
 * Created by wuyinlei on 2017/8/14.
 *
 * @function
 */

public class OtherUserPresenter extends BasePresenter<OtherUserContract.View> implements OtherUserContract.Presenter {


    public OtherUserPresenter(OtherUserContract.View view) {
        super(view);
    }

    @Override
    public void queryUserInfo(String objectId) {
        BmobUtils.queryUserInfo(objectId, new BmobUtils.OnListener<MyUser>() {
            @Override
            public void onError(int errorCode, String message) {
                getView().onQueryUserInfoFailure(errorCode, message);
            }

            @Override
            public void onSuccess(MyUser user) {
                if (user != null) {
                    getView().onQueryUserInfoSuccess(user);
                } else {
//                    getView().showError(R.string.register_ing);

                }
            }
        });
    }

    @Override
    public void addFriend(MyUser currentUser, String alias, MyUser friend) {

        BmobUtils.addFriend(currentUser.getObjectId(), alias, friend, new BmobUtils.OnListener<String>() {
            @Override
            public void onError(int errorCode, String message) {
                getView().showError(R.string.add_friend_failure);
            }

            @Override
            public void onSuccess(String data) {
                getView().addFriendSuccess(data);
            }
        });
    }

    @Override
    public void queryIsFriend(String objectId) {
        BmobUtils.queryIsFriend(objectId, new BmobUtils.OnListener<CircleFriend>() {
            @Override
            public void onError(int errorCode, String message) {

            }

            @Override
            public void onSuccess(CircleFriend data) {
                if (data != null) {
                    getView().isFriend(true);
                } else {
                    getView().isFriend(false);
                }
            }
        });
    }
}
