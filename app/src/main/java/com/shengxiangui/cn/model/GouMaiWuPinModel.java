package com.shengxiangui.cn.model;

import java.util.List;

public class GouMaiWuPinModel {
    /**
     * msg_code : 0000
     * msg : ok
     * data : [{"data_list":[{"cs_wares_weight":"900","device_ccid":"aaaaaaaaaaaaaaaa10150018","cs_scale_number":"01","cs_wares_wm":"900","cs_total_price":"0.02","cs_selling_price":"0.01","server_id":"8","cs_wares_name":"苹果","cs_wares_unit":"g","cs_per_unit":"500g","cs_wares_id":"1","cs_door_number":"02","cs_wares_img_url":"http://yjn-znjj.oss-cn-hangzhou.aliyuncs.com/20210421090318000001.jpg"},{"cs_wares_weight":"400","device_ccid":"aaaaaaaaaaaaaaaa10150018","cs_scale_number":"02","cs_wares_wm":"1","cs_total_price":"0.01","cs_selling_price":"0.01","server_id":"8","cs_wares_name":"康师傅绿茶","cs_wares_unit":"个","cs_per_unit":"个","cs_wares_id":"2","cs_door_number":"02","cs_wares_img_url":"http://yjn-znjj.oss-cn-hangzhou.aliyuncs.com/20220708133953000001.png"}],"operate_type":"2","device_name":"神灯生鲜柜2","code":"cs_0001","text":"1","type":"7","all_money":"0.03"}]
     */

    private String msg_code;
    private String msg;
    private List<DataBean> data;

    public String getMsg_code() {
        return msg_code;
    }

    public void setMsg_code(String msg_code) {
        this.msg_code = msg_code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * data_list : [{"cs_wares_weight":"900","device_ccid":"aaaaaaaaaaaaaaaa10150018","cs_scale_number":"01","cs_wares_wm":"900","cs_total_price":"0.02","cs_selling_price":"0.01","server_id":"8","cs_wares_name":"苹果","cs_wares_unit":"g","cs_per_unit":"500g","cs_wares_id":"1","cs_door_number":"02","cs_wares_img_url":"http://yjn-znjj.oss-cn-hangzhou.aliyuncs.com/20210421090318000001.jpg"},{"cs_wares_weight":"400","device_ccid":"aaaaaaaaaaaaaaaa10150018","cs_scale_number":"02","cs_wares_wm":"1","cs_total_price":"0.01","cs_selling_price":"0.01","server_id":"8","cs_wares_name":"康师傅绿茶","cs_wares_unit":"个","cs_per_unit":"个","cs_wares_id":"2","cs_door_number":"02","cs_wares_img_url":"http://yjn-znjj.oss-cn-hangzhou.aliyuncs.com/20220708133953000001.png"}]
         * operate_type : 2
         * device_name : 神灯生鲜柜2
         * code : cs_0001
         * text : 1
         * type : 7
         * all_money : 0.03
         */

        private String operate_type;
        private String device_name;
        private String code;
        private String text;
        private String type;
        private String all_money;
        private List<DataListBean> data_list;

        public String getOperate_type() {
            return operate_type;
        }

        public void setOperate_type(String operate_type) {
            this.operate_type = operate_type;
        }

        public String getDevice_name() {
            return device_name;
        }

        public void setDevice_name(String device_name) {
            this.device_name = device_name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getAll_money() {
            return all_money;
        }

        public void setAll_money(String all_money) {
            this.all_money = all_money;
        }

        public List<DataListBean> getData_list() {
            return data_list;
        }

        public void setData_list(List<DataListBean> data_list) {
            this.data_list = data_list;
        }

        public static class DataListBean {
            /**
             * cs_wares_weight : 900
             * device_ccid : aaaaaaaaaaaaaaaa10150018
             * cs_scale_number : 01
             * cs_wares_wm : 900
             * cs_total_price : 0.02
             * cs_selling_price : 0.01
             * server_id : 8
             * cs_wares_name : 苹果
             * cs_wares_unit : g
             * cs_per_unit : 500g
             * cs_wares_id : 1
             * cs_door_number : 02
             * cs_wares_img_url : http://yjn-znjj.oss-cn-hangzhou.aliyuncs.com/20210421090318000001.jpg
             */

            private String cs_wares_weight;
            private String device_ccid;
            private String cs_scale_number;
            private String cs_wares_wm;
            private String cs_total_price;
            private String cs_selling_price;
            private String server_id;
            private String cs_wares_name;
            private String cs_wares_unit;
            private String cs_per_unit;
            private String cs_wares_id;
            private String cs_door_number;
            private String cs_wares_img_url;

            public String getCs_wares_weight() {
                return cs_wares_weight;
            }

            public void setCs_wares_weight(String cs_wares_weight) {
                this.cs_wares_weight = cs_wares_weight;
            }

            public String getDevice_ccid() {
                return device_ccid;
            }

            public void setDevice_ccid(String device_ccid) {
                this.device_ccid = device_ccid;
            }

            public String getCs_scale_number() {
                return cs_scale_number;
            }

            public void setCs_scale_number(String cs_scale_number) {
                this.cs_scale_number = cs_scale_number;
            }

            public String getCs_wares_wm() {
                return cs_wares_wm;
            }

            public void setCs_wares_wm(String cs_wares_wm) {
                this.cs_wares_wm = cs_wares_wm;
            }

            public String getCs_total_price() {
                return cs_total_price;
            }

            public void setCs_total_price(String cs_total_price) {
                this.cs_total_price = cs_total_price;
            }

            public String getCs_selling_price() {
                return cs_selling_price;
            }

            public void setCs_selling_price(String cs_selling_price) {
                this.cs_selling_price = cs_selling_price;
            }

            public String getServer_id() {
                return server_id;
            }

            public void setServer_id(String server_id) {
                this.server_id = server_id;
            }

            public String getCs_wares_name() {
                return cs_wares_name;
            }

            public void setCs_wares_name(String cs_wares_name) {
                this.cs_wares_name = cs_wares_name;
            }

            public String getCs_wares_unit() {
                return cs_wares_unit;
            }

            public void setCs_wares_unit(String cs_wares_unit) {
                this.cs_wares_unit = cs_wares_unit;
            }

            public String getCs_per_unit() {
                return cs_per_unit;
            }

            public void setCs_per_unit(String cs_per_unit) {
                this.cs_per_unit = cs_per_unit;
            }

            public String getCs_wares_id() {
                return cs_wares_id;
            }

            public void setCs_wares_id(String cs_wares_id) {
                this.cs_wares_id = cs_wares_id;
            }

            public String getCs_door_number() {
                return cs_door_number;
            }

            public void setCs_door_number(String cs_door_number) {
                this.cs_door_number = cs_door_number;
            }

            public String getCs_wares_img_url() {
                return cs_wares_img_url;
            }

            public void setCs_wares_img_url(String cs_wares_img_url) {
                this.cs_wares_img_url = cs_wares_img_url;
            }
        }
    }
}
