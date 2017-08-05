package com.cainiao.common.utils;

import android.app.Activity;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * Created by wuyinlei on 2017/8/5.
 */

public class DisplayUtils {

    /**
     * Change Dip to PX
     *
     * @param resources Resources
     * @param dp        Dip
     * @return PX
     */
    public static float dipToPx(Resources resources, float dp) {
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics);
    }


    public static int getScreenWidth(Activity activity) {
        DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
        //int width = activity.getWindowManager().getDefaultDisplay().getWidth();
        return displayMetrics.widthPixels;
    }
}
