package com.cainiao.factory.presenter.account;


import com.cainiao.common.presenter.BaseContract;
import com.cainiao.factory.model.MyUser;

/**
 * Created by wuyinlei on 2017/8/2.
 *
 * @function 用户契约类  用户修改用户信息
 */

public interface PersonalContract {

    public interface View extends BaseContract.View<PersonalContract.Presenter> {

        void onSaveFailure(int errorCode,String errMsg);

        void onSaveSuccess(String msg);

//        void onRequestDataSuccess(MyUser user);
//
//        void onRequestFailure(String msg);

    }


    public interface Presenter extends BaseContract.Presenter {

        void updateUserInfo(
                String avatar,
                            String desc,
                            String address,
                            boolean sex,
                            String alias,
                            String birthday,
                            Integer age);



    }

}
