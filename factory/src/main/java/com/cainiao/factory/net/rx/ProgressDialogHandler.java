//package com.cainiao.factory.net.rx;
//
//import android.content.Context;
//import android.content.DialogInterface;
//import android.os.Handler;
//import android.os.Message;
//import android.support.annotation.StringRes;
//
//import com.cainiao.common.widget.CustomProgress;
//
//
//public class ProgressDialogHandler extends Handler {
//
//    public static final int SHOW_PROGRESS_DIALOG = 1;
//
//    public static final int DISMISS_PROGRESS_DIALOG = 0;
//
//
//    private Context mContext;
//
//    private boolean cancelable;
//
//    DialogInterface.OnCancelListener mCancelListener;
//
//    private String str;
//
//
//    public ProgressDialogHandler(Context context) {
//        super();
//        this.mContext = context;
//    }
//
//    public ProgressDialogHandler(Context context, String str, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
//        super();
//        this.mContext = context;
//        this.mCancelListener = cancelListener;
//        this.str = str;
//
//    }
//
//
//    public void showProgressDialog() {
//
//    }
//
//    public void dismissProgressDialog() {
//        CustomProgress.disMiss();
//    }
//
//    @Override
//    public void handleMessage(Message msg) {
//        switch (msg.what) {
//            case SHOW_PROGRESS_DIALOG:
//                showProgressDialog();
//                break;
//            case DISMISS_PROGRESS_DIALOG:
//                dismissProgressDialog();
//                break;
//        }
//    }
//
//
//}
