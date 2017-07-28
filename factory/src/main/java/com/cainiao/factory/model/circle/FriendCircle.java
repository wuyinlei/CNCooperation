package com.cainiao.factory.model.circle;

import com.cainiao.factory.model.MyUser;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by wuyinlei on 2017/7/28.
 *
 * @function 朋友圈bean
 */

public class FriendCircle extends BmobObject {

    //标题
//    private String title;

    private String content;
    // 帖子的发布者 是一对一 帖子属于某个用户
    private MyUser author;

    //朋友圈的图片列表
    private List<String> circleimages;

    // 多对多 喜欢该帖子的所有用户(用户也可以喜欢多个帖子)
    private BmobRelation likes;

    private String viewcount;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MyUser getAuthor() {
        return author;
    }

    public void setAuthor(MyUser author) {
        this.author = author;
    }

    public List<String> getCircleimages() {
        return circleimages;
    }

    public void setCircleimages(List<String> circleimages) {
        this.circleimages = circleimages;
    }

    public BmobRelation getLikes() {
        return likes;
    }

    public void setLikes(BmobRelation likes) {
        this.likes = likes;
    }

    public String getViewcount() {
        return viewcount;
    }

    public void setViewcount(String viewcount) {
        this.viewcount = viewcount;
    }
}
