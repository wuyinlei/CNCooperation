package com.cainiao.factory.helper;

import com.cainiao.factory.db.Friend;
import com.cainiao.factory.model.MyUser;
import com.cainiao.factory.model.im.UserInfo;
import com.cainiao.factory.utils.BmobUtils;

import cn.bmob.v3.BmobQuery;


/**
 * Created by wuyinlei on 2017/8/6.
 */

public class UserHelper {


    /**
     * 搜索一个用户 有限本地缓存  如果没有就走网络
     *
     * @param userId 用户id
     * @return User
     */
//    public static UserInfo searchUser(String userId) {
//        UserInfo user = findFromLocal(userId);
//        UserInfo user = null;
//        if (user == null) {
//            return findFormNet(userId);
//        }
//        return user;
//    }

//    private static UserInfo findFormNet(String userId) {
//
//        BmobQuery<MyUser> query = new BmobQuery<>();
//        query.setSQL("")
//
//        BmobUtils.queryUserInfo(userId, new BmobUtils.OnListener<UserInfo>() {
//            @Override
//            public void onError(int errorCode, String message) {
//
//            }
//
//            @Override
//            public void onSuccess(UserInfo data) {
//                listener.onSuccess(data);
//            }
//        });
//
//    }
//
//    private static UserInfo findFromLocal(String userId) {
//
//        return SQLite.select()
//                .from(Friend.class)
//                .where(Friend_Table.id.eq(userId))
//                .querySingle();
//    }


}
