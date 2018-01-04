package com.cainiao.cncooperation.ui.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.cainiao.cncooperation.R;
import com.cainiao.common.base.BaseActivity;
import com.cainiao.common.constant.Common;
import com.cainiao.factory.app.Account;
import com.cainiao.factory.presenter.account.PersonalContract;

public class PersonalActivity extends BaseActivity {


    private Fragment mCurrentFragment;

    private String objectId;

    /**
     * 进入到别人的个人页面
     *
     * @param context  上下文
     * @param objectId 用户objectId
     */
    public static void show(Context context, String objectId) {
        Intent intent = new Intent(context, PersonalActivity.class);
        intent.putExtra(Common.Constance.OBECJT_ID, objectId);
        context.startActivity(intent);
    }

    @Override
    protected BaseActivity injectTarget() {
        return this;
    }


    @Override
    protected boolean initArgs(Bundle bundle) {
        objectId = bundle.getString(Common.Constance.OBECJT_ID);
        return true;
    }

    @Override
    protected void initView() {
        super.initView();

//        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_left);

        if (Account.getUser().getObjectId().equals(objectId)) {
            //如果传递过来的用户名就是当前登录的用户
            mCurrentFragment = new PersonalFragment();
        } else {
            //如果传递过来的用户名不是当前登录的用户
            mCurrentFragment = OtherUserFragment.newInstance(objectId);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ((PersonalFragment) mCurrentFragment).onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_personal;
    }
}
