package com.cainiao.cncooperation.ui.account;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cainiao.cncooperation.R;
import com.cainiao.common.base.BaseActivity;
import com.cainiao.factory.model.MyUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * @author : mubai
 * @function :
 * @explain:
 */

public class ForgetPasswordActivity extends BaseActivity{


    @BindView(R.id.et_now)
    EditText mEtNow;
    @BindView(R.id.et_new)
    EditText mEtNew;
    @BindView(R.id.et_new_password)
    EditText mEtNewPassword;
    @BindView(R.id.btn_update_password)
    Button mBtnUpdatePassword;



    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_forget;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_update_password)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_update_password:
                //1.获取输入框的值
                String now = mEtNow.getText().toString().trim();
                String news = mEtNew.getText().toString().trim();
                String new_password = mEtNewPassword.getText().toString();
                //2.判断是否为空
                if(!TextUtils.isEmpty(now) & !TextUtils.isEmpty(news) & !TextUtils.isEmpty(new_password)){
                    //3.判断两次新密码是否一致
                    if(news.equals(new_password)){
                        //4.重置密码
                        MyUser.updateCurrentUserPassword(now, news, new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e == null){
                                    Toast.makeText(ForgetPasswordActivity.this,
                                            R.string.reset_successfully, Toast.LENGTH_SHORT).show();
                                    finish();
                                }else{
                                    Toast.makeText(ForgetPasswordActivity.this, R.string.reset_failed, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else {
                        Toast.makeText(this, getString(R.string.text_two_input_not_consistent), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(this, getString(R.string.text_tost_empty), Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
}
