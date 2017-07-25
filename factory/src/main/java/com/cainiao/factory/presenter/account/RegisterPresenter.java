package com.cainiao.factory.presenter.account;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.cainiao.common.presenter.BasePresenter;
import com.cainiao.common.utils.RegularUtils;
import com.cainiao.factory.R;
import com.cainiao.factory.model.MyUser;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.datatype.BmobSmsState;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;


/**
 * Created by wuyinlei on 2017/6/10.
 * <p>
 * 注册Presenter实现类
 */

public class RegisterPresenter extends BasePresenter<RegisterContract.View>
        implements RegisterContract.Presenter {

    private RegisterContract.View mView;
    private String mVerifyCode;

    public RegisterPresenter(RegisterContract.View view) {
        super(view);
        mView = view;
    }


    @Override
    public void register(final String phone, final String name, final String password, final String verifyCode) {
        if (!checkMobile(phone)) {
            mView.showPhoneError(R.string.register_phone_err);
            return;
        }

        if (TextUtils.isEmpty(name)) {
            mView.showNameError(((Context) mView).getResources().getString(R.string.login_name));

            return;
        }

        if (TextUtils.isEmpty(password) || password.length() < 6) {
            mView.showPasswordError(((Context) mView).getResources().getString(R.string.change_password_old_new_hint));
            return;
        }

        BmobSMS.verifySmsCode(phone, verifyCode, new UpdateListener() {

            @Override
            public void done(BmobException ex) {
                if (ex == null) {//短信验证码已验证成功
                    Log.i("smile", "验证通过");
                    toRegister(name, phone, password, verifyCode);
                } else {
                    Log.i("smile", "验证失败：code =" + ex.getErrorCode() + ",msg = " + ex.getLocalizedMessage());
                    mView.showError(R.string.register_code_error);
                }
            }
        });

    }

    private void toRegister(final String name, final String phone, final String password, String verifyCode) {

        MyUser bu = new MyUser();
        bu.setUsername(name);
        bu.setPassword(password);
        bu.setMobilePhoneNumber(phone);
//注意：不能用save方法进行注册
        bu.signUp(new SaveListener<MyUser>() {
            @Override
            public void done(MyUser user, BmobException e) {
                if (e == null) {
                    //注册成功
                    mView.registerSuccess(user);
                } else {
                    mView.showError(R.string.register_failure);
                }
            }
        });

        //下面的方式不对 因为已经对验证码进行了验证  所以在调用下面的注册会导致验证码失效
//
//        MyUser user = new MyUser();
//        user.setMobilePhoneNumber(phone);//设置手机号码（必填）
//        user.setUsername(name);                  //设置用户名，如果没有传用户名，则默认为手机号码
//        user.setPassword(password);                  //设置用户密码
//        user.setAge(18);                        //设置额外信息：此处为年龄
//        user.signOrLogin(verifyCode, new SaveListener<MyUser>() {
//
//            @Override
//            public void done(MyUser user, BmobException e) {
//                if (e == null) {
//                    //注册成功
//                    mView.registerSuccess(user);
//                } else {
//                    mView.showError(R.string.register_failure);
//                }
//            }
//        });


    }


    @Override
    public boolean checkMobile(String phone) {
        return RegularUtils.isMobileExact(phone);
    }


    @Override
    public void sendVerifyCode(String phone) {
        if (!checkMobile(phone)) {
            mView.showPhoneError(R.string.register_phone_err);
            return;
        }
        BmobSMS.requestSMSCode(phone, "模板名称", new QueryListener<Integer>() {

            @Override
            public void done(Integer smsId, BmobException ex) {
                if (ex == null) {//验证码发送成功

                    BmobSMS.querySmsState(smsId, new QueryListener<BmobSmsState>() {
                        @Override
                        public void done(BmobSmsState bmobSmsState, BmobException e) {
                            Log.i("smile", "短信状态：" + bmobSmsState.getSmsState() + ",验证状态：" + bmobSmsState.getVerifyState());
                            if (bmobSmsState.getSmsState().equals("SUCCESS")) {

                                mView.verifyCodeSuccess(R.string.register_msg_code);

                            } else if (bmobSmsState.getSmsState().equals("FAIL")) {
                                //发送失败

                            } else if (bmobSmsState.getSmsState().equals("SENDING")) {
                                //发送中

                            }
                        }
                    });
                    Log.i("smile", "短信id：" + smsId);//用于查询本次短信发送详情

                }
            }
        });
    }

}
