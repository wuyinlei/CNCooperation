package com.cainiao.factory.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.Model;

import java.util.Date;
import java.util.List;

/**
 * Created by wuyinlei on 2017/8/4.
 *
 * @function 群信息表
 */
@Table(database = AppDatabase.class)
public class Groups extends BaseDbModel<Model>{

    @PrimaryKey
    private String id;
    @Column
    private String name;
    @Column
    private String desc;
    @Column
    private String picture;
    @Column
    private int notifyLevel;
    @Column
    private Date joinAt;
    @Column
    private Date modifyAt;

    @ForeignKey(tableClass = Friend.class, stubbedRelationship = true)
    private Friend owner;

    List<GroupMembers> groupMembers;


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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getNotifyLevel() {
        return notifyLevel;
    }

    public void setNotifyLevel(int notifyLevel) {
        this.notifyLevel = notifyLevel;
    }

    public Date getJoinAt() {
        return joinAt;
    }

    public void setJoinAt(Date joinAt) {
        this.joinAt = joinAt;
    }

    public Date getModifyAt() {
        return modifyAt;
    }

    public void setModifyAt(Date modifyAt) {
        this.modifyAt = modifyAt;
    }

    public Friend getOwner() {
        return owner;
    }

    public void setOwner(Friend owner) {
        this.owner = owner;
    }

    public void setGroupMembers(List<GroupMembers> groupMembers) {
        this.groupMembers = groupMembers;
    }

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "groupMembers")
    public List<GroupMembers> getGroupMembers() {
        return groupMembers;
    }
}
