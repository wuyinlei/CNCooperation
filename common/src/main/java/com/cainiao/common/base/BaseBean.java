package com.cainiao.common.base;

import java.io.Serializable;

/**
 * Created by wuyinlei on 2017/7/23.
 */

public class BaseBean<T> implements Serializable {

    private static final int SUCCESS = 0;

    private int code;

    private String info;

    private String desc;

    private T date;

    public boolean isSuccess() {
        return SUCCESS == code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public T getDate() {
        return date;
    }

    public void setDate(T date) {
        this.date = date;
    }
}
