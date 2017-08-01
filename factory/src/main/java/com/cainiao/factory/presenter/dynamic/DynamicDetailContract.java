package com.cainiao.factory.presenter.dynamic;

import android.support.annotation.StringRes;

import com.cainiao.common.presenter.BaseContract;
import com.cainiao.factory.model.MyUser;
import com.cainiao.factory.model.circle.DetailComment;
import com.cainiao.factory.model.circle.FriendCircle;
import com.cainiao.factory.presenter.account.RegisterContract;

import java.util.List;

/**
 * Created by wuyinlei on 2017/7/29.
 *
 * @function 单个动态详情界面契约
 */

public interface DynamicDetailContract {


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
         */
        void onCommentSuccess(String content);


        void requestDataSuccess(FriendCircle friendCircle);

        void requestCommentDataSuccess(List<DetailComment> viewBeen);

        void loadMoreCommentDataSuccess(List<DetailComment> viewBeen);

    }


    public interface Presenter extends BaseContract.Presenter {

        /**
         * 发布评论
         *
         * @param postId      需要评论的朋友圈objectId
         * @param currentUser 当前用户
         * @param content     评论内容
         * @param isAlias     是否需要别名(匿名发送)
         */
        void publishComment(String postId, MyUser currentUser, String content, boolean isAlias);


        /**
         * 更新评论的个数
         *
         * @param objectId    当前动态的objectId
         * @param commentSize 当前的评论个数
         */
        void updateCommentSize(String objectId, String commentSize);

        /**
         * 请求数据
         *
         * @param postId objectId
         */
        void requestDetailData(String postId);


        void collectLikes(String postId);

        /**
         * 点赞成功之后的更新喜欢的个数
         *
         * @param objectId   当前的动态objectId
         * @param likesCount 喜欢的个数
         */
        void updateLikes(String objectId, String likesCount);


        boolean checkContent(String content);

        /**
         * 请求第一页数据
         *
         * @param limit 第一页数据的个数
         */
        void requestCommentData(int limit, int page, String objectId);

        /**
         * 请求下一页数据
         *
         * @param limit 每页的数据限制
         * @param skip  忽略前多少条
         */
        void loadMoreCommentData(String objectId, int limit, int skip);

        /**
         * 更新查看的个数
         *
         * @param viewCount 当前已经的查看个数
         * @param objectId  当前动态的objectId
         */
        void updateViewCount(String viewCount, String objectId);

    }


}
