package com.cainiao.common.base;

/**
 * Created by wuyinlei on 2017/8/2.
 */

public interface BaseView {

    void showLoading();

    void showError(String msg);

    void dismissLoading();
}
