package com.shengxiangui.cn.model;

import java.util.List;

public class PeiZhiBiaoModel {
    /**
     * msg_code : 0000
     * msg : ok
     * row_num : 1
     * data : [{"door_count":"3","door_list":[{"count":"17","cs_door_number":"01"},{"count":"2","cs_door_number":"02"},{"count":"2","cs_door_number":"03"}]}]
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
         * door_count : 3
         * door_list : [{"count":"17","cs_door_number":"01"},{"count":"2","cs_door_number":"02"},{"count":"2","cs_door_number":"03"}]
         */

        private String door_count;
        private List<DoorListBean> door_list;

        public String getDoor_count() {
            return door_count;
        }

        public void setDoor_count(String door_count) {
            this.door_count = door_count;
        }

        public List<DoorListBean> getDoor_list() {
            return door_list;
        }

        public void setDoor_list(List<DoorListBean> door_list) {
            this.door_list = door_list;
        }

        public static class DoorListBean {
            /**
             * count : 17
             * cs_door_number : 01
             */

            private String count;
            private String cs_door_number;

            public String getCount() {
                return count;
            }

            public void setCount(String count) {
                this.count = count;
            }

            public String getCs_door_number() {
                return cs_door_number;
            }

            public void setCs_door_number(String cs_door_number) {
                this.cs_door_number = cs_door_number;
            }
        }
    }
}
