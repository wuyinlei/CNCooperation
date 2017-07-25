package com.cainiao.cncooperation;

import com.cainiao.common.base.BaseApplication;

import cn.bmob.v3.Bmob;

/**
 * Created by wuyinlei on 2017/7/22.
 *
 * @function 全局的application
 *
 */

public class CNApplication extends BaseApplication {


    @Override
    public void onCreate() {
        super.onCreate();


        //第一：默认初始化
        Bmob.initialize(this, "d6bc5f51d6b9294f22c8d4a1a863f910");
        //第一：默认初始化
//        Bmob.initialize(this, "d6bc5f51d6b9294f22c8d4a1a863f910");


    }
}
