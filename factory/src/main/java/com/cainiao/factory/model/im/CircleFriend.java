package com.cainiao.factory.model.im;

import com.cainiao.factory.model.MyUser;

import cn.bmob.v3.BmobObject;

/**
 * Created by wuyinlei on 2017/8/14.
 *
 * @function 朋友表
 */

public class CircleFriend extends BmobObject {


    private String originId;  //自己
    private String alias;  //别名
    private MyUser user;  //好友信息
    private String targetId;  //目标id

    public String getOriginId() {
        return originId;
    }

    public void setOriginId(String originId) {
        this.originId = originId;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public MyUser getUser() {
        return user;
    }

    public void setUser(MyUser user) {
        this.user = user;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getTargetId() {
        return targetId;
    }
}
