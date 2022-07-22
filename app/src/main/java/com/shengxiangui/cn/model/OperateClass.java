package com.shengxiangui.cn.model;

public class OperateClass {

    public static String renYuanZhuagnTai="0";//人员状态 补货员和用户两种  1用户 2 补货员
    public static String menZhuangTai;//1开门 2关门

    /**
     * 1.用户：安卓屏开门响应
     * 2.用户：开门时重量上传
     * 3.用户：关门时重量上传
     * 4.补货员：安卓屏开门响应
     * 5.补货员：关门时重量上传
     * 6.补货员：同步某个货道重量（开门时）
     *
     * @return
     */

    public static String getOperateType() {

        String value = "";
        if (renYuanZhuagnTai.equals("1")) {//用户

            if (menZhuangTai.equals("1")) {

                value = "1";
            } else if (menZhuangTai.equals("2")) {

                value = "3";
            }
        } else if (renYuanZhuagnTai.equals("2")) {//补货员
            if (menZhuangTai.equals("1")) {//开门

                value = "4";
            } else if (menZhuangTai.equals("2")) {

                value = "5";
            }

        }
        return value;
    }


    public static String getKaiMenHeRenYuanZhuangTai() {
        String value = "";
        if (getOperateType().equals("1")) {//用户开门

            value = "2";
        } else if (getOperateType().equals("4")) {//补货员开门

            value = "6";
        }
        return value;

    }
}
