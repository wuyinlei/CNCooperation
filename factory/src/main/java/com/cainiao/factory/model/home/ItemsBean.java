package com.cainiao.factory.model.home;

/**
 * Created by wuyinlei on 2017/8/1.
 *
 * @function 轮播图下方的5个图标
 */

public class ItemsBean {

    /**
     * img_url : http://i8.mifile.cn/b2c-mimall-media/07a7e897271cd4b17d499cd92a4b0802.jpg?bg=DBB298
     * img_url_webp : http://i8.mifile.cn/v1/a1/407c7d37-0b97-878d-733b-d08e9b2a2493.webp?bg=DBB298
     * action : {"type":"plugin","path":"ShopPlugin://com.xiaomi.shop2.plugin.goodsdetail.GoodsDetailFragment?pluginId=101","extra":"{\"commodityId\":\"10000064\",\"goodsId\":\"\",\"productId\":\"10000064\"}","log_code":"31androidhomegallery_540001005#rid=8740957befe30ad6606e33a0bd06753e&t=ad&page=home&act=product&pid=10000064&page_id=1770&bid=3000003.1&adp=734&adm=2434"}
     * product_price : 0
     * product_org_price : 0
     * w : 1080
     * h : 540
     * ad_position_id : 734
     * material_id : 2434
     * img_rgb : #DBB298
     * img_url_color : #DBB298
     */

    private String img_url;
    private String img_url_webp;
    private ActionBeanItem action;
    private String product_price;
    private String product_org_price;
    private int w;
    private int h;
    private int ad_position_id;
    private int material_id;
    private String img_rgb;
    private String img_url_color;

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getImg_url_webp() {
        return img_url_webp;
    }

    public void setImg_url_webp(String img_url_webp) {
        this.img_url_webp = img_url_webp;
    }

    public ActionBeanItem getAction() {
        return action;
    }

    public void setAction(ActionBeanItem action) {
        this.action = action;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getProduct_org_price() {
        return product_org_price;
    }

    public void setProduct_org_price(String product_org_price) {
        this.product_org_price = product_org_price;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getAd_position_id() {
        return ad_position_id;
    }

    public void setAd_position_id(int ad_position_id) {
        this.ad_position_id = ad_position_id;
    }

    public int getMaterial_id() {
        return material_id;
    }

    public void setMaterial_id(int material_id) {
        this.material_id = material_id;
    }

    public String getImg_rgb() {
        return img_rgb;
    }

    public void setImg_rgb(String img_rgb) {
        this.img_rgb = img_rgb;
    }

    public String getImg_url_color() {
        return img_url_color;
    }

    public void setImg_url_color(String img_url_color) {
        this.img_url_color = img_url_color;
    }

}
