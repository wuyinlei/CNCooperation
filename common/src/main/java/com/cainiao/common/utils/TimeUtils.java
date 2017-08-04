package com.cainiao.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wuyinlei on 2017/8/1.
 *
 * @function 时间工具类
 */

public class TimeUtils {

    /**
     * 返回当前的时间  按照2017-08-01 00：00：00
     *
     * @return 当前时间
     */
    public static String currentTimeFormat() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(new Date());
    }

}
