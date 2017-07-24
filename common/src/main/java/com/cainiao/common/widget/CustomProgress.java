package com.cainiao.common.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.cainiao.common.R;

/**
 * Created by wuyinlei on 2017/7/24.
 *
 * @function 自定义的一个dialog
 */

public class CustomProgress extends Dialog {

    private static CustomProgress sPrograss;

    public CustomProgress(@NonNull Context context) {
        super(context);
    }

    public CustomProgress(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected CustomProgress(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    /**
     * 当窗口焦点改变时调用
     */
    public void onWindowFocusChanged(boolean hasFocus) {
        ImageView imageView = (ImageView) findViewById(R.id.spinnerImageView);
        // 获取ImageView上的动画背景
        AnimationDrawable spinner = (AnimationDrawable) imageView.getBackground();
        // 开始动画
        spinner.start();
    }

    /**
     * 给Dialog设置提示信息
     *
     * @param message 需要显示的信息
     */
    public void setMessage(CharSequence message) {
        if (message != null && message.length() > 0) {
            findViewById(R.id.message).setVisibility(View.VISIBLE);
            TextView txt = (TextView) findViewById(R.id.message);
            txt.setText(message);
            txt.invalidate();
        }
    }

    public  void show(Context context){
        show(context,context.getResources().getString(R.string.loading),true,null);
    }

    /**
     * 弹出自定义ProgressDialog
     *
     * @param context        上下文
     * @param message        提示
     * @param cancelable     是否按返回键取消
     * @param cancelListener 按下返回键监听
     * @return CustomProgress
     */
    public  static CustomProgress show(Context context, CharSequence message, boolean cancelable, OnCancelListener cancelListener) {
        sPrograss = new CustomProgress(context, R.style.Custom_Progress);
        sPrograss.setTitle("");
        sPrograss.setContentView(R.layout.custom_prograss_dialog_layout);
        if (message == null || message.length() == 0) {
            sPrograss.findViewById(R.id.message).setVisibility(View.GONE);
        } else {
            TextView txt = (TextView) sPrograss.findViewById(R.id.message);
            txt.setText(message);
        }
        // 按返回键是否取消
        sPrograss.setCancelable(cancelable);
        // 监听返回键处理
        if (cancelListener != null) {
            sPrograss.setOnCancelListener(cancelListener);
        }
        // 设置居中
        sPrograss.getWindow().getAttributes().gravity = Gravity.CENTER;
        WindowManager.LayoutParams lp = sPrograss.getWindow().getAttributes();
        // 设置背景层透明度
        lp.dimAmount = 0.2f;
        sPrograss.getWindow().setAttributes(lp);
        // dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        sPrograss.show();
        return sPrograss;

    }

    /**
     * 关闭dialog
     */
    public static void disMiss() {
        if (sPrograss != null && sPrograss.isShowing()) {
            sPrograss.dismiss();
        }
    }

}
