package com.cainiao.factory.db;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by wuyinlei on 2017/8/4.
 * 数据库的基本信息
 */
@Database(name = AppDatabase.NAME, version = AppDatabase.VERSION)
public class AppDatabase {

    public static final String NAME = "CN_RONGYUN_DB";

    public static final int VERSION = 1;

}