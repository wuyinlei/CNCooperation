package com.cainiao.factory.model.home;

import java.util.List;

/**
 * Created by wuyinlei on 2017/8/1.
 *
 * @function 扫一扫和我的二维码
 */

public class TitleLeftMenuBean {

    /**
     * view_type : title_left_menu
     * stat :
     * body : {"items":[{"img_url":"http://i8.mifile.cn/b2c-mimall-media/171fa9c4374c42c0329b96265991bd64.png","action":{"type":"plugin","path":"ShopPlugin://com.xiaomi.shop2.plugin.barcode.BarcodeFragment?pluginId=110","log_code":"app#&page=home"},"title":"扫一扫"},{"img_url":"http://i8.mifile.cn/b2c-mimall-media/f5bb5cbe1526455753e0850e41e8c646.png","action":{"type":"plugin","path":"ShopPlugin://com.xiaomi.shop2.plugin.qrcode.RootFragment?pluginId=178","log_code":"app#&page=home"},"title":"我的二维码"}],"img_url":"http://i8.mifile.cn/b2c-mimall-media/27d9c36ba8853bbcdddb2c968e167e93.png","light_img_url":"http://i8.mifile.cn/b2c-mimall-media/14c56e26b10f551c622cee67a2832e7f.png"}
     */

    private String view_type;
    private String stat;
    private BodyBeanX body;

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

    public BodyBeanX getBody() {
        return body;
    }

    public void setBody(BodyBeanX body) {
        this.body = body;
    }

    public static class BodyBeanX {
        /**
         * items : [{"img_url":"http://i8.mifile.cn/b2c-mimall-media/171fa9c4374c42c0329b96265991bd64.png","action":{"type":"plugin","path":"ShopPlugin://com.xiaomi.shop2.plugin.barcode.BarcodeFragment?pluginId=110","log_code":"app#&page=home"},"title":"扫一扫"},{"img_url":"http://i8.mifile.cn/b2c-mimall-media/f5bb5cbe1526455753e0850e41e8c646.png","action":{"type":"plugin","path":"ShopPlugin://com.xiaomi.shop2.plugin.qrcode.RootFragment?pluginId=178","log_code":"app#&page=home"},"title":"我的二维码"}]
         * img_url : http://i8.mifile.cn/b2c-mimall-media/27d9c36ba8853bbcdddb2c968e167e93.png
         * light_img_url : http://i8.mifile.cn/b2c-mimall-media/14c56e26b10f551c622cee67a2832e7f.png
         */

        private String img_url;
        private String light_img_url;
        private List<ItemsBean> items;

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public String getLight_img_url() {
            return light_img_url;
        }

        public void setLight_img_url(String light_img_url) {
            this.light_img_url = light_img_url;
        }

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean {
            /**
             * img_url : http://i8.mifile.cn/b2c-mimall-media/171fa9c4374c42c0329b96265991bd64.png
             * action : {"type":"plugin","path":"ShopPlugin://com.xiaomi.shop2.plugin.barcode.BarcodeFragment?pluginId=110","log_code":"app#&page=home"}
             * title : 扫一扫
             */

            private String img_url;
            private ActionBeanXX action;
            private String title;

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }

            public ActionBeanXX getAction() {
                return action;
            }

            public void setAction(ActionBeanXX action) {
                this.action = action;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public static class ActionBeanXX {
                /**
                 * type : plugin
                 * path : ShopPlugin://com.xiaomi.shop2.plugin.barcode.BarcodeFragment?pluginId=110
                 * log_code : app#&page=home
                 */

                private String type;
                private String path;
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
