package com.cainiao.factory.presenter.dynamic;

import android.support.annotation.StringRes;

import com.cainiao.common.presenter.BaseContract;
import com.cainiao.factory.presenter.account.RegisterContract;

import java.util.ArrayList;

/**
 * Created by wuyinlei on 2017/7/28.
 *
 * @function 发布动态的契约
 */

public interface DynamicPublishContract {

    public interface View extends BaseContract.View<RegisterContract.Presenter> {

        //显示字符串错误
        void showError(@StringRes int str);

        /**
         * 发布失败
         *
         * @param code 错误码
         * @param msg  错误信息
         */
        void onFailure(int code, String msg);


        /**
         * 发布成功
         * @param postId
         * @param str  提示信息
         */
        void publishDynamicSuccess(String postId, @StringRes int str);


    }


    public interface Presenter extends BaseContract.Presenter {

        //发起注册
        void dynamicPublish(String content, ArrayList<String> images);

        boolean checkContent(String content);

    }
}
