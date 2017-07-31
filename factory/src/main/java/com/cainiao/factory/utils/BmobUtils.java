package com.cainiao.factory.utils;

import com.cainiao.factory.model.circle.DetailComment;
import com.cainiao.factory.model.circle.FriendCircle;
import com.cainiao.factory.model.circle.FriendCircleComment;
import com.cainiao.factory.presenter.dynamic.DynamicDetailContract;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by wuyinlei on 2017/7/31.
 *
 * @function Bomb数据库的封装
 */

public class BmobUtils {

    /**
     * 查询档期动态的所有的评论
     *
     * @param limit  每页评论个数限制
     * @param page 当前评论页
     * @param objectId  当前动态的objectId
     * @param view  当前this
     */
    public static void queryAllComment(int limit, int page, String objectId, final DynamicDetailContract.View view) {

        BmobQuery<FriendCircleComment> query = new BmobQuery<>();
        ////用此方式可以构造一个BmobPointer对象。只需要设置objectId就行
        FriendCircle post = new FriendCircle();
        post.setObjectId(objectId);
        query.addWhereEqualTo("post", new BmobPointer(post));
        //希望同时查询该评论的发布者的信息，以及该帖子的作者的信息，这里用到上面`include`的并列对象查询和内嵌对象的查询
        query.include("author,post.author");
        query.setLimit(limit);  //设置查询的个数
        query.setSkip((page - 1) * limit);  //过滤多少
        query.findObjects(new FindListener<FriendCircleComment>() {

            @Override
            public void done(List<FriendCircleComment> comments, BmobException e) {
                if (comments != null && e == null) {

                    List<DetailComment> detailComments = new ArrayList<>();
                    for (FriendCircleComment comment : comments) {
                        DetailComment detailComment = new DetailComment();
                        detailComment.setAvatar(comment.getAuthor().getAvatar());
                        detailComment.setContent(comment.getContent());
                        detailComment.setCreateDate(comment.getCreatedAt());
                        detailComment.setUsername(comment.getAuthor().getUsername());
                        detailComments.add(detailComment);
                    }

                    view.requestCommentDataSuccess(detailComments);
                }
            }
        });

    }

}
