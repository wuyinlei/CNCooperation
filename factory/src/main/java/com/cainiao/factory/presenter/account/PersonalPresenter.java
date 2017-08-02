package com.cainiao.factory.presenter.account;

import android.content.Context;

import com.cainiao.common.presenter.BasePresenter;
import com.cainiao.factory.R;
import com.cainiao.factory.utils.BmobUtils;

/**
 * Created by wuyinlei on 2017/8/2.
 *
 * @function 用户信息更改的Presenter
 */

public class PersonalPresenter extends BasePresenter<PersonalContract.View> implements PersonalContract.Presenter {

    private Context mContext;

    public PersonalPresenter(PersonalContract.View view) {
        super(view);
        mContext = (Context) view;
    }

    @Override
    public void updateUserInfo(String avatar, String desc, String address, boolean sex, String alias, String birthday, Integer age) {
        BmobUtils.updateUserInfo(mContext, avatar, desc, address, sex, alias, birthday, age, new BmobUtils.OnListener<String>() {
            @Override
            public void onError(int errorCode, String message) {
                getView().onSaveFailure(errorCode, message);
            }

            @Override
            public void onSuccess(String str) {
                getView().onSaveSuccess(mContext.getResources().getString(R.string.update_userinfo_success));
            }
        });
    }
}
