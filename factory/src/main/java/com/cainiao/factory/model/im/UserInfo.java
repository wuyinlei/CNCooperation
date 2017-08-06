package com.cainiao.factory.model.im;

import com.cainiao.common.utils.TimeUtils;


import cn.bmob.v3.BmobObject;

/**
 * Created by wuyinlei on 2017/8/5.
 *
 * @function 数据库保存的好友信息
 */

public class UserInfo extends BmobObject {


    private String userid;
    private String name;
    private String portrail;
    private String joinAt;
    private String alias;

    public UserInfo(String userid, String name, String portrail, String joinAt, String alias) {
        this.userid = userid;
        this.name = name;
        this.portrail = portrail;
        this.joinAt = joinAt;
        this.alias = alias;
    }

    public UserInfo(String userid, String name, String portrail, String alias) {
        this.userid = userid;
        this.name = name;
        this.portrail = portrail;
        this.alias = alias;
        this.joinAt = TimeUtils.currentTimeFormat();
    }

    public UserInfo(String userid, String name, String portrail) {
        this.userid = userid;
        this.name = name;
        this.portrail = portrail;
        this.joinAt = TimeUtils.currentTimeFormat();
    }


    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPortrail() {
        return portrail;
    }

    public void setPortrail(String portrail) {
        this.portrail = portrail;
    }

    public String getJoinAt() {
        return joinAt;
    }

    public void setJoinAt(String joinAt) {
        this.joinAt = joinAt;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
