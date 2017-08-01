package com.cainiao.factory.model.home;

import java.util.List;

/**
 * Created by wuyinlei on 2017/8/1.
 *
 * @function 首页轮播图数据
 */

public class HeaderBean {

    /**
     * view_type : gallery
     * stat :
     * body : {"items":[{"img_url":"http://i8.mifile.cn/b2c-mimall-media/02d09eebee36ef3a6e35ab866f853d13.jpg?bg=DEB49A","img_url_webp":"http://i8.mifile.cn/v1/a1/c2140d09-3bce-fa85-489a-024cc3ef9583.webp?bg=DEB49A","action":{"type":"plugin","path":"ShopPlugin://com.xiaomi.shop2.plugin.goodsdetail.GoodsDetailFragment?pluginId=101","extra":"{\"commodityId\":\"10000064\",\"goodsId\":\"\",\"productId\":\"10000064\"}","log_code":"31androidhomegallery001004#rid=54fd89fff0afcea4523fc0340626f149&t=ad&page=home&act=product&pid=10000064&page_id=1770&bid=3000002.1&adp=2&adm=2433"},"w":1080,"h":768,"ad_position_id":2,"material_id":2433,"img_url_color":"#DEB49A"},{"img_url":"http://i8.mifile.cn/b2c-mimall-media/3fe714339cbe46fa570c19f295a07c9e.jpg?bg=D6D9DD","img_url_webp":"http://i8.mifile.cn/v1/a1/e77a0936-9f17-7251-c728-4df2b25bfafc.webp?bg=D6D9DD","action":{"type":"plugin","path":"ShopPlugin://com.xiaomi.shop2.plugin.hdchannel.RootFragment?pluginId=15102","extra":"{\"extra_url\":\"http:\\/\\/api.m.mi.com\\/v1\\/home\\/activity_page\",\"extra_title\":\"8.1-max 2 \\u4e13\\u9898\",\"extra_ver\":\"2076\"}","log_code":"31androidhomegallery002004#rid=aeed53b1d02b762eeffda32efcdfa2e3&t=ad&page=home&act=other&page_id=1770&bid=3000002.2&adp=1&adm=2427"},"w":1080,"h":768,"ad_position_id":1,"material_id":2427,"img_url_color":"#D6D9DD"}]}
     */

    private String view_type;
    private String stat;
    private BodyBeanXX body;

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

    public BodyBeanXX getBody() {
        return body;
    }

    public void setBody(BodyBeanXX body) {
        this.body = body;
    }

    public static class BodyBeanXX {
        private List<ItemsBeanX> items;

        public List<ItemsBeanX> getItems() {
            return items;
        }

        public void setItems(List<ItemsBeanX> items) {
            this.items = items;
        }

        public static class ItemsBeanX {
            /**
             * img_url : http://i8.mifile.cn/b2c-mimall-media/02d09eebee36ef3a6e35ab866f853d13.jpg?bg=DEB49A
             * img_url_webp : http://i8.mifile.cn/v1/a1/c2140d09-3bce-fa85-489a-024cc3ef9583.webp?bg=DEB49A
             * action : {"type":"plugin","path":"ShopPlugin://com.xiaomi.shop2.plugin.goodsdetail.GoodsDetailFragment?pluginId=101","extra":"{\"commodityId\":\"10000064\",\"goodsId\":\"\",\"productId\":\"10000064\"}","log_code":"31androidhomegallery001004#rid=54fd89fff0afcea4523fc0340626f149&t=ad&page=home&act=product&pid=10000064&page_id=1770&bid=3000002.1&adp=2&adm=2433"}
             * w : 1080
             * h : 768
             * ad_position_id : 2
             * material_id : 2433
             * img_url_color : #DEB49A
             */

            private String img_url;
            private String img_url_webp;
            private ActionBeanXXX action;
            private int w;
            private int h;
            private int ad_position_id;
            private int material_id;
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

            public ActionBeanXXX getAction() {
                return action;
            }

            public void setAction(ActionBeanXXX action) {
                this.action = action;
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

            public String getImg_url_color() {
                return img_url_color;
            }

            public void setImg_url_color(String img_url_color) {
                this.img_url_color = img_url_color;
            }

            public static class ActionBeanXXX {
                /**
                 * type : plugin
                 * path : ShopPlugin://com.xiaomi.shop2.plugin.goodsdetail.GoodsDetailFragment?pluginId=101
                 * extra : {"commodityId":"10000064","goodsId":"","productId":"10000064"}
                 * log_code : 31androidhomegallery001004#rid=54fd89fff0afcea4523fc0340626f149&t=ad&page=home&act=product&pid=10000064&page_id=1770&bid=3000002.1&adp=2&adm=2433
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
        }
    }
}
