package com.cainiao.factory.model.circle;

import com.cainiao.factory.model.MyUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuyinlei on 2017/7/29.
 *
 * @function 朋友圈的可视bean
 */

public class CircleViewBean {

    private String avator;
    private String objectId;
    private String username;
    private String createDate;
    private String content;
    private String likescount;
    private String commentcount;
    private List<String> images;
    private String viewcount;

    private List<CircleComment> comment = new ArrayList<>();

    public String getAvator() {
        return avator;
    }

    public void setAvator(String avator) {
        this.avator = avator;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getObjectId() {
        return objectId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getLikescount() {
        return likescount;
    }

    public void setLikescount(String likescount) {
        this.likescount = likescount;
    }

    public String getCommentcount() {
        return commentcount;
    }

    public void setCommentcount(String commentcount) {
        this.commentcount = commentcount;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getViewcount() {
        return viewcount;
    }

    public void setViewcount(String viewcount) {
        this.viewcount = viewcount;
    }

    public List<CircleComment> getComment() {
        return comment;
    }

    public void setComment(List<CircleComment> comment) {
        this.comment = comment;
    }

    public static class CircleComment {
        String comment;
        MyUser user;

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public MyUser getUser() {
            return user;
        }

        public void setUser(MyUser user) {
            this.user = user;
        }
    }

    @Override
    public String toString() {
        return "CircleViewBean{" +
                "avator='" + avator + '\'' +
                ", username='" + username + '\'' +
                ", createDate='" + createDate + '\'' +
                ", content='" + content + '\'' +
                ", likescount='" + likescount + '\'' +
                ", commentcount='" + commentcount + '\'' +
                ", images=" + images +
                ", viewcount='" + viewcount + '\'' +
                '}';
    }
}
