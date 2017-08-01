package com.cainiao.factory.model.home;

/**
 * Created by wuyinlei on 2017/8/1.
 *
 * @function 轮播图下方的5个图片的按钮action
 */

public class ActionBeanItem {

    /**
     * type : plugin
     * path : ShopPlugin://com.xiaomi.shop2.plugin.goodsdetail.GoodsDetailFragment?pluginId=101
     * extra : {"commodityId":"10000064","goodsId":"","productId":"10000064"}
     * log_code : 31androidhomegallery_540001005#rid=8740957befe30ad6606e33a0bd06753e&t=ad&page=home&act=product&pid=10000064&page_id=1770&bid=3000003.1&adp=734&adm=2434
     */

    private String type;
    private String path;
    private String extra;
    private String log_code;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getLog_code() {
        return log_code;
    }

    public void setLog_code(String log_code) {
        this.log_code = log_code;
    }
}
