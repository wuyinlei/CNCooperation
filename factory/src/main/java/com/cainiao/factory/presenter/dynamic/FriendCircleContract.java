package com.cainiao.factory.presenter.dynamic;

import android.support.annotation.StringRes;

import com.cainiao.common.presenter.BaseContract;
import com.cainiao.factory.model.MyUser;
import com.cainiao.factory.model.circle.CircleViewBean;
import com.cainiao.factory.presenter.account.RegisterContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuyinlei on 2017/7/29.
 *
 * @function 朋友圈企契约
 */

public interface FriendCircleContract {

    public interface View extends BaseContract.View<RegisterContract.Presenter> {

        //显示字符串错误
        void showError(@StringRes int str);

        /**
         * 点赞喜欢失败
         *
         * @param code 错误码
         * @param msg  错误信息
         */
        void onLikesFailure(int code, String msg);

        /**
         * 点赞喜欢成功
         *
         * @param str 提示信息
         */
        void onLikesSuccess(@StringRes int str);

        /**
         * 评论失败
         *
         * @param code 错误码
         * @param msg  错误信息
         */
        void onCommentFailure(int code, String msg);

        /**
         * 评论成功
         *
         * @param str 提示信息
         */
        void onCommentSuccess(@StringRes int str, String content);


        void requestDataSuccess(List<CircleViewBean> viewBeen);

        void loadMoreDataSuccess(List<CircleViewBean> viewBeen);

    }


    public interface Presenter extends BaseContract.Presenter {

        /**
         * 发布评论
         *
         * @param postId      需要评论的朋友圈objectId
         * @param currentUser 当前用户
         * @param content     评论内容
         */
        void publishComment(String postId, MyUser currentUser, String content);

        /**
         * 点赞
         *
         * @param postId      当前朋友圈的objectId
         * @param currentUser 当前用户
         */
        void likesCircle(String postId, MyUser currentUser);


        boolean checkContent(String content);

        /**
         * 请求第一页数据
         *
         * @param limit 第一页数据的个数
         */
        void requestData(int limit);

        /**
         * 请求下一页数据
         *
         * @param limit 每页的数据限制
         * @param skip  忽略前多少条
         */
        void loadMoreData(int limit, int skip);



    }

}
