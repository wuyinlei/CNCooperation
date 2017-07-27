package com.cainiao.cncooperation.ui.activity;

import android.content.Intent;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cainiao.cncooperation.R;
import com.cainiao.common.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class FriendCircleActivity extends BaseActivity {

    @BindView(R.id.action_mindcirrcle_message)
    RelativeLayout mActionBack;

    @BindView(R.id.action_mindcircle_view)
    ImageButton mActionImageBack;

    @BindView(R.id.action_bar_title)
    TextView mActionBarTitle;

    @BindView(R.id.action_mindcirrcle_publish)
    RelativeLayout mActionPublish;

    @BindView(R.id.action_create_btn)
    ImageButton mActionImagePublish;


    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_friend_circle;
    }

    @OnClick(R.id.action_mindcirrcle_publish)
    public void jumpToPublish(){
       DynamicPublishActivity.show(this);
    }


    @OnClick(R.id.action_mindcirrcle_message)
    public void jumpToMessage(){
        DynamicPublishActivity.show(this);
    }


}
