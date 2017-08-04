package com.cainiao.factory.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.Model;

import java.util.Date;

/**
 * Created by wuyinlei on 2017/8/4.
 *
 * @function 会话表  包含群聊和单聊
 */
@Table(database = AppDatabase.class)
public class Session extends BaseDbModel<Model>{


    @PrimaryKey
    private String id;  //Id   Message中的接收者User的id或者群id
    @Column
    private String picture;  //图片  接收者用户的头像或者群的图片
    @Column
    private String title; //标题  用户的名称  或者群的名称
    @Column
    private String content;  //显示在界面上的简单内容  是Message的一个描述
    @Column
    private int receiverType = Message.RECEIVER_TYPE_NONE;  //类型  对应人  或者群
    @Column
    private int unReadCount;  //未读消息数量  当灭有在当前界面的时候 应当增加未读数量
    @Column
    private Date modifyAt;  //最后更改时间
    @ForeignKey(tableClass = Message.class)
    private Message message;   //对应的消息 外键为Message的Id

    public Session(Message message) {
        if (message.getGroup() == null) {
            receiverType = Message.RECEIVER_TYPE_NONE;
            Friend other = message.getOther();
            id = other.getId();
            picture = other.getPortrait();
            title = other.getName();
        } else {
            receiverType = Message.RECEIVER_TYPE_GROUP;
            id = message.getGroup().getId();
            picture = message.getGroup().getPicture();
            title = message.getGroup().getName();
        }
        this.message = message;
        this.content = message.getSampleContent();
        this.modifyAt = message.getCreateAt();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getReceiverType() {
        return receiverType;
    }

    public void setReceiverType(int receiverType) {
        this.receiverType = receiverType;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public int getUnReadCount() {
        return unReadCount;
    }

    public void setUnReadCount(int unReadCount) {
        this.unReadCount = unReadCount;
    }

    public Date getModifyAt() {
        return modifyAt;
    }

    public void setModifyAt(Date modifyAt) {
        this.modifyAt = modifyAt;
    }

}
