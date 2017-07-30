package com.cainiao.factory;


import com.cainiao.factory.model.MyUser;

import cn.bmob.v3.BmobUser;

/**
 * Created by wuyinlei on 2017/7/26.
 *
 * @function 账户管理类
 */

public class Account {

    /**
     * 获取当前登录的用户信息
     *
     * @return user
     */
    public static MyUser getUser() {
        //如果为null  返回一个new User  其次从数据库中查询
        return BmobUser.getCurrentUser(MyUser.class);
    }

    /**
     * 返回当前账号是否登录
     * </p>
     *
     * @return true  false
     */
    public static boolean isLogin() {

        return getUser() != null;
    }

    /**
     * 获取到当前用户的头像
     *
     * @return 当前用户的头像
     */
    public static String getAvatar() {
        return getUser().getAvatar();
    }

    /**
     * 获取到当前用户的名字
     *
     * @return 用户名字
     */
    public static String getUserName() {
        return getUser().getUsername();
    }
}
