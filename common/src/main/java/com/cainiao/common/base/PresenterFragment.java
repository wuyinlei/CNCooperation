package com.cainiao.common.base;

import android.content.Context;
import android.support.annotation.StringRes;

import com.cainiao.common.presenter.BaseContract;

/**
 * Created by wuyinlei on 2017/8/8.
 *
 * @function 需要实现Presenter的继承的基类
 */

public abstract class PresenterFragment<Presenter extends BaseContract.Presenter>
        extends BaseFragment implements BaseContract.View<Presenter> {

    protected Presenter mPresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        initPresenter();
    }

    @Override
    public void showError(@StringRes int str) {

    }


    protected abstract Presenter initPresenter();

    @Override
    public void showLoading() {

    }

    @Override
    public void setPresenter(Presenter presenter) {
        //View中额赋值Presenter
        this.mPresenter = presenter;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null){
            mPresenter.destroy();
        }
    }

}
