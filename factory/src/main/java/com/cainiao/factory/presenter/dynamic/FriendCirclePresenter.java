package com.cainiao.factory.presenter.dynamic;

import com.cainiao.common.presenter.BasePresenter;
import com.cainiao.factory.R;
import com.cainiao.factory.model.MyUser;
import com.cainiao.factory.model.circle.CircleViewBean;
import com.cainiao.factory.model.circle.FriendCircle;
import com.cainiao.factory.model.circle.FriendCircleComment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by wuyinlei on 2017/7/29.
 *
 * @function 朋友圈列表P
 */

public class FriendCirclePresenter extends BasePresenter<FriendCircleContract.View>
        implements FriendCircleContract.Presenter {
    public FriendCirclePresenter(FriendCircleContract.View view) {
        super(view);
    }

    @Override
    public void publishComment(String postId, MyUser currentUser, String content) {

    }

    @Override
    public void likesCircle(String postId, MyUser currentUser) {

    }


    @Override
    public boolean checkContent(String content) {
        return false;
    }

    @Override
    public void requestData(int limit) {

        requestOnlyData(limit, 0);

    }

    private void requestOnlyData(int limit, final int skip) {
        Observable.create(new Observable.OnSubscribe<List<FriendCircle>>() {

            @Override
            public void call(final Subscriber<? super List<FriendCircle>> subscriber) {
                BmobQuery<FriendCircle> query = new BmobQuery<>();
                query.order("-createdAt");
//		query.setCachePolicy(CachePolicy.NETWORK_ONLY);


                query.setLimit(10);  //限制最多10条数据结果作为一页
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
                            subscriber.onError(e);
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
                    viewBean.setViewcount(new Random(100) + "");
//                    viewBean.setLikescount(friendCircle.getLove() + "");
                    viewBean.setUsername(friendCircle.getAuthor().getUsername());
//                    viewBean.setCommentcount();
                    BmobQuery<FriendCircleComment> query = new BmobQuery<>();
                    ////用此方式可以构造一个BmobPointer对象。只需要设置objectId就行
                    FriendCircle post = new FriendCircle();
                    post.setObjectId(friendCircles.get(i).getObjectId());
                    query.addWhereEqualTo("post", new BmobPointer(post));
                    //希望同时查询该评论的发布者的信息，以及该帖子的作者的信息，这里用到上面`include`的并列对象查询和内嵌对象的查询
                    query.include("author,post.author");
                    query.findObjects(new FindListener<FriendCircleComment>() {

                        @Override
                        public void done(List<FriendCircleComment> objects, BmobException e) {
                            if (objects != null && e == null) {
                                List<CircleViewBean.CircleComment> comments = new ArrayList<>();
                                for (int i = 0; i < objects.size(); i++) {
                                    viewBean.setCommentcount(objects.size() + "");
                                    CircleViewBean.CircleComment commentBean = new CircleViewBean.CircleComment();
                                    commentBean.setUser(objects.get(i).getAuthor());
                                    commentBean.setComment(objects.get(i).getContent());
                                    comments.add(commentBean);
                                }

                                viewBean.setComment(comments);
                            }
                        }
                    });

                    circleViewBeanList.add(viewBean);

                }

                return Observable.just(circleViewBeanList);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<CircleViewBean>>() {
                    @Override
                    public void call(List<CircleViewBean> viewBeen) {
                        if (viewBeen != null && viewBeen.size() > 0) {
                            if (skip <= 0) {
                                getView().requestDataSuccess(viewBeen);
                            } else {
                                getView().loadMoreDataSuccess(viewBeen);
                            }
                        } else {
                            getView().showError(R.string.request_error);
                        }
                    }
                });
    }

    @Override
    public void loadMoreData(int limit, int skip) {
        requestOnlyData(limit, skip);
    }
}
