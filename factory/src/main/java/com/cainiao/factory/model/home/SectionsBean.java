package com.cainiao.factory.model.home;

import java.util.List;

/**
 * Created by wuyinlei on 2017/8/1.
 *
 *
 */

public class SectionsBean {

    /**
     * view_type : gallery_540
     * stat :
     * body : {"items":[{"img_url":"http://i8.mifile.cn/b2c-mimall-media/07a7e897271cd4b17d499cd92a4b0802.jpg?bg=DBB298","img_url_webp":"http://i8.mifile.cn/v1/a1/407c7d37-0b97-878d-733b-d08e9b2a2493.webp?bg=DBB298","action":{"type":"plugin","path":"ShopPlugin://com.xiaomi.shop2.plugin.goodsdetail.GoodsDetailFragment?pluginId=101","extra":"{\"commodityId\":\"10000064\",\"goodsId\":\"\",\"productId\":\"10000064\"}","log_code":"31androidhomegallery_540001005#rid=8740957befe30ad6606e33a0bd06753e&t=ad&page=home&act=product&pid=10000064&page_id=1770&bid=3000003.1&adp=734&adm=2434"},"product_price":"0","product_org_price":"0","w":1080,"h":540,"ad_position_id":734,"material_id":2434,"img_rgb":"#DBB298","img_url_color":"#DBB298"},{"img_url":"http://i8.mifile.cn/b2c-mimall-media/47cedb749025aefad97b2bb6ec2e669f.jpg?bg=C0BDBA","img_url_webp":"http://i8.mifile.cn/v1/a1/4dcf390b-68f7-ed1e-8340-9782b687a848.webp?bg=C0BDBA","action":{"type":"plugin","path":"ShopPlugin://com.xiaomi.shop2.plugin.hdchannel.RootFragment?pluginId=15102","extra":"{\"extra_url\":\"http:\\/\\/api.m.mi.com\\/v1\\/home\\/activity_page\",\"extra_title\":\"8.1-max 2 \\u4e13\\u9898\",\"extra_ver\":\"2076\"}","log_code":"31androidhomegallery_540002005#rid=bc28556ae456e99393ad4dce0134d907&t=ad&page=home&act=other&page_id=1770&bid=3000003.2&adp=835&adm=2428"},"product_price":"0","product_org_price":"0","w":1080,"h":540,"ad_position_id":835,"material_id":2428,"img_rgb":"#C0BDBA","img_url_color":"#C0BDBA"}]}
     */

    private String view_type;
    private String stat;
    private BodyBeanItem body;

    public String getView_type() {
        return view_type;
    }

    public void setView_type(String view_type) {
        this.view_type = view_type;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public BodyBeanItem getBody() {
        return body;
    }

    public void setBody(BodyBeanItem body) {
        this.body = body;
    }

}
