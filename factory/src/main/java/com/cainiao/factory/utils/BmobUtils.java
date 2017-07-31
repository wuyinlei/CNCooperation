package com.cainiao.factory.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.cainiao.factory.Account;
import com.cainiao.factory.R;
import com.cainiao.factory.model.MyUser;
import com.cainiao.factory.model.circle.CircleViewBean;
import com.cainiao.factory.model.circle.DetailComment;
import com.cainiao.factory.model.circle.FriendCircle;
import com.cainiao.factory.model.circle.FriendCircleComment;
import com.cainiao.factory.presenter.dynamic.DynamicDetailContract;
import com.cainiao.factory.presenter.dynamic.DynamicPublishContract;
import com.cainiao.factory.presenter.dynamic.FriendCircleContract;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static java.security.AccessController.getContext;

/**
 * Created by wuyinlei on 2017/7/31.
 *
 * @function Bomb数据库的封装
 */

public class BmobUtils {

    /**
     * 查询当前动态的所有的评论
     *
     * @param limit    每页评论个数限制
     * @param page     当前评论页
     * @param objectId 当前动态的objectId
     * @param view     当前this
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


    /**
     * 登录逻辑
     *
     * @param username 用户名
     * @param password 密码
     */
    public static void login(String username, String password) {
        BmobUser bu2 = new BmobUser();
        bu2.setUsername(username);
        bu2.setPassword(password);
        bu2.login(new SaveListener<BmobUser>() {

            @Override
            public void done(BmobUser bmobUser, BmobException e) {
                if (e == null) {

                } else {
//                        loge(e);
                }
            }
        });
    }


    /**
     * 更新当前动态的查看数
     *
     * @param viewCount 当前查看的个数
     * @param objectId  档期动态的objectId
     */
    public static void updateViewCount(String viewCount, String objectId) {

        FriendCircle friendCircle = new FriendCircle();
        int random = new Random().nextInt(30);
        if (viewCount == null) {
            friendCircle.setViewcount(String.valueOf(random));
        } else {
            int count = Integer.parseInt(viewCount) + random;
            friendCircle.setViewcount(String.valueOf(count));
        }
        friendCircle.update(objectId, new UpdateListener() {

            @Override
            public void done(BmobException e) {

            }
        });
    }

    /**
     * 请求数据  这个是请求的当个动态的数据 不包含评论
     *
     * @param postId 当前冬天的objectId
     * @param view   当前view
     */
    public static void requestDetailData(String postId, final DynamicDetailContract.View view) {
        BmobQuery<FriendCircle> query = new BmobQuery<>();
        query.include("author,post.author");
        query.getObject(postId, new QueryListener<FriendCircle>() {

            @Override
            public void done(FriendCircle friendCircle, BmobException e) {
                if (e == null && friendCircle != null) {
                    view.requestDataSuccess(friendCircle);
                } else {
                    assert e != null;
                    view.onCommentFailure(e.getErrorCode(), e.getMessage());
                }
            }

        });
    }

    /**
     * 发布动态
     *
     * @param content 发送的动态内容
     * @param images  图片数组
     * @param view    当前view也就是View层
     */
    public static void dynamicPublish(String content, ArrayList<String> images, final DynamicPublishContract.View view) {
        final FriendCircle friendCircle = new FriendCircle();
        if (checkContent(content)) {
            view.showError(R.string.mind_circle_content_not_null);
            return;
        }
        friendCircle.setContent(content);
        friendCircle.setAuthor(Account.getUser());
        friendCircle.setLove(0);
        friendCircle.setHate(0);
        friendCircle.setShare(0);
        friendCircle.setComment(0);
        friendCircle.setPass(true);
        friendCircle.setViewcount("1");
        friendCircle.setCommentSize("0");
        if (images != null && images.size() > 0)
            friendCircle.setCircleimages(images);
        friendCircle.save(new SaveListener<String>() {
            @Override
            public void done(String postId, BmobException e) {
                if (e == null) {
                    view.publishDynamicSuccess(postId, R.string.mind_circle_publish_success);

                } else {
                    view.onFailure(e.getErrorCode(), e.getMessage());
                }
            }
        });
    }

    /**
     * 检测发送的文字是否为空
     *
     * @param content 发送的内容
     * @return false  true
     */
    private static boolean checkContent(String content) {

        return TextUtils.isEmpty(content);
    }


