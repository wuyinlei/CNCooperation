package com.cainiao.cncooperation.ui.setting;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import com.cainiao.cncooperation.R;
import com.cainiao.common.base.BaseActivity;

public class SettingActivity extends BaseActivity {


    @Override
    protected BaseActivity injectTarget() {
        return this;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_setting;
    }

    /**
     * 进入到当前的activity
     *
     * @param context 上下文
     */
    public static void show(Context context) {

        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);

    }
}
