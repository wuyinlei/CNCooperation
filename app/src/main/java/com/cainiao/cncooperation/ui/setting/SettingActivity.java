package com.cainiao.cncooperation.ui.setting;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import com.cainiao.cncooperation.R;
import com.cainiao.cncooperation.ui.account.AccountActivity;
import com.cainiao.common.base.BaseActivity;
import com.cainiao.factory.model.MyUser;

import butterknife.OnClick;

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

    @OnClick(R.id.loginout)
    public void loginout(){
        MyUser.logOut();
        AccountActivity.show(this);
    }
}
