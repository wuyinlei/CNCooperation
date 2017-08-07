package com.cainiao.factory.presenter.dynamic;

import android.content.Context;
import android.text.TextUtils;

import com.cainiao.common.presenter.BasePresenter;
import com.cainiao.factory.R;
import com.cainiao.factory.utils.BmobUtils;

import java.util.ArrayList;

/**
 * Created by wuyinlei on 2017/7/28.
 *
 * @function 发布动态的数据
 */

public class DynamicPublishPresenter extends BasePresenter<DynamicPublishContract.View>
        implements DynamicPublishContract.Presenter {

    private Context mContext;

    public DynamicPublishPresenter(DynamicPublishContract.View view) {
        super(view);
        this.mContext = (Context) view;
    }

    @Override
    public void dynamicPublish(String content, ArrayList<String> images) {

        if (checkContent(content)){
            getView().showError(R.string.mind_circle_content_not_null);
            return;
        }

        BmobUtils.dynamicPublish(mContext,content, images, new BmobUtils.OnListener<String>() {
            @Override
            public void onError(int errorCode, String message) {

            }

            @Override
            public void onSuccess(String data) {
                String[] split = data.split("=");
                getView().publishDynamicSuccess(split[0], split[1]);
            }
        });

//        MyUser user = BmobUser.getCurrentUser(MyUser.class);
//        final FriendCircle friendCircle = new FriendCircle();
//        if (checkContent(content)) {
//            getView().showError(R.string.mind_circle_content_not_null);
//            return;
//        }
//        friendCircle.setContent(content);
//        friendCircle.setAuthor(Account.getUser());
//        friendCircle.setLove(0);
//        friendCircle.setHate(0);
//        friendCircle.setShare(0);
//        friendCircle.setComment(0);
//        friendCircle.setPass(true);
//        friendCircle.setViewcount("1");
//        friendCircle.setCommentSize("0");
//        if (images != null && images.size() > 0)
//            friendCircle.setCircleimages(images);
//        friendCircle.save(new SaveListener<String>() {
//            @Override
//            public void done(String postId, BmobException e) {
//                if (e == null) {
//                    getView().publishDynamicSuccess(postId,R.string.mind_circle_publish_success);
//
//
//                } else {
//                    getView().onFailure(e.getErrorCode(), e.getMessage());
//                }
//            }
//        });
    }



    @Override
    public boolean checkContent(String content) {

        return TextUtils.isEmpty(content);
    }
}
