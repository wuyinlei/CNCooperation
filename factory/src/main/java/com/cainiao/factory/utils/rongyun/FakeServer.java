package com.cainiao.factory.utils.rongyun;

import android.content.Context;
import android.util.Log;

import com.cainiao.common.constant.Common;
import com.cainiao.common.utils.SharedUtils;
import com.cainiao.factory.app.Account;

import org.json.JSONException;
import org.json.JSONObject;

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

    private static String token;

    /**
     * 获取融云Token, 通过调用融云ServerApi获得
     */
    public static void getToken(String userId, String userName, String userPortrait, HttpUtil.OnResponse callback) {
        try {
            String register_data = "userId=" + URLEncoder.encode(userId, "UTF-8")
                    + "&name=" + URLEncoder.encode(userName, "UTF-8")
                    + "&portraitUri=" + URLEncoder.encode(userPortrait, "UTF-8");
            HttpUtil httpUtil = new HttpUtil();
            httpUtil.setOnResponse(callback);
            httpUtil.post(APP_KEY, APP_SECRET, register_data, callback);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static void getRongYunToken(final Context context) {

        FakeServer.getToken(Account.getUser().getObjectId(), Account.getUserName(), "http://loveruolan.oss-cn-shanghai.aliyuncs.com/portrait/201707/6e8ad1e62302f9c446b78c00d51b9cff.jpg", new HttpUtil.OnResponse() {
            @Override
            public void onResponse(int code, String body) {
                if (code == 200) {
                    JSONObject jsonObj = null;
                    try {
                        jsonObj = new JSONObject(body);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    assert jsonObj != null;
                    token = jsonObj.optString("token");
                    SharedUtils.putString(context, Common.Constance.RONGYUNG_IM_TOKEN, token);


                    Log.i("HomeFragment", "获取的 token 值为:\n" + token + '\n');
                } else {
                    Log.i("HomeFragment", "获取 token 失败" + '\n');
                }
            }
        });
    }

}
