//package com.cainiao.factory.db;
//
//import com.cainiao.factory.Account;
//import com.raizlabs.android.dbflow.annotation.Column;
//import com.raizlabs.android.dbflow.annotation.ForeignKey;
//import com.raizlabs.android.dbflow.annotation.PrimaryKey;
//import com.raizlabs.android.dbflow.annotation.Table;
//import com.raizlabs.android.dbflow.structure.Model;
//
//import java.util.Date;
//
///**
// * Created by wuyinlei on 2017/8/4.
// * @function 消息表
// */
//@Table(database = AppDatabase.class)
//public class Message extends BaseDbModel<Model>{
//
//    public static final int RECEIVER_TYPE_NONE = 1;
//    public static final int RECEIVER_TYPE_GROUP = 2;
//
//    public static final int TYPE_STR = 1;
//    public static final int TYPE_PIC = 2;
//    public static final int TYPE_FILE = 3;
//    public static final int TYPE_AUDIO = 4;
//
//    public static final int STATUS_DONE = 0;
//    public static final int STATUS_CREATED = 1;
//    public static final int STATUS_FAILED = 2;
//
//    @PrimaryKey
//    private String id;
//    @Column
//    private String content;
//    @Column
//    private String attach;
//    @Column
//    private int type;
//    @Column
//    private Date createAt;
//    @Column
//    private int status;
//
//    @ForeignKey(tableClass = Groups.class, stubbedRelationship = true)
//    private Groups group;
//
//    @ForeignKey(tableClass = Friend.class, stubbedRelationship = true)
//    private Friend sender;
//
//    @ForeignKey(tableClass = Friend.class, stubbedRelationship = true)
//    private Friend receiver;
//
//    /**
//     * 构建一个简单的消息描述  用于简化消息显示
//     * @return 一个消息描述
//     */
//    String getSampleContent() {
//        if (type == TYPE_PIC)
//            return "[图片]";
//        else if (type == TYPE_AUDIO)
//            return "🎵";
//        else if (type == TYPE_FILE)
//            return "📃";
//        return content;
//    }
//
//    /**
//     * 当消息类型为普通消息(发送给人的消息)
//     * 该方法用于返回 和我聊天的人是谁
//     *
//     * <p>
//     *     和我聊天  要么对方是发送者 要么对方是接收者
//     * </p>
//     * @return  和我聊天的人
//     */
//    Friend getOther() {
//        if (Account.getUser().getObjectId().equals(sender.getId())) {
//            return receiver;
//        } else {
//            return sender;
//        }
//    }
//
//
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getContent() {
//        return content;
//    }
//
//    public void setContent(String content) {
//        this.content = content;
//    }
//
//    public String getAttach() {
//        return attach;
//    }
//
//    public void setAttach(String attach) {
//        this.attach = attach;
//    }
//
//    public int getType() {
//        return type;
//    }
//
//    public void setType(int type) {
//        this.type = type;
//    }
//
//    public Date getCreateAt() {
//        return createAt;
//    }
//
//    public void setCreateAt(Date createAt) {
//        this.createAt = createAt;
//    }
//
//    public int getStatus() {
//        return status;
//    }
//
//    public void setStatus(int status) {
//        this.status = status;
//    }
//
//    public Groups getGroup() {
//        return group;
//    }
//
//    public void setGroup(Groups group) {
//        this.group = group;
//    }
//
//    public Friend getSender() {
//        return sender;
//    }
//
//    public void setSender(Friend sender) {
//        this.sender = sender;
//    }
//
//    public Friend getReceiver() {
//        return receiver;
//    }
//
//    public void setReceiver(Friend receiver) {
//        this.receiver = receiver;
//    }
//}
