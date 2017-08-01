package com.cainiao.factory.model.home;

/**
 * Created by wuyinlei on 2017/8/1.
 *
 * @function  festival 节日
 */

public class FestivalBean {

    /**
     * view_type : festival
     * stat :
     * body : {"action":{"type":"plugin","path":"ShopPlugin://com.xiaomi.shop2.plugin.barcode.BarcodeFragment?pluginId=110","log_code":"app#&page=home"},"img_url":"http://i8.mifile.cn/b2c-mimall-media/7d6e1e87f9698a495bfd58d87e4b3712.png","light_img_url":"http://i8.mifile.cn/b2c-mimall-media/7e2b28a186f7fb399391a025878b79fc.png"}
     * action : {"type":"plugin","path":"ShopPlugin://com.xiaomi.shop2.plugin.barcode.BarcodeFragment?pluginId=110","log_code":"app#&page=home"}
     */

    private String view_type;
    private String stat;
    private BodyBean body;
    private ActionBeanX action;

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

    public BodyBean getBody() {
        return body;
    }

    public void setBody(BodyBean body) {
        this.body = body;
    }

    public ActionBeanX getAction() {
        return action;
    }

    public void setAction(ActionBeanX action) {
        this.action = action;
    }

    public static class BodyBean {
        /**
         * action : {"type":"plugin","path":"ShopPlugin://com.xiaomi.shop2.plugin.barcode.BarcodeFragment?pluginId=110","log_code":"app#&page=home"}
         * img_url : http://i8.mifile.cn/b2c-mimall-media/7d6e1e87f9698a495bfd58d87e4b3712.png
         * light_img_url : http://i8.mifile.cn/b2c-mimall-media/7e2b28a186f7fb399391a025878b79fc.png
         */

        private ActionBean action;
        private String img_url;
        private String light_img_url;

        public ActionBean getAction() {
            return action;
        }

        public void setAction(ActionBean action) {
            this.action = action;
        }

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

        public static class ActionBean {
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

    public static class ActionBeanX {
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
