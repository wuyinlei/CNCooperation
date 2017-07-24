package com.cainiao.common.base;

import java.io.Serializable;

/**
 * Created by wuyinlei on 2017/7/23.
 */

public class BaseBean <T> implements Serializable {

    private static final int SUCCESS = 1;

    private int errorCode;

    private String errorStr;

    private T results;

    public boolean success() {
        return errorStr.equals("success");
    }

    public static int getSUCCESS() {
        return SUCCESS;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorStr() {
        return errorStr;
    }

    public void setErrorStr(String errorStr) {
        this.errorStr = errorStr;
    }

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }
}
