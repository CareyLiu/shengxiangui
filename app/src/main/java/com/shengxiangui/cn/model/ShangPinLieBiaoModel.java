package com.shengxiangui.cn.model;

import java.io.Serializable;
import java.util.List;

public class ShangPinLieBiaoModel implements Serializable {


    private String next;
    private String msg_code;
    private String msg;
    private String row_num;
    private List<DataBean> data;

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

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

    public static class DataBean implements Serializable{
        /**
         * user_to_text : 太好了，物超所值，祝双城神灯科技生意兴隆
         * user_to_time : 2020-07-03 19:38:04
         * of_user_id : 2156
         * user_img_url : https://shop.hljsdkj.com/commons/easyui/images/portrait86x86.png
         * user_to_score : 5.00
         * user_name : 未设置
         */

        public String cs_wares_img_url;
        public String cs_wares_name;
        public String cs_selling_price;
        public String cs_pricing_method;
        public String wares_unit;
        public String cs_w_wares_weight;

    }
}
