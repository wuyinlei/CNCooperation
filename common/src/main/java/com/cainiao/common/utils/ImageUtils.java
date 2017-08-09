package com.cainiao.common.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.SystemClock;

import com.cainiao.common.base.BaseApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by wuyinlei on 2017/8/8.
 *
 * @function 图片地址工具类
 */

public class ImageUtils {


    private static final String thumbImgDirPath = BaseApplication.getInstance().getCacheDir().getAbsolutePath();
    private static File thumbImgDir;

    public static File genThumbImgFile(String srcImgPath) {
        if (thumbImgDir == null)
            thumbImgDir = new File(thumbImgDirPath);
        if (!thumbImgDir.exists())
            thumbImgDir.mkdirs();
        String thumbImgName = SystemClock.currentThreadTimeMillis() + getFileNameFromPath(srcImgPath);
        File imageFileThumb = null;
        try {
            // 读取图片。
            InputStream is = new FileInputStream(srcImgPath);
            Bitmap bmpSource = BitmapFactory.decodeStream(is);

            File imageFileSource = new File(srcImgPath);
            imageFileSource.createNewFile();

            FileOutputStream fosSource = new FileOutputStream(imageFileSource);

            // 保存原图。
            bmpSource.compress(Bitmap.CompressFormat.JPEG, 100, fosSource);

            // 创建缩略图变换矩阵。
            Matrix m = new Matrix();
            m.setRectToRect(new RectF(0, 0, bmpSource.getWidth(), bmpSource.getHeight()), new RectF(0, 0, 160, 160), Matrix.ScaleToFit.CENTER);

            // 生成缩略图。
            Bitmap bmpThumb = Bitmap.createBitmap(bmpSource, 0, 0, bmpSource.getWidth(), bmpSource.getHeight(), m, true);

            imageFileThumb = new File(ImageUtils.thumbImgDirPath, thumbImgName);
            imageFileThumb.createNewFile();

            FileOutputStream fosThumb = new FileOutputStream(imageFileThumb);

            // 保存缩略图。
            bmpThumb.compress(Bitmap.CompressFormat.JPEG, 60, fosThumb);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageFileThumb;
    }

    /**
     * 计算压缩比例值
     *
     * @param options   解析图片的配置信息
     * @param reqWidth  所需图片压缩尺寸最小宽度
     * @param reqHeight 所需图片压缩尺寸最小高度
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // 保存图片原宽高值
        final int height = options.outHeight;
        final int width = options.outWidth;
        // 初始化压缩比例为1
        int inSampleSize = 1;

        // 当图片宽高值任何一个大于所需压缩图片宽高值时,进入循环计算系统
        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // 压缩比例值每次循环两倍增加,
            // 直到原图宽高值的一半除以压缩值后都~大于所需宽高值为止
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }


    /**
     * 获取文件名
     *
     * @param filepath dir+filename
     */
    public static String getFileNameFromPath(String filepath) {
        if ((filepath != null) && (filepath.length() > 0)) {
            int sep = filepath.lastIndexOf('/');
            if ((sep > -1) && (sep < filepath.length() - 1)) {
                return filepath.substring(sep + 1);
            }
        }
        return filepath;
    }


}
