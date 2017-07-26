package com.cainiao.factory.presenter.account;

import android.support.annotation.StringRes;

import com.cainiao.common.presenter.BaseContract;
import com.cainiao.factory.model.MyUser;


/**
 * Created by wuyinlei on 2017/6/10.
 *
 * @function 注册
 */

public interface RegisterContract {

    public interface View extends BaseContract.View<Presenter> {
        //注册成功
        void registerSuccess(MyUser user);

        //显示字符串错误
        void showError(@StringRes int str);

        /**
         * 注册失败
         *
         * @param code 错误码
         * @param msg  错误信息
         */
        void onFailure(int code, String msg);

        void showPasswordError(@StringRes int str);

        void showPhoneError(@StringRes int str);

        void showNameError(@StringRes int str);


        void verifyCodeError(@StringRes int str);

        void verifyCodeFailed(@StringRes int str);

        void verifyCodeSuccess(@StringRes int str);


    }


    public interface Presenter extends BaseContract.Presenter {

        //发起注册
        void register(String phone, String name, String password,String code);

        //检查手机号是否正确
        boolean checkMobile(String phone);

        //发送验证码
        void sendVerifyCode(String phone);


    }
}
