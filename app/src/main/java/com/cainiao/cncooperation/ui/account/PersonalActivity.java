package com.cainiao.cncooperation.ui.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.cainiao.cncooperation.R;
import com.cainiao.cncooperation.ui.dynamic.FriendCircleDetailActivity;
import com.cainiao.common.base.BaseActivity;
import com.cainiao.common.constant.Common;

public class PersonalActivity extends BaseActivity {

    private String username;

    /**
     * 进入到别人的个人页面
     * @param context  上下文
     * @param username  用户名
     */
    public static void show(Context context, String username) {
        Intent intent = new Intent(context, PersonalActivity.class);
        intent.putExtra(Common.Constance.USER_NAME, username);
        context.startActivity(intent);
    }

    /**
     * 进入到当前的user的个人页面
     * @param context 上下文
     */
//    public static void show(Context context) {
//        Intent intent = new Intent(context, FriendCircleDetailActivity.class);
//        context.startActivity(intent);
//    }

    @Override
    protected boolean initArgs(Bundle bundle) {
        username = bundle.getString(Common.Constance.USER_NAME);
        return true;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_personal;
    }
}
