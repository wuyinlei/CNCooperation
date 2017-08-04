package com.cainiao.factory;

import android.os.Parcel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import io.rong.common.ParcelUtils;
import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MessageContent;

/**
 * 自定义消息
 */

@MessageTag(value = "CM:custom", flag = MessageTag.ISCOUNTED | MessageTag.ISPERSISTED)
public class CustomizeMessage extends MessageContent {

    private long mSendTime;
    private String mContent;

    /**
     * 发消息时调用，将自定义消息对象序列化为消息数据:
     * 首先将消息属性封装成json，再将json转换成byte数组
     */
    @Override
    public byte[] encode() {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("time", getSendTime());
            jsonObject.put("content", getContent());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            return jsonObject.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    public CustomizeMessage() {

    }

    /**
     * 将收到的消息进行解析，byte -> json,再将json中的内容取出赋值给消息属性
     */
    public CustomizeMessage(byte[] data) {
        String jsonString = null;

        try {
            jsonString = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            if (jsonObject.has("time"))
                setSendTime(jsonObject.optLong("time"));
            if (jsonObject.has("content"))
                setContent(jsonObject.optString("content"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param in 通过初始化传入的Parcel，为消息属性赋值
     */
    private CustomizeMessage(Parcel in) {
        setSendTime(ParcelUtils.readLongFromParcel(in));
        setContent(ParcelUtils.readFromParcel(in));
    }

    /**
     * 读取接口，目的是要从Parcel中构造一个实现了Parcelable的类的实例处理
     */
    public static final Creator<CustomizeMessage> CREATOR = new Creator<CustomizeMessage>() {
        @Override
        public CustomizeMessage createFromParcel(Parcel source) {
            return new CustomizeMessage(source);
        }

        @Override
        public CustomizeMessage[] newArray(int size) {
            return new CustomizeMessage[size];
        }
    };

    /**
     * 描述了包含在Parcelable对象排列信息中的特殊对象的类型
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * 将类的数据写入到外部提供的Parcel中
     * @param dest 对象被写入的Parcel
     * @param flags 对象如何被写入的附加标志
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        ParcelUtils.writeToParcel(dest, mSendTime);
        ParcelUtils.writeToParcel(dest, mContent);
    }

    public static CustomizeMessage obtain(long time, String content) {
        CustomizeMessage customizeMessage = new CustomizeMessage();
        customizeMessage.mSendTime = time;
        customizeMessage.mContent = content;
        return customizeMessage;
    }


    public long getSendTime() {
        return mSendTime;
    }

    public String getContent() {
        return mContent;
    }

    private void setSendTime(long mSendTime) {
        this.mSendTime = mSendTime;
    }

    private void setContent(String mContent) {
        this.mContent = mContent;
    }

}
