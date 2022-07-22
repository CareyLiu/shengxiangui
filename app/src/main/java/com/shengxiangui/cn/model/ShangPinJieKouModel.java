package com.shengxiangui.cn.model;

import java.util.List;

public class ShangPinJieKouModel {
    /**
     * msg_code : 0000
     * msg : ok
     * row_num : 1
     * data : [{"selling_price":"4.00","cs_scale_number":"02","cs_wares_number":"0002","cs_wares_name":"3121426242134441872","cs_door_number":"02","membership_price":"3.20"},{"selling_price":"4.00","cs_scale_number":"01","cs_wares_number":"0002","cs_wares_name":"3121426242134441872","cs_door_number":"03","membership_price":"3.20"},{"selling_price":"4.00","cs_scale_number":"02","cs_wares_number":"0002","cs_wares_name":"3121426242134441872","cs_door_number":"03","membership_price":"3.20"},{"selling_price":"3.51","cs_scale_number":"03","cs_wares_number":"0003","cs_wares_name":"52182048","cs_door_number":"01","membership_price":"2.81"},{"selling_price":"3.50","cs_scale_number":"14","cs_wares_number":"0005","cs_wares_name":"26761872","cs_door_number":"01","membership_price":"2.80"},{"selling_price":"15.00","cs_scale_number":"15","cs_wares_number":"0006","cs_wares_name":"42153381","cs_door_number":"01","membership_price":"12.00"},{"selling_price":"15.00","cs_scale_number":"16","cs_wares_number":"0007","cs_wares_name":"42153381","cs_door_number":"01","membership_price":"12.00"},{"selling_price":"3.00","cs_scale_number":"04","cs_wares_number":"0008","cs_wares_name":"34441872","cs_door_number":"01","membership_price":"2.40"},{"selling_price":"3.00","cs_scale_number":"17","cs_wares_number":"0008","cs_wares_name":"34441872","cs_door_number":"01","membership_price":"2.40"},{"selling_price":"3.00","cs_scale_number":"01","cs_wares_number":"0008","cs_wares_name":"34441872","cs_door_number":"02","membership_price":"2.40"},{"selling_price":"5.00","cs_scale_number":"01","cs_wares_number":"0009","cs_wares_name":"46873222278","cs_door_number":"01","membership_price":"4.00"},{"selling_price":"5.00","cs_scale_number":"05","cs_wares_number":"0011","cs_wares_name":"49784450","cs_door_number":"01","membership_price":"4.00"},{"selling_price":"6.00","cs_scale_number":"06","cs_wares_number":"0012","cs_wares_name":"38272591","cs_door_number":"01","membership_price":"4.80"},{"selling_price":"8.00","cs_scale_number":"07","cs_wares_number":"0013","cs_wares_name":"41443823输入的不是汉字！","cs_door_number":"01","membership_price":"6.40"},{"selling_price":"5.00","cs_scale_number":"08","cs_wares_number":"0014","cs_wares_name":"41443823输入的不是汉字！","cs_door_number":"01","membership_price":"4.00"},{"selling_price":"5.00","cs_scale_number":"09","cs_wares_number":"0015","cs_wares_name":"41443823输入的不是汉字！","cs_door_number":"01","membership_price":"4.00"},{"selling_price":"5.00","cs_scale_number":"10","cs_wares_number":"0016","cs_wares_name":"41443823输入的不是汉字！","cs_door_number":"01","membership_price":"4.00"},{"selling_price":"5.00","cs_scale_number":"11","cs_wares_number":"0017","cs_wares_name":"41443823输入的不是汉字！","cs_door_number":"01","membership_price":"4.00"},{"selling_price":"5.00","cs_scale_number":"12","cs_wares_number":"0018","cs_wares_name":"41443823输入的不是汉字！","cs_door_number":"01","membership_price":"4.00"},{"selling_price":"5.00","cs_scale_number":"13","cs_wares_number":"0019","cs_wares_name":"41443823输入的不是汉字！","cs_door_number":"01","membership_price":"4.00"},{"selling_price":"0.00","cs_scale_number":"02","cs_wares_number":"0","cs_wares_name":"462041725435","cs_door_number":"01","membership_price":"0.00"}]
     */

    private String msg_code;
    private String msg;
    private String row_num;
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

    public String getRow_num() {
        return row_num;
    }

    public void setRow_num(String row_num) {
        this.row_num = row_num;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * selling_price : 4.00
         * cs_scale_number : 02
         * cs_wares_number : 0002
         * cs_wares_name : 3121426242134441872
         * cs_door_number : 02
         * membership_price : 3.20
         */

        private String selling_price;
        private String cs_scale_number;
        private String cs_wares_number;
        private String cs_wares_name;
        private String cs_door_number;
        private String membership_price;

        public String getSelling_price() {
            return selling_price;
        }

        public void setSelling_price(String selling_price) {
            this.selling_price = selling_price;
        }

        public String getCs_scale_number() {
            return cs_scale_number;
        }

        public void setCs_scale_number(String cs_scale_number) {
            this.cs_scale_number = cs_scale_number;
        }

        public String getCs_wares_number() {
            return cs_wares_number;
        }

        public void setCs_wares_number(String cs_wares_number) {
            this.cs_wares_number = cs_wares_number;
        }

        public String getCs_wares_name() {
            return cs_wares_name;
        }

        public void setCs_wares_name(String cs_wares_name) {
            this.cs_wares_name = cs_wares_name;
        }

        public String getCs_door_number() {
            return cs_door_number;
        }

        public void setCs_door_number(String cs_door_number) {
            this.cs_door_number = cs_door_number;
        }

        public String getMembership_price() {
            return membership_price;
        }

        public void setMembership_price(String membership_price) {
            this.membership_price = membership_price;
        }
    }
}
