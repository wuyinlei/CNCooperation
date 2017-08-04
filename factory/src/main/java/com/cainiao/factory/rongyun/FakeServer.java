package com.cainiao.factory.rongyun;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by wuyinlei on 2017/8/4.
 *
 * @function 融云获取token的
 */

public class FakeServer {

    /**
     * 由"开发者平台"提供的 App Key 和 App Secret，
     */
    private static final String APP_KEY = "sfci50a7s1b2i";
    private static final String APP_SECRET = "aOarfZBaaQV5FX";

    /**
     * 获取融云Token, 通过调用融云ServerApi获得
     */
    public static void getToken(String userId, String userName, String userPortrait, HttpUtil.OnResponse callback) {
        try {
            String register_data = "userId=" + URLEncoder.encode(userId,"UTF-8")
                    + "&name=" + URLEncoder.encode(userName,"UTF-8")
                    + "&portraitUri=" + URLEncoder.encode(userPortrait,"UTF-8");
            HttpUtil httpUtil = new HttpUtil();
            httpUtil.setOnResponse(callback);
            httpUtil.post(APP_KEY, APP_SECRET, register_data, callback);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
