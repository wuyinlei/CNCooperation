package com.cainiao.cncooperation.ui.account;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.cainiao.cncooperation.R;
import com.cainiao.cncooperation.ui.dynamic.FriendCircleDetailActivity;
import com.cainiao.common.base.BaseActivity;
import com.cainiao.common.constant.Common;
import com.cainiao.factory.Account;

public class PersonalActivity extends BaseActivity {


    private Fragment mCurrentFragment;

    private String username;

    /**
     * 进入到别人的个人页面
     * @param context  上下文
     * @param username  用户名
     */
    public static void show(Context context, String username) {
        Intent intent = new Intent(context, PersonalActivity.class);
        intent.putExtra(Common.Constance.USER_NAME, username);
        context.startActivity(intent);
    }

    @Override
    protected BaseActivity injectTarget() {
        return this;
    }


    @Override
    protected boolean initArgs(Bundle bundle) {
        username = bundle.getString(Common.Constance.USER_NAME);
        return true;
    }

    @Override
    protected void initView() {
        super.initView();

//        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_left);

        if (Account.getUserName().equals(username)){
            //如果传递过来的用户名就是当前登录的用户
            mCurrentFragment = new PersonalFragment();
        } else {
            //如果传递过来的用户名不是当前登录的用户
            mCurrentFragment = OtherUserFragment.newInstance(username);
        }

        getSupportFragmentManager().beginTransaction()
                .add(R.id.lay_container, mCurrentFragment)
                .commit();

//        Glide.with(this)
//                .load(R.mipmap.user_component_bg_login)
//                .centerCrop()
//                .into(new ViewTarget<ImageView, GlideDrawable>(mIvBg) {
//                    @Override
//                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
//                        Drawable drawable = resource.getCurrent();
//                        //使用适配包进行包装
//                        drawable = DrawableCompat.wrap(drawable);
//
//                        this.view.setImageDrawable(drawable);
//                    }
//                });

    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_personal;
    }
}
