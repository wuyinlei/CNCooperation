package com.cainiao.factory.presenter.dynamic;

import com.cainiao.common.presenter.BasePresenter;
import com.cainiao.factory.R;
import com.cainiao.factory.model.MyUser;
import com.cainiao.factory.model.circle.CircleViewBean;
import com.cainiao.factory.model.circle.FriendCircle;
import com.cainiao.factory.utils.BmobUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
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

        BmobUtils.requestFriendCircle(limit,skip,getView());

    }

    @Override
    public void loadMoreData(int limit, int skip) {
        requestOnlyData(limit, skip);
    }
}
