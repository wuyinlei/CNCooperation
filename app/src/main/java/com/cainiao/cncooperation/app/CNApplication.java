package com.cainiao.cncooperation.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cainiao.common.base.BaseApplication;
import com.cainiao.common.widget.nineimage.NineGridView;

import cn.bmob.v3.Bmob;

/**
 * Created by wuyinlei on 2017/7/22.
 *
 * @function 全局的application
 *
 */

public class CNApplication extends BaseApplication {


    @Override
    public void onCreate() {
        super.onCreate();


        //第一：默认初始化
        Bmob.initialize(this, "d6bc5f51d6b9294f22c8d4a1a863f910");
        //第一：默认初始化
//        Bmob.initialize(this, "d6bc5f51d6b9294f22c8d4a1a863f910");

        NineGridView.setImageLoader(new PicassoImageLoader());

    }

    /**
     * Glide加载
     */
    private class PicassoImageLoader implements NineGridView.ImageLoader {

        @Override
        public void onDisplayImage(Context context, ImageView imageView, String url) {
            Glide.with(context).load(url)//
                    .into(imageView);
        }

        @Override
        public Bitmap getCacheImage(String url) {
            return null;
        }
    }

}
