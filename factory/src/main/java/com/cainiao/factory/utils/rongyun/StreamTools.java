package com.cainiao.factory.utils.rongyun;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by wuyinlei on 2017/8/4.
 *
 * @function 将输入的字节流转换为字符串
 */

public class StreamTools {

    static String readInputStream(InputStream inputStream) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int len;
            byte[] buffer = new byte[1024];
            while ((len = inputStream.read(buffer))!=-1) {
                baos.write(buffer,0,len);
            }
            inputStream.close();
            baos.close();
            byte[] byte_result = baos.toByteArray();
            return new String(byte_result);
        } catch (IOException e) {
            e.printStackTrace();
            return "Error";
        }
    }
}
