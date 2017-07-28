package com.cainiao.common.widget.camera;

/**
 * Created by wuyinlei on 2017/7/28.
 *
 * @function 上传图片bean
 */

public class UploadImage {

    private String picadress; //选择的图片本地地址
    private String picToCompose;//图片压缩后地址
    boolean isCompose; //是否进行了压缩
    private String picimage; //服务器返回的图片地址
    private boolean hasUpload;//已经上传


    public String getPicadress() {
        return picadress;
    }

    public void setPicadress(String picadress) {
        this.picadress = picadress;
    }

    public String getPicToCompose() {
        return picToCompose;
    }

    public void setPicToCompose(String picToCompose) {
        this.picToCompose = picToCompose;
    }

    public boolean isCompose() {
        return isCompose;
    }

    public void setCompose(boolean compose) {
        isCompose = compose;
    }

    public String getPicimage() {
        return picimage;
    }

    public void setPicimage(String picimage) {
        this.picimage = picimage;
    }

    public boolean isHasUpload() {
        return hasUpload;
    }

    public void setHasUpload(boolean hasUpload) {
        this.hasUpload = hasUpload;
    }
}
