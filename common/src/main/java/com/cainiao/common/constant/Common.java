package com.cainiao.common.constant;

import java.net.PortUnreachableException;

/**
 * Created by wuyinlei on 2017/7/22.
 *
 * @function 常量类
 */
public class Common {


    /**
     * 一些不可变的永恒的参数  通常用于以下配置
     */
    public interface Constance {

        /**
         * 引导页配置
         */
        public String GUIDE_SHOW = "GUIDE_SHOW";

        /**
         * 用户名
         */
        public String USER_NAME = "USER_NAME";

        /**
         * 用户密码
         */
        public String USER_PASSWORD = "USER_PASSWORD";

        /**
         * 是否登录
         */
        public String IS_LOGIN = "IS_LOGIN";

        public String BASE_URL = "http://api.xinliji.me/com/";

        public int LIMIT_COUNT = 10;  //每次请求10个数据

        public int SKIP_COUNT = 10;  //忽略的前面请求的个数


        //发布动态的时候用到的
        public String OBECJT_ID = "OBECJT_ID";
        public String DYNAMIC_IMAGE = "DYNAMIC_IMAGE";
        public String DYNAMIC_CONTENT = "DYNAMIC_CONTENT";

        public int DYNAMIC_REQUEST_CODE = 0X100;
        public int DYNAMIC_RESULT_CODE = 0X101;

        public int USER_INFO_REQUEST_CODE = 0X102;
        public int USER_INFO_RESULT_CODE = 0X103;

        public String USER_UPDATE_INFO_TYPE = "USER_UPDATE_INFO_TYPE";
        public int USER_NICK_NAME_TYPE = 1;
        public int USER_ADDRESS_TYPE = 2;
        public int USER_DESC_TYPE = 3;
        public int USER_PHONE_TYPE = 4;

        public String USER_UPDATE_INFO_EXTRA = "USER_UPDATE_INFO_EXTRA";
        public String USER_UPDATE_INFO_RESULT = "USER_UPDATE_INFO_RESULT";


        //分享的点击的链接
        public String GITHUB_URL = "https://github.com/wuyinlei";

        //接收者id
        public String RECEIVER_ID = "RECEIVER_ID";


        public String CHAT_TYPE = "CHAT_TYPE";
        public final static int GROUP_TYPE = 0X105;  //群聊
        public final static int SINGLE_TYPE = 0X106;  //单聊

        public final static String RONGYUNG_IM_TOKEN = "RONGYUNG_IM_TOKEN";  //TOKEN

    }

    public static class Path {
        public static String getExt(String path, String defaultExt) {
            int extIndex = path.lastIndexOf(".");
            String ext = extIndex >= 0 ? path.substring(extIndex + 1).toLowerCase() : defaultExt;
            if ("0".equalsIgnoreCase(ext))
                ext = defaultExt;
            return ext;
        }
    }

}
