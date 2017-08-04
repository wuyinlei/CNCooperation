package com.cainiao.cncooperation.ui.account;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cainiao.cncooperation.R;
import com.cainiao.common.base.BaseActivity;
import com.cainiao.common.constant.Common;
import com.cainiao.factory.utils.BmobUtils;

import java.lang.reflect.Type;

import butterknife.BindView;
import butterknife.OnClick;

public class EditorInfoActivity extends BaseActivity {


    @BindView(R.id.action_mindcircle_view)
    ImageView mIcBack;

    @BindView(R.id.action_bar_title)
    TextView mTvTile;

    @BindView(R.id.action_create_btn)
    ImageView mIvSave;

    @BindView(R.id.edit_change)
    EditText mEditChange;

    @Override
    protected BaseActivity injectTarget() {
        return this;
    }

    private int EDIT_TYPE;
    private String defaultStr;

    /**
     * 显示该avtivity
     *
     * @param context 上下文
     * @param type    编辑类型
     */
    public static void show(Context context, int type, String defaultStr) {
        Intent intent = new Intent(context, EditorInfoActivity.class);
        intent.putExtra(Common.Constance.USER_UPDATE_INFO_TYPE, type);
        intent.putExtra(Common.Constance.USER_UPDATE_INFO_EXTRA, defaultStr);
        ((Activity) context).startActivityForResult(intent, Common.Constance.USER_INFO_REQUEST_CODE);
    }

    @Override
    protected boolean initArgs(Bundle bundle) {
        if (bundle != null) {
            EDIT_TYPE = bundle.getInt(Common.Constance.USER_UPDATE_INFO_TYPE, 0);
            defaultStr = bundle.getString(Common.Constance.USER_UPDATE_INFO_EXTRA, "default_value");
        }
        return true;
    }

    @Override
    protected void initView() {
        super.initView();
//        mTvTile.setText(getString(R.string.userinfo_update));
        mIvSave.setImageResource(R.drawable.ic_autorenew_black_24dp);


        editInfoInitialize();

    }

    private void editInfoInitialize() {

        mEditChange.setText(defaultStr);
        mEditChange.setSelection(mEditChange.getText().toString().length());

        if (EDIT_TYPE == Common.Constance.USER_NICK_NAME_TYPE) {
            //用户昵称编辑
            mTvTile.setText(getString(R.string.alias_username));
            mEditChange.setHint(getString(R.string.alias_username));


        } else if (EDIT_TYPE == Common.Constance.USER_ADDRESS_TYPE) {
            //用户地址编辑
            mTvTile.setText(getString(R.string.personal_address));
            mEditChange.setHint(getString(R.string.personal_address));

        } else if (EDIT_TYPE == Common.Constance.USER_DESC_TYPE) {
            //用户描述编辑
            mTvTile.setText(getString(R.string.personal_desc));
            mEditChange.setHint(getString(R.string.personal_desc));

        } else if (EDIT_TYPE == Common.Constance.USER_PHONE_TYPE) {
            //绑定手机号

            //布局需要改变


        }


    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_editor_info;
    }

    @OnClick(R.id.action_mindcirrcle_publish)
    public void save() {

    }

    @OnClick(R.id.action_mindcirrcle_message)
    public void close() {
        finish();
    }

}
