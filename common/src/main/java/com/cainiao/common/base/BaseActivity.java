package com.cainiao.common.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.cainiao.common.widget.state.StateView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by wuyinlei on 2017/7/22.
 *
 * @function activity的基类
 */

public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder mUnbinder;
    public StateView mStateView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在界面未初始化之前调用额初始化窗口
        Glide.get(this).clearMemory();


        if (initArgs(getIntent().getExtras())) {
            //设置界面layoutId
            setContentView(getContentLayoutId());

            mStateView = StateView.inject(injectTarget());

            initBefore();

            initView();

            initListener();

            initData();

        } else {
            finish();
        }

    }

    protected  void initBefore(){}

    protected abstract BaseActivity injectTarget();

    /**
     * 用于子类设置各种监听
     */
    protected  void initListener(){

    }


    /**
     * 初始化窗口
     * @param bundle  数据
     * @return  如果初始化成功  返回true
     */
    protected boolean initArgs(Bundle bundle) {
        return true;
    }

    /**
     * 当前控件id
     *
     * @return layoutId
     */
    protected abstract int getContentLayoutId();

    /**
     * 初始化控件
     */
    protected void initView() {
        mUnbinder = ButterKnife.bind(this);
    }


    /**
     * 初始化数据
     */
    protected void initData() {

    }

    @Override
    public boolean onSupportNavigateUp() {
        //当点击界面导航返回时  finish当前界面
        finish();
        return super.onSupportNavigateUp();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();

        Glide.get(this).clearMemory();
    }
}
