package com.shengxiangui.mqtt;

import android.content.Context;

import com.shengxiangui.cn.ConstanceValue;
import com.shengxiangui.cn.Notice;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

import static com.shengxiangui.cn.model.OperateClass.renYuanZhuagnTai;

public class JieXiZhiLing {

    /**
     * if (leixing.equals("M01.")) {// 开门
     * Notice notice = new Notice();
     * notice.type = ConstanceValue.KAIMEN;
     * RxBus.getDefault().sendRx(notice);
     * } else if (leixing.equals("M03")) {// 清零
     * Notice notice = new Notice();
     * notice.type = ConstanceValue.QINGLING;
     * RxBus.getDefault().sendRx(notice);
     * } else if (leixing.equals("M04")) {// 校准
     * Notice notice = new Notice();
     * notice.type = ConstanceValue.JIAOZHUN;
     * RxBus.getDefault().sendRx(notice);
     * } else if (leixing.equals("M05")) {// 查询/同步秤盘重量（单个）
     * Notice notice = new Notice();
     * notice.type = ConstanceValue.CHAXUNDANGE;
     * RxBus.getDefault().sendRx(notice);
     * } else if (leixing.equals("M06")) {// 查询生鲜柜下所有秤盘重量
     * Notice notice = new Notice();
     * notice.type = ConstanceValue.CHAXUNSUOYOU;
     * RxBus.getDefault().sendRx(notice);
     * }
     *
     * @param notice
     */
    public static List<String> chuLiMqttMingLing(Context context, Notice notice) {
        if (notice.type == ConstanceValue.KAIMEN) {

            List<String> list = (List<String>) notice.content;
            list.get(1);//柜门号
            list.get(2);//身份

            renYuanZhuagnTai =  Integer.valueOf(list.get(2)+"")+"";
            YingJianZhiLing.kaiGui(Integer.valueOf(list.get(1)));//开柜


            return list;

        } else if (notice.type == ConstanceValue.QINGLING) {

            List<String> list = (List<String>) notice.content;
            Integer data0 = Integer.valueOf(list.get(1));//门编号
            Integer data1 = Integer.valueOf(list.get(2));//秤盘编号

            YingJianZhiLing.xiaChuanQingLing(data0, data1);//清零功能


            return list;

        } else if (notice.type == ConstanceValue.JIAOZHUN) {

            List<String> list = (List<String>) notice.content;
            Integer data0 = Integer.valueOf(list.get(0));//门编号
            Integer data1 = Integer.valueOf(list.get(1));//秤盘编号
            YingJianZhiLing.xiaChuanQingLing(data0, data1);//校准功能
            return list;

        } else if (notice.type == ConstanceValue.CHAXUNDANGE) {

            List<String> list = (List<String>) notice.content;
            Integer data0 = Integer.valueOf(list.get(0));//门编号
            Integer data1 = Integer.valueOf(list.get(1));//秤盘编号


            YingJianZhiLing.chaXunDanGe(1, data0);
            //访问接口上传实时重量

            //  YingJianZhiLing.xiaChuanQingLing(data0, data1);//校准功能

            return list;

        } else if (notice.type == ConstanceValue.CHAXUNSUOYOU) {
            //访问接口上传实时重量
            List<String> list = (List<String>) notice.content;
            Integer data0 = Integer.valueOf(list.get(0));//门编号
            YingJianZhiLing.chaXunDanGe(2, data0);


            return list;
        } else {
            return (List<String>) notice.content;
        }
    }


}
