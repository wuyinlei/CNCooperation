package com.cainiao.factory.presenter.dynamic;

import android.text.TextUtils;

import com.cainiao.common.presenter.BasePresenter;
import com.cainiao.factory.model.MyUser;
import com.cainiao.factory.model.circle.DetailComment;
import com.cainiao.factory.model.circle.FriendCircle;
import com.cainiao.factory.model.circle.FriendCircleComment;
import com.cainiao.factory.utils.BmobUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by wuyinlei on 2017/7/29.
 *
 * @function 动态详情p层
 */

public class DynamicDetailPresenter extends BasePresenter<DynamicDetailContract.View> implements DynamicDetailContract.Presenter {


    public DynamicDetailPresenter(DynamicDetailContract.View view) {
        super(view);
    }


    @Override
    public void publishComment(String postId, MyUser currentUser, String content) {

    }

    @Override
    public void updateCommentSize(String objectId, String commentSize) {

    }

    @Override
    public void requestDetailData(String postId) {

        BmobUtils.requestDetailData(postId,getView());

//        BmobQuery<FriendCircle> query = new BmobQuery<>();
//        query.include("author,post.author");
//        query.getObject(postId, new QueryListener<FriendCircle>() {
//
//            @Override
//            public void done(FriendCircle friendCircle, BmobException e) {
//                if (e == null && friendCircle != null) {
//                    getView().requestDataSuccess(friendCircle);
//                } else {
//                    assert e != null;
//                    getView().onCommentFailure(e.getErrorCode(), e.getMessage());
//                }
//            }
//
//        });
    }

    @Override
    public void collectLikes(String postId) {

    }

    @Override
    public void updateLikes(String objectId, String likesCount) {

    }

    @Override
    public boolean checkContent(String content) {

        return TextUtils.isEmpty(content);
    }

    @Override
    public void requestCommentData(int limit, int page,final String objectId) {
//        BmobQuery<FriendCircleComment> query = new BmobQuery<>();
//        ////用此方式可以构造一个BmobPointer对象。只需要设置objectId就行
//        FriendCircle post = new FriendCircle();
//        post.setObjectId(objectId);
//        query.addWhereEqualTo("post", new BmobPointer(post));
//        //希望同时查询该评论的发布者的信息，以及该帖子的作者的信息，这里用到上面`include`的并列对象查询和内嵌对象的查询
//        query.include("author,post.author");
//        query.findObjects(new FindListener<FriendCircleComment>() {
//
//            @Override
//            public void done(List<FriendCircleComment> comments, BmobException e) {
//                if (comments != null && e == null) {
//                    List<DetailComment> detailComments = new ArrayList<>();
//                    for (FriendCircleComment comment : comments) {
//                        DetailComment detailComment = new DetailComment();
//                        detailComment.setAvatar(comment.getAuthor().getAvatar());
//                        detailComment.setContent(comment.getContent());
//                        detailComment.setCreateDate(comment.getCreatedAt());
//                        detailComment.setUsername(comment.getAuthor().getUsername());
//                        detailComments.add(detailComment);
//                    }
//
//                    getView().requestCommentDataSuccess(detailComments);
//
//
//                }
//            }
//        });

        BmobUtils.queryAllComment(limit,page,objectId,getView());

    }

    @Override
    public void loadMoreCommentData(String objectId, int limit, int skip) {

    }

    @Override
    public void updateViewCount(String viewCount, String objectId) {


        BmobUtils.updateViewCount(viewCount,objectId);

//        FriendCircle friendCircle = new FriendCircle();
//        int random = new Random().nextInt(30);
//        if (viewCount == null) {
//            friendCircle.setViewcount(String.valueOf(random));
//        } else {
//            int count = Integer.parseInt(viewCount) + random;
//            friendCircle.setViewcount(String.valueOf(count));
//        }
//        friendCircle.update(objectId, new UpdateListener() {
//
//            @Override
//            public void done(BmobException e) {
//
//            }
//        });


    }
}
