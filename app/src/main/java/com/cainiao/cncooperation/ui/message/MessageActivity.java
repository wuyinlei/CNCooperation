package com.cainiao.cncooperation.ui.message;

import android.content.Context;
import android.content.Intent;

import com.cainiao.cncooperation.R;
import com.cainiao.common.base.BaseActivity;

public class MessageActivity extends BaseActivity {


    public static void show(Context context){
        Intent intent = new Intent(context,MessageActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_message;
    }
}
