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

    private int love;
    private int hate;
    private int share;
    private int comment;
    private boolean isPass;
    private boolean myFav;//收藏
    private int viewcount;
    private int commentSize;

    private boolean myLove;//赞

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

    public int getLove() {
        return love;
    }

    public void setLove(int love) {
        this.love = love;
    }

    public int getHate() {
        return hate;
    }

    public void setHate(int hate) {
        this.hate = hate;
    }

    public void setViewcount(int viewcount) {
        this.viewcount = viewcount;
    }

    public int getViewcount() {
        return viewcount;
    }

    public int getShare() {
        return share;
    }

    public void setShare(int share) {
        this.share = share;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public boolean isPass() {
        return isPass;
    }

    public void setPass(boolean pass) {
        isPass = pass;
    }

    public boolean isMyFav() {
        return myFav;
    }

    public void setMyFav(boolean myFav) {
        this.myFav = myFav;
    }

    public boolean isMyLove() {
        return myLove;
    }

    public void setMyLove(boolean myLove) {
        this.myLove = myLove;
    }

    public int getCommentSize() {
        return commentSize;
    }

    public void setCommentSize(int commentSize) {
        this.commentSize = commentSize;
    }
}
