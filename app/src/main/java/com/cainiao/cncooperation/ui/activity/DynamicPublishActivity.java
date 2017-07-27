package com.cainiao.cncooperation.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cainiao.cncooperation.R;
import com.cainiao.common.base.BaseActivity;
import com.kyleduo.switchbutton.SwitchButton;

import butterknife.BindView;

/**
 * Created by wuyinlei on 2017/7/27.
 *
 * @function 动态发布界面
 */

public class DynamicPublishActivity extends BaseActivity {

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

    @BindView(R.id.circle_content_edit)
    EditText mCircleContentEdit;

    @BindView(R.id.photo_viewer)
    RecyclerView mPhotoViewer;

    @BindView(R.id.circle_location_layout)
    RelativeLayout mCircleLocationLayout;

    @BindView(R.id.circle_anon_switch)
    SwitchButton mCircleAnonSwitch;

    public static void show(Context context){
        Intent intent = new Intent(context,DynamicPublishActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.mind_circle_publish_edit_view;
    }

    @Override
    protected void initView() {
        super.initView();
        mActionImageBack.setBackgroundResource(R.drawable.ic_close_drawable);
        mActionBarTitle.setText(getString(R.string.circle_content_publish));
        mActionImagePublish.setBackgroundResource(R.drawable.mind_circle_post_icon);

    }
}