    /**
     * 请求朋友圈的数据
     *
     * @param limit 每页的数量
     * @param skip  分页加载的时候忽略的前多少个
     * @param view  当前的view
     */
    public static void requestFriendCircle(final int limit, final int skip, final FriendCircleContract.View view) {

        Observable.create(new Observable.OnSubscribe<List<FriendCircle>>() {

            @Override
            public void call(final Subscriber<? super List<FriendCircle>> subscriber) {
                BmobQuery<FriendCircle> query = new BmobQuery<>();
                query.order("-createdAt");
//		query.setCachePolicy(CachePolicy.NETWORK_ONLY);


                query.setLimit(limit);  //限制最多10条数据结果作为一页
                BmobDate date = new BmobDate(new Date(System.currentTimeMillis()));
                query.addWhereLessThan("createdAt", date);
//        LogUtils.i(TAG,"SIZE:"+Constant.NUMBERS_PER_PAGE*pageNum);
                query.setSkip(skip);  //忽略前10条数据（即第一页数据结果）
//        LogUtils.i(TAG,"SIZE:"+Constant.NUMBERS_PER_PAGE*pageNum);
                query.include("author,post.author");  // query.include("author,post.author");
                query.findObjects(new FindListener<FriendCircle>() {
                    @Override
                    public void done(List<FriendCircle> list, BmobException e) {
                        if (e == null) {
                            subscriber.onNext(list);
                            subscriber.onCompleted();
                        } else {
//                            subscriber.onError(e);
                        }
                    }
                });
            }
        }).flatMap(new Func1<List<FriendCircle>, Observable<List<CircleViewBean>>>() {

            @Override
            public Observable<List<CircleViewBean>> call(List<FriendCircle> friendCircles) {
                final List<CircleViewBean> circleViewBeanList = new ArrayList<>();

                for (int i = 0; i < friendCircles.size(); i++) {

                    FriendCircle friendCircle = friendCircles.get(i);

                    final CircleViewBean viewBean = new CircleViewBean();
                    viewBean.setAvator(friendCircle.getAuthor().getAvatar() == null ?
                            "http://loveruolan.oss-cn-shanghai.aliyuncs.com/portrait/201707/6e8ad1e62302f9c446b78c00d51b9cff.jpg"
                            : friendCircle.getAuthor().getAvatar());
                    viewBean.setContent(friendCircle.getContent());
                    viewBean.setCreateDate(friendCircle.getCreatedAt());
                    viewBean.setImages(friendCircle.getCircleimages());
                    viewBean.setViewcount(friendCircle.getViewcount() + "");
                    viewBean.setLikescount(friendCircle.getLove() + "");
//                    viewBean.setLikescount(friendCircle.getLove() + "");
                    viewBean.setCommentcount(friendCircle.getCommentSize() + "");
                    viewBean.setObjectId(friendCircle.getObjectId());
                    viewBean.setUsername(friendCircle.getAuthor().getUsername());
//                    viewBean.setCommentcount();
//                    BmobQuery<FriendCircleComment> query = new BmobQuery<>();
//                    ////用此方式可以构造一个BmobPointer对象。只需要设置objectId就行
//                    FriendCircle post = new FriendCircle();
//                    post.setObjectId(friendCircles.get(i).getObjectId());
//                    query.addWhereEqualTo("post", new BmobPointer(post));
//                    //希望同时查询该评论的发布者的信息，以及该帖子的作者的信息，这里用到上面`include`的并列对象查询和内嵌对象的查询
//                    query.include("author,post.author");
//                    query.findObjects(new FindListener<FriendCircleComment>() {
//
//                        @Override
//                        public void done(List<FriendCircleComment> objects, BmobException e) {
//                            if (objects != null && e == null) {
//                                List<CircleViewBean.CircleComment> comments = new ArrayList<>();
//                                for (int i = 0; i < objects.size(); i++) {
//                                    viewBean.setCommentcount(objects.size() + "");
//                                    CircleViewBean.CircleComment commentBean = new CircleViewBean.CircleComment();
//                                    commentBean.setUser(objects.get(i).getAuthor());
//                                    commentBean.setComment(objects.get(i).getContent());
//                                    comments.add(commentBean);
//                                }
//
//                                // viewBean.setComment(comments);
//
//
//                            }
//                        }
//                    });

                    circleViewBeanList.add(viewBean);
                }
                //// TODO: 2017/7/29 暂时还没想到怎么解决为好
                return Observable.just(circleViewBeanList).delay(500, TimeUnit.MICROSECONDS);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<CircleViewBean>>() {
                    @Override
                    public void call(List<CircleViewBean> viewBeen) {
                        if (viewBeen != null && viewBeen.size() > 0) {
                            if (skip <= 0) {
                                view.requestDataSuccess(viewBeen);
                            } else {
                                view.loadMoreDataSuccess(viewBeen);
                            }
                        } else {
                            view.showError(R.string.request_error);
                        }
                    }
                });
    }

    /**
     * 添加点赞
     *
     * @param context  上下文
     * @param objectId 当前动态的id
     */
    public static void addLikes(final Context context, String objectId) {
        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        FriendCircle postss = new FriendCircle();
        postss.setObjectId(objectId);
//将当前用户添加到Post表中的likes字段值中，表明当前用户喜欢该帖子
        BmobRelation relation = new BmobRelation();
//将当前用户添加到多对多关联中
        relation.add(user);
//多对多关联指向`post`的`likes`字段
        postss.setLikes(relation);
        postss.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Toast.makeText(context, "点赞成功", Toast.LENGTH_SHORT).show();

//                    Log.i("bmob","多对多关联添加成功");
                } else {
                    Log.i("bmob", "失败：" + e.getMessage());
                }
            }

        });
    }

    /**
     * 查询一个用户发布的多有的动态
     *
     * @param limit    每页需要查询的数量
     * @param page     当前页数
     * @param listener 监听器
     */
    public static void queryAllUserDynamic(int limit, int page, final OnListener<FriendCircle> listener) {
        //查询一个用户发表的所以帖子
        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        BmobQuery<FriendCircle> query = new BmobQuery<>();
        query.addWhereEqualTo("author", user);    // 查询当前用户的所有帖子
        query.order("-updatedAt");
        query.include("author");// 希望在查询帖子信息的同时也把发布人的信息查询出来
        query.setLimit(limit);
        query.setSkip((page - 1) * limit);
        query.findObjects(new FindListener<FriendCircle>() {

            @Override
            public void done(List<FriendCircle> object, BmobException e) {
                if (e == null) {
                    listener.onSuccess(object);
                } else {
                    listener.onError(e.getErrorCode(), e.getMessage());
                }
            }

        });
    }


    /**
     * 查询所有的收藏该动态的人
     *
     * @param objectId 动态id
     * @param limit    每页需要查询的数量
     * @param page     当前页数
     * @param listener 查询的监听
     */
    public static void queryAllCollectUser(String objectId, final int limit, int page, final OnListener<MyUser> listener) {
        //查询搜藏该帖子的所有人
        BmobQuery<MyUser> query = new BmobQuery<>();
        FriendCircle posts = new FriendCircle();
        posts.setObjectId(objectId);
        // TODO 不友好
        query.addWhereRelatedTo("likes", new BmobPointer(posts));
        query.setLimit(limit);
        query.setSkip((page - 1) * limit);
        query.findObjects(new FindListener<MyUser>() {
            @Override
            public void done(List<MyUser> list, BmobException e) {
                if (e == null) {
                    listener.onSuccess(list);
                } else {
                    listener.onError(e.getErrorCode(), e.getMessage());
                }
            }
        });
    }

    /**
     * 添加一对多关联 -> 创建评论并关联评论和帖子
     *
     * @param objectId 当前的动态的id
     * @param hasAlias 是否是别名评论
     * @param content  评论的内容
     * @param listener 评论的监听器
     */
    public static void addOneToMore(String objectId, boolean hasAlias, String content, final OnAddCommentListener<String> listener) {
        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        FriendCircle post = new FriendCircle();
        post.setObjectId(objectId);
        FriendCircleComment comment = new FriendCircleComment();
        comment.setContent(content);
        comment.setPost(post);
        comment.setAuthor(user);
        comment.setAlias(hasAlias);
        comment.save(new SaveListener<String>() {
            @Override
            public void done(String commentTips, BmobException e) {
                if (e == null) {
                    listener.onSuccess(commentTips);
                } else {
                    listener.onError(e.getErrorCode(), e.getMessage());
                }
            }
        });
    }

    public interface OnListener<T> {

        void onSuccess(List<T> data);

        void onError(int errorCode, String message);

        void onSuccess(T data);

    }

    public interface OnAddCommentListener<T> {

        void onError(int errorCode, String message);

        void onSuccess(T data);

    }


}
