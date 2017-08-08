package com.cainiao.common.base;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.cainiao.common.R;
import com.cainiao.common.presenter.BaseContract;

/**
 * Created by wuyinlei on 2017/8/8.
 *
 * @function PresenterToolbarActivity
 */

public abstract class PresenterToolbarActivity extends PresenterActivity<BaseContract.Presenter> {

    protected Toolbar mToolbar;

    @Override
    protected void initView() {
        super.initView();
        initToolbar((Toolbar) findViewById(R.id.toolbar));
    }

    public void initToolbar(Toolbar toolbar) {
        mToolbar = toolbar;
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        initTitleNeedBack();
    }

    protected void initTitleNeedBack() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(false);
        }
    }

}
