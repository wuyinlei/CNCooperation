package com.cainiao.factory.net.rx;

import android.content.Context;
import android.content.DialogInterface;

import com.cainiao.common.widget.CustomProgress;
import com.cainiao.factory.R;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;


public abstract class ProgressDialogSubscriber<T> extends ErrorHandlerSubscriber<T>
        implements DialogInterface.OnCancelListener {

    private Context mContext;
    private String str;
    private boolean cancelable;
    private DialogInterface.OnCancelListener mCancelListener;

    public ProgressDialogSubscriber(Context context) {
        super(context);
        this.mContext = context;
        this.str = context.getResources().getString(R.string.loading);
        cancelable = true;
        mCancelListener = null;
    }

    public ProgressDialogSubscriber(Context context, String str, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context);
        this.mContext = context;
        this.str = str;
        this.cancelable = cancelable;
        this.mCancelListener = cancelListener;
    }


    @Override
    public void onSubscribe(@NonNull Disposable d) {
        if (isShowDialog())
            CustomProgress.show(mContext, str, cancelable, mCancelListener);
    }

    @Override
    public void onComplete() {
        if (isShowDialog())
            CustomProgress.disMiss();
    }

    /**
     * 本次请求是否显示dialog
     *
     * @return true / false
     */
    protected boolean isShowDialog() {
        return true;
    }

    @Override
    public void onError(Throwable e) {
        super.onError(e);
        if (isShowDialog()) {
            CustomProgress.disMiss();
        }
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        if (dialog != null)
            dialog.dismiss();
    }
}
