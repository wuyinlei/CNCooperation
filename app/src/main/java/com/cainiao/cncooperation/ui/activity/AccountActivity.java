package com.cainiao.cncooperation.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.cainiao.cncooperation.R;
import com.cainiao.cncooperation.ui.fragment.account.AccountTrigger;
import com.cainiao.cncooperation.ui.fragment.account.LoginFragment;
import com.cainiao.cncooperation.ui.fragment.account.RegisterFragment;
import com.cainiao.common.base.BaseActivity;

import butterknife.BindView;

public class AccountActivity extends BaseActivity implements AccountTrigger {

    private Fragment mCurrentFragment;
    private Fragment mLoginFragment;
    private Fragment mRegisterFragment;

    @BindView(R.id.iv_bg)
    ImageView mIvBg;

    /**
     * 跳转到登录界面
     *
     * @param context 上下文
     */
    public static void show(Context context) {
        Intent intent = new Intent(context, AccountActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_account;
    }

    @Override
    protected void initView() {
        super.initView();

        overridePendingTransition(R.anim.login_activity_in, R.anim.login_activity_out);
        mLoginFragment = mCurrentFragment = new RegisterFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.lay_container, mCurrentFragment)
                .commit();

        Glide.with(this)
                .load(R.mipmap.login_register_bg)
                .centerCrop()
                .into(new ViewTarget<ImageView, GlideDrawable>(mIvBg) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        Drawable drawable = resource.getCurrent();
                        //使用适配包进行包装
                        drawable = DrawableCompat.wrap(drawable);

                        this.view.setImageDrawable(drawable);
                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mCurrentFragment != null)
            mCurrentFragment.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void triggerView() {
        Fragment fragment = null;
        if (mCurrentFragment == mLoginFragment) {
            if (mRegisterFragment == null)
                mRegisterFragment = new RegisterFragment();
            fragment = mRegisterFragment;
        } else {
            fragment = mLoginFragment;
        }

        mCurrentFragment = fragment;
        //切换显示
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.lay_container, mCurrentFragment)
                .commit();

    }
}
