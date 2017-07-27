package com.cainiao.common.widget.nineimage;

import android.content.Context;

import java.util.List;

/**
 * Created by wuyinlei on 2017/3/21.
 */

public class NineGridClickViewAdapter extends NineGridViewAdapter {
    private int statusHeight;

    public NineGridClickViewAdapter(Context context, List<ImageInfo> imageInfo) {
        super(context, imageInfo);
        statusHeight = getStatusHeight(context);
    }

    @Override
    protected void onImageItemClick(Context context, NineGridView nineGridView, int index, List<ImageInfo> imageInfo) {

    }

    /**
     * 获得状态栏的高度
     */
    public int getStatusHeight(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }


}
