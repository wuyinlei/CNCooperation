//package com.cainiao.factory.net.rx;
//
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//
//import com.cainiao.factory.net.error.RxErrorDisposer;
//import com.cainiao.factory.net.exception.BaseException;
//
//import io.reactivex.annotations.NonNull;
//import io.reactivex.disposables.Disposable;
//
//
//public abstract class ProgressSubscriberddd<T> extends ErrorHandlerSubscriber<T>
//        implements DialogInterface.OnCancelListener {
//
//    private ProgressDialog mProgressDialog;
//
//    private RxErrorDisposer mErrorDisposer;
//
//    private Context mContext;
//
//    public ProgressSubscriberddd(Context context) {
//        super(context);
//    }
//
//    @Override
//    public void onSubscribe(@NonNull Disposable d) {
//        mProgressDialog.show();
//    }
//
//
//
//    @Override
//    public void onCancel(DialogInterface dialog) {
//        dialog.dismiss();
//    }
//
//    /**
//     * 本次请求是否显示dialog
//     *
//     * @return  true / false
//     */
//    protected boolean isShowDialog() {
//        return true;
//    }
//
//    @Override
//    public void onError(Throwable e) {
//        super.onError(e);
//        BaseException exception = mErrorDisposer.handlerError(e);
//        if (isShowDialog()){
//            // TODO: 2017/7/23 view显示错误
//        }
//    }
//
//
//}
