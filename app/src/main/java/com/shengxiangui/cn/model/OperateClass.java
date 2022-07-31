package com.shengxiangui.cn.model;

import java.util.ArrayList;
import java.util.List;

public class OperateClass {


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

    public static String getOperateType(String guiMenDiZhi, String suoDiZhi) {

        YingJianXinXiModel yingJianXinXiModel = getYingJianXinXi(guiMenDiZhi, suoDiZhi);
        String renYuanZhuagnTai = yingJianXinXiModel.renYuanZhuagnTai;
        String menZhuangTai = yingJianXinXiModel.renYuanZhuagnTai;

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

        } else if (renYuanZhuagnTai.equals("3")) {//会员
            if (menZhuangTai.equals("1")) {

                value = "8";
            } else if (menZhuangTai.equals("2")) {

                value = "9";
            }
        }
        return value;
    }

    /**
     * 进行中的状态
     *
     * @param guiMenDiZhi
     * @param suoDiZhi
     * @return
     */
    public static String getKaiMenHeRenYuanZhuangTai(String guiMenDiZhi, String suoDiZhi) {
        String value = "";
        if (getOperateType(guiMenDiZhi, suoDiZhi).equals("1")) {//用户开门
            value = "2";
        } else if (getOperateType(guiMenDiZhi, suoDiZhi).equals("4")) {//补货员

            value = "5";
        } else if (getOperateType(guiMenDiZhi, suoDiZhi).equals("8")) {
            value = "10";
        }
        return value;

    }


    public static class YingJianXinXiModel {
        public int guiMenDiZhi;//柜门地址
        public String renYuanZhuagnTai = null;//人员状态 补货员和用户两种  1用户 2 补货员 3.会员
        public String menZhuangTai = null;//1开门 2关门
        public int suoDiZhi;//锁地址
        public String huiYuanKaHao = null;//会员卡号


        public int mqtt_zhiling;
        public int chengPanHao;//秤盘号
        public int dangQianZhongLiang;//当前重量
        public int jiaoZhunZhongLiang;//校准重量


        public int duShuDi;//度数低
        public int duShuGao;//度数高
        public int dengKaiGuanZhuangTai;//开关状态
        public int xiaoDuZhuangTai;//消毒状态
    }

    public static List<YingJianXinXiModel> operateClasses = new ArrayList<>();

    /**
     * @param guiMenDiZhi      柜门地址
     * @param renYuanZhuagnTai 人员状态
     * @param menZhuangTai     门状态 开或者关
     * @param suoDiZhi         锁地址
     * @param huiYuanKaHao     会员卡号
     */
    public void xinZengJiBenXinXi(String guiMenDiZhi, String suoDiZhi, String renYuanZhuagnTai, String menZhuangTai, String huiYuanKaHao) {

        YingJianXinXiModel yingJianXinXiModel = new YingJianXinXiModel();
        yingJianXinXiModel.guiMenDiZhi = Integer.parseInt(guiMenDiZhi);
        yingJianXinXiModel.huiYuanKaHao = huiYuanKaHao;
        yingJianXinXiModel.renYuanZhuagnTai = renYuanZhuagnTai;
        yingJianXinXiModel.menZhuangTai = menZhuangTai;
        yingJianXinXiModel.suoDiZhi = Integer.parseInt(suoDiZhi);
        operateClasses.add(yingJianXinXiModel);

    }

    /**
     * 更新所有硬件基本信息
     *
     * @param guiMenDiZhi      柜门地址
     * @param renYuanZhuagnTai 人员状态
     * @param menZhuangTai     门状态 开或者关
     * @param suoDiZhi         锁地址
     * @param huiYuanKaHao     会员卡号
     */
    public static YingJianXinXiModel gengXinJiBenXinXi(String guiMenDiZhi, String suoDiZhi, String renYuanZhuagnTai, String menZhuangTai, String huiYuanKaHao) {
        YingJianXinXiModel yingJianXinXiModel=null;
        for (int i = 0; i < operateClasses.size(); i++) {
            yingJianXinXiModel = operateClasses.get(i);

            if (String.valueOf(yingJianXinXiModel.guiMenDiZhi).equals(guiMenDiZhi) && String.valueOf(yingJianXinXiModel.suoDiZhi).equals(suoDiZhi)) {

                if (renYuanZhuagnTai != null) {
                    yingJianXinXiModel.renYuanZhuagnTai = renYuanZhuagnTai;
                }

                if (huiYuanKaHao != null) {
                    yingJianXinXiModel.huiYuanKaHao = huiYuanKaHao;
                }

                if (menZhuangTai != null) {
                    yingJianXinXiModel.menZhuangTai = menZhuangTai;
                }

                break;
            }
        }

        return yingJianXinXiModel;
    }

    /**
     * 更新人员状态
     *
     * @param guiMenDiZhi      柜门地址
     * @param renYuanZhuagnTai 人员状态
     * @param suoDiZhi         锁地址
     */
    public static void gengXinRenYuanZhuangTai(String guiMenDiZhi, String suoDiZhi, String renYuanZhuagnTai) {

        for (int i = 0; i < operateClasses.size(); i++) {

            YingJianXinXiModel yingJianXinXiModel = operateClasses.get(i);

            if (String.valueOf(yingJianXinXiModel.guiMenDiZhi).equals(guiMenDiZhi) && String.valueOf(yingJianXinXiModel.suoDiZhi).equals(suoDiZhi)) {

                yingJianXinXiModel.renYuanZhuagnTai = renYuanZhuagnTai;

                break;
            }
        }

    }

    /**
     * 通过柜门地址获取信息
     *
     * @param guiMenDiZhi 柜门地址
     * @param suoDiZhi    锁地址
     * @return
     */
    public static YingJianXinXiModel getYingJianXinXi(String guiMenDiZhi, String suoDiZhi) {
        YingJianXinXiModel yingJianXinXiModel = null;
        for (int i = 0; i < operateClasses.size(); i++) {
            yingJianXinXiModel = operateClasses.get(i);

            if (String.valueOf(yingJianXinXiModel.guiMenDiZhi).equals(guiMenDiZhi) && String.valueOf(yingJianXinXiModel.suoDiZhi).equals(suoDiZhi)) {
                break;
            }
        }
        return yingJianXinXiModel;
    }


}
