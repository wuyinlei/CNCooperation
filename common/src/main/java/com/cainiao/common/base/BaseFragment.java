package com.cainiao.common.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cainiao.common.presenter.BaseContract;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by wuyinlei on 2017/7/22.
 *
 * @function fragment的基类
 */

public abstract class BaseFragment extends Fragment {

    private View mRoot;
    private Unbinder mUnbinder;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        initArgus(getArguments());
    }

    private void initArgus(Bundle arguments) {

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = mRoot;
        if (mRoot == null) {
            int layoutId = setLayoutId();
            view = inflater.inflate(layoutId, container, false);
            mRoot = view;
            initView(view);
            initListener();
            initData();
        } else {
            if (mRoot.getParent() != null) {
                //把当前root从其父控件中移除
                ((ViewGroup) mRoot.getParent()).removeView(mRoot);
            }
        }
        return mRoot = view;
    }


    /**
     * 初始化数据  也就是请求数据
     */
    protected void initData() {

    }


    /**
     * 设置Listener
     */
    protected void initListener() {

    }


    /**
     * 初始化View
     *
     * @param view 根布局view
     */
    protected void initView(View view) {
        mUnbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (mRoot != null) {
            if (mUnbinder != null)
                mUnbinder.unbind();
            mRoot = null;
        }


    }


    public abstract int setLayoutId();
}
