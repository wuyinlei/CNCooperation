package com.cainiao.common.base;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.annotation.StringRes;

import com.cainiao.common.R;
import com.cainiao.common.presenter.BaseContract;


/**
 * Created by wuyinlei on 2017/8/8.
 *
 * @function PresenterActivity
 */

public abstract class PresenterActivity<Presenter extends
        BaseContract.Presenter> extends BaseActivity implements BaseContract.View<Presenter> {

    protected Presenter mPresenter;

    private ProgressDialog mLoadingProgressDialog;

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void initBefore() {
        super.initBefore();
        initPresenter();
    }

    @Override
    public void showError(@StringRes int str) {

    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.mPresenter = presenter;
    }

    protected abstract Presenter initPresenter();


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.destroy();
        }
    }

    @Override
    public void showLoading() {
        if (mStateView != null)
            mStateView.showLoading();
        else {
            if (mLoadingProgressDialog == null) {
                mLoadingProgressDialog = new ProgressDialog(this, R.style.AppTheme_Dialog_Alert_Light);
                mLoadingProgressDialog.setCanceledOnTouchOutside(false);
                mLoadingProgressDialog.setCancelable(true);
                mLoadingProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        finish();
                    }
                });
            }
            mLoadingProgressDialog.setMessage(getText(R.string.prompt_loading));
            mLoadingProgressDialog.show();
        }
    }

    protected void hideLoading() {
        if (mLoadingProgressDialog != null) {
            mLoadingProgressDialog.dismiss();
            mLoadingProgressDialog = null;
        }
        if (mStateView != null)
            mStateView.showContent();
    }


}
