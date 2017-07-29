package com.cainiao.factory.presenter.dynamic;

import android.text.TextUtils;

import com.cainiao.common.presenter.BasePresenter;
import com.cainiao.factory.R;
import com.cainiao.factory.model.MyUser;
import com.cainiao.factory.model.circle.FriendCircle;

import java.util.ArrayList;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by wuyinlei on 2017/7/28.
 *
 * @function 发布动态的数据
 */

public class DynamicPublishPresenter extends BasePresenter<DynamicPublishContract.View>
        implements DynamicPublishContract.Presenter {


    public DynamicPublishPresenter(DynamicPublishContract.View view) {
        super(view);
    }

    @Override
    public void dynamicPublish(String content, ArrayList<String> images) {
        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        final FriendCircle friendCircle = new FriendCircle();
        if (checkContent(content)) {
            getView().showError(R.string.mind_circle_content_not_null);
            return;
        }
        friendCircle.setContent(content);
        friendCircle.setAuthor(user);
        friendCircle.setLove(0);
        friendCircle.setHate(0);
        friendCircle.setShare(0);
        friendCircle.setComment(0);
        friendCircle.setPass(true);
        if (images != null && images.size() > 0)
            friendCircle.setCircleimages(images);
        friendCircle.save(new SaveListener<String>() {
            @Override
            public void done(String postId, BmobException e) {
                if (e == null) {
                    getView().publishDynamicSuccess(R.string.mind_circle_publish_success);


                } else {
                    getView().onFailure(e.getErrorCode(), e.getMessage());
                }
            }
        });
    }



    @Override
    public boolean checkContent(String content) {

        return TextUtils.isEmpty(content);
    }
}
