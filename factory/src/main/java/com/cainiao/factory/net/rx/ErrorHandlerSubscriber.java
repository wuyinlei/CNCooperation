package com.cainiao.factory.net.rx;



import android.content.Context;
import android.util.Log;

import com.cainiao.factory.net.error.RxErrorDisposer;
import com.cainiao.factory.net.exception.BaseException;


public abstract class ErrorHandlerSubscriber<T> extends DefaultSubscribe<T> {

    private RxErrorDisposer mErrorDisposer = null;

    private Context mContext;

    public ErrorHandlerSubscriber(Context context) {
        this.mContext = context;
        mErrorDisposer = new RxErrorDisposer(mContext);
    }

    @Override
    public void onError(Throwable e) {
        BaseException baseException =  mErrorDisposer.handlerError(e);

        if(baseException==null){
            e.printStackTrace();
            Log.d("ErrorHandlerSubscriber",e.getMessage());
        }
        else {
            mErrorDisposer.showErrorMessage(baseException);
        }
    }
}
