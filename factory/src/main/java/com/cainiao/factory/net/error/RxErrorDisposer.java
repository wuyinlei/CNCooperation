package com.cainiao.factory.net.error;

import android.content.Context;
import android.widget.Toast;

import com.cainiao.factory.net.exception.BaseException;
import com.cainiao.factory.net.exception.ErrorMessageDispose;
import com.google.gson.JsonParseException;

import java.net.SocketException;
import java.net.SocketTimeoutException;


/**
 * Created by wuyinlei on 2017/7/23.
 *
 * @function 请求出错的分发器
 */

public class RxErrorDisposer {

    private Context mContext;

    public RxErrorDisposer(Context context){
        this.mContext = context;
    }

    public BaseException handlerError(Throwable e){
        BaseException exception = new BaseException();

        if (e instanceof JsonParseException) {
            exception.setCode(BaseException.JSON_ERROR);
        } else if (e instanceof SocketException) {

            exception.setCode(BaseException.SOCKET_ERROR);
        } else if (e instanceof SocketTimeoutException) {
            exception.setCode(BaseException.SOCKET_TIMEOUT_ERROR);
        } else {
            exception.setCode(BaseException.UNKNOWN_ERROR);
        }

        exception.setDisplayMessage(ErrorMessageDispose.create(mContext,exception.getCode()));

        return exception;
    }

    public void showErrorMessage(BaseException e){
        Toast.makeText(mContext, e.getDisplayMessage(), Toast.LENGTH_SHORT).show();
    }
}
