package com.cainiao.factory.net.exception;

/**
 * Created by wuyinlei on 2017/7/23.
 */

public class ApiException extends BaseException{


    public ApiException(int status, String message) {
        super(status, message);
    }
}
