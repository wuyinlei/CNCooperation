package com.cainiao.factory.model.circle;

/**
 * Created by wuyinlei on 2017/7/29.
 *
 * @function 文章动态详情bean
 */

public class DetailComment {

    private String username;
    private String avatar;
    private String createDate;
    private String content;
    private boolean isAlias;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isAlias() {
        return isAlias;
    }

    public void setAlias(boolean alias) {
        isAlias = alias;
    }
}
