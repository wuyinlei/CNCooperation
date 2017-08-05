package com.cainiao.factory.model.im;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by wuyinlei on 2017/8/5.
 */

public class GroupModel extends BmobObject {

    private String name;  //群组名
    private String desc;  //群描述
    private String picture;  //群头像
    private List<String> users = new ArrayList<>();




}
