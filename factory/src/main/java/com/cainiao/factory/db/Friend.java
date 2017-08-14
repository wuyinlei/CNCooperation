package com.cainiao.factory.db;

import com.cainiao.factory.model.MyUser;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.Model;

import java.util.Date;
import java.util.List;

/**
 * Created by wuyinlei on 2017/8/4.
 *
 * @function 好友成员信息
 */
@Table(database = AppDatabase.class)
public class Friend extends BaseDbModel<Model> {

    public static final int SEX_MAN = 1;
    public static final int SEX_WOMAN = 2;

    @PrimaryKey
    private String id;
    @Column
    private String name;
    @Column
    private String phone;
    @Column
    private String portrait;
    @Column
    private String alias;
    //时间字段
    @Column
    private Date modifyAt;

    List<GroupMembers> groupMembers;

    public Friend(String originId, String alias, MyUser targetUser) {

        this.id  = originId;
        this.alias = alias;
        this.name = targetUser.getUsername();
        this.phone = targetUser.getMobilePhoneNumber();
        this.portrait = targetUser.getAvatar();
        this.modifyAt = new Date();

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Date getModifyAt() {
        return modifyAt;
    }

    public void setModifyAt(Date modifyAt) {
        this.modifyAt = modifyAt;
    }

    public List<GroupMembers> getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(List<GroupMembers> groupMembers) {
        this.groupMembers = groupMembers;
    }
}
